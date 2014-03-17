package sobol.metaheuristics.nsgaII;

import sobol.base.solution.SolutionSet;

public interface Notifier
{
	void newIteration(int generations, int evaluations, SolutionSet front);
}