package sobol.base.random.halton;

import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.random.generic.AbstractRandomGeneratorFactory;

public class HaltonRandomGeneratorFactory implements AbstractRandomGeneratorFactory
{
	public AbstractRandomGenerator create(int dimensions)
	{
		return new HaltonRandomGenerator(dimensions);
	}
}