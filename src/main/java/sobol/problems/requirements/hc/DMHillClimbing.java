package sobol.problems.requirements.hc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.metaheuristics.eliteset.EliteSet;
import sobol.metaheuristics.mining.FrequentItemSetMiner;
import sobol.metaheuristics.mining.Pattern;
import sobol.problems.requirements.model.Project;

/**
 * Hill Climbing searcher with data mining for the next release problem
 *
 */
public class DMHillClimbing extends HillClimbing {
    FrequentItemSetMiner miner;

    /**
     * Initializes the Hill Climbing search process
     */
    public DMHillClimbing(PrintWriter detailsFile, Project project, int budget, int maxEvaluations) throws Exception {
        super(detailsFile, project, budget, maxEvaluations);
        miner = new FrequentItemSetMiner();
    }

    /**
     * Executes the Hill Climbing search with random restarts
     */
    @Override
    public boolean[] execute() throws Exception {
        
        int customerCount = project.getCustomerCount();
        AbstractRandomGenerator random = RandomGeneratorFactory.createForPopulation(customerCount);
        EliteSet eliteset = new EliteSet();
        boolean miningExecuted = false;
        int miningPoint = maxEvaluations / 2;
        int currPattern = 0;
        List<Pattern> patterns = new ArrayList<Pattern>();
        
        
        this.bestSolution = createRandomSolution(random);
        Solution hcrs = new Solution(project);
        hcrs.setAllCustomers(bestSolution);
        this.fitness = evaluate(hcrs);

        boolean[] solution = new boolean[customerCount];
        copySolution(bestSolution, solution);

        while (localSearch(solution)) {
            this.randomRestartCount++;
            
            if(evaluations < miningPoint) {
                eliteset.addSolution(getCustomersInSolution(solution), this.fitness);
                solution = createRandomSolution(random);
            }
            else {
                if(!miningExecuted) {
                    patterns = doFrequentItemsetMining(eliteset.getListOfPatterns());
		    if(patterns.isEmpty()) {
                        System.out.println("WARN: No patterns found.");
		    }
                    currPattern = 0;
                    miningExecuted = true;
                }
                
                if(! patterns.isEmpty()) {
                    solution = generateSolutionUsingPattern(patterns.get(currPattern), random);
                    currPattern = ++currPattern % patterns.size();
                } else {
                    //no patterns found - fallback to normal solution generation
                    solution = createRandomSolution(random);
                }
            }            
        }

        return bestSolution;
    }
    
    private int[] getCustomersInSolution(boolean[] solution) {
        List<Integer> custIds = new ArrayList<Integer>();
        
        for(int i=0; i < solution.length; i++) {
            if(solution[i] == true) {
                custIds.add(i);
            }
        }
       
        int[] arrIds = new int[custIds.size()];
        for(int i=0; i < custIds.size(); i++) {
            arrIds[i] = custIds.get(i);
        }
        
        return arrIds;
    }
    
    private List<Pattern> doFrequentItemsetMining(List<String> listOfClusters) throws IOException {
        return miner.doMining(listOfClusters);
    }

    private boolean[] generateSolutionUsingPattern(Pattern pattern, AbstractRandomGenerator random) {
        boolean[] solution = createRandomSolution(random);
        
        for (int idx : pattern.pattern) {
            solution[idx] = true;
        }
        
        return solution;
    }    
}