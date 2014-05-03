package sobol.metaheuristics.eliteset;

import java.util.Arrays;

/**
 * This class represents an solution in elite set.
 */
public class EliteItem {
    public int[] solution;
    public Double fitness;

    public EliteItem(int[] solution, double fitness) {
        this.solution = solution;
        this.fitness = fitness;
    }

    public String getItemAsString() {
        StringBuilder builder = new StringBuilder();
        
        for (int idx = 0; idx < solution.length; idx++) {
            builder.append(solution[idx]);
            builder.append(" ");
        }
        
        return builder.toString().trim();
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Arrays.hashCode(this.solution);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EliteItem other = (EliteItem) obj;
        if (!Arrays.equals(this.solution, other.solution)) {
            return false;
        }
        return true;
    }
}
