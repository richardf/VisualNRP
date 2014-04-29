package sobol.base.solutionType;

import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.solution.IntSolution;
import sobol.base.solution.Solution;

public class IntSolutionType extends SolutionType
{
	private int size;
	private int[] upperBound;
	private int[] lowerBound;
	private AbstractRandomGenerator random;
	
	public IntSolutionType(int size, int min, int max)
	{
		this.random = RandomGeneratorFactory.createForPopulation(size);
		this.size = size;
		this.lowerBound = new int[size];
		this.upperBound = new int[size];
		setAllLowerBounds(min);
		setAllUpperBounds(max);
	}

	public IntSolutionType(int size)
	{
		this(size, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public int getUpperBound(int index)
	{
		return this.upperBound[index];
	}

	public void setAllUpperBounds(int value)
	{
		for (int i = 0; i < size; i++)
			this.upperBound[i] = value;
	}

	public int getLowerBound(int index)
	{
		return this.lowerBound[index];
	}

	public void setUpperBound(int index, int value)
	{
		this.upperBound[index] = value;
	}

	public void setAllLowerBounds(int value)
	{
		for (int i = 0; i < size; i++)
			this.lowerBound[i] = value;
	}

	public void setLowerBound(int index, int value)
	{
		this.lowerBound[index] = value;
	}
	
	public int[] getRandomIndividual()
	{
		return random.randInt(lowerBound[0], upperBound[0]);
	}

	@Override
	public int getSolutionSize()
	{
		return size;
	}

	@Override
	public Solution newSolution(int numberOfObjectives)
	{
		return new IntSolution(this, numberOfObjectives, size);
	}
}