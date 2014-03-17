package sobol.base.algorithm;

import sobol.base.solution.Solution;
import sobol.base.solutionType.SolutionType;

public abstract class Problem
{
	protected SolutionType solutionType_;
	protected int numberOfObjectives_;

	public Problem(SolutionType solutionType, int numberOfObjectives)
	{
		this.solutionType_ = solutionType;
		this.numberOfObjectives_ = numberOfObjectives;
	}

	public int getNumberOfObjectives()
	{
		return numberOfObjectives_;
	}

	public Solution newSolution()
	{
		return solutionType_.newSolution(this.numberOfObjectives_);
	}

	public Solution newSolution(Solution solution)
	{
		Solution s = solution.clone();

		for (int i = 0; i < this.numberOfObjectives_; i++)
			s.setObjective(i, solution.getObjective(i));
		
		s.setCrowdingDistance(solution.getCrowdingDistance());
		s.setFitness(solution.getFitness());
		s.setLocation(solution.getLocation());
		s.setRank(solution.getRank());
		return s;
	}

	public abstract void evaluate(Solution solution);
}