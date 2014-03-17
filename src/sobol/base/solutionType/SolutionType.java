package sobol.base.solutionType;

import sobol.base.solution.Solution;

public abstract class SolutionType
{
	public abstract int getSolutionSize();
	
	public abstract Solution newSolution(int numberOfObjectives_);
}