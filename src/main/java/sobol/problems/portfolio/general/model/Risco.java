package sobol.problems.portfolio.general.model;

import java.util.Hashtable;
import java.util.Set;

/**
 * Classe que representa um risco que pode estar associado a um ou mais projetos
 */
public class Risco
{
	private String nome;
	private double probabilidade;
	private Hashtable<Projeto, Double> impactos;

	/**
	 * Constructor
	 */
	public Risco(String nome, double probabilidade)
	{
		this.nome = nome;
		this.probabilidade = probabilidade;
		this.impactos = new Hashtable<Projeto, Double>();
	}
	
	/**
	 * Retorna o nome do risco
	 */
	public String getNome()
	{
		return this.nome;
	}
	
	/**
	 * Pega a probabilidade desse risco ocorrer
	 */
	public double getProbabilidade() 
	{
		return this.probabilidade;
	}
	
	/**
	 * Adiciona um efeito de relacionamento entre um risco e um projeto
	 *
	 * @param projeto Projeto afetado
	 * @param impacto O impacto causado por esse risco ao afetar o projeto 
	 */
	public Risco adicionaEfeito(Projeto projeto, double impacto)
	{
		impactos.put(projeto, impacto);
		return this;
	}
	
	/**
	 * Retorna a lista de projetos afetados pelo risco
	 */
	public Set<Projeto> pegaProjetosAfetados()
	{
		return impactos.keySet();
	}

	/**
	 * Retorna o impacto de um risco sobre um projeto
	 *  
	 * @param p	Projeto que será consultado
	 */
	public double pegaImpactoProjeto(Projeto p)
	{
		try
		{
			return impactos.get(p);
		}
		catch (Exception e)
		{
			return 0.0;
		}
	}
}