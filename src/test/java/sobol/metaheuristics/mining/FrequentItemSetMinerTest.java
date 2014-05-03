package sobol.metaheuristics.mining;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class FrequentItemSetMinerTest {

    @Test
    public void doMiningWithoutPatternsShouldReturnEmptyList() throws IOException {
        List<String> data = new ArrayList<String>();
        data.add("0 1 2 3");
        
        FrequentItemSetMiner miner = new FrequentItemSetMiner();
        List<Pattern> patterns = miner.doMining(data);
        
        assertEquals(0, patterns.size());
    }
    
    @Test
    public void doMiningWith1PatternShouldReturnList() throws IOException {
        List<String> data = new ArrayList<String>();
        data.add("0 1 2 3");
        data.add("1 2 3");
        
        FrequentItemSetMiner miner = new FrequentItemSetMiner();
        List<Pattern> patterns = miner.doMining(data);
        
        assertEquals(1, patterns.size());
        assertTrue(patterns.contains(new Pattern("1 2 3")));
    }

    @Test
    public void doMiningWithPatternsShouldReturnTop2List() throws IOException {
        List<String> data = new ArrayList<String>();
        data.add("0 1 2 3");
        data.add("1 2 3");
        data.add("0 1 3");
        data.add("1 2");
        
        FrequentItemSetMiner miner = new FrequentItemSetMiner(2, 2, " ");
        List<Pattern> patterns = miner.doMining(data);
        
        assertEquals(2, patterns.size());
        assertEquals(new Pattern("1 2"), patterns.get(0));
        assertEquals(new Pattern("1 3"), patterns.get(1));
    }
    
   @Test
    public void doMiningWithPatternsShouldIgnorePatternWith1Element() throws IOException {
        List<String> data = new ArrayList<String>();
        data.add("1");
        data.add("1 2");
        
        FrequentItemSetMiner miner = new FrequentItemSetMiner();
        List<Pattern> patterns = miner.doMining(data);

        assertEquals(0, patterns.size());
    }
}