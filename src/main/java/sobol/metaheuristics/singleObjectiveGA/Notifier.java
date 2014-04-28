package sobol.metaheuristics.singleObjectiveGA;

import sobol.base.solution.Solution;

public interface Notifier
{
	void newIteration(int number, Solution best);
}