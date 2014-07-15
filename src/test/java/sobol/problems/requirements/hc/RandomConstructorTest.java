package sobol.problems.requirements.hc;

import java.util.Arrays;
import org.junit.Test;
import sobol.base.random.generic.AbstractRandomGenerator;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import sobol.problems.requirements.model.Project;

public class RandomConstructorTest {

    @Test(expected=RuntimeException.class)
    public void generateSolutionWithoutRandomGenerator() {
        Project project = mock(Project.class);        
        Constructor constr = new RandomConstructor(project);

        constr.generateSolution();
    }
    
    @Test
    public void generateRandomSolution() {
        AbstractRandomGenerator random = mock(AbstractRandomGenerator.class);
        Project project = mock(Project.class);
        Constructor constr = new RandomConstructor(project);
        constr.setRandomGenerator(random);
        
        when(project.getCustomerCount()).thenReturn(5);
        when(random.randDouble()).thenReturn(new double[] {0.6, 0.1, 0.2, 0.7, 0.8});
        boolean[] sol = constr.generateSolution();
        
        assertEquals(5, sol.length);
        assertTrue(Arrays.equals(new boolean[] {true,false,false,true,true}, sol));
    }
    
    @Test
    public void generateSolutionWith3Customers() {
        AbstractRandomGenerator random = mock(AbstractRandomGenerator.class);
        Project project = mock(Project.class);
        Constructor constr = new RandomConstructor(project);
        constr.setRandomGenerator(random);
        
        when(project.getCustomerCount()).thenReturn(5);
        when(random.singleInt(anyInt(), anyInt())).thenReturn(4, 2, 2, 1, 0);
        boolean[] sol = constr.generateSolutionWith(3);
        
        assertEquals(5, sol.length);
        int count = 0;
        for(int i=0; i < sol.length; i++) {
            if(sol[i] == true) {
                count++;
            }
        }
        assertEquals(3, count);
    }    
    
    @Test
    public void generateSolutionWithAtLeast2AndAtMost4Customers() {
        AbstractRandomGenerator random = mock(AbstractRandomGenerator.class);
        Project project = mock(Project.class);
        Constructor constr = new RandomConstructor(project);
        constr.setRandomGenerator(random);
        
        when(project.getCustomerCount()).thenReturn(5);
        when(random.singleInt(anyInt(), anyInt())).thenReturn(3, 4, 2, 2, 1, 0);
        boolean[] sol = constr.generateSolutionInInterval(2, 4);
        
        assertEquals(5, sol.length);
        int count = 0;
        for(int i=0; i < sol.length; i++) {
            if(sol[i] == true) {
                count++;
            }
        }
        assertTrue(count >= 2 && count <=4);
    }     
}
