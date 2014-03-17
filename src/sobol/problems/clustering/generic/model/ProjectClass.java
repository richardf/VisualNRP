package sobol.problems.clustering.generic.model;

import java.util.Vector;

/**
 * Classe que representa um elemento em um programa
 * 
 * @author Marcio Barros
 */
public class ProjectClass
{
	private String name;
	private ProjectPackage _package;
	private ElementType type;
	private ElementVisibility visibility;
	private boolean isAbstract;
	private Vector<Dependency> dependencies;

	/**
	 * Inicializa uma classe
	 */
	public ProjectClass(String name, ElementType type, ElementVisibility visibility, boolean isAbstract)
	{
		this.name = name;
		this._package = null;
		this.type = type;
		this.visibility = visibility;
		this.isAbstract = isAbstract;
		this.dependencies = new Vector<Dependency>();
	}

	/**
	 * Inicializa uma classe com valores default
	 */
	public ProjectClass(String name)
	{
		this(name, ElementType.CLASS, ElementVisibility.PUBLIC, false);
	}

	/**
	 * Inicializa uma classe com valores default
	 */
	public ProjectClass(String name, ProjectPackage _package)
	{
		this(name);
		setPackage(_package);
	}
	
	/**
	 * Retorna o nome da classe
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Retorna o pacote da classe
	 */
	public ProjectPackage getPackage()
	{
		return _package;
	}
	
	/**
	 * Altera o pacote da classe
	 */
	public void setPackage(ProjectPackage _package)
	{
		this._package = _package;
	}
	
	/**
	 * Retorna o tipo da classe
	 */
	public ElementType getType()
	{
		return type;
	}

	/**
	 * Retorna a visibilidade da classe
	 */
	public ElementVisibility getVisibility()
	{
		return visibility;
	}

	/**
	 * Verifica se a classe é abstrata
	 */
	public boolean isAbstract()
	{
		return isAbstract;
	}

	/**
	 * Retorna o número de dependências da classe
	 */
	public int getDependencyCount()
	{
		return dependencies.size();
	}
	
	/**
	 * Retorna uma dependência, dado seu índice
	 */
	public Dependency getDependencyIndex(int index)
	{
		return dependencies.elementAt(index);
	}
	
	/**
	 * Adiciona uma dependência na classe
	 */
	public ProjectClass addDependency(String elementName, DependencyType type)
	{
		Dependency dependency = new Dependency(elementName, type);
		dependencies.add(dependency);
		return this;
	}
	
	/**
	 * Adiciona uma dependência na classe
	 */
	public ProjectClass addDependency(String elementName)
	{
		Dependency dependency = new Dependency(elementName, DependencyType.USES);
		dependencies.add(dependency);
		return this;
	}
	
	/**
	 * Retorna uma enumeração das dependências da classe
	 */
	public Iterable<Dependency> getDependencies()
	{
		return dependencies;
	}
}