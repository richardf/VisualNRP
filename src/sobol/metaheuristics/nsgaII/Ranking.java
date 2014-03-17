/**
 * Ranking.java
 *
 * @author Juan J. Durillo
 * @version 1.0
 */

package sobol.metaheuristics.nsgaII;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;

/**
 * This class implements some facilities for ranking solutions. Given a
 * <code>SolutionSet</code> object, their solutions are ranked according to
 * scheme proposed in NSGA-II; as a result, a set of subsets are obtained. The
 * subsets are numbered starting from 0 (in NSGA-II, the numbering starts from
 * 1); thus, subset 0 contains the non-dominated solutions, subset 1 contains
 * the non-dominated solutions after removing those belonging to subset 0, and
 * so on.
 */
public class Ranking
{
	private static final int FIRST_DOMINATES_SECOND = -1;
	private static final int SECOND_DOMINATES_FIRST = 1;
	private static final int NO_DOMINANCE = 0;
	private static final int IDENTICAL_SOLUTIONS = 2;
	
	private static final double EPSILON = 1e-10;
	
	/**
	 * The <code>SolutionSet</code> to rank
	 */
	private SolutionSet solutionSet_;

	/**
	 * An array containing all the fronts found during the search
	 */
	private SolutionSet[] ranking_;

	/**
	 * Constructor.
	 * 
	 * @param solutionSet The <code>SolutionSet</code> to be ranked.
	 */
	@SuppressWarnings("unchecked")
	public Ranking(SolutionSet solutionSet)
	{
		solutionSet_ = solutionSet;

		// dominateMe[i] contains the number of solutions dominating i
		int[] dominateMe = new int[solutionSet_.size()];

		// iDominate[k] contains the list of solutions dominated by k
		List<Integer>[] iDominate = new List[solutionSet_.size()];

		// front[i] contains the list of individuals belonging to the front i
		List<Integer>[] front = new List[solutionSet_.size() + 1];

		// Initialize the fronts
		for (int i = 0; i < front.length; i++)
			front[i] = new LinkedList<Integer>();

		// -> Fast non dominated sorting algorithm
		for (int p = 0; p < solutionSet_.size(); p++)
		{
			// Initialice the list of individuals that i dominate and the number
			// of individuals that dominate me
			iDominate[p] = new LinkedList<Integer>();
			dominateMe[p] = 0;

			// For all q individuals , calculate if p dominates q or vice versa
			for (int q = solutionSet_.size()-1; q >= 0; q--)
			{
				if (p != q)
				{
					int flagDominate = compare(solutionSet.get(p), solutionSet.get(q));
					
					if (flagDominate == IDENTICAL_SOLUTIONS)
						solutionSet.remove(q);

					else if (flagDominate == FIRST_DOMINATES_SECOND)
						iDominate[p].add(new Integer(q));
					
					else if (flagDominate == SECOND_DOMINATES_FIRST)
						dominateMe[p]++;
				}
			}

			// If nobody dominates p, p belongs to the first front
			if (dominateMe[p] == 0)
			{
				front[0].add(new Integer(p));
				solutionSet.get(p).setRank(0);
			}
		}

		// Obtain the rest of fronts
		int i = 0;
		Iterator<Integer> it1, it2;
		while (front[i].size() != 0)
		{
			i++;
			it1 = front[i - 1].iterator();
			while (it1.hasNext())
			{
				it2 = iDominate[it1.next().intValue()].iterator();
				while (it2.hasNext())
				{
					int index = it2.next().intValue();
					dominateMe[index]--;
					if (dominateMe[index] == 0)
					{
						front[i].add(new Integer(index));
						solutionSet_.get(index).setRank(i);
					}
				}
			}
		}

		ranking_ = new SolutionSet[i];
		// 0,1,2,....,i-1 are front, then i fronts
		for (int j = 0; j < i; j++)
		{
			ranking_[j] = new SolutionSet(front[j].size());
			it1 = front[j].iterator();
			
			while (it1.hasNext())
			{
				ranking_[j].add(solutionSet.get(it1.next().intValue()));
			}
		}
	}

	/**
	 * Compare two solutions for domination
	 * 
	 * @param solution1		First solution to compare
	 * @param solution2		Second solution to compare
	 */
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
		{
			if (dominate1 == 0)
				if (solution1.calculateDistance(solution2) < EPSILON)
					return IDENTICAL_SOLUTIONS;

			return NO_DOMINANCE; // No one dominate the other
		}

		if (dominate1 == 1)
			return FIRST_DOMINATES_SECOND; // solution1 dominate

		return SECOND_DOMINATES_FIRST; // solution2 dominate
	}

	/**
	 * Returns a <code>SolutionSet</code> containing the solutions of a given
	 * rank.
	 * 
	 * @param rank The rank
	 * @return Object representing the <code>SolutionSet</code>.
	 */
	public SolutionSet getSubfront(int rank)
	{
		return ranking_[rank];
	}

	/**
	 * Returns the total number of subFronts founds.
	 */
	public int getNumberOfSubfronts()
	{
		return ranking_.length;
	}
}