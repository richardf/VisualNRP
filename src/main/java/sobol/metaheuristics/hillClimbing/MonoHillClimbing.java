package sobol.metaheuristics.hillClimbing;

import sobol.base.algorithm.Problem;
import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;
import sobol.base.visitor.NeighborVisitor;

public class MonoHillClimbing
{
	private Problem problem;
	private NeighborVisitor visitor;
	private int maxEvaluations;
	private boolean randomRestart;
	private int restarts;
	private Notifier notifier;

	/**
	 * Inicializa o hill climbing sem random restart
	 */
	public MonoHillClimbing(Problem problem, NeighborVisitor visitor, int maxEvaluations, Notifier notifier)
	{
		this(problem, visitor, maxEvaluations, false, notifier);
	}
	
	/**
	 *  Inicializa o hill climbing, possivelmente com random restart
	 */
	public MonoHillClimbing(Problem problem, NeighborVisitor visitor, int maxEvaluations, boolean randomRestart, Notifier notifier)
	{
		this.problem = problem;
		this.visitor = visitor;
		this.maxEvaluations = maxEvaluations;
		this.randomRestart = randomRestart;
		this.notifier = notifier;
	}

	/**
	 * Retorna o número de restarts do processo de cálculo
	 * @return
	 */
	public double getRestartCount()
	{
		return restarts;
	}
	
	/**
	 * Executa a busca heurística HC
	 */
	public SolutionSet execute()
	{
		Solution bestIndividual = problem.newSolution();
		problem.evaluate(bestIndividual);
		int evaluations = 1;
		
		Solution currentIndividual = bestIndividual;
		boolean restartRequired = false; 
		restarts = 0;

		while (evaluations < maxEvaluations)
		{
			if (restartRequired)
			{
				if (!randomRestart)
					break;
				
				currentIndividual = problem.newSolution();
				problem.evaluate(currentIndividual);
				evaluations++;

				if (currentIndividual.getObjective(0) < bestIndividual.getObjective(0))
					bestIndividual = currentIndividual;
				
				if (notifier != null)
					notifier.newIteration(evaluations, bestIndividual.getObjective(0));
				
				restarts++;
			}

			int neighborCount = visitor.neighborCount(currentIndividual);
			restartRequired = true;

			for (int i = 0; i < neighborCount; i++)
			{
				Solution neighbor = visitor.getNeighbor(currentIndividual, i);
				problem.evaluate(neighbor);
				evaluations++;
				
				if (neighbor.getObjective(0) < bestIndividual.getObjective(0))
				{
					restartRequired = false;
					bestIndividual = neighbor;
				}

				if (notifier != null)
					notifier.newIteration(evaluations, bestIndividual.getObjective(0));
			}
		}

		// Return a population with the best individual
		SolutionSet resultPopulation = new SolutionSet(1);
		resultPopulation.add(bestIndividual);
		return resultPopulation;
	}
}