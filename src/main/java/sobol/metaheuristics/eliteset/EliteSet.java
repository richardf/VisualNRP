package sobol.metaheuristics.eliteset;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents an elite set, ie., a set containing the best
 * solutions found when executing the metaheuristic.
 */
public class EliteSet {
    private static final int DEFAULT_ELITE_SIZE = 10; /* Value used if maximum elite set size is not especified */
    private int maxSize; /* Maximum size of elite set */
    private List<EliteItem> eliteSet;
    
    public EliteSet(int maxSize) {
        
        if(maxSize < 1) {
            throw new RuntimeException("The size of elite set must be greater than 0.");
        }
        
        this.maxSize = maxSize;
        this.eliteSet = new LinkedList<EliteItem>();
    }
    
    public EliteSet() {
        this(DEFAULT_ELITE_SIZE);
    }
    
    public boolean addSolution(int[] solution, double fitness) {
        
        if(shouldAddSolution(fitness)) {
            EliteItem newItem = new EliteItem(solution, fitness);
            
            if(solutionIsNotPresent(newItem)) {
                addSolutionToEliteSet(newItem, fitness);
                return true;
            }
        }
        
        return false;
    }
    
    public void printEliteSet() {
        for (EliteItem eliteItem : eliteSet) {
            System.out.print(eliteItem.fitness + "\t");
            
            for (int elem : eliteItem.solution) {
                System.out.print(elem + " ");
            }
            System.out.println("");
        }
    }
    
    public List<EliteItem> getItens() {
        return eliteSet;
    }
    
    public List<String> getListOfPatterns() {
        List<String> clustersOfSet = new ArrayList<String>();
        
        for (EliteItem eliteItem : eliteSet) {
            clustersOfSet.add(eliteItem.getItemAsString());
        }
        
        return clustersOfSet;
    }
    
    private boolean shouldAddSolution(double fitness) {
        return eliteSet.size() == maxSize ? fitness > eliteSet.get(0).fitness : true;
    }
    
    private boolean isEliteSetFull() {
        return eliteSet.size() == maxSize;
    }
    
    private int getInsertPosition(double fitnessNew) {
        int idx = 0;
        
        for (; idx < eliteSet.size(); idx++) {
            if(fitnessNew < eliteSet.get(idx).fitness) {
                break;
            }
        }
        
        return idx;
    }
    
    private boolean solutionIsNotPresent(EliteItem newSolution) {
        Iterator<EliteItem> it = eliteSet.iterator();
        boolean isPresent = false;
        
        while(!isPresent && it.hasNext()) {
            EliteItem solution = it.next();
            
            if(solution.fitness.equals(newSolution.fitness)) {
                isPresent = solution.equals(newSolution);
            }
            else if(solution.fitness > newSolution.fitness) {
                break;
            }
        }
        
        return !isPresent;
    }

    private void addSolutionToEliteSet(EliteItem newItem, double fitness) {
        if(isEliteSetFull()) {
            eliteSet.remove(0);
            eliteSet.add(getInsertPosition(fitness), newItem);
        }
        else {
            eliteSet.add(getInsertPosition(fitness), newItem);
        }
    }
}
