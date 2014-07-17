package sobol.experiments.utils;

import java.util.Arrays;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Test;
import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.random.pseudo.PseudoRandomGeneratorFactory;


public class WeightedSelectorTest {
    
    @Test
    public void GetWeightedRandomWith1Element() {
        AbstractRandomGenerator random = mock(AbstractRandomGenerator.class);
        int[] weights = new int[] {1};
        when(random.singleInt(anyInt(), anyInt())).thenReturn(0);
        
        WeightedSelector ws = new WeightedSelector(weights, random);
        
        assertEquals(0, ws.getWeightedRandom());
    }

    @Test
    public void GetWeightedRandomWith2Elements() {
        AbstractRandomGenerator random = mock(AbstractRandomGenerator.class);
        int[] weights = new int[] {1, 9};
        when(random.singleInt(anyInt(), anyInt())).thenReturn(1, 10);
        
        WeightedSelector ws = new WeightedSelector(weights, random);
        
        assertEquals(0, ws.getWeightedRandom());
        assertEquals(1, ws.getWeightedRandom());
    }
    
    @Test
    public void GetWeightedRandomWith5ElementsAndRealRandomGenerator() {
        int size = 5;
        RandomGeneratorFactory.setRandomFactoryForPopulation(new PseudoRandomGeneratorFactory());
        AbstractRandomGenerator random = RandomGeneratorFactory.createForPopulation(size);        
        int[] weights = new int[size];
        int[] values = new int[size];
        int[] counter = new int[size];
        Arrays.fill(counter, 0);
        
        //weight for elem i << i+1
        for(int i = 0; i < size; i++) {
            values[i] =  i;
            weights[i] = (i+1)*10;
        }
        
        WeightedSelector ws = new WeightedSelector(weights, random);
        
        for(int i = 0; i < 500; i++) {
            int pos = ws.getWeightedRandom();
            counter[pos]++;
        }

        assertTrue(counter[0] < counter[size-1]);
    }    
}
