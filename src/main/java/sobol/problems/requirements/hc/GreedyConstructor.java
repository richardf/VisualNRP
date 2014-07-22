/*
 * Greedy constructor that uses the relation Profit / Cost for selecting customers
 * to a solution. Customers high higher profit/cost ratio are more likely selected.
 */
package sobol.problems.requirements.hc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.experiments.utils.WeightedSelector;
import sobol.problems.requirements.model.Project;

public class GreedyConstructor implements Constructor {

    private AbstractRandomGenerator random;
    private Project project;
    private Map<Integer, Integer> profitLossRatios;
    
    public GreedyConstructor(Project project) {
        this.project = project;
        profitLossRatios = calculateProfitLossRatios();
    }
    
    private Map<Integer, Integer> calculateProfitLossRatios() {
        int numberOfCustomers = project.getCustomerCount();
        boolean[] sol = new boolean[numberOfCustomers];
        Map<Integer, Integer> profitLoss = new HashMap<Integer, Integer>(project.getCustomerCount());
        Arrays.fill(sol, false);

        for (int customer = 0; customer < numberOfCustomers; customer++) {
            sol[customer] = true;
            double profit = project.getCustomerProfit(customer);
            int cost = project.calculateCost(sol);
            sol[customer] = false;
            profitLoss.put(customer, (int)((profit / cost) * 1000));
        }      
        
        return profitLoss;
    }
    
    public boolean[] generateSolution() {
        
        int customerCount = project.getCustomerCount();
        int numberOfCustomers = random.singleInt(1, customerCount + 1);
        return generateSolutionWith(numberOfCustomers);
    }

    public boolean[] generateSolutionWith(int numberOfCustomers) {
        checkRandomGenerator();
        
        int customerCount = project.getCustomerCount();
        boolean[] solution = new boolean[customerCount];
        Arrays.fill(solution, false);
        List<Integer> customers = new ArrayList<Integer>(profitLossRatios.keySet());

        for (int i = 0; i < numberOfCustomers; i++) {
            
            int[] weights = getWeightsFor(customers);
            WeightedSelector ws = new WeightedSelector(weights, random);
            int selected = ws.getWeightedRandom();
            selected = customers.remove(selected);
            
            solution[selected] = true;
        }
        
        return solution;
    }

    public boolean[] generateSolutionInInterval(int minCustomers, int maxCustomers) {
        int numberOfCustomers = random.singleInt(minCustomers, maxCustomers + 1);
        return generateSolutionWith(numberOfCustomers);
    }

    public void setRandomGenerator(AbstractRandomGenerator random) {
        this.random = random;
    }
    
    private void checkRandomGenerator() {
        if(random == null) {
            throw new RuntimeException("Set the random generator before using this class.");
        }
    }    

    private int[] getWeightsFor(List<Integer> customers) {
        int[] weights = new int[customers.size()];
        int idx = 0;
        
        for(Integer cust : customers) {
            weights[idx] = profitLossRatios.get(cust);
            idx++;
        }
        return weights;
    }
    
    private List<Integer> generateListOfSize(int size) {
        List<Integer> list = new ArrayList<Integer>(size);

        for (int i = 0; i < size; i++) {
            list.add(i);
        }

        return list;
    }     
}
