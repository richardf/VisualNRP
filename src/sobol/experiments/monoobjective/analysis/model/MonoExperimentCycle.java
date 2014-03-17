package sobol.experiments.monoobjective.analysis.model;

/**
 * Classe que representa um ciclo de uma instância em um resultado de experimento
 * 
 * @author Marcio Barros
 */
public class MonoExperimentCycle
{
	private double executionTime;
	private double objective;
	private long location;
	private int[] solution;
	
	/**
	 * Inicializa o ciclo de instância, dado o número de objetivos e tamanho da solução
	 */
	public MonoExperimentCycle(int solutionSize)
	{
		this.executionTime = 0.0;
		this.location = 0;
		this.objective = 0.0;
		this.solution = new int[solutionSize];
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
	 * Retorna o objetivo do ciclo
	 */
	public double getObjective()
	{
		return objective;
	}

	/**
	 * Altera o objetivo do ciclo
	 */
	public void setObjective(double objective)
	{
		this.objective = objective;
	}
	
	/**
	 * Retorna o número da iteração em que a solução foi encontrada 
	 */
	public long getLocation()
	{
		return location;
	}

	/**
	 * Altera o número da iteração em que a solução foi encontrada 
	 */
	public void setLocation(long location)
	{
		this.location = location;
	}

	/**
	 * Retorna todos os valores da solução representada no vértice
	 */
	public int[] getSolution()
	{
		return solution;
	}
	
	/**
	 * Retorna um valor da solução representada no vértice, dado seu índice 
	 */
	public int getSolution(int index)
	{
		return solution[index];
	}

	/**
	 * Altera todos os valores da solução representada pelo vértice
	 */
	public void setSolution(int[] values)
	{
		for (int i = 0; i < this.solution.length; i++)
			this.solution[i] = values[i];
	}
	
	/**
	 * Altera um valor da solução representada pelo vértice
	 */
	public void setSolution(int index, int value)
	{
		this.solution[index] = value;
	}
}