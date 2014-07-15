/*
 * Constructor that generates a random solution.
 */
package sobol.problems.requirements.hc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.problems.requirements.model.Project;

public class RandomConstructor implements Constructor {

    private AbstractRandomGenerator random;
    private Project project;
    
    public RandomConstructor(Project project) {
        this.project = project;
    }
    
    public void setRandomGenerator(AbstractRandomGenerator random) {
        this.random = random;
    }
    
    public boolean[] generateSolution() {
        checkRandomGenerator();
        
        int customerCount = project.getCustomerCount();
        boolean[] solution = new boolean[customerCount];
        double[] sample = random.randDouble();

        for (int i = 0; i < customerCount; i++) {
            solution[i] = (sample[i] >= 0.5);
        }

        return solution;
    }

    public boolean[] generateSolutionWith(int numberOfCustomers) {
        checkRandomGenerator();
        
        int customerCount = project.getCustomerCount();
        boolean[] solution = new boolean[customerCount];
        Arrays.fill(solution, false);
        List<Integer> listOfPossibilities = generateListOfSize(customerCount);

        for (int i = 1; i <= numberOfCustomers; i++) {
            int rand = random.singleInt(0, listOfPossibilities.size());
            int position = listOfPossibilities.remove(rand);

            solution[position] = true;
        }

        return solution;
    }
    
    public boolean[] generateSolutionInInterval(int minCustomers, int maxCustomers) {
        checkRandomGenerator();
        
        int numberOfCustomers = random.singleInt(minCustomers, maxCustomers + 1);
        return generateSolutionWith(numberOfCustomers);
    }   
    
    private List<Integer> generateListOfSize(int size) {
        List<Integer> list = new ArrayList<Integer>(size);

        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        return list;
    } 
    
    private void checkRandomGenerator() {
        if(random == null) {
            throw new RuntimeException("Set the random generator before using this class.");
        }
    }
}

