package sobol.base.random;

import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.random.generic.AbstractRandomGeneratorFactory;

public class RandomGeneratorFactory
{
	private static AbstractRandomGeneratorFactory currentOperatorFactory = null;
	private static AbstractRandomGeneratorFactory currentPopulationFactory = null;
	
	public static void setRandomFactoryForOperator(AbstractRandomGeneratorFactory factory)
	{
		currentOperatorFactory = factory;
	}
	
	public static void setRandomFactoryForPopulation(AbstractRandomGeneratorFactory factory)
	{
		currentPopulationFactory = factory;
	}

	public static AbstractRandomGenerator createForOperator(int dimensions)
	{
		if (currentOperatorFactory == null)
			throw new AssertionError("You have not set the random number generator factory for operators");
		
		return currentOperatorFactory.create(dimensions);
	}
	
	public static AbstractRandomGenerator createForPopulation(int dimensions)
	{
		if (currentPopulationFactory == null)
			throw new AssertionError("You have not set the random number generator factory for the population");

		return currentPopulationFactory.create(dimensions);
	}
}