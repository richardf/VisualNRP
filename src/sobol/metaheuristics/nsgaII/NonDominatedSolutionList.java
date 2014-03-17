/**
 * NonDominatedSolutionList.java 
 *
 * @author Juan J. Durillo
 * @version 1.0
 */

package sobol.metaheuristics.nsgaII;

import java.util.Comparator;
import java.util.Iterator;
import sobol.base.comparator.DominanceComparator;
import sobol.base.comparator.SolutionComparator;
import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;

/**
 * This class implements an unbound list of non-dominated solutions
 */
public class NonDominatedSolutionList extends SolutionSet
{
	private static final long serialVersionUID = 2499039751150527555L;

	/**
	 * Stores a <code>Comparator</code> for dominance checking
	 */
	private Comparator<Solution> dominance_ = new DominanceComparator();

	/**
	 * Stores a <code>Comparator</code> for checking if two solutions are equal
	 */
	private static final Comparator<Solution> equal_ = new SolutionComparator();

	/**
	 * Constructor. The objects of this class are lists of non-dominated
	 * solutions according to a Pareto dominance comparator.
	 */
	public NonDominatedSolutionList()
	{
		super();
	}

	/**
	 * Constructor. This constructor creates a list of non-dominated individuals
	 * using a comparator object.
	 * 
	 * @param dominance The comparator for dominance checking.
	 */
	public NonDominatedSolutionList(Comparator<Solution> dominance)
	{
		super();
		dominance_ = dominance;
	}

	/**
	 * Inserts a solution in the list
	 * 
	 * @param solution The solution to be inserted.
	 * @return true if the operation success, and false if the solution is
	 *         dominated or if an identical individual exists. The decision
	 *         variables can be null if the solution is read from a file; in
	 *         that case, the domination tests are omitted
	 */
	public boolean add(Solution solution)
	{
		Iterator<Solution> iterator = solutionsList_.iterator();

		while (iterator.hasNext())
		{
			Solution listIndividual = iterator.next();
			int flag = dominance_.compare(solution, listIndividual);

			if (flag == -1)
			{
				iterator.remove();
			}
			else if (flag == 0)  // Non-dominated solutions
			{
				flag = equal_.compare(solution, listIndividual);
				
				if (flag == 0)
					return false; // The new solution is in the list
			}
			else if (flag == 1)
			{ 
				return false;	// The new solution is dominated
			}
		}

		// At this point, the solution is inserted into the list
		solutionsList_.add(solution);
		return true;
	}
}