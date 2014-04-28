/**
 * NSGAII.java
 * @author Juan J. Durillo
 * @version 1.0  
 */
package sobol.metaheuristics.nsgaII;

import sobol.base.algorithm.Problem;
import sobol.base.comparator.CrowdingComparator;
import sobol.base.crossover.CrossoverOperator;
import sobol.base.mutation.MutationOperator;
import sobol.base.selection.SelectionOperator;
import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;

/**
 * This class implements the NSGA-II algorithm.
 * 
 * ---------------------------------------------------
 * 
 * A implementação original do JMETAL permitia soluções duplicadas e, com isto, perdia muitas
 * soluções boas que ficavam com crowding distance ruim ... fiz uma mudança para eliminar as
 * soluções duplicadas mas, ainda assim, algumas são perdidas porque o arquivo é pequeno.
 * Penso em mudar isto, mas não seria mais um NSGA ... 
 * 
 * ---------------------------------------------------
 */
public class NSGAII
{
	private Problem problem_;

	/**
	 * Constructor
	 */
	public NSGAII(Problem problem)
	{
		this.problem_ = problem;
	}

	/**
	 * Runs the NSGA-II algorithm.
	 */
	public SolutionSet execute(int populationSize, int maxEvaluations, CrossoverOperator crossoverOperator, MutationOperator mutationOperator, SelectionOperator selectionOperator, Notifier notifier)
	{
		SolutionSet population = new SolutionSet(populationSize);
		Distance distance = new Distance();
		int evaluations = 0;
		int generations = 0;

		// Create the initial solutionSet
		for (int i = 0; i < populationSize; i++)
		{
			Solution newSolution = problem_.newSolution();
			problem_.evaluate(newSolution);
			evaluations++;
			population.add(newSolution);
		}

		// Generations ...
		while (evaluations < maxEvaluations)
		{
			// Create the offSpring solutionSet
			SolutionSet offspringPopulation = new SolutionSet(populationSize);

			for (int i = 0; i < (populationSize / 2) && evaluations < maxEvaluations; i++)
			{
				Solution parent0 = selectionOperator.execute(population);
				Solution parent1 = selectionOperator.execute(population);

				Solution[] offSpring = crossoverOperator.execute(parent0, parent1);
				mutationOperator.execute(offSpring[0]);
				mutationOperator.execute(offSpring[1]);
				
				problem_.evaluate(offSpring[0]);
				problem_.evaluate(offSpring[1]);
				evaluations += 2;

				offspringPopulation.add(offSpring[0]);
				offspringPopulation.add(offSpring[1]);
			}

			// Create the solutionSet union of solutionSet and offSpring
			SolutionSet union = population.union(offspringPopulation);

			// Ranking the union
			Ranking ranking = new Ranking(union);
			int remain = populationSize;
			int index = 0;
			population.clear();

			// Obtain the next front
			SolutionSet front = ranking.getSubfront(index);

			while ((index < ranking.getNumberOfSubfronts()) && (remain > 0) && (remain >= front.size()))
			{
				// Assign crowding distance to individuals
				distance.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());

				// Add the individuals of this front
				for (int k = 0; k < front.size(); k++)
					population.add(front.get(k));

				// Decrement remain
				remain = remain - front.size();

				// Obtain the next front
				index++;

				if (remain > 0 && index < ranking.getNumberOfSubfronts())
					front = ranking.getSubfront(index);
			}

			// Remain is less than front(index).size, insert only the best one
			if ((index < ranking.getNumberOfSubfronts()) && (remain > 0) && (remain < front.size()))
			{
				// front contains individuals to insert
				distance.crowdingDistanceAssignment(front, problem_.getNumberOfObjectives());
				front.sort(new CrowdingComparator());

				for (int k = 0; k < remain; k++)
					population.add(front.get(k));

				//if (index == 0)
				//	System.out.println("*** " + (front.size() - remain) + " solutions are being thrown out ...");
				
				remain = 0;
			}
			else if (remain > 0)
			{
				for (int k = 0; k < remain; k++)
				{
					Solution newSolution = problem_.newSolution();
					problem_.evaluate(newSolution);
					evaluations++;
					population.add(newSolution);
				}

				remain = 0;
			}

			if (notifier != null)
				notifier.newIteration(generations++, evaluations, population);
		}

		// Return the first non-dominated front
		Ranking ranking = new Ranking(population);
		return ranking.getSubfront(0);
	}

	/**
	 * Runs the NSGA-II algorithm.
	 */
	public SolutionSet execute(int populationSize, int maxEvaluations, CrossoverOperator crossoverOperator, MutationOperator mutationOperator, SelectionOperator selectionOperator)
	{
		return execute(populationSize, maxEvaluations, crossoverOperator, mutationOperator, selectionOperator, null);
	}
}