package sobol.base.mutation;

import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.solution.BinarySolution;
import sobol.base.solution.Solution;

public class BinaryFlipMutation implements MutationOperator
{
	private AbstractRandomGenerator random;
	private double probability;
	
	public BinaryFlipMutation(double probability)
	{
		this.random = RandomGeneratorFactory.createForOperator(1);
		this.probability = probability;
	}

	public Solution execute(Solution solution)
	{
		BinarySolution binSolution = (BinarySolution)solution;
		
		for (int i = 0; i < binSolution.numberOfVariables(); i++)
			if (random.singleDouble() < probability)
				binSolution.bits_.flip(i);

		return solution;
	}
}