package sobol.problems.requirements.hc;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import sobol.base.random.generic.AbstractRandomGenerator;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.pseudo.PseudoRandomGeneratorFactory;
import sobol.problems.requirements.model.Project;

public class GreedyConstructorTest {
    
    @Test(expected=RuntimeException.class)
    public void generateSolutionWithoutRandomGenerator() {
        Project project = mock(Project.class);        
        Constructor constr = new GreedyConstructor(project);

        constr.generateSolution();
    }    
    
    @Test
    public void generateSolutionWith2CustomersShouldReturnSolutionWithBiggerRatio() {
        AbstractRandomGenerator random = mock(AbstractRandomGenerator.class);
        Project project = mock(Project.class);
        when(project.getCustomerCount()).thenReturn(2);
        when(project.getCustomerProfit(anyInt())).thenReturn(100, 1);
        when(project.calculateCost(any(boolean[].class))).thenReturn(1, 10);
        when(random.singleInt(anyInt(), anyInt())).thenReturn(1, 0);

        Constructor constr = new GreedyConstructor(project);
        constr.setRandomGenerator(random);  
        
        boolean[] sol = constr.generateSolution();
        assertEquals(2, sol.length);
        assertTrue(sol[0]);
        assertFalse(sol[1]);
    }
    
    @Test
    public void generateSolutionWith2CustomersShouldReturnSolutionWithLowestRatioWithSmallProbability() {
        AbstractRandomGenerator random = mock(AbstractRandomGenerator.class);
        Project project = mock(Project.class);
        when(project.getCustomerCount()).thenReturn(2);
        when(project.getCustomerProfit(anyInt())).thenReturn(100, 1);
        when(project.calculateCost(any(boolean[].class))).thenReturn(1, 10);
        when(random.singleInt(anyInt(), anyInt())).thenReturn(1, 100010);

        Constructor constr = new GreedyConstructor(project);
        constr.setRandomGenerator(random);  
        
        boolean[] sol = constr.generateSolution();
        assertEquals(2, sol.length);
        assertFalse(sol[0]);
        assertTrue(sol[1]);
    }
    
    @Test
    public void generateSolutionShouldRespectProfitLossRatio() {
        RandomGeneratorFactory.setRandomFactoryForPopulation(new PseudoRandomGeneratorFactory());
        AbstractRandomGenerator random = RandomGeneratorFactory.createForPopulation(4);
        Project project = mock(Project.class);
        when(project.getCustomerCount()).thenReturn(4);
        when(project.getCustomerProfit(anyInt())).thenReturn(100, 1, 10, 10);
        when(project.calculateCost(any(boolean[].class))).thenReturn(1, 10, 10, 1);

        Constructor constr = new GreedyConstructor(project);
        constr.setRandomGenerator(random);

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < 4; i++) {
            map.put(i, 0);
        }
        
        for(int i = 0; i < 1000; i++) {
            boolean[] sol = constr.generateSolution();
            for(int j=0; j<sol.length; j++) {
                if(sol[j] == true) {
                    map.put(j, map.get(j)+1);
                }
            }
        }

        assertTrue(map.get(0) > map.get(1));
        assertTrue(map.get(0) > map.get(2));
    }
}
