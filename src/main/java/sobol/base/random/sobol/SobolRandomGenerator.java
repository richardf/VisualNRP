package sobol.base.random.sobol;

import sobol.base.random.generic.AbstractRandomGenerator;

class SobolRandomGenerator implements AbstractRandomGenerator
{
	private Sobol generator;
	private int dimensions;
	
	public SobolRandomGenerator(int dimensions)
	{
		this.dimensions = dimensions;
		this.generator = new Sobol(dimensions);
	}

	public int[] randInt(int minBound, int maxBound)
	{
		int[] result = new int[dimensions];
		double[] points = generator.next();
		
		for (int i = 0; i < dimensions; i++)
			result[i] = minBound + (int)(points[i] * (maxBound - minBound));
		
		return result;
	}
    public int singleInt(int minBound, int maxBound)
    {
        double[] points = generator.next();
        return minBound + (int)(points[0] * (maxBound - minBound));
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