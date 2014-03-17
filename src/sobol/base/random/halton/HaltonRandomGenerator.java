package sobol.base.random.halton;

import java.util.ArrayList;
import java.util.List;
import sobol.base.random.generic.AbstractRandomGenerator;

class HaltonRandomGenerator implements AbstractRandomGenerator
{
	private List<VDCorputSequence> sequences;
	
	public HaltonRandomGenerator(int dimensions)
	{
		if (dimensions > Primes.PRIME.length)
			throw new AssertionError("The number of dimensions of a Halton sequence must up to 1000");

		this.sequences = new ArrayList<VDCorputSequence>();
		
		for (int i = 0; i < dimensions; i++)
			this.sequences.add(new VDCorputSequence(Primes.PRIME[i]));
	}

	public int[] randInt(int minBound, int maxBound)
	{
		int dimensions = sequences.size();
		int[] result = new int[dimensions];
		
		for (int i = 0; i < dimensions; i++)
			result[i] = minBound + (int)(sequences.get(i).next() * (maxBound - minBound));
		
		return result;
	}

    public int singleInt(int minBound, int maxBound)
    {
        return minBound + (int)(sequences.get(0).next() * (maxBound - minBound));
    }

	public double[] randDouble()
	{
		int dimensions = sequences.size();
		double[] result = new double[dimensions];
		
		for (int i = 0; i < dimensions; i++)
			result[i] = sequences.get(i).next();
		
		return result;
	}

	public double singleDouble()
	{
		return sequences.get(0).next();
	}
}