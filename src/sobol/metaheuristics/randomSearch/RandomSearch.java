/**
 * RandomSearch.java
 * @author Juan J. Durillo
 * @version 1.0
 */
package sobol.metaheuristics.randomSearch;

import sobol.base.algorithm.Problem;
import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;
import sobol.metaheuristics.nsgaII.NonDominatedSolutionList;

public class RandomSearch
{
	private Problem problem_;

	public RandomSearch(Problem problem)
	{
		this.problem_ = problem;
	}

	public SolutionSet execute(int maxEvaluations)
	{
		NonDominatedSolutionList ndl = new NonDominatedSolutionList();

		for (int i = 0; i < maxEvaluations; i++)
		{
			Solution newSolution = problem_.newSolution();
			problem_.evaluate(newSolution);
			ndl.add(newSolution);
		}

		return ndl;
	}
}