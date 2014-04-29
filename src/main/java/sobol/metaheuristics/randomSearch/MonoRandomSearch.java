package sobol.metaheuristics.randomSearch;

import sobol.base.algorithm.Problem;
import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;

public class MonoRandomSearch
{
	private Problem problem_;
	private int maxEvaluations;

	public MonoRandomSearch(Problem problem, int maxEvaluations)
	{
		this.problem_ = problem;
		this.maxEvaluations = maxEvaluations;
	}

	public SolutionSet execute()
	{
		Solution best = problem_.newSolution();
		problem_.evaluate(best);
		int evaluations = 1;

		while (evaluations < maxEvaluations)
		{
			Solution current = problem_.newSolution();
			problem_.evaluate(current);
			evaluations++;

			if (current.getObjective(0) < best.getObjective(0))
				best = current;
		}

		SolutionSet result = new SolutionSet(1);
		result.add(best);
		return result;
	}
}