package sobol.metaheuristics.eliteset;

import org.junit.Test;
import static org.junit.Assert.*;

public class EliteSetTest {
    
    @Test(expected = RuntimeException.class)
    public void elitesetWithNegativeSizeShouldThrowException() {
        new EliteSet(-1);
    }
    
    @Test(expected = RuntimeException.class)
    public void elitesetWithSizeZeroShouldThrowException() {
        new EliteSet(0);
    }
    
    @Test
    public void addSolutionInEmptyEliteSetShouldReturnSetWith1Element() {
        EliteSet eliteSet = new EliteSet();
        eliteSet.addSolution(new int[] {0, 0, 1, 1}, 100.5);
        assertEquals(1, eliteSet.getItens().size());
    }
    
    @Test
    public void addWorseSolutionInFullEliteSetShouldNotChangeSet() {
        EliteSet eliteSet = new EliteSet(2);
        eliteSet.addSolution(new int[] {1, 2, 3}, 100.5);
        eliteSet.addSolution(new int[] {2, 4, 6}, 50.0);
        eliteSet.addSolution(new int[] {3, 4, 8}, 10.0);
        
        assertEquals(2, eliteSet.getItens().size());
        assertEquals(50.0, eliteSet.getItens().get(0).fitness, 0.01);
        assertEquals(100.5, eliteSet.getItens().get(1).fitness, 0.01);
        assertArrayEquals(new int[] {2, 4, 6}, eliteSet.getItens().get(0).solution);
        assertArrayEquals(new int[] {1, 2, 3}, eliteSet.getItens().get(1).solution);
    }
    
    @Test
    public void addBetterSolutionInFullEliteSetShouldChangeSet() {
        EliteSet eliteSet = new EliteSet(2);
        eliteSet.addSolution(new int[] {1, 2, 3}, 100.5);
        eliteSet.addSolution(new int[] {2, 4, 6}, 50.0);
        eliteSet.addSolution(new int[] {3, 10, 11}, 500.1);
        
        assertEquals(2, eliteSet.getItens().size());
        assertEquals(100.5, eliteSet.getItens().get(0).fitness, 0.01);
        assertEquals(500.1, eliteSet.getItens().get(1).fitness, 0.01);
        assertArrayEquals(new int[] {1, 2, 3}, eliteSet.getItens().get(0).solution);
        assertArrayEquals(new int[] {3, 10, 11}, eliteSet.getItens().get(1).solution);
    }
    
    @Test
    public void addSameSolutionTwiceShouldNotChangeSet() {
        EliteSet eliteSet = new EliteSet(2);
        eliteSet.addSolution(new int[] {0, 1, 0}, 100.5);
        eliteSet.addSolution(new int[] {0, 1, 0}, 100.5);

        assertEquals(1, eliteSet.getItens().size());
        assertEquals(100.5,eliteSet.getItens().get(0).fitness, 0.01);
        assertArrayEquals(new int[] {0, 1, 0}, eliteSet.getItens().get(0).solution);
    }    
}