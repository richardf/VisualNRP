package sobol.problems.clustering.generic.model;

/**
 * Tipo de visibilidade de uma classe
 * 
 * @author Marcio Barros
 */
public enum ElementVisibility
{
	PUBLIC ("public"),
	DEFAULT ("default");

	private final String identifier;

	/**
	 * Inicializa a visibilidade de um elemento
	 */
	ElementVisibility(String id)
	{
		this.identifier = id;
	}

	/**
	 * Retorna o identificador da visibilidade
	 */
	public String getIdentifier()
	{
		return identifier;
	}

	/**
	 * Retorna uma visibilidade de elemento, dado um identificador
	 */
	public static ElementVisibility fromIdentifier(String id)
	{
		for (ElementVisibility type: ElementVisibility.values())
			if (type.getIdentifier().compareToIgnoreCase(id) == 0)
				return type;
		
		return null;
	}
}