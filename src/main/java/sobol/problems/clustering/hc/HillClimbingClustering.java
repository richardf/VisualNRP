package sobol.problems.clustering.hc;

import java.io.PrintWriter;
import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.random.pseudo.PseudoRandomGeneratorFactory;
import sobol.problems.clustering.generic.calculator.ICalculadorIncremental;
import sobol.problems.clustering.generic.model.Project;

/**
 * Hill Climbing searcher for the clustering problem
 * 
 * @author Marcio Barros
 */
public class HillClimbingClustering
{
	/**
	 * Selection order for the modules being moved
	 */
	private int[] selectionOrder;
	
	/**
	 * Best solution found by the Hill Climbing search
	 */
	private int[] bestSolution;

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
	private ICalculadorIncremental calculator;

	/**
	 * Number of classes in the project under evaluation
	 */
	private int classCount;

	/**
	 * Number of packages in the project under evaluation
	 */
	private int packageCount;

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
	 * 
	 * @param detailsFile File where details of the search process will be saved
	 * @param project Project whose classes will be distributed into clusters
	 * @param maxEvaluations Budget of fitness evaluations
	 */
	public HillClimbingClustering(PrintWriter detailsFile, ICalculadorIncremental calculador, Project project, int maxEvaluations) throws Exception
	{
		this.classCount = project.getClassCount();
		this.packageCount = classCount;
		this.calculator = calculador;
		this.maxEvaluations = maxEvaluations;
		this.detailsFile = detailsFile;
		
		createDefaultSelectionOrder(project);
		//createRandomSelectionOrder(project);

		this.evaluations = 0;
		this.randomRestartCount = 0;
		this.restartBestFound = 0;
	}

	/**
	 * Initializes the Hill Climbing search process
	 * 
	 * @param project Project whose classes will be distributed into clusters
	 * @param maxEvaluations Budget of fitness evaluations
	 */
	public HillClimbingClustering(ICalculadorIncremental calculador, Project project, int maxEvaluations) throws Exception
	{
		this(null, calculador, project, maxEvaluations);
	}

	/**
	 * Gera a ordem default de seleção dos módulos
	 */
	protected void createDefaultSelectionOrder(Project project)
	{
		int classCount = project.getClassCount();
		this.selectionOrder = new int[classCount];
		
		for (int i = 0; i < classCount; i++)
			this.selectionOrder[i] = i;
	}	

	/**
	 * Gera uma ordem aleatória de seleção dos módulos
	 */
	protected void createRandomSelectionOrder(Project project)
	{
		int classCount = project.getClassCount();
		int[] temporaryOrder = new int[classCount];
		
		for (int i = 0; i < classCount; i++)
			temporaryOrder[i] = i;

		this.selectionOrder = new int[classCount];
		PseudoRandomGeneratorFactory factory = new PseudoRandomGeneratorFactory();
		AbstractRandomGenerator generator = factory.create(classCount);
		double[] random = generator.randDouble();
		
		for (int i = 0; i < classCount; i++)
		{
			int index = (int)(random[i] * (classCount - i));
			this.selectionOrder[i] = temporaryOrder[index];
			
			for (int j = index; j < classCount-1; j++)
				temporaryOrder[j] = temporaryOrder[j+1];
		}
		
		for (int i = 0; i < classCount; i++)
		{
			boolean achou = false;
			
			for (int j = 0; j < classCount && !achou; j++)
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
	public int[] getBestSolution()
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
	public String printSolution(int[] solution)
	{
		String s = "[" + solution[0];

		for (int i = 1; i < solution.length; i++)
			s += " " + solution[i];

		return s + "]";
	}

	/**
	 * Copies a source solution to a target one
	 */
	private void copySolution(int[] source, int[] target)
	{
		for (int i = 0; i < classCount; i++)
			target[i] = source[i];
	}

	/**
	 * Evaluates the fitness of a solution, saving detail information
	 */
	private double evaluate()
	{
		double fit = calculator.evaluate();

		if (++evaluations % 10000 == 0 && detailsFile != null)
			detailsFile.println(evaluations + "; " + fitness);

		return fit;
	}

	/**
	 * Runs a neighborhood visit starting from a given solution
	 */
	private NeighborhoodVisitorResult visitNeighbors(int[] solution)
	{
		this.calculator.moveAll(solution);
		double startingFitness = evaluate();

		if (evaluations > maxEvaluations)
			return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);

		if (startingFitness > fitness)
			return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR, startingFitness);
		
		for (int i = 0; i < classCount; i++)
		{
			for (int j = 0; j < packageCount; j++)
			{
				int moduloI = selectionOrder[i];
				int moduloJ = selectionOrder[j];
				
				if (solution[moduloI] != moduloJ)
				{
					calculator.moveClass(moduloI, moduloJ);
					double neighborFitness = evaluate();

					if (evaluations > maxEvaluations)
						return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.SEARCH_EXHAUSTED);

					if (neighborFitness > startingFitness)
					{
						solution[moduloI] = moduloJ;
						return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.FOUND_BETTER_NEIGHBOR, neighborFitness);
					}
					else
						calculator.moveClass(moduloI, solution[moduloI]);
				}
			}
		}

		return new NeighborhoodVisitorResult(NeighborhoodVisitorStatus.NO_BETTER_NEIGHBOR);
	}

	/**
	 * Performs the local search starting from a given solution
	 */
	private boolean localSearch(int[] solution)
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
	 * Executes the Hill Climbing search with random restarts
	 */
	public int[] execute() throws Exception
	{
		AbstractRandomGenerator random = RandomGeneratorFactory.createForPopulation(classCount);

		this.bestSolution = random.randInt(0, packageCount - 1);
		this.calculator.moveAll(bestSolution);
		this.fitness = evaluate();

		int[] solution = new int[classCount];
		copySolution(bestSolution, solution);

		while (localSearch(solution))
		{			
			this.randomRestartCount++;			
			solution = random.randInt(0, packageCount - 1);
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