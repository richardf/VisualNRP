package sobol.base.mutation;

import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.solution.IntSolution;
import sobol.base.solution.Solution;
import sobol.base.solutionType.IntSolutionType;

public class IntFlipMutation implements MutationOperator
{
	private AbstractRandomGenerator random;
	private double probability;
	
	public IntFlipMutation(double probability)
	{
		this.random = RandomGeneratorFactory.createForOperator(1);
		this.probability = probability;
	}

	public Solution execute(Solution solution)
	{
		IntSolution intSolution = (IntSolution)solution; 
		IntSolutionType type = intSolution.getType();
		
		for (int i = 0; i < type.getSolutionSize(); i++)
			if (random.singleDouble() < probability)
			{
				int value = random.randInt(type.getUpperBound(i), type.getLowerBound(i))[0];
				intSolution.setValue(i, value);
			}

		return solution;
	}
}