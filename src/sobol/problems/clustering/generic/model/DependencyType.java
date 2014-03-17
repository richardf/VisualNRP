package sobol.problems.clustering.generic.model;

/**
 * Tipo de dependência entre duas classes
 * 
 * @author Marcio Barros
 */
public enum DependencyType
{
	USES ("uses"),
	IMPLEMENTS ("implements"),
	EXTENDS ("extends");

	private final String identifier;

	/**
	 * Inicializa um tipo de dependência
	 */
	DependencyType(String id)
	{
		this.identifier = id;
	}

	/**
	 * Retorna o identificador do tipo de dependência
	 */
	public String getIdentifier()
	{
		return identifier;
	}
	
	/**
	 * Retorna um tipo de dependência, dado um identificado
	 */
	public static DependencyType fromIdentifier(String id)
	{
		for (DependencyType type: DependencyType.values())
			if (type.getIdentifier().compareToIgnoreCase(id) == 0)
				return type;
		
		return null;
	}
}