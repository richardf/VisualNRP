package sobol.experiments.multiobjective.execution;

import java.util.Vector;
import sobol.base.solution.Solution;

/**
 * Interface que representa os eventos do ciclo de vida de um experimento
 * 
 * @author Marcio Barros
 */
public interface MultiExperimentListener
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
	 * @param instanceFrontier	Fronteira eficiente da instância (final)
	 */
	public void terminateInstance(int instanceNumber, Vector<Solution> instanceFrontier) throws Exception;

	/**
	 * Publica os dados de um ciclo de uma instância
	 * 
	 * @param cycleNumber		Número do ciclo
	 * @param executionTime		Tempo de execução
	 * @param cycleFrontier		Fronteira eficiente do ciclo
	 * @param instanceFrontier	Fronteira eficiente da instância (parcial)
	 */
	public void publishCycle(int cycleNumber, int instanceNumber, long executionTime, Vector<Solution> cycleFrontier, Vector<Solution> instanceFrontier) throws Exception;
}