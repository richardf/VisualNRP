/**
 * QualityIndicator.java
 *
 * @author Antonio J. Nebro
 * @version 1.0
 * 
 * This class provides methods for calculating the values of quality indicators
 * from a solution set. After creating an instance of this class, which requires
 * the file containing the true Pareto of the problem as a parementer, methods
 * such as getHypervolume(), getSpread(), etc. are available
 */

package sobol.base.qualityIndicator;

import sobol.base.algorithm.Problem;
import sobol.base.solution.SolutionSet;

/**
 * QualityIndicator class
 */
public class QualityIndicator
{
	SolutionSet trueParetoFront_;
	double trueParetoFrontHypervolume_;
	Problem problem_;

	/**
	 * Copies the objectives of the solution set to a matrix
	 */
	private double[][] writeObjectivesToMatrix(SolutionSet solutionSet)
	{
		if (solutionSet.size() == 0)
			return null;

		double[][] objectives;
		objectives = new double[solutionSet.size()][solutionSet.get(0).numberOfObjectives()];

		for (int i = 0; i < solutionSet.size(); i++)
			for (int j = 0; j < solutionSet.get(0).numberOfObjectives(); j++)
				objectives[i][j] = solutionSet.get(i).getObjective(j);

		return objectives;
	}

	/**
	 * Returns the hypervolume of solution set
	 */
	public double getHypervolume(SolutionSet solutionSet)
	{
		return new Hypervolume().hypervolume(writeObjectivesToMatrix(solutionSet), writeObjectivesToMatrix(trueParetoFront_), problem_.getNumberOfObjectives());
	}

	/**
	 * Returns the hypervolume of the true Pareto front
	 */
	public double getTrueParetoFrontHypervolume()
	{
		return trueParetoFrontHypervolume_;
	}

	/**
	 * Returns the inverted generational distance of solution set
	 */
	public double getIGD(SolutionSet solutionSet)
	{
		return new InvertedGenerationalDistance().invertedGenerationalDistance(writeObjectivesToMatrix(solutionSet), writeObjectivesToMatrix(trueParetoFront_), problem_.getNumberOfObjectives());
	}

	/**
	 * Returns the generational distance of solution set
	 */
	public double getGD(SolutionSet solutionSet)
	{
		return new GenerationalDistance().generationalDistance(writeObjectivesToMatrix(solutionSet), writeObjectivesToMatrix(trueParetoFront_), problem_.getNumberOfObjectives());
	}

	/**
	 * Returns the spread of solution set
	 */
	public double getSpread(SolutionSet solutionSet)
	{
		return new Spread().spread(writeObjectivesToMatrix(solutionSet), writeObjectivesToMatrix(trueParetoFront_), problem_.getNumberOfObjectives());
	}

	/**
	 * Returns the epsilon indicator of solution set
	 */
	public double getEpsilon(SolutionSet solutionSet)
	{
		return new Epsilon().epsilon(writeObjectivesToMatrix(solutionSet), writeObjectivesToMatrix(trueParetoFront_), problem_.getNumberOfObjectives());
	}
}