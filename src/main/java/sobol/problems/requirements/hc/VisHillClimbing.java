package sobol.problems.requirements.hc;

import java.io.PrintWriter;
import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.problems.requirements.NeighborhoodVisitorResult;
import sobol.problems.requirements.NeighborhoodVisitorStatus;
import sobol.problems.requirements.VisualHeuristics;
import sobol.problems.requirements.model.Project;

/**
 * Hill Climbing searcher with random sampling phase for the next release problem
 *
 */
public class VisHillClimbing extends HillClimbing {

    private int numberSamplingIter;
    private float intervalSize;
    private int minCustomers;
    private int maxCustomers;

    /**
     * Initializes the Hill Climbing search process
     */
    public VisHillClimbing(PrintWriter detailsFile, Project project, int budget, 
            int maxEvaluations, int numberSamplingIter, float intervalSize, Constructor constructor) throws Exception {
        super(detailsFile, project, budget, maxEvaluations, constructor);
        this.numberSamplingIter = numberSamplingIter;
        this.intervalSize = intervalSize;
    }

    /**
     * Runs a neighborhood visit starting from a given solution
     */
    @Override
    protected NeighborhoodVisitorResult visitNeighbors(Solution solution) {
        double startingFitness = evaluate(solution);

        if (evaluations > maxEvaluations) {
            return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);
        }

        if (startingFitness > fitness) {
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

    /**
     * Executes the Hill Climbing search with random restarts
     */
    @Override
    public boolean[] execute() throws Exception {
        int customerCount = project.getCustomerCount();
        random = RandomGeneratorFactory.createForPopulation(customerCount);
        constructor.setRandomGenerator(random);
        this.bestSolution = new boolean[customerCount];
        
        this.minCustomers = executeRandomSampling(numberSamplingIter, project);
        this.maxCustomers = VisualHeuristics.calculateIntervalMax(minCustomers, intervalSize, customerCount);
        
        boolean[] solution = constructor.generateSolutionInInterval(minCustomers, maxCustomers);
        Solution hcrs = new Solution(project);
        hcrs.setAllCustomers(bestSolution);
        double currFitness = evaluate(hcrs);
        
        if(currFitness > this.fitness) {
            copySolution(solution, this.bestSolution);
            this.fitness = currFitness;
        }

        while (localSearch(solution)) {
            this.randomRestartCount++;
            solution = constructor.generateSolutionInInterval(minCustomers, maxCustomers);
        }

        return bestSolution;
    }

    private int executeRandomSampling(int numberSamplingIter, Project project) {
        int numberOfCustomersBest = 0;
        Solution hcrs = new Solution(project);
        Constructor sampConstructor = new RandomConstructor(project);
        sampConstructor.setRandomGenerator(random);                
        
        for (int numElemens = 1; numElemens <= project.getCustomerCount(); numElemens++) {

            for (int deriv = 0; deriv < numberSamplingIter; deriv++) {
                
                boolean[] solution = sampConstructor.generateSolutionWith(numElemens);
                hcrs.setAllCustomers(solution);
                double solFitness = evaluate(hcrs);
                
                if(solFitness > this.fitness) {
                    copySolution(solution, this.bestSolution);
                    this.fitness = solFitness;
                    numberOfCustomersBest = numElemens;
                }
            }
        }

        return numberOfCustomersBest;
    }
}