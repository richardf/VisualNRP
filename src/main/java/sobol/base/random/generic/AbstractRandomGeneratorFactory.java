package sobol.base.random.generic;

public interface AbstractRandomGeneratorFactory
{
	public AbstractRandomGenerator create(int dimensions);
}