package sobol.base.random.faure;

import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.random.generic.AbstractRandomGeneratorFactory;

public class FaureRandomGeneratorFactory implements AbstractRandomGeneratorFactory
{
	public AbstractRandomGenerator create(int dimensions)
	{
		return new FaureRandomGenerator(dimensions);
	}
}