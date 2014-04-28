package sobol.base.random.pseudo;

import sobol.base.random.generic.AbstractRandomGenerator;

class PseudoRandomGenerator implements AbstractRandomGenerator
{
	private int dimensions;
	
	public PseudoRandomGenerator(int dimensions)
	{
		this.dimensions = dimensions;
	}

	public int[] randInt(int minBound, int maxBound)
	{
		int[] result = new int[dimensions];
		
		for (int i = 0; i < dimensions; i++)
			result[i] = PseudoRandom.randInt(minBound, maxBound);
		
		return result;
	}

    public int singleInt(int minBound, int maxBound)
    {
        return PseudoRandom.randInt(minBound, maxBound);
    }

	public double[] randDouble()
	{
		double[] result = new double[dimensions];
		
		for (int i = 0; i < dimensions; i++)
			result[i] = PseudoRandom.randDouble(0, 1);
		
		return result;
	}

	public double singleDouble()
	{
		return PseudoRandom.randDouble(0, 1);
	}
}