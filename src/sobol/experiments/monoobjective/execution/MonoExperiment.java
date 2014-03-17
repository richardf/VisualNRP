package sobol.experiments.monoobjective.execution;

import java.util.Vector;
import sobol.base.solution.Solution;

public abstract class MonoExperiment<InstanceClass>
{
	private Vector<MonoExperimentListener> listeners;

	/**
	 * Inicializa um experimento
	 */
	public MonoExperiment()
	{
		this.listeners = new Vector<MonoExperimentListener>();
	}
	
	/**
	 * Adiciona um listener no experimento
	 */
	public void addListerner(MonoExperimentListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * Prepara a execução do experimento
	 */
	private void prepareExperiment() throws Exception
	{
		for (MonoExperimentListener listener : listeners)
			listener.prepareExperiment();
	}

	/**
	 * Termina a execução do experimento
	 */
	private void terminateExperiment() throws Exception
	{
		for (MonoExperimentListener listener : listeners)
			listener.terminateExperiment();
	}

	/**
	 * Prepara a execução de uma instância
	 * 
	 * @param instanceNumber	Número da instância
	 */
	private void prepareInstance(int instanceNumber) throws Exception
	{
		for (MonoExperimentListener listener : listeners)
			listener.prepareInstance(instanceNumber);
	}

	/**
	 * Termina a execução de uma instância
	 * 
	 * @param instanceNumber		Número de instância
	 */
	private void terminateInstance(int instanceNumber) throws Exception
	{
		for (MonoExperimentListener listener : listeners)
			listener.terminateInstance(instanceNumber);
	}

	/**
	 * Retorna os dados de uma determinada solução
	 * 
	 * @param solution		Solução cujos dados serão capturados
	 */
	protected double[] getSolutionData(Solution solution)
	{
		double[] data = new double[1];
		data[0] = solution.getObjective(0);
		return data;
	}
	
	/**
	 * Publica os resultados de um ciclo
	 * 
	 * @param cycleNumber		Número do ciclo
	 * @param executionTime		Tempo de execução do ciclo
	 * @param cycleSolution		Solução encontrada para o ciclo
	 */
	private void publishCycle(int cycleNumber, int instanceNumber, long executionTime, Solution cycleSolution) throws Exception
	{
		for (MonoExperimentListener listener : listeners)
		{
			double[] data = getSolutionData(cycleSolution);
			listener.publishCycle(cycleNumber, instanceNumber, cycleSolution, executionTime, data);
		}
	}

	/**
	 * Executa um ciclo do algoritmo sobre uma instância
	 *
	 * @param instance			Instância que será executada
	 * @param instanceNumber	Número da instância que será executada
	 */
	protected abstract Solution runCycle(InstanceClass instance, int instanceNumber, int cycleNumber) throws Exception;
	
	/**
	 * Executa todos os ciclos do experimento para uma instância
	 * 
	 * @param instance			Instância que será tratada nesta rodada
	 * @param instanceNumber	Número da instância
	 * @param cycles			Número de ciclos que serão executados
	 */
	private void runCycles(InstanceClass instance, int instanceNumber, int cycles) throws Exception
	{
		prepareInstance(instanceNumber);
		
		for (int i = 0; i < cycles; i++)
		{
			long initTime = System.currentTimeMillis();
			Solution result = runCycle(instance, instanceNumber, i);
			long executionTime = System.currentTimeMillis() - initTime;
			publishCycle(i, instanceNumber, executionTime, result);
		}
		
		terminateInstance(instanceNumber);
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