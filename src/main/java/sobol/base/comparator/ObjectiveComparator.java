/**
 * ObjectiveComparator.java
 * 
 * @author Juan J. Durillo
 * @version 1.0
 */
package sobol.base.comparator;

import java.util.Comparator;
import sobol.base.solution.Solution;

/**
 * This class implements a <code>Comparator</code> (a method for comparing
 * <code>Solution</code> objects) based on a objective values.
 */
public class ObjectiveComparator implements Comparator<Solution>
{
	/**
	 * Stores the index of the objective to compare
	 */
	private int nObj;

	/**
	 * Constructor.
	 * 
	 * @param nObj The index of the objective to compare
	 */
	public ObjectiveComparator(int nObj)
	{
		this.nObj = nObj;
	}

	/**
	 * Compares two solutions.
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

		double objetive1 = o1.getObjective(this.nObj);
		double objetive2 = o2.getObjective(this.nObj);

		if (objetive1 < objetive2)
			return -1;

		if (objetive1 > objetive2)
			return 1;

		return 0;
	}
}