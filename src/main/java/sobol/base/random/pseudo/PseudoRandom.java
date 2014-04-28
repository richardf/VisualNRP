/**
 * PseudoRandom.java
 *
 * @author Juan J. Durillo
 * @version 1.0
 *
 */
package sobol.base.random.pseudo;

import java.util.Random;

/**
 * Class representing some randoms facilities
 */
class PseudoRandom
{
	private static RandomGeneratorInternal random = null;

	/**
	 * Constructor. Creates a new instance of PseudoRandom.
	 */
	private PseudoRandom()
	{
		if (random == null)
			random = new RandomGeneratorInternal();
	}

	/**
	 * Returns a random int value between a minimum bound and maximum bound
	 * using the PseudoRandom generator.
	 * 
	 * @param minBound The minimum bound.
	 * @param maxBound The maximum bound. Return A pseudo random int value
	 *            between minBound and maxBound.
	 */
	public static int randInt(int minBound, int maxBound)
	{
		if (random == null)
			new PseudoRandom();

		return random.rnd(minBound, maxBound);
	}

	/**
	 * Returns a random double value between a minimum bound and a maximum bound
	 * using the PseudoRandom generator.
	 * 
	 * @param minBound The minimum bound.
	 * @param maxBound The maximum bound.
	 * @return A pseudo random double value between minBound and maxBound
	 */
	public static double randDouble(double minBound, double maxBound)
	{
		if (random == null)
			new PseudoRandom();

		return random.rndreal(minBound, maxBound);
	}
}

class RandomGeneratorInternal
{
	double seed;
	double[] oldrand = new double[55];
	int jrand;

	/**
	 * Constructor
	 */
	public RandomGeneratorInternal()
	{
		//this.seed = (new Random(123)).nextDouble();
		this.seed = (new Random(System.nanoTime())).nextDouble();
		this.randomize();
	}

	/**
	 * Get seed number for random and start it up 
	 */
	void randomize()
	{
		for (int j1 = 0; j1 <= 54; j1++)
			oldrand[j1] = 0.0;

		jrand = 0;
		warmup_random(seed);
	}

	/**
	 * Get randomize off and running 
	 */
	void warmup_random(double seed)
	{
		int j1, ii;
		double new_random, prev_random;
		oldrand[54] = seed;
		new_random = 0.000000001;
		prev_random = seed;
		for (j1 = 1; j1 <= 54; j1++)
		{
			ii = (21 * j1) % 54;
			oldrand[ii] = new_random;
			new_random = prev_random - new_random;
			if (new_random < 0.0)
			{
				new_random += 1.0;
			}
			prev_random = oldrand[ii];
		}
		advance_random();
		advance_random();
		advance_random();
		jrand = 0;
	}

	/**
	 * Create next batch of 55 random numbers 
	 */
	void advance_random()
	{
		int j1;
		double new_random;
		for (j1 = 0; j1 < 24; j1++)
		{
			new_random = oldrand[j1] - oldrand[j1 + 31];
			if (new_random < 0.0)
			{
				new_random = new_random + 1.0;
			}
			oldrand[j1] = new_random;
		}
		for (j1 = 24; j1 < 55; j1++)
		{
			new_random = oldrand[j1] - oldrand[j1 - 24];
			if (new_random < 0.0)
			{
				new_random = new_random + 1.0;
			}
			oldrand[j1] = new_random;
		}
	}

	/**
	 * Fetch a single random number between 0.0 and 1.0 
	 */
	double randomperc()
	{
		jrand++;
		if (jrand >= 55)
		{
			jrand = 1;
			advance_random();
		}
		return ((double) oldrand[jrand]);
	}

	/**
	 * Fetch a single random integer between low and high including the bounds 
	 */
	synchronized int rnd(int low, int high)
	{
		int res;
		if (low >= high)
		{
			res = low;
		}
		else
		{
			res = low + (int) (randomperc() * (high - low /*+ 1*/));
			if (res > high)
			{
				res = high;
			}
		}
		return (res);
	}

	/**
	 * Fetch a single random real number between low and high including the bound 
	 */
	synchronized double rndreal(double low, double high)
	{
		return (low + (high - low) * randomperc());
	}
}