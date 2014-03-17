package sobol.experiments.multiobjective.analysis.model;

import java.util.Vector;

/**
 * Classe que representa o resultado de um experimento
 * 
 * @author Marcio Barros
 */
public class MultiExperimentResult
{
	private Vector<MultiExperimentInstance> instances;
	private String name;
	
	/**
	 * Inicializa o resultado do experimento, indicando seu nome
	 */
	public MultiExperimentResult(String name)
	{
		this.name = name;
		this.instances = new Vector<MultiExperimentInstance>();
	}

	/**
	 * Retorna o nome do experimento
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Altera o nome do experimento
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Retorna o número de instâncias do resultado do experimento
	 */
	public int getInstanceCount()
	{
		return instances.size();
	}
	
	/**
	 * Retorna uma instância do resultado do experimento, dado seu índice
	 */
	public MultiExperimentInstance getInstanceIndex(int index)
	{
		return instances.elementAt(index);
	}
	
	/**
	 * Adiciona uma instância no resultado do experimento
	 */
	public void addInstance(MultiExperimentInstance item)
	{
		instances.add(item);
	}
	
	/**
	 * Remove uma instância do resultado do experimento, dado seu índice
	 */
	public void removeInstance(int index)
	{
		instances.remove(index);
	}

	/**
	 * Remove um objetivo das fronteiras de Pareto das instâncias
	 */
	public void removeObjective(int index)
	{
		for (MultiExperimentInstance instance : instances)
			instance.removeObjective(index);
	}
}