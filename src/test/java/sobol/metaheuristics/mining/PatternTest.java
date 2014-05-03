package sobol.metaheuristics.mining;

import org.junit.Test;
import static org.junit.Assert.*;

public class PatternTest {

    @Test
    public void compareTwoDifferentPatterns() {
        Pattern p1 = new Pattern("1 2 3");
        Pattern p2 = new Pattern("2 3 4");
        
        assertFalse(p1.equals(p2));
    }
    
    @Test
    public void compareTwoEqualPatterns() {
        Pattern p1 = new Pattern("1 2 3");
        Pattern p2 = new Pattern("1 2 3");
        
        assertTrue(p1.equals(p2));
    }

    @Test
    public void createPatternWithDefaultSeparator() {
        Pattern p1 = new Pattern("1 2 3");
        
        assertArrayEquals(new int[] {1, 2, 3}, p1.pattern);
    }
    
    @Test
    public void createPatternWithCustomSeparator() {
        Pattern p1 = new Pattern("1:2:3", ":");
        
        assertArrayEquals(new int[] {1, 2, 3}, p1.pattern);
    }
}