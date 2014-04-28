package sobol.experiments.multiobjective.analysis.model;


/**
 * Classe que representa um ciclo de uma instância em um resultado de experimento
 * 
 * @author Marcio Barros
 */
public class MultiExperimentCycle
{
	private ParetoFront front;
	private double executionTime;
	
	/**
	 * Inicializa o ciclo de instância, dado o número de objetivos e tamanho da solução
	 */
	public MultiExperimentCycle(int objectivesCount, int solutionSize)
	{
		this.front = new ParetoFront(objectivesCount, solutionSize);
		this.executionTime = 0.0;
	}
	
	/**
	 * Retorna a frente de Pareto do ciclo
	 */
	public ParetoFront getFront()
	{
		return front;
	}
	
	/**
	 * Retorna o tempo de execução do ciclo
	 */
	public double getExecutionTime()
	{
		return executionTime;
	}

	/**
	 * Altera o tempo de execução do ciclo
	 */
	public void setExecutionTime(double executionTime)
	{
		this.executionTime = executionTime;
	}

	/**
	 * Retorna o valor mínimo de um objetivo para o ciclo
	 * 
	 * @param numeroObjetivo		Número do objetivo desejado
	 */
	public double getMinimumObjective(int numeroObjetivo)
	{
		return front.getMinimumObjective(numeroObjetivo);
	}

	/**
	 * Remove um objetivo da frente de Pareto do ciclo, dado seu índice
	 */
	public void removeObjective(int index)
	{
		front.removeObjective(index);
	}
}