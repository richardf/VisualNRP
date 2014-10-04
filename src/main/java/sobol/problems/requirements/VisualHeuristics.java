package sobol.problems.requirements;

public class VisualHeuristics {

    static public int calculateIntervalMax(int numberCustomersBest, float intervalSize, int customerCount) {
        int size = Math.round(customerCount * intervalSize);
        int maxNCustomers = numberCustomersBest + size;
        
        if(maxNCustomers > customerCount) {
            maxNCustomers = customerCount;
        }
        return maxNCustomers;
    }    
    
    static public int numberOfCustomersServedBySolution(boolean[] solution) {
        int count = 0;
        for (int i = 0; i < solution.length; i++) {
            if (solution[i] == true) {
                count++;
            }
        }
        return count;
    }
}
