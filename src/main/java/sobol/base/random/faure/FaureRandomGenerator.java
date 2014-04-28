package sobol.base.random.faure;

import sobol.base.random.generic.AbstractRandomGenerator;

class FaureRandomGenerator implements AbstractRandomGenerator
{
	private int dimensions;
	private Faure generator;
	
	public FaureRandomGenerator(int dimensions)
	{
		this.dimensions = dimensions;
		this.generator = new Faure(dimensions);
	}

	public int[] randInt(int minBound, int maxBound)
	{
		int[] result = new int[dimensions];
		double[] data = generator.next();
		
		for (int i = 0; i < dimensions; i++)
			result[i] = minBound + (int)(data[i] * (maxBound - minBound));
		
		return result;
	}

    public int singleInt(int minBound, int maxBound)
    {
        double[] data = generator.next();
        return minBound + (int)(data[0] * (maxBound - minBound));
    }

	public double[] randDouble()
	{
		return generator.next();
	}

	public double singleDouble()
	{
		return generator.next()[0];
	}
}