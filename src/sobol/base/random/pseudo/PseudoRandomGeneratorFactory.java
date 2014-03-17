package sobol.base.random.pseudo;

import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.random.generic.AbstractRandomGeneratorFactory;

public class PseudoRandomGeneratorFactory implements AbstractRandomGeneratorFactory
{
	public AbstractRandomGenerator create(int dimensions)
	{
		return new PseudoRandomGenerator(dimensions);
	}
}