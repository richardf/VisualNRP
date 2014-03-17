package sobol.problems.portfolio.mono;

import java.io.PrintWriter;
import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.problems.portfolio.general.calculation.PortfolioCalculator;
import sobol.problems.portfolio.general.model.ProjetosCandidatos;

/**
 * Hill Climbing searcher for the portfolio selection problem
 * 
 * @author Marcio Barros
 */
public class HillClimbingPortfolio
{
	/**
	 * Infinite (too bad) fitness
	 */
	private static final double INFINITE = -1000000000000.0;

	/**
	 * Best solution found by the Hill Climbing search
	 */
	private boolean[] bestSolution;

	/**
	 * Fitness of the best solution found
	 */
	private double fitness;

	/**
	 * Number of random restart executed
	 */
	private int randomRestartCount;

	/**
	 * Number of the random restart where the best solution was found
	 */
	private int restartBestFound;

	/**
	 * File where details of the search process will be printed
	 */
	private PrintWriter detailsFile;

	/**
	 * Calculator used in the search process
	 */
	private PortfolioCalculator calculator;

	/**
	 * Available budget to run selected projects
	 */
	private double availableBudget;

	/**
	 * Number of projects in the candidate group under evaluation
	 */
	private int projectCount;

	/**
	 * Number of fitness evaluations available in the budget
	 */
	private int maxEvaluations;

	/**
	 * Number of fitness evaluations executed
	 */
	private int evaluations;

	/**
	 * Initializes the Hill Climbing search process
	 */
	public HillClimbingPortfolio(PrintWriter detailsFile, ProjetosCandidatos projects, int maxEvaluations) throws Exception
	{
		this.calculator = new PortfolioCalculator(projects);
		this.availableBudget = projects.getLimiteOrcamento(); 
		this.projectCount = projects.pegaNumeroProjetos();
		this.maxEvaluations = maxEvaluations;
		this.detailsFile = detailsFile;

		this.evaluations = 0;
		this.randomRestartCount = 0;
		this.restartBestFound = 0;
	}

	/**
	 * Initializes the Hill Climbing search process
	 */
	public HillClimbingPortfolio(ProjetosCandidatos projects, int maxEvaluations) throws Exception
	{
		this(null, projects, maxEvaluations);
	}

	/**
	 * Returns the number of random restarts executed during the search process
	 */
	public int getRandomRestarts()
	{
		return randomRestartCount;
	}

	/**
	 * Returns the number of the restart in which the best solution was found
	 */
	public int getRandomRestartBestFound()
	{
		return restartBestFound;
	}

	/**
	 * Returns the best solution found by the search process
	 */
	public boolean[] getBestSolution()
	{
		return bestSolution;
	}
	
	/**
	 * Returns the fitness of the best solution
	 */
	public double getFitness()
	{
		return fitness;
	}

	/**
	 * Prints a solution into a string
	 */
	public String printSolution(boolean[] solution)
	{
		String s = "[" + (solution[0] ? "S" : "-");

		for (int i = 1; i < solution.length; i++)
			s += " " + (solution[i] ? "S" : "-");

		return s + "]";
	}

	/**
	 * Copies a source solution to a target one
	 */
	private void copySolution(boolean[] source, boolean[] target)
	{
		for (int i = 0; i < projectCount; i++)
			target[i] = source[i];
	}

	/**
	 * Evaluates the fitness of a solution, saving detail information
	 */
	private double evaluate(boolean[] solution)
	{
		double cost = Math.abs(calculator.calculaCustoPortfolio(solution));
		double fit = INFINITE;

		if (cost <= availableBudget)
		{
			double earnings = calculator.calculaRetornoPortfolio(solution);
			double risk = calculator.calculaRiscoPortfolio(solution);
			fit = (risk == 0) ? earnings : cost * (earnings / risk);
		}

		if (++evaluations % 10000 == 0 && detailsFile != null)
			detailsFile.println(evaluations + "; " + fitness);

		return fit;
	}

	/**
	 * Runs a neighborhood visit starting from a given solution
	 */
	private NeighborhoodVisitorResult visitNeighbors(boolean[] solution)
	{
		double startingFitness = evaluate(solution);

		if (evaluations > maxEvaluations)
			return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);

		if (startingFitness > fitness)
			return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR, startingFitness);
		
		for (int i = 0; i < projectCount; i++)
		{
			solution[i] = !solution[i];
			double neighborFitness = evaluate(solution);

			if (evaluations > maxEvaluations)
				return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);

			if (neighborFitness > startingFitness)
				return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR, neighborFitness);

			solution[i] = !solution[i];
		}

		return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.NO_BETTER_NEIGHBOR);
	}

	/**
	 * Performs the local search starting from a given solution
	 */
	private boolean localSearch(boolean[] solution)
	{
		NeighborhoodVisitorResult result;
		
		do
		{
			result = visitNeighbors(solution);
			
			if (result.getStatus() == NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR && result.getNeighborFitness() > fitness)
			{
				copySolution(solution, bestSolution);
				this.fitness = result.getNeighborFitness();
				this.restartBestFound = randomRestartCount;
			}
		
		} while (result.getStatus() == NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR);
		
		return (result.getStatus() == NeighborhoodVisitorStatus.NO_BETTER_NEIGHBOR);
	}

	/**
	 * Creates a random solution
	 */
	private boolean[] createRandomSolution(AbstractRandomGenerator random)
	{
		boolean[] solution = new boolean[projectCount];
		double[] sample = random.randDouble();
		
		for (int i = 0; i < projectCount; i++)
			solution[i] = (sample[i] >= 0.5);
		
		return solution;
	}
	
	/**
	 * Executes the Hill Climbing search with random restarts
	 */
	public boolean[] execute() throws Exception
	{
		AbstractRandomGenerator random = RandomGeneratorFactory.createForPopulation(projectCount);

		this.bestSolution = createRandomSolution(random);
		this.fitness = evaluate(bestSolution);

		boolean[] solution = new boolean[projectCount];
		copySolution(bestSolution, solution);

		while (localSearch(solution))
		{			
			this.randomRestartCount++;		
			solution = createRandomSolution(random);
		}

		return bestSolution;
	}
}

/**
 * Possible results of the local search phase
 */
enum NeighborhoodVisitorStatus
{
	FOUND_BETTER_NEIGHBOR, NO_BETTER_NEIGHBOR, SEARCH_EXHAUSTED
}

/**
 * Class that represents the results of the local search phase
 */
class NeighborhoodVisitorResult
{
	/**
	 * Status in the end of the local search
	 */
	private NeighborhoodVisitorStatus status;

	/**
	 * Fitness of the best neighbor, in case one has been found
	 */
	private double neighborFitness;

	/**
	 * Initializes a successful local search status
	 * 
	 * @param status Status of the search, quite certainly a successful one
	 * @param fitness Fitness of the best neighbor found
	 */
	public NeighborhoodVisitorResult(NeighborhoodVisitorStatus status, double fitness)
	{
		this.status = status;
		this.neighborFitness = fitness;
	}

	/**
	 * Initializes an unsuccessful local search
	 * 
	 * @param status Status of the search: failure to find a better neighbor or
	 *            exhaustion
	 */
	public NeighborhoodVisitorResult(NeighborhoodVisitorStatus status)
	{
		this.status = status;
		this.neighborFitness = 0.0;
	}

	/**
	 * Returns the status of the local search
	 */
	public NeighborhoodVisitorStatus getStatus()
	{
		return status;
	}

	/**
	 * Return the fitness of the best neighbor found, if any
	 */
	public double getNeighborFitness()
	{
		return neighborFitness;
	}
}