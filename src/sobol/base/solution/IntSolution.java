package sobol.base.solution;

import sobol.base.solutionType.IntSolutionType;

public class IntSolution extends Solution
{
	private IntSolutionType type;
	private int[] value_;

	public IntSolution(IntSolutionType type, int numberOfObjectives, int size)
	{
		super(numberOfObjectives);
		this.type = type;
		this.value_ = new int[size];
		randomize();
	}

	public int getValue(int index)
	{
		return value_[index];
	}

	public void setValue(int index, int value)
	{
		this.value_[index] = value;
	}
	
	public IntSolutionType getType()
	{
		return type;
	}

	@Override
	public void randomize()
	{
		int[] rnd = type.getRandomIndividual();
		
		for (int i = 0; i < value_.length; i++)
			value_[i] = rnd[i];
	}

	@Override
	public int numberOfVariables()
	{
		return value_.length;
	}

	@Override
	public Solution clone()
	{
		IntSolution result = new IntSolution(type, numberOfObjectives(), value_.length);
		
		for (int i = 0; i < value_.length; i++)
			result.setValue(i, value_[i]);

		copySolutionData(result);
		return result;
	}

	@Override
	public double calculateDistance(Solution other)
	{
		double distance = 0.0;

		for (int i = 0; i < value_.length; i++)
		{
			double diff = getValue(i) - ((IntSolution)other).getValue(i);
			distance += Math.pow(diff, 2.0);
		}

		return Math.sqrt(distance);
	}
	
	@Override
	public String getShortDescription()
	{
		String result = " [" + value_[0];
		
		for(int i = 1; i < value_.length; i++)
			result += " " + value_[i];

		return result + "]";
	}
}