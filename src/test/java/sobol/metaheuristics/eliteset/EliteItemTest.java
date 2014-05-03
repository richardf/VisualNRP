package sobol.metaheuristics.eliteset;

import org.junit.Test;
import static org.junit.Assert.*;

public class EliteItemTest {

    @Test
    public void testThatTwoEliteItensAreDifferent() {
        EliteItem item1 = new EliteItem(new int[] {1, 3, 4}, 120.4);
        EliteItem item2 = new EliteItem(new int[] {1, 2, 4}, 120.4);
        
        assertFalse(item1.equals(item2));
    }
    
    @Test
    public void testThatTwoEliteItensAreEqual() {
        EliteItem item1 = new EliteItem(new int[] {1, 3, 5}, 120.4);
        EliteItem item2 = new EliteItem(new int[] {1, 3, 5}, 120.4);
        
        assertTrue(item1.equals(item2));
    }
    
    @Test
    public void getEliteItemAsString() {
        EliteItem item = new EliteItem(new int[] {1, 3, 7, 12}, 120.4);
        String strItem = item.getItemAsString();
        assertTrue(strItem.equals("1 3 7 12"));
    }
}