package sobol.problems.requirements.hc;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.problems.requirements.model.Project;

/**
 * Hill Climbing searcher for the next release problem
 *
 */
public class VisHillClimbing extends HillClimbing {

    private int minCustomers;
    private int maxCustomers;

    /**
     * Initializes the Hill Climbing search process
     */
    public VisHillClimbing(PrintWriter detailsFile, Project project, int budget, int maxEvaluations, int minCustomers, int maxCustomers) throws Exception {
        super(detailsFile, project, budget, maxEvaluations);
        this.minCustomers = minCustomers;
        this.maxCustomers = maxCustomers;
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

        for (int i = 0; i < len; i++) {
            int customerI = selectionOrder[i];

            solution.flipCustomer(customerI);
            int numberOfCustomers = numberOfCustomersServedBySolution(solution.getSolution());

            if(numberOfCustomers >= minCustomers && numberOfCustomers <= maxCustomers) {
                double neighborFitness = evaluate(solution);

                if (evaluations > maxEvaluations) {
                    return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);
                }

                if (neighborFitness > startingFitness) {
                    return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR, neighborFitness);
                }
            }

            solution.flipCustomer(customerI);
        }

        return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.NO_BETTER_NEIGHBOR);
    }

    /**
     * Executes the Hill Climbing search with random restarts
     */
    @Override
    public boolean[] execute() throws Exception {
        int customerCount = project.getCustomerCount();
        AbstractRandomGenerator random = RandomGeneratorFactory.createForPopulation(customerCount);

        this.bestSolution = createRandomSolutionInInterval(minCustomers, maxCustomers, random);
        Solution hcrs = new Solution(project);
        hcrs.setAllCustomers(bestSolution);
        this.fitness = evaluate(hcrs);

        boolean[] solution = new boolean[customerCount];
        copySolution(bestSolution, solution);

        while (localSearch(solution)) {
            this.randomRestartCount++;
            solution = createRandomSolutionInInterval(minCustomers, maxCustomers, random);
        }

        return bestSolution;
    }

    private int numberOfCustomersServedBySolution(boolean[] solution) {
        int count = 0;
        for (int i = 0; i < solution.length; i++) {
            if (solution[i] == true) {
                count++;
            }
        }
        return count;
    }
    
    private boolean[] createRandomSolutionInInterval(int minCustomers, int maxCustomers, AbstractRandomGenerator random) {
        int customerCount = project.getCustomerCount();
        boolean[] solution = new boolean[customerCount];
        Arrays.fill(solution, false);
        List<Integer> listOfPossibilities = generateListOfSize(customerCount);
        int numberOfCustomers = random.singleInt(minCustomers, maxCustomers+1);
        
        for (int i = 1; i <= numberOfCustomers; i++) {
            int rand = random.singleInt(0, listOfPossibilities.size());
            int position = listOfPossibilities.remove(rand);

            solution[position] = true;
        }

        return solution;
    }

    private List<Integer> generateListOfSize(int size) {
        List<Integer> list = new ArrayList<Integer>();

        for (int i=0; i < size; i++) {
            list.add(i);
        }

        return list;
    }
}