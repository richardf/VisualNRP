package sobol.base.random.sobol;

import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.random.generic.AbstractRandomGeneratorFactory;

public class SobolRandomGeneratorFactory implements AbstractRandomGeneratorFactory
{
	public AbstractRandomGenerator create(int dimensions)
	{
		return new SobolRandomGenerator(dimensions);
	}
}