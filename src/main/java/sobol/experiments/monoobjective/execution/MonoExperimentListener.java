package sobol.experiments.monoobjective.execution;

import sobol.base.solution.Solution;

/**
 * Interface que representa os eventos do ciclo de vida de um experimento
 * 
 * @author Marcio Barros
 */
public interface MonoExperimentListener
{
	/**
	 * Prepara a execução do experimento
	 */
	public void prepareExperiment() throws Exception;

	/**
	 * Encerra a execução do experimento
	 */
	public void terminateExperiment() throws Exception;
	
	/**
	 * Prepara a execução de uma instância
	 * 
	 * @param instanceNumber	Número da instância
	 */
	public void prepareInstance(int instanceNumber) throws Exception;

	/**
	 * Encerra a execução de uma instância
	 * 
	 * @param instanceNumber	Número da instância
	 */
	public void terminateInstance(int instanceNumber) throws Exception;

	/**
	 * Publica os dados de um ciclo de uma instância
	 * 
	 * @param cycleNumber		Número do ciclo
	 * @param instanceNumber	Número da instância
	 * @param Solution			Solução encontrada no ciclo
	 * @param executionTime		Tempo de execução
	 * @param data				Dados relevantes sobre a solução
	 */
	public void publishCycle(int cycleNumber, int instanceNumber, Solution solution, long executionTime, double[] data) throws Exception;
}