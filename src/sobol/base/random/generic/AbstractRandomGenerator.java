package sobol.base.random.generic;

public interface AbstractRandomGenerator
{
	int[] randInt(int minBound, int maxBound);

    int singleInt(int minBound, int maxBound);

    double[] randDouble();
	
	double singleDouble();
}