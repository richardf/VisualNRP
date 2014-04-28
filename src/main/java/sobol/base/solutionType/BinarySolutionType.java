/**
 * BinarySolutionType
 *
 * @author Antonio J. Nebro
 * @version 1.0
 * 
 * Class representing the solution type of solutions composed of Binary 
 * variables
 */
package sobol.base.solutionType;

import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.solution.BinarySolution;
import sobol.base.solution.Solution;

public class BinarySolutionType extends SolutionType
{
	private int size;
	private AbstractRandomGenerator random;
	
	public BinarySolutionType(int size)
	{
		this.random = RandomGeneratorFactory.createForPopulation(size);
		this.size = size;
	}

	public double[] getRandomIndividual()
	{
		return random.randDouble();
	}

	@Override
	public int getSolutionSize()
	{
		return size;
	}

	@Override
	public Solution newSolution(int numberOfObjectives)
	{
		return new BinarySolution(this, numberOfObjectives, size);
	}
}