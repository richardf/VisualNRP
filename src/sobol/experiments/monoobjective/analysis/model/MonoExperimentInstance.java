package sobol.experiments.monoobjective.analysis.model;

import java.util.Vector;


/**
 * Classe que representa uma instância do resultado de um experimento
 * 
 * @author Marcio Barros
 */
public class MonoExperimentInstance
{
	private Vector<MonoExperimentCycle> cycles;
	private String name;
	
	/**
	 * Inicializa a instância
	 */
	public MonoExperimentInstance()
	{
		this.name = "";
		this.cycles = new Vector<MonoExperimentCycle>();
	}

	/**
	 * Retorna o nome da instância
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Altera o nome da instância
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Retorna o número de ciclos calculados para a instância
	 */
	public int getCycleCount()
	{
		return cycles.size();
	}
	
	/**
	 * Retorna um ciclo calculado para a instância, dado seu índice
	 */
	public MonoExperimentCycle getCycleIndex(int index)
	{
		return cycles.elementAt(index);
	}
	
	/**
	 * Adiciona um ciclo na instância
	 */
	public void addCycle(MonoExperimentCycle item)
	{
		cycles.add(item);
	}
	
	/**
	 * Remove um ciclo da instância, dado seu índice
	 */
	public void removeCycle(int index)
	{
		cycles.remove(index);
	}
	
	/**
	 * Retorna o tempo de execução de cada ciclo da instância
	 */
	public double[] getExecutionTimes()
	{
		double[] results = new double[cycles.size()];
		
		for (int i = 0; i < cycles.size(); i++)
			results[i] = getCycleIndex(i).getExecutionTime();
		
		return results;
	}
	
	/**
	 * Retorna o objetivo de cada ciclo da instância
	 */
	public double[] getObjectives()
	{
		double[] results = new double[cycles.size()];
		
		for (int i = 0; i < cycles.size(); i++)
			results[i] = getCycleIndex(i).getObjective();
		
		return results;
	}
	
	/**
	 * Retorna o tempo de identificação da melhor solução de cada ciclo da instância
	 */
	public double[] getLocation()
	{
		double[] results = new double[cycles.size()];
		
		for (int i = 0; i < cycles.size(); i++)
			results[i] = getCycleIndex(i).getLocation();
		
		return results;
	}
}