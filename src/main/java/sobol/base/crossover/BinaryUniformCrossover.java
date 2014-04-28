package sobol.base.crossover;

import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.solution.BinarySolution;
import sobol.base.solution.Solution;

public class BinaryUniformCrossover implements CrossoverOperator
{
	private AbstractRandomGenerator random;
	private double probability;
	
	public BinaryUniformCrossover(double probability)
	{
		this.random = RandomGeneratorFactory.createForOperator(1);
		this.probability = probability;
	}

	public Solution[] execute(Solution parent0, Solution parent1)
	{
		BinarySolution[] offSpring = new BinarySolution[2];
		offSpring[0] = (BinarySolution) parent0.clone();
		offSpring[1] = (BinarySolution) parent1.clone();
		
		if (random.singleDouble() < probability)
		{
			for (int j = 0; j < parent0.numberOfVariables(); j++)
			{
				boolean bit0 = offSpring[0].bits_.get(j);
				boolean bit1 = offSpring[1].bits_.get(j);

				if (random.singleDouble() > 0.5)
				{
					boolean swap = bit1;
					bit1 = bit0;
					bit0 = swap;
				}

				offSpring[0].bits_.set(j, bit0);
				offSpring[1].bits_.set(j, bit1);
			}
		}

		for (int i = 0; i < offSpring.length; i++)
		{
			offSpring[i].setCrowdingDistance(0.0);
			offSpring[i].setRank(0);
		}
		
		return offSpring;
	}
}