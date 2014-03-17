package sobol.problems.requirements.hc;

import java.io.PrintWriter;
import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.random.pseudo.PseudoRandomGeneratorFactory;
import sobol.problems.requirements.model.Project;

/**
 * Hill Climbing searcher for the next release problem
 * 
 * @author Marcio Barros
 */
public class HillClimbing
{
	/**
	 * Order under which requirements will be accessed
	 */
	private int[] selectionOrder;	
	
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
	 * Set of requirements to be optimized
	 */
	private Project project;

	/**
	 * Available budget to select requirements
	 */
	private int availableBudget;

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
	public HillClimbing(PrintWriter detailsFile, Project project, int budget, int maxEvaluations) throws Exception
	{
		this.project = project;
		this.availableBudget = budget; 
		this.maxEvaluations = maxEvaluations;
		this.detailsFile = detailsFile;
		this.evaluations = 0;
		this.randomRestartCount = 0;
		this.restartBestFound = 0;
		
		//createDefaultSelectionOrder(project);
		createRandomSelectionOrder(project);
	}

	/**
	 * Gera a ordem default de seleção dos requisitos
	 */
	protected void createDefaultSelectionOrder(Project project)
	{
		int customerCount = project.getCustomerCount();
		this.selectionOrder = new int[customerCount];
		
		for (int i = 0; i < customerCount; i++)
			this.selectionOrder[i] = i;
	}	

	/**
	 * Gera uma ordem aleatória de seleção dos requisitos
	 */
	protected void createRandomSelectionOrder(Project project)
	{
		int customerCount = project.getCustomerCount();
		int[] temporaryOrder = new int[customerCount];
		
		for (int i = 0; i < customerCount; i++)
			temporaryOrder[i] = i;

		this.selectionOrder = new int[customerCount];
		PseudoRandomGeneratorFactory factory = new PseudoRandomGeneratorFactory();
		AbstractRandomGenerator generator = factory.create(customerCount);
		double[] random = generator.randDouble();
		
		for (int i = 0; i < customerCount; i++)
		{
			int index = (int)(random[i] * (customerCount - i));
			this.selectionOrder[i] = temporaryOrder[index];
			
			for (int j = index; j < customerCount-1; j++)
				temporaryOrder[j] = temporaryOrder[j+1];
		}
		
		for (int i = 0; i < customerCount; i++)
		{
			boolean achou = false;
			
			for (int j = 0; j < customerCount && !achou; j++)
				if (this.selectionOrder[j] == i)
					achou = true;
			
			if (!achou)
				System.out.println("ERRO DE GERACAO DE INICIO ALEATORIO");
		}
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
		int len = source.length;
		
		for (int i = 0; i < len; i++)
			target[i] = source[i];
	}

	/**
	 * Evaluates the fitness of a solution, saving detail information
	 */
	private double evaluate(Solution solution)
	{
		if (++evaluations % 10000 == 0 && detailsFile != null)
			detailsFile.println(evaluations + "; " + fitness);

		int cost = solution.getCost();
		return (cost <= availableBudget) ? solution.getProfit() : -cost;
	}

	/**
	 * Runs a neighborhood visit starting from a given solution
	 */
	private NeighborhoodVisitorResult visitNeighbors(Solution solution)
	{
		double startingFitness = evaluate(solution);

		if (evaluations > maxEvaluations)
			return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);

		if (startingFitness > fitness)
			return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR, startingFitness);
		
		int len = project.getCustomerCount();
		
		for (int i = 0; i < len; i++)
		{
			int customerI = selectionOrder[i];

			solution.flipCustomer(customerI);
			//solution[moduloI] = !solution[moduloI];
			double neighborFitness = evaluate(solution);

			if (evaluations > maxEvaluations)
				return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);

			if (neighborFitness > startingFitness)
				return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR, neighborFitness);

			solution.flipCustomer(customerI);
			//solution[moduloI] = !solution[moduloI];
		}

		return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.NO_BETTER_NEIGHBOR);
	}

	/**
	 * Performs the local search starting from a given solution
	 */
	private boolean localSearch(boolean[] solution)
	{
		NeighborhoodVisitorResult result;
		Solution hcrs = new Solution(project);
		hcrs.setAllCustomers(solution);
		
		do
		{
			result = visitNeighbors(hcrs);
			
			if (result.getStatus() == NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR && result.getNeighborFitness() > fitness)
			{
				copySolution(hcrs.getSolution(), bestSolution);
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
		int customerCount = project.getCustomerCount();
		boolean[] solution = new boolean[customerCount];
		double[] sample = random.randDouble();
		
		for (int i = 0; i < customerCount; i++)
			solution[i] = (sample[i] >= 0.5);
		
		return solution;
	}
	
	/**
	 * Executes the Hill Climbing search with random restarts
	 */
	public boolean[] execute() throws Exception
	{
		int customerCount = project.getCustomerCount();
		AbstractRandomGenerator random = RandomGeneratorFactory.createForPopulation(customerCount);

		this.bestSolution = createRandomSolution(random);
		Solution hcrs = new Solution(project);
		hcrs.setAllCustomers(bestSolution);
		this.fitness = evaluate(hcrs);

		boolean[] solution = new boolean[customerCount];
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