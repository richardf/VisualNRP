/**
 * LexicoGraphicalComparator.java
 *
 * @author Juan J. durillo
 * @version 1.0
 *
 */

package sobol.base.qualityIndicator;

import java.util.Comparator;

/**
 * This class implements the <code>Comparator</code> interface. It is used to
 * compare points given as <code>double</code>. The order used is the
 * lexicograhphical.
 */
public class LexicoGraphicalComparator implements Comparator<double[]>
{

	/**
	 * The compare method compare the objects o1 and o2.
	 * 
	 * @param o1 An object that reference a double[]
	 * @param o2 An object that reference a double[]
	 * @return -1 if o1 < o1, 1 if o1 > o2 or 0 in other case.
	 */
	public int compare(double[] o1, double[] o2)
	{
		// Cast to double [] o1 and o2.
		double[] pointOne = o1;
		double[] pointTwo = o2;

		// To determine the first i, that pointOne[i] != pointTwo[i];
		int index = 0;
		while ((index < pointOne.length) && (index < pointTwo.length) && pointOne[index] == pointTwo[index])
		{
			index++;
		}
		if ((index >= pointOne.length) || (index >= pointTwo.length))
		{
			return 0;
		}
		else if (pointOne[index] < pointTwo[index])
		{
			return -1;
		}
		else if (pointOne[index] > pointTwo[index])
		{
			return 1;
		}
		return 0;
	} // compare
} // LexicoGraphicalComparator
