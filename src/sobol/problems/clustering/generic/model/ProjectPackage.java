package sobol.problems.clustering.generic.model;

/**
 * Classe que representa um pacote em um programa
 * 
 * @author Marcio Barros
 */
public class ProjectPackage
{
	private String name;

	/**
	 * Inicializa um pacote
	 */
	public ProjectPackage(String name)
	{
		this.name = name;
	}

	/**
	 * Retorna o nome do pacote
	 */
	public String getName()
	{
		return name;
	}
}