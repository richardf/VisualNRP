package sobol.base.solution;

import java.util.BitSet;
import sobol.base.solutionType.BinarySolutionType;

public class BinarySolution extends Solution
{
	public BitSet bits_;
	protected int numberOfBits_;
	private BinarySolutionType type;

	public BinarySolution(BinarySolutionType type, int numberOfObjectives, int numberOfBits)
	{
		super(numberOfObjectives);
		this.type = type;
		this.numberOfBits_ = numberOfBits;
		this.bits_ = new BitSet(numberOfBits_);
		randomize();
	}

	@Override
	public void randomize()
	{
		double[] rnd = type.getRandomIndividual();
		
		for (int i = 0; i < numberOfBits_; i++)
		{
			if (rnd[i] < 0.5)
				bits_.set(i, true);
			else
				bits_.set(i, false);
		}
	}

	public boolean getIth(int bit)
	{
		return bits_.get(bit);
	}

	public void setIth(int bit, boolean value)
	{
		bits_.set(bit, value);
	}

	public void flip(int bit)
	{
		bits_.flip(bit);
	}
	
	public int hammingDistance(BinarySolution other)
	{
		int distance = 0;

		for (int i = 0; i < numberOfBits_; i++)
			if (bits_.get(i) != other.bits_.get(i))
				distance++;

		return distance;		
	}
	
	@Override
	public int numberOfVariables()
	{
		return numberOfBits_;
	}

	@Override
	public Solution clone()
	{
		BinarySolution result = new BinarySolution(type, numberOfObjectives(), numberOfBits_);
		
		for (int i = 0; i < numberOfBits_; i++)
			result.setIth(i, bits_.get(i));

		copySolutionData(result);
		return result;
	}

	@Override
	public double calculateDistance(Solution other)
	{
		return hammingDistance((BinarySolution)other);
	}
	
	@Override
	public String getShortDescription()
	{
		String result = " [" + (bits_.get(0) ? 1 : 0);
		
		for(int i = 1; i < numberOfBits_; i++)
			result += " " + (bits_.get(i) ? 1 : 0);

		return result + "]";
	}
}