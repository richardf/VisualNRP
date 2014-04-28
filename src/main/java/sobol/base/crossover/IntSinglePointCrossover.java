package sobol.base.crossover;

import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.solution.IntSolution;
import sobol.base.solution.Solution;

public class IntSinglePointCrossover implements CrossoverOperator
{
	private AbstractRandomGenerator random;
	private double probability;

	public IntSinglePointCrossover(double probability)
	{
		this.random = RandomGeneratorFactory.createForOperator(1);
		this.probability = probability;
	}

	public Solution[] execute(Solution parent0, Solution parent1)
	{
		IntSolution[] offSpring = new IntSolution[2];
		offSpring[0] = (IntSolution)parent0.clone();
		offSpring[1] = (IntSolution)parent1.clone();

		if (random.singleDouble() < probability)
		{
			int crossoverPoint = random.randInt(0, parent0.numberOfVariables() - 1)[0];

			for (int i = crossoverPoint; i < parent0.numberOfVariables(); i++)
			{
				int value0 = offSpring[0].getValue(i);
				int value1 = offSpring[1].getValue(i);
				offSpring[0].setValue(i, value1);
				offSpring[1].setValue(i, value0);
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