/**
 * Distance.java
 *
 * @author Antonio J. Nebro
 * @author Juanjo Durillo
 * @version 1.0
 */
package sobol.metaheuristics.nsgaII;

import javax.management.JMException;
import sobol.base.comparator.ObjectiveComparator;
import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;

/**
 * This class implements some facilities for distances
 */
public class Distance
{
	/**
	 * Constructor.
	 */
	public Distance()
	{
	}

	/**
	 * Returns a matrix with distances between solutions in a
	 * <code>SolutionSet</code>.
	 * 
	 * @param solutionSet The <code>SolutionSet</code>.
	 * @return a matrix with distances.
	 */
	public double[][] distanceMatrix(SolutionSet solutionSet)
	{
		double[][] distance = new double[solutionSet.size()][solutionSet.size()];

		for (int i = 0; i < solutionSet.size(); i++)
		{
			distance[i][i] = 0.0;
			Solution solutionI = solutionSet.get(i);
			
			for (int j = i + 1; j < solutionSet.size(); j++)
			{
				Solution solutionJ = solutionSet.get(j);
				distance[i][j] = this.distanceBetweenObjectives(solutionI, solutionJ);
				distance[j][i] = distance[i][j];
			}
		}

		return distance;
	}

	/**
	 * Returns the minimum distance from a <code>Solution</code> to a
	 * <code>SolutionSet according to the objective values</code>.
	 * 
	 * @param solution The <code>Solution</code>.
	 * @param solutionSet The <code>SolutionSet</code>.
	 * @return The minimum distance between solution and the set.
	 * @throws JMException
	 */
	public double distanceToSolutionSetInObjectiveSpace(Solution solution, SolutionSet solutionSet)
	{
		double distance = Double.MAX_VALUE;

		for (int i = 0; i < solutionSet.size(); i++)
		{
			double aux = this.distanceBetweenObjectives(solution, solutionSet.get(i));
		
			if (aux < distance)
				distance = aux;
		}

		return distance;
	}

	/**
	 * Returns the minimum distance from a <code>Solution</code> to a
	 * <code>SolutionSet according to the variable values</code>.
	 * 
	 * @param solution The <code>Solution</code>.
	 * @param solutionSet The <code>SolutionSet</code>.
	 * @return The minimum distance between solution and the set.
	 * @throws JMException
	 */
	public double distanceToSolutionSetInSolutionSpace(Solution solution, SolutionSet solutionSet)
	{
		double distance = Double.MAX_VALUE;

		for (int i = 0; i < solutionSet.size(); i++)
		{
			double aux = this.distanceBetweenSolutions(solution, solutionSet.get(i));
		
			if (aux < distance)
				distance = aux;
		}

		return distance;
	}

	/**
	 * Returns the distance between two solutions in the search space.
	 * 
	 * @param solutionI The first <code>Solution</code>.
	 * @param solutionJ The second <code>Solution</code>.
	 * @return the distance between solutions.
	 * @throws JMException
	 */
	public double distanceBetweenSolutions(Solution solutionI, Solution solutionJ)
	{
		return solutionI.calculateDistance(solutionJ);
	}

	/**
	 * Returns the distance between two solutions in objective space.
	 * 
	 * @param solutionI The first <code>Solution</code>.
	 * @param solutionJ The second <code>Solution</code>.
	 * @return the distance between solutions in objective space.
	 */
	public double distanceBetweenObjectives(Solution solutionI, Solution solutionJ)
	{
		double distance = 0.0;

		for (int nObj = 0; nObj < solutionI.numberOfObjectives(); nObj++)
		{
			double diff = solutionI.getObjective(nObj) - solutionJ.getObjective(nObj);
			distance += Math.pow(diff, 2.0);
		}

		return Math.sqrt(distance);
	}

	/**
	 * Assigns crowding distances to all solutions in a <code>SolutionSet</code>
	 * .
	 * 
	 * @param solutionSet The <code>SolutionSet</code>.
	 * @param nObjs Number of objectives.
	 */
	public void crowdingDistanceAssignment(SolutionSet solutionSet, int nObjs)
	{
		int size = solutionSet.size();

		if (size == 0)
			return;

		if (size == 1)
		{
			solutionSet.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			return;
		}

		if (size == 2)
		{
			solutionSet.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			solutionSet.get(1).setCrowdingDistance(Double.POSITIVE_INFINITY);
			return;
		}

		// Use a new SolutionSet to evite alter original solutionSet
		SolutionSet front = new SolutionSet(size);
		
		for (int i = 0; i < size; i++)
			front.add(solutionSet.get(i));

		for (int i = 0; i < size; i++)
			front.get(i).setCrowdingDistance(0.0);

		double objetiveMaxn;
		double objetiveMinn;
		double distance;

		for (int i = 0; i < nObjs; i++)
		{
			// Sort the population by Obj n
			front.sort(new ObjectiveComparator(i));
			objetiveMinn = front.get(0).getObjective(i);
			objetiveMaxn = front.get(front.size() - 1).getObjective(i);

			// Set de crowding distance
			front.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			front.get(size - 1).setCrowdingDistance(Double.POSITIVE_INFINITY);

			for (int j = 1; j < size - 1; j++)
			{
				distance = front.get(j + 1).getObjective(i) - front.get(j - 1).getObjective(i);
				distance = distance / (objetiveMaxn - objetiveMinn);
				distance += front.get(j).getCrowdingDistance();
				front.get(j).setCrowdingDistance(distance);
			}
		}
	}
}