package sobol.experiments.utils;

public class Calculator
{
	public Calculator()
	{
	}
	
	public double calculateAverage(double[] values)
	{
		double sum = 0.0;
		
		for (int i = 0; i < values.length; i++)
			sum += values[i];
		
		return sum / values.length;
	}
	
	public double calculateMaximum(double[] values)
	{
		double max = values[0];
		
		for (int i = 1; i < values.length; i++)
			if (values[i] > max)
				max = values[i];
		
		return max;
	}
	
	public double calculateStandardDeviation(double[] values)
	{
		double sum = 0.0;
		double avg = calculateAverage(values);
		
		for (int i = 0; i < values.length; i++)
			sum += (values[i] - avg) * (values[i] - avg);
		
		return Math.pow(sum / (values.length-1), 0.5);
	}
}