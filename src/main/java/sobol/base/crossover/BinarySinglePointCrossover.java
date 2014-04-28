package sobol.base.crossover;

import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.solution.BinarySolution;
import sobol.base.solution.Solution;

public class BinarySinglePointCrossover implements CrossoverOperator
{
	private AbstractRandomGenerator random;
	private double probability;
	
	public BinarySinglePointCrossover(double probability)
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
			int numberOfBits = parent0.numberOfVariables();
			int crossoverPoint = random.randInt(0, numberOfBits - 1)[0];

			for (int i = crossoverPoint; i < numberOfBits; i++)
			{
				boolean swap = offSpring[0].bits_.get(i);
				offSpring[0].bits_.set(i, offSpring[1].bits_.get(i));
				offSpring[1].bits_.set(i, swap);
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