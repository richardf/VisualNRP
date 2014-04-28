/**
 * SPEA2.java
 * @author Juan J. Durillo
 * @version 1.0
 */

package sobol.metaheuristics.spea2;

import javax.management.JMException;
import sobol.base.algorithm.Problem;
import sobol.base.crossover.CrossoverOperator;
import sobol.base.mutation.MutationOperator;
import sobol.base.selection.SelectionOperator;
import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;
import sobol.metaheuristics.nsgaII.Notifier;
import sobol.metaheuristics.nsgaII.Ranking;

/**
 * This class representing the SPEA2 algorithm
 */
public class SPEA2
{
	/**
	 * Defines the number of tournaments for creating the mating pool
	 */
	public static final int TOURNAMENTS_ROUNDS = 1;

	/**
	 * Stores the problem to solve
	 */
	private Problem problem_;

	/**
	 * Constructor. Create a new SPEA2 instance
	 * 
	 * @param problem Problem to solve
	 */
	public SPEA2(Problem problem)
	{
		this.problem_ = problem;
	}

	/**
	 * Runs of the Spea2 algorithm.
	 * 
	 * @return a <code>SolutionSet</code> that is a set of non dominated
	 *         solutions as a result of the algorithm execution
	 * @throws JMException
	 */
	public SolutionSet execute(int populationSize, int archiveSize, int maxEvaluations, CrossoverOperator crossoverOperator, MutationOperator mutationOperator, SelectionOperator selectionOperator, Notifier notifier) throws JMException, ClassNotFoundException
	{
		// Initialize the variables
		SolutionSet solutionSet = new SolutionSet(populationSize);
		SolutionSet archive = new SolutionSet(archiveSize);
		int evaluations = 0;
		int generations = 0;

		// -> Create the initial solutionSet
		for (int i = 0; i < populationSize; i++)
		{
			Solution newSolution = problem_.newSolution();
			problem_.evaluate(newSolution);
			evaluations++;
			solutionSet.add(newSolution);
		}

		while (evaluations < maxEvaluations)
		{
			SolutionSet union = ((SolutionSet) solutionSet).union(archive);
			Spea2Fitness spea = new Spea2Fitness(union);
			spea.fitnessAssign();
			archive = spea.environmentalSelection(archiveSize);
			
			// Create a new offspringPopulation
			SolutionSet offSpringSolutionSet = new SolutionSet(populationSize);
			Solution parent0, parent1;
			
			while (offSpringSolutionSet.size() < populationSize)
			{
				int j = 0;

				do
				{
					j++;
					parent0 = (Solution) selectionOperator.execute(archive);
				} while (j < SPEA2.TOURNAMENTS_ROUNDS);
				
				int k = 0;
				
				do
				{
					k++;
					parent1 = (Solution) selectionOperator.execute(archive);
				} while (k < SPEA2.TOURNAMENTS_ROUNDS);

				// make the crossover
				Solution[] offSpring = (Solution[]) crossoverOperator.execute(parent0, parent1);
				mutationOperator.execute(offSpring[0]);
				problem_.evaluate(offSpring[0]);
				offSpringSolutionSet.add(offSpring[0]);
				evaluations++;
			}

			solutionSet = offSpringSolutionSet;

			if (notifier != null)
				notifier.newIteration(generations++, evaluations, solutionSet);
		}

		Ranking ranking = new Ranking(archive);
		return ranking.getSubfront(0);
	}
}