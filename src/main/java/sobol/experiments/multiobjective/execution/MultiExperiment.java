package sobol.experiments.multiobjective.execution;

import java.util.Vector;
import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;

@SuppressWarnings("unused")
public abstract class MultiExperiment<InstanceClass>
{
	private static int NON_DOMINATING = 0;
	private static int DOMINATED = 1;
	private static int DOMINATOR = 2;
	
	private Vector<MultiExperimentListener> listeners;

	/**
	 * Inicializa um experimento
	 */
	public MultiExperiment()
	{
		this.listeners = new Vector<MultiExperimentListener>();
	}
	
	/**
	 * Adiciona um listener no experimento
	 */
	public void addListerner(MultiExperimentListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Prepara a execução do experimento
	 */
	public void prepareExperiment() throws Exception
	{
		for (MultiExperimentListener listener : listeners)
			listener.prepareExperiment();
	}

	/**
	 * Termina a execução do experimento
	 */
	public void terminateExperiment() throws Exception
	{
		for (MultiExperimentListener listener : listeners)
			listener.terminateExperiment();
	}

	/**
	 * Prepara a execução de uma instância
	 * 
	 * @param instanceNumber	Número da instância
	 */
	public void prepareInstance(int instanceNumber) throws Exception
	{
		for (MultiExperimentListener listener : listeners)
			listener.prepareInstance(instanceNumber);
	}

	/**
	 * Termina a execução de uma instância
	 * 
	 * @param instanceNumber		Número de instância
	 * @param instanceFrontier		Fronteira eficiente da instância (final)
	 */
	public void terminateInstance(int instanceNumber, Vector<Solution> instanceFrontier) throws Exception
	{
		for (MultiExperimentListener listener : listeners)
			listener.terminateInstance(instanceNumber, instanceFrontier);
	}

	/**
	 * Publica os resultados de um ciclo
	 * 
	 * @param cycleNumber		Número do ciclo
	 * @param executionTime		Tempo de execução do ciclo
	 * @param cycleFrontier		Fronteira eficiente do ciclo
	 * @param instanceFrontier	Fronteira eficiente da instância (atual)
	 */
	public void publishCycle(int cycleNumber, int instanceNumber, long executionTime, Vector<Solution> cycleFrontier, Vector<Solution> instanceFrontier) throws Exception
	{
		for (MultiExperimentListener listener : listeners)
			listener.publishCycle(cycleNumber, instanceNumber, executionTime, cycleFrontier, instanceFrontier);
	}
	
	/**
	 * Verifica se duas soluções são idênticas
	 * 
	 * @param solution1		Primeira solução
	 * @param solution2		Segunda solução
	 */
	private boolean identicalSolutions(Solution solution1, Solution solution2)
	{
		return solution1.calculateDistance(solution2) < 1E-10;
	}

	/**
	 * Verifica se duas soluções tem os mesmos objetivos
	 * 
	 * @param solution1		Primeira solução
	 * @param solution2		Segunda solução
	 * @param tolerance		Diferença tolerável entre os requisitos
	 */
	private boolean identicalObjectives(Solution solution1, Solution solution2, double tolerance)
	{
		if (solution1.numberOfObjectives() != solution2.numberOfObjectives())
			return false;
		
		for (int i = 0; i < solution1.numberOfObjectives(); i++)
		{
			double objective1 = solution1.getObjective(i);
			double objective2 = solution2.getObjective(i);
			
			if (Math.abs(objective1 - objective2) > tolerance)
				return false;
		}
		
		return true;
	}
	
	/**
	 * Verifica se uma lista de soluções contém uma nova solução
	 * 
	 * @param solutions		Lista de soluções
	 * @param newSolution	Nova solução que será verificada
	 */
	private boolean containsIdenticalSolution(Vector<Solution> solutions, Solution newSolution)
	{
		for (int i = 0; i < solutions.size(); i++)
		{
			Solution current = solutions.get(i);
			
			if (identicalSolutions(current, newSolution))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Verifica se uma lista de soluções contém uma solução com os mesmos objetivos de uma nova
	 * 
	 * @param solutions		Lista de soluções
	 * @param newSolution	Nova solução que será verificada
	 * @param tolerance		Diferença tolerável entre os requisitos
	 */
	private boolean checkDuplicateObjectives(Vector<Solution> solutions, Solution newSolution, double tolerance)
	{
		for (int i = 0; i < solutions.size(); i++)
		{
			Solution current = solutions.get(i);
			
			if (identicalObjectives(current, newSolution, tolerance))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Retorna o conjunto de soluções únicas de um resultado
	 * 
	 * @param result		Conjunto de soluções
	 */
	private Vector<Solution> getUniqueSolutions(SolutionSet result)
	{
		Vector<Solution> uniqueSolutions = new Vector<Solution>();

		for (int i = 0; i < result.size(); i++)
		{
			Solution solution = result.get(i);
			
			if (!containsIdenticalSolution(uniqueSolutions, solution))
				uniqueSolutions.add(solution);
		}
		
		return uniqueSolutions;
	}
	
	/**
	 * Retorna o conjunto de soluções com objetivos únicos
	 * 
	 * @param result		Conjunto de soluções
	 * @param tolerance		Diferença tolerável entre os requisitos
	 */
	private Vector<Solution> getUniqueObjectives(SolutionSet result, double tolerance)
	{
		Vector<Solution> unique = new Vector<Solution>();

		for (int i = 0; i < result.size(); i++)
		{
			Solution solution = result.get(i);
			
			if (!checkDuplicateObjectives(unique, solution, tolerance))
				unique.add(solution);
		}
		
		return unique;
	}

	/**
	 * Verifica a dominação entre duas soluções
	 * 
	 * @param current		Solução atual
	 * @param newSolution	Nova solução
	 */
	private int checkDomination (Solution current, Solution newSolution)
	{
		boolean currentAlwaysDominated = true;
		boolean newAlwaysDominated = true;
		
		for (int i = 0; i < current.numberOfObjectives(); i++)
		{
			double currentObjective = current.getObjective(i);
			double newObjective = newSolution.getObjective(i);
			double difference = currentObjective - newObjective;
		
			if (difference < -0.001)
				newAlwaysDominated = false;

			if (difference > 0.001)
				currentAlwaysDominated = false;
				
			if (!currentAlwaysDominated && !newAlwaysDominated)
				return NON_DOMINATING;
		}
				
		if (currentAlwaysDominated)
			return DOMINATED;
				
		return DOMINATOR;
	} 

	/**
	 * Agrupa duas fronteiras eficientes, segundo seus objetivos
	 * 
	 * @param frontier1		Primeira fronteira eficiente
	 * @param frontier2		Segunda fronteira eficiente
	 */
	public void mergeFrontiers(Vector<Solution> frontier1, Vector<Solution> frontier2)
	{
		for (int i = 0; i < frontier2.size(); i++)
		{
			Solution newSolution = frontier2.get(i);
			int newStatus = NON_DOMINATING;
			
			for (int j = frontier1.size()-1; j >= 0; j--)
			{
				Solution current = frontier1.get(j);
				int status = checkDomination(current, newSolution);
				
				if (status == DOMINATOR)
					frontier1.remove(j);
				
				if (status == DOMINATED)
					newStatus = DOMINATED;
			}
				
			if (newStatus != DOMINATED)
				frontier1.add(newSolution);
		}
	}

	/**
	 * Executa um ciclo do algoritmo sobre uma instância
	 *
	 * @param instance			Instância que será executada
	 * @param instanceNumber	Número da instância que será executada
	 */
	public abstract SolutionSet runCycle(InstanceClass instance, int cycleNumber, int instanceNumber) throws Exception;
	
	/**
	 * Executa todos os ciclos do experimento para uma instância
	 * 
	 * @param instance			Instância que será tratada nesta rodada
	 * @param instanceNumber	Número da instância
	 * @param cycles			Número de ciclos que serão executados
	 */
	public void runCycles(InstanceClass instance, int instanceNumber, int cycles) throws Exception
	{
		prepareInstance(instanceNumber);
		Vector<Solution> instanceFrontier = new Vector<Solution>();
		
		for (int i = 0; i < cycles; i++)
		{
			long initTime = System.currentTimeMillis();
			SolutionSet result = runCycle(instance, i, instanceNumber);
			long executionTime = System.currentTimeMillis() - initTime;
			
			Vector<Solution> cycleFrontier = getUniqueObjectives(result, 0.001);
			mergeFrontiers(instanceFrontier, cycleFrontier);
			publishCycle(i, instanceNumber, executionTime, cycleFrontier, instanceFrontier);
		}
		
		terminateInstance(instanceNumber, instanceFrontier);
	}

	/**
	 * Roda o experimento para todas as instâncias
	 * 
	 * @param instances		Vetor de instâncias
	 * @param cycles		Número de ciclos que serão executados
	 */
	public void run(Vector<InstanceClass> instances, int cycles) throws Exception
	{
		prepareExperiment();

		for (int i = 0; i < instances.size(); i++)
		{
			InstanceClass instance = instances.get(i);
			runCycles(instance, i, cycles);
		}

		terminateExperiment();
	}
}