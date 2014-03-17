package sobol.problems.portfolio.general.model;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Classe que representa um conjunto de projetos candidatos ao portfolio
 * 
 * @author Marcio Barros
 */
public class ProjetosCandidatos 
{
	private Vector<Projeto> projetos;
	private Vector<Risco> riscos;
	private double limiteOrcamento;
	
	/**
	 * Constructor
	 */
	public ProjetosCandidatos()
	{
		this.projetos = new Vector<Projeto>();
		this.riscos = new Vector<Risco>();
	}
	
	/**
	 * Retorna o limite de orçamento dos projetos candidatos
	 */
	public double getLimiteOrcamento()
	{
		return this.limiteOrcamento;
	}
	
	/**
	 * Altera o limite de orçamento dos projetos candidatos
	 */
	public void setLimiteOrcamento(double limite)
	{
		this.limiteOrcamento = limite;
	}
	
	/**
	 * Retorna o número de projetos candidatos
	 */
	public int pegaNumeroProjetos()
	{
		return projetos.size();
	}

	/**
	 * Retorna o projeto na posição indicada
	 *
	 * @param index Índice indicativo da posição do projeto no vetor de projetos
	 */
	public Projeto pegaProjetoIndice(int index)
	{
		return projetos.elementAt(index);
	}

	/**
	 * Retorna o objeto projeto do projeto com o nome indicado
	 *
	 * @param nome Nome do projeto que se quer procurar
	 */
	public Projeto pegaProjetoNome(String nome) 
	{
		for (Projeto projeto : projetos)
			if (projeto.getNome().compareToIgnoreCase(nome) == 0)
				return projeto;
				
		return null;
	}
	
	/**
	 * Adiciona um projeto na lista de projetos
	 *
	 * @param projeto Projeto que se quer incluir na lista
	 */
	public void adicionaProjeto(Projeto projeto)
	{
		projetos.add(projeto);
	}

	/**
	 * Remove um projeto da lista de projetos
	 *
	 * @param index Índice do projeto que se quer remover da lista
	 */
	public void removeProjeto(int index)
	{
		projetos.remove(index);
	}

	/**
	 * Retorna a lista de projetos
	 */	
	public Enumeration<Projeto> pegaProjetos()
	{
		return projetos.elements();
	}

	/**
	 * Retorna o custo total de todos os projetos
	 */
	public double pegaCustoTotal()
	{
		double custo = 0.0;
		
		for (Projeto p : projetos)
			custo += p.getCusto();
		
		return custo;
	}
	
	/**
	 * Retorna o número de riscos
	 */
	public int pegaNumeroRiscos()
	{
		return riscos.size();
	}

	/**
	 * Retorna o risco na posição indicada
	 *
	 * @param index Índice indicativo da posição do risco no vetor de riscos
	 */
	public Risco pegaRiscoIndice(int index)
	{
		return riscos.elementAt(index);
	}

	/**
	 * Retorna o objeto risco do risco com o nome indicado
	 *
	 * @param nome Nome do risco que se quer procurar
	 */
	public Risco pegaRiscoNome(String nome) 
	{
		for (Risco risco : riscos)
			if (risco.getNome().compareToIgnoreCase(nome) == 0)
				return risco;
				
		return null;
	}

	/**
	 * Adiciona um risco na lista de riscos
	 *
	 * @param risco risco que se quer incluir na lista
	 */
	public void adicionaRisco(Risco risco)
	{
		riscos.add(risco);
	}
	
	/**
	 * Remove um risco da lista de riscos
	 *
	 * @param index Índice do risco que se quer remover da lista
	 */	
	public void removeRisco(int index)
	{
		riscos.remove(index);
	}
	
	/**
	 * Retorna a lista de riscos
	 */	
	public Enumeration<Risco> pegaRiscos()
	{
		return riscos.elements();
	}
}