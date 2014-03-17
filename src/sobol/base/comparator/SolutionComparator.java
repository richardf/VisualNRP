/**
 * SolutionComparator.java
 * 
 * @author Juan J. Durillo
 * @version 1.0
 */
package sobol.base.comparator;

import java.util.Comparator;
import sobol.base.solution.Solution;

/**
 * This class implements a <code>Comparator</code> (a method for comparing
 * <code>Solution</code> objects) based on the values of the variables.
 */
public class SolutionComparator implements Comparator<Solution>
{
	private static final double EPSILON = 1e-10;

	public int compare(Solution o1, Solution o2)
	{
		Solution solution1 = o1, solution2 = o2;

		if (solution1.numberOfVariables() != solution2.numberOfVariables())
			return -1;
		
		if (solution1.calculateDistance(solution2) < EPSILON)
			return 0;
		
		return -1;
	}
}