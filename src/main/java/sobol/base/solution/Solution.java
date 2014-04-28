package sobol.base.solution;

public abstract class Solution
{
	/**
	 * Stores the objectives values of the solution.
	 */
	private double[] objective_;

	/**
	 * Stores the crowding distance of the the solution in a <code>SolutionSet</code>. Used in NSGA-II.
	 */
	private double crowdingDistance_;

	/**
	 * Stores the so called rank of the solution. Used in NSGA-II
	 */
	private int rank_;

	/**
	 * This field is intended to be used to know the location of a solution into a <code>SolutionSet</code>
	 */
	private int location_;
	
	/**
	 * Solution fitness - used by SPEA2
	 */
	private double fitness_;

	/**
	 * Constructor.
	 * 
	 * @param problem The problem to solve
	 * @throws ClassNotFoundException
	 */
	public Solution(int numberOfObjectives)
	{
		objective_ = new double[numberOfObjectives];
		crowdingDistance_ = 0.0;
		rank_ = 0;
		location_ = 0;
		fitness_ = 0.0;
	}

	/**
	 * Returns the number of objectives.
	 */
	public int numberOfObjectives()
	{
		return objective_.length;
	}

	/**
	 * Sets the value of the i-th objective.
	 */
	public void setObjective(int i, double value)
	{
		objective_[i] = value;
	}

	/**
	 * Returns the value of the i-th objective.
	 */
	public double getObjective(int i)
	{
		return objective_[i];
	}

	/**
	 * Sets the crowding distance of a solution in a <code>SolutionSet</code>.
	 */
	public void setCrowdingDistance(double distance)
	{
		crowdingDistance_ = distance;
	}

	/**
	 * Gets the crowding distance of the solution into a <code>SolutionSet</code>
	 */
	public double getCrowdingDistance()
	{
		return crowdingDistance_;
	}

	/**
	 * Sets the rank of a solution
	 */
	public void setRank(int value)
	{
		this.rank_ = value;
	}

	/**
	 * Gets the rank of the solution
	 */
	public int getRank()
	{
		return this.rank_;
	}

	/**
	 * Sets the location of the solution into a SolutionSet
	 */
	public void setLocation(int location)
	{
		this.location_ = location;
	}

	/**
	 * Gets the location of this solution in a <code>SolutionSet</code>
	 */
	public int getLocation()
	{
		return this.location_;
	}

	/**
	 * Sets the fitness of a solution. The value is stored in
	 * <code>fitness_</code>.
	 * 
	 * @param fitness The fitness of the solution.
	 */
	public void setFitness(double fitness)
	{
		fitness_ = fitness;
	}

	/**
	 * Gets the fitness of the solution. Returns the value of stored in the
	 * variable <code>fitness_</code>. <b> REQUIRE </b>: This method has to be
	 * invoked after calling <code>setFitness()</code>.
	 * 
	 * @return the fitness.
	 */
	public double getFitness()
	{
		return fitness_;
	}
	
	protected void copySolutionData(Solution target)
	{
		for (int i = 0; i < objective_.length; i++)
			target.setObjective(i, objective_[i]);
		
		target.setCrowdingDistance(this.getCrowdingDistance());
		target.setFitness(this.getFitness());
		target.setRank(this.getRank());
		target.setLocation(this.getLocation());
	}

	public abstract int numberOfVariables();

	public abstract Solution clone();
	
	public abstract void randomize();
	
	public abstract double calculateDistance(Solution other);
	
	public abstract String getShortDescription();
}