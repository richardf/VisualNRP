/**
 * gGA.java
 * @author Antonio J. Nebro
 * @version 1.0
 */
package sobol.metaheuristics.singleObjectiveGA;

import java.util.Comparator;
import sobol.base.algorithm.Problem;
import sobol.base.comparator.ObjectiveComparator;
import sobol.base.crossover.CrossoverOperator;
import sobol.base.mutation.MutationOperator;
import sobol.base.selection.SelectionOperator;
import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;

/**
 * Class implementing a generational genetic algorithm
 */
public class gGA
{
	private Problem problem_;

	/**
	 * Constructor Create a new GGA instance.
	 * 
	 * @param problem Problem to solve.
	 */
	public gGA(Problem problem)
	{
		this.problem_ = problem;
	}

	/**
	 * Execute the GGA algorithm without a notifier
	 */
	public SolutionSet execute(int populationSize, int maxEvaluations, CrossoverOperator crossoverOperator, MutationOperator mutationOperator, SelectionOperator selectionOperator)
	{
		return execute(populationSize, maxEvaluations, crossoverOperator, mutationOperator, selectionOperator, null);
	}

	/**
	 * Execute the GGA algorithm given a notifier
	 */
	public SolutionSet execute(int populationSize, int maxEvaluations, CrossoverOperator crossoverOperator, MutationOperator mutationOperator, SelectionOperator selectionOperator, Notifier notifier)
	{
		// Single objective comparator
		Comparator<Solution> comparator = new ObjectiveComparator(0);

		// Initialize the variables
		SolutionSet population = new SolutionSet(populationSize);
		SolutionSet offspringPopulation = new SolutionSet(populationSize);
		int evaluations = 0;

		// Create the initial population
		for (int i = 0; i < populationSize; i++)
		{
			Solution newIndividual = problem_.newSolution();
			problem_.evaluate(newIndividual);
			evaluations++;
			population.add(newIndividual);
		}

		// Sort population
		population.sort(comparator);

		while (evaluations < maxEvaluations)
		{
			// Copy the best two individuals to the offspring population
			offspringPopulation.add(population.get(0).clone());
			offspringPopulation.add(population.get(1).clone());

			// Reproductive cycle
			for (int i = 0; i < (populationSize / 2 - 1); i++)
			{
				// Selection
				Solution parent0 = selectionOperator.execute(population);
				Solution parent1 = selectionOperator.execute(population);

				// Crossover
				Solution[] offspring = crossoverOperator.execute(parent0, parent1);

				// Mutation
				mutationOperator.execute(offspring[0]);
				mutationOperator.execute(offspring[1]);

				// Evaluation of the new individual
				problem_.evaluate(offspring[0]);
				problem_.evaluate(offspring[1]);
				evaluations += 2;

				// Replacement: the two new individuals are inserted in the offspring population
				offspringPopulation.add(offspring[0]);
				offspringPopulation.add(offspring[1]);
			}

			// The offspring population becomes the new current population
			population.clear();

			for (int i = 0; i < populationSize; i++)
				population.add(offspringPopulation.get(i));

			offspringPopulation.clear();
			population.sort(comparator);

			if (notifier != null)
				notifier.newIteration(evaluations, population.get(0));
		}

		// Return a population with the best individual
		SolutionSet resultPopulation = new SolutionSet(1);
		resultPopulation.add(population.get(0));
		return resultPopulation;
	}
}