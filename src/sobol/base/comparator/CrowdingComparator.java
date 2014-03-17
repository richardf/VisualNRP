/**
 * CrowdingComparator.java
 * 
 * @author Juan J. Durillo
 * @version 1.0
 */
package sobol.base.comparator;

import java.util.Comparator;
import sobol.base.solution.Solution;

/**
 * This class implements a <code>Comparator</code> (a method for comparing
 * <code>Solution</code> objects) based on the crowding distance, as in NSGA-II.
 */
public class CrowdingComparator implements Comparator<Solution>
{
	/**
	 * stores a comparator for check the rank of solutions
	 */
	private static final Comparator<Solution> comparator = new RankComparator();

	/**
	 * Compare two solutions.
	 * 
	 * @param o1 Object representing the first <code>Solution</code>.
	 * @param o2 Object representing the second <code>Solution</code>.
	 * @return -1, or 0, or 1 if o1 is less than, equal, or greater than o2,
	 *         respectively.
	 */
	public int compare(Solution o1, Solution o2)
	{
		if (o1 == null)
			return 1;

		if (o2 == null)
			return -1;

		int flagComparatorRank = comparator.compare(o1, o2);

		if (flagComparatorRank != 0)
			return flagComparatorRank;

		/* His rank is equal, then distance crowding comparator */
		double distance1 = o1.getCrowdingDistance();
		double distance2 = o2.getCrowdingDistance();

		if (distance1 > distance2)
			return -1;

		if (distance1 < distance2)
			return 1;

		return 0;
	}
}