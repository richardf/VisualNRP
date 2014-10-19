package sobol.problems.requirements;

import org.junit.Test;
import static org.junit.Assert.*;

public class VisUpperLimitCalculatorTest {

    private VisUpperLimitCalculator calculator;

    
    @Test(expected = RuntimeException.class)
    public void getUpperWithOneLimitShouldThrowRunTimeExceptionIfCalledTwice() {
        calculator = new VisUpperLimitCalculator(10, 100, 0.1f);
        calculator.getUpper();
        calculator.getUpper();
    }
    
    @Test
    public void getUpperWithOneLimitShouldReturn20() {
        calculator = new VisUpperLimitCalculator(10, 100, 0.1f);
        assertEquals(20, calculator.getUpper());
    }

    @Test
    public void getUpperWithOneLimitShouldReturn20AndThen30() {
        calculator = new VisUpperLimitCalculator(10, 100, 0.1f, 0.2f);
        assertEquals(20, calculator.getUpper());
        assertEquals(30, calculator.getUpper());
    }    
    
    @Test
    public void getUpperWithOneLimitShouldReturn20AndThen() {
        calculator = new VisUpperLimitCalculator(10, 100, 0.1f, 0.2f);
        calculator.getUpper();
        calculator.addFitness(100);
        calculator.addFitness(150);
        calculator.getUpper();
        calculator.addFitness(200);
        calculator.addFitness(150);
        
        assertEquals(30, calculator.getBestUpper());
    }    
    
}