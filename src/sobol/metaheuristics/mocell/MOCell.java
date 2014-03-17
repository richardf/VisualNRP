package sobol.metaheuristics.mocell;

import java.util.Comparator;
import sobol.base.algorithm.Problem;
import sobol.base.comparator.CrowdingComparator;
import sobol.base.comparator.DominanceComparator;
import sobol.base.crossover.CrossoverOperator;
import sobol.base.mutation.MutationOperator;
import sobol.base.selection.SelectionOperator;
import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;
import sobol.metaheuristics.nsgaII.Distance;
import sobol.metaheuristics.nsgaII.Ranking;

/**
 * This class represents an asynchronous version of MOCell algorithm, combining
 * aMOCell2 and aMOCell3.
 */
public class MOCell
{
	private Problem problem_;

	public MOCell(Problem problem)
	{
		this.problem_ = problem;
	}

	/**
	 * Execute the algorithm
	 */
	public SolutionSet execute(int populationSize, int archiveSize, int maxEvaluations, CrossoverOperator crossoverOperator, MutationOperator mutationOperator, SelectionOperator selectionOperator)
	{
		// Init the parameters
		int evaluations;
		SolutionSet currentPopulation;
		CrowdingArchive archive;
		SolutionSet[] neighbors;
		Neighborhood neighborhood;
		Comparator<Solution> dominance = new DominanceComparator();
		Comparator<Solution> crowdingComparator = new CrowdingComparator();
		Distance distance = new Distance();

		// Initialize the variables
		currentPopulation = new SolutionSet(populationSize);
		archive = new CrowdingArchive(archiveSize, problem_.getNumberOfObjectives());
		evaluations = 0;
		neighborhood = new Neighborhood(populationSize);
		neighbors = new SolutionSet[populationSize];

		// Create the initial population
		for (int i = 0; i < populationSize; i++)
		{
			Solution individual = problem_.newSolution();
			problem_.evaluate(individual);
			currentPopulation.add(individual);
			individual.setLocation(i);
			evaluations++;
		}

		// Main loop
		while (evaluations < maxEvaluations)
		{
			for (int ind = 0; ind < currentPopulation.size(); ind++)
			{
				Solution individual = problem_.newSolution(currentPopulation.get(ind));

				Solution[] parents = new Solution[2];
				Solution[] offSpring;

				neighbors[ind] = neighborhood.getEightNeighbors(currentPopulation, ind);
				neighbors[ind].add(individual);

				// parents
				parents[0] = (Solution) selectionOperator.execute(neighbors[ind]);

				if (archive.size() > 0)
					parents[1] = (Solution) selectionOperator.execute(archive);
				else
					parents[1] = (Solution) selectionOperator.execute(neighbors[ind]);

				// Create a new individual, using genetic operators mutation and
				// crossover
				offSpring = (Solution[]) crossoverOperator.execute(parents[0], parents[1]);
				mutationOperator.execute(offSpring[0]);

				// Evaluate individual an his constraints
				problem_.evaluate(offSpring[0]);
				evaluations++;

				int flag = dominance.compare(individual, offSpring[0]);

				if (flag == 1)
				{
					offSpring[0].setLocation(individual.getLocation());
					currentPopulation.replace(offSpring[0].getLocation(), offSpring[0]);
					archive.add(problem_.newSolution(offSpring[0]));
				}
				else if (flag == 0)
				{
					neighbors[ind].add(offSpring[0]);
					offSpring[0].setLocation(-1);
					Ranking rank = new Ranking(neighbors[ind]);
					
					for (int j = 0; j < rank.getNumberOfSubfronts(); j++)
						distance.crowdingDistanceAssignment(rank.getSubfront(j), problem_.getNumberOfObjectives());
					
					neighbors[ind].sort(crowdingComparator);
					Solution worst = neighbors[ind].get(neighbors[ind].size() - 1);

					if (worst.getLocation() == -1)
					{
						archive.add(problem_.newSolution(offSpring[0]));
					}
					else
					{
						offSpring[0].setLocation(worst.getLocation());
						currentPopulation.replace(offSpring[0].getLocation(), offSpring[0]);
						archive.add(problem_.newSolution(offSpring[0]));
					}
				}
			}
		}

		return archive;
	}
}