package sobol.problems.requirements;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.problems.requirements.hc.Constructor;
import sobol.problems.requirements.hc.Solution;
import sobol.problems.requirements.model.Project;

public class VisIteratedLocalSearch extends IteratedLocalSearch {

    private double bestFitness;
    private boolean[] bestSol;
    private int minCustomers;
    private int maxCustomers;
    private int numberSamplingIter;
    private float intervalSize;    
    
    public VisIteratedLocalSearch(PrintWriter detailsFile, Project project, int budget, int maxEvaluations, int numberSamplingIter, float intervalSize, Constructor constructor) throws Exception {
        super(detailsFile, project, budget, maxEvaluations, constructor);
        this.numberSamplingIter = numberSamplingIter;
        this.intervalSize = intervalSize;        
    }

    @Override
    public boolean[] execute() throws Exception {
        int customerCount = project.getCustomerCount();
        AbstractRandomGenerator random = RandomGeneratorFactory.createForPopulation(customerCount);
        constructor.setRandomGenerator(random);
        bestSol = new boolean[customerCount];

        this.minCustomers = executeRandomSampling(numberSamplingIter, project);
        this.maxCustomers = VisualHeuristics.calculateIntervalMax(minCustomers, intervalSize, customerCount);
        
        this.currSolution = constructor.generateSolutionInInterval(minCustomers, maxCustomers);
        Solution hcrs = new Solution(project);
        hcrs.setAllCustomers(currSolution);
        this.currFitness = evaluate(hcrs);

        localSearch(currSolution);
        copySolution(currSolution, bestSol);
        bestFitness = this.currFitness;
        
        while (evaluations < maxEvaluations) {
            this.randomRestartCount++;
            boolean[] perturbedSolution = perturbSolution(bestSol, random, customerCount);
            localSearch(perturbedSolution);
            
            if(shouldAccept(currFitness, bestFitness)) {
                copySolution(currSolution, bestSol);
                bestFitness = this.currFitness;
            }
        }

        return bestSol;
    }    
    
    @Override
    protected boolean[] perturbSolution(boolean[] solution, AbstractRandomGenerator random, int customerCount) {
        boolean[] newSolution = Arrays.copyOf(solution, customerCount);
        int amount = Math.round(0.05f * customerCount);
        
        List<Integer> satisfied = new ArrayList<Integer>();
        List<Integer> notSatisfied = new ArrayList<Integer>();
        for(int i = 0; i < solution.length; i++) {
            if (solution[i] == true) {
                satisfied.add(i);
            } else {
                notSatisfied.add(i);
            }
        }
        
        for(int i = 0; i < amount; i++) {
            boolean isAddOperation = false;
            
            if(canAddAndRemoveCustomer(satisfied)) {
                isAddOperation = random.singleDouble() <= 0.5 ? true : false;
            } else if (canAddCustomer(satisfied)) {
                isAddOperation = true;
            }
            
            int customer;
            if(isAddOperation) {
                int rand = random.singleInt(0, notSatisfied.size());
                customer = notSatisfied.remove(rand);
                satisfied.add(rand);
            } else {
                int rand = random.singleInt(0, satisfied.size());
                customer = satisfied.remove(rand);
                notSatisfied.add(rand);
            }
            
            newSolution[customer] = !newSolution[customer];
        }
        
        return newSolution;
    }    
    
    private int executeRandomSampling(int numberSamplingIter, Project project) {
        int numberOfCustomersBest = 0;
        Solution hcrs = new Solution(project);
                
        for (int numElemens = 1; numElemens <= project.getCustomerCount(); numElemens++) {

            for (int deriv = 0; deriv < numberSamplingIter; deriv++) {
                
                boolean[] solution = constructor.generateSolutionWith(numElemens);
                hcrs.setAllCustomers(solution);
                double solFitness = evaluate(hcrs);
                
                if(solFitness > this.bestFitness) {
                    copySolution(solution, this.bestSol);
                    this.bestFitness = solFitness;
                    numberOfCustomersBest = numElemens;
                }
            }
        }

        return numberOfCustomersBest;
    }    
    
    @Override
    protected NeighborhoodVisitorResult visitNeighbors(Solution solution) {
        double startingFitness = evaluate(solution);

        if (evaluations > maxEvaluations) {
            return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);
        }

        if (startingFitness > currFitness) {
            return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR, startingFitness);
        }

        int len = project.getCustomerCount();
        boolean[] solutionAsArray = new boolean[len];
        copySolution(solution.getSolution(), solutionAsArray);
        
        for (int i = 0; i < len; i++) {
            int customerI = selectionOrder[i];

            solutionAsArray[customerI] = (solutionAsArray[customerI] == true) ? false : true;
            int numberOfCustomers = VisualHeuristics.numberOfCustomersServedBySolution(solutionAsArray);

            if(numberOfCustomers >= minCustomers && numberOfCustomers <= maxCustomers) {

                solution.flipCustomer(customerI);
                double neighborFitness = evaluate(solution);

                if (evaluations > maxEvaluations) {
                    return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);
                }

                if (neighborFitness > startingFitness) {
                    return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR, neighborFitness);
                }
                solution.flipCustomer(customerI);
            }

            solutionAsArray[customerI] = (solutionAsArray[customerI] == true) ? false : true;
        }

        return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.NO_BETTER_NEIGHBOR);
    }    

    private boolean canAddAndRemoveCustomer(List<Integer> satisfied) {
        return canAddCustomer(satisfied) && (satisfied.size()-1) >= this.minCustomers;
    }

    private boolean canAddCustomer(List<Integer> satisfied) {
        return (satisfied.size()+1) <= this.maxCustomers;
    }
}
