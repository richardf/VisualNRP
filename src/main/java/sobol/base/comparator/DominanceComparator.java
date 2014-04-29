/**
 * DominanceComparator.java
 * 
 * @author Juan J. Durillo
 * @version 1.0
 */
package sobol.base.comparator;

import java.util.Comparator;
import sobol.base.solution.Solution;

/**
 * This class implements a <code>Comparator</code> (a method for comparing
 * <code>Solution</code> objects) based on a constraint violation test +
 * dominance checking, as in NSGA-II.
 */
public class DominanceComparator implements Comparator<Solution>
{
	public int compare(Solution solution1, Solution solution2)
	{
		if (solution1 == null)
			return 1;

		if (solution2 == null)
			return -1;

		// dominate1 indicates if some objective of solution1 dominates the same objective in solution2.
		// dominate2 is the complementary of dominate1.
		// flag stores the result of the comparison
		int dominate1 = 0; 
		int dominate2 = 0; 
		int flag;

		// Equal number of violated constraint. Apply a dominance Test
		for (int i = 0; i < solution1.numberOfObjectives(); i++)
		{
			double value1 = solution1.getObjective(i);
			double value2 = solution2.getObjective(i);

			if (value1 < value2)
				flag = -1;
			else if (value1 > value2)
				flag = 1;
			else
				flag = 0;

			if (flag == -1)
				dominate1 = 1;

			if (flag == 1)
				dominate2 = 1;
		}

		if (dominate1 == dominate2)
			return 0; // No one dominate the other

		if (dominate1 == 1)
			return -1; // solution1 dominate

		return 1; // solution2 dominate
	}
}