package sobol.metaheuristics.hillClimbing;

import java.util.ArrayList;
import java.util.List;
import sobol.base.algorithm.Problem;
import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;
import sobol.base.visitor.NeighborVisitor;
import sobol.metaheuristics.nsgaII.NonDominatedSolutionList;

public class HillClimbing
{
	private Problem problem;
	private NeighborVisitor visitor;
	private int maxEvaluations;

	/**
	 * Constructor
	 * 
	 * @param problem Problem to solve
	 */
	public HillClimbing(Problem problem, NeighborVisitor visitor, int maxEvaluations)
	{
		this.problem = problem;
		this.visitor = visitor;
		this.maxEvaluations = maxEvaluations;
	}
	
	/**
	 * Runs the algorithm.
	 */
	public SolutionSet execute()
	{
		NonDominatedSolutionList ndl = new NonDominatedSolutionList();
		List<Solution> pending = new ArrayList<Solution>();
		int pendingCount = 0;
		int pendingWalker = 0;
		int evaluations = 0;

		while (evaluations < maxEvaluations)
		{
			if (pendingCount <= pendingWalker)
			{
				Solution solution = problem.newSolution();
				problem.evaluate(solution);
				evaluations++;
				
				if (ndl.add(solution))
				{
					pending.add(solution);
					pendingCount++;
				}
			}
			
			if (pendingCount > pendingWalker)
			{
				Solution current = pending.get(pendingWalker);
				pendingWalker++;
				
				int neighborCount = visitor.neighborCount(current);
	
				for (int i = 0; i < neighborCount; i++)
				{
					Solution neighbor = visitor.getNeighbor(current, i);
					problem.evaluate(neighbor);
					evaluations++;
					
					if (ndl.add(neighbor))
					{
						pending.add(neighbor);
						pendingCount++;
					}
				}
			}
		}

		return ndl;
	}
}