package sobol.problems.portfolio.general.calculation;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import sobol.problems.portfolio.general.model.Projeto;
import sobol.problems.portfolio.general.model.ProjetosCandidatos;
import sobol.problems.portfolio.general.model.Risco;

/**
 * Classe responsável pelos cálculos relacionados a portfolios, dado um conjunto de projetos candidatos
 * 
 * @author Marcio Barros
 */
public class PortfolioCalculator
{
	private int numeroRiscos;
	private int numeroProjetos;
	private int numeroCenarios;
	private double[] probabilidadeCenario;
	private double[][] impactosCenarios;
	private double[][] correlacoesProjetos;
	private double[] retornoEsperadoProjeto;
	private double[] riscoProjeto;
	private double[] custoProjeto;
	private double[] valorPresenteProjeto;
	
	/**
	 * Constructor
	 * @throws Exception 
	 */	
	public PortfolioCalculator(ProjetosCandidatos candidatos)
	{
		this.numeroRiscos = candidatos.pegaNumeroRiscos();
		this.numeroProjetos = candidatos.pegaNumeroProjetos();
		this.numeroCenarios = (int) Math.pow(2, numeroRiscos);

		this.probabilidadeCenario = calculaProbabilidadesCenarios(numeroCenarios, numeroRiscos, candidatos);
		this.impactosCenarios = calculaImpactosCenarios(numeroCenarios, numeroRiscos, numeroProjetos, candidatos);

		this.correlacoesProjetos = calculaCorrelacaoSpearman(numeroCenarios, numeroRiscos, numeroProjetos, probabilidadeCenario, impactosCenarios);
		this.retornoEsperadoProjeto = calculaRetorno(numeroCenarios, numeroProjetos, impactosCenarios, probabilidadeCenario);
		this.riscoProjeto = calculaRisco(numeroCenarios, numeroProjetos, impactosCenarios, probabilidadeCenario);
		this.custoProjeto = calculaCusto(numeroProjetos, candidatos);
		this.valorPresenteProjeto = calculaValorPresente(numeroProjetos, candidatos);
		
		//salvaCorrelacoes(correlacoesProjetos, numeroProjetos);
		salvaDadosProjetos(custoProjeto, retornoEsperadoProjeto, riscoProjeto, numeroProjetos);
	}
	
	/**
	 * Salva as correlações entre os projetos em um arquivo
	 */
	protected void salvaCorrelacoes(double[][] correlacoes, int numeroProjetos)
	{
		try 
		{
			FileWriter outFile = new FileWriter("c:\\temp\\correlacoes " + numeroProjetos + ".txt");
			PrintWriter out = new PrintWriter(outFile);
			
			for (int i = 0; i < numeroProjetos; i++)
			{
				out.print(correlacoes[i][0]);

				for (int j = 1; j < numeroProjetos; j++)
					out.print("\t" + correlacoes[i][j]);
				
				out.println();
			}
			
			out.close();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Salva os dados dos projetos em um arquivo
	 */
	protected void salvaDadosProjetos(double[] custo, double[] retorno, double[] risco, int numeroProjetos)
	{
		NumberFormat nf = new DecimalFormat("0.00");
		
		try 
		{
			FileWriter outFile = new FileWriter("c:\\temp\\projetos " + numeroProjetos + ".txt");
			PrintWriter out = new PrintWriter(outFile);
			
			for (int i = 0; i < numeroProjetos; i++)
				out.println(nf.format(custo[i]) + "\t" + nf.format(retorno[i]) + "\t" + nf.format(risco[i]));
			
			out.close();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Verifica se um determinado risco está ativo em um cenário
	 * 
	 * @param numeroCenario		Número do cenário desejado
	 * @param numeroRisco		Número do risco desejado
	 */
	public boolean existeRiscoCenario(int numeroCenario, int numeroRisco)
	{
		return (numeroCenario & (1 << numeroRisco)) != 0;
	}
	
	/**
	 * Preenche o vetor de probabilidades dos cenários
	 * 
	 * @param numeroCenarios		Número de cenários sendo calculados
	 * @param numeroRiscos			Número de riscos sendo considerados
	 */
	private double[] calculaProbabilidadesCenarios(int numeroCenarios, int numeroRiscos, ProjetosCandidatos candidatos)
	{
		double[] probabilidadeCenario = new double[numeroCenarios];

		for (int i = 0; i < numeroCenarios; i++)
		{
			probabilidadeCenario[i] = 1.0;
			
			for (int j = 0; j < numeroRiscos; j++)
			{
				Risco risco = candidatos.pegaRiscoIndice(j);
				
				if (existeRiscoCenario(i, j))
					probabilidadeCenario[i] *= risco.getProbabilidade() / 100.0;
				else 
					probabilidadeCenario[i] *= (1.0 - risco.getProbabilidade() / 100.0);
			}
		}
		
		return probabilidadeCenario;
	}
	
	/**
	 * Retorna a probabilidade de ocorrência de um cenário
	 * 
	 * @param numeroCenario		Número do cenário desejado
	 */
	public double pegaProbabilidadeCenario(int numeroCenario)
	{
		return probabilidadeCenario[numeroCenario];
	}

	/**
	 * Calcula os impactos nos projetos em todos os cenário de risco
	 * 
	 * @param numeroCenarios		Número de cenários sendo calculados
	 * @param numeroRiscos			Número de riscos sendo considerados
	 * @param numeroProjetos		Número de projetos sendo considerados
	 */
	private double[][] calculaImpactosCenarios(int numeroCenarios, int numeroRiscos, int numeroProjetos, ProjetosCandidatos candidatos) 
	{
		double[][] impactosCenarios = new double[numeroProjetos][numeroCenarios];
		
		for (int i = 0; i < numeroProjetos; i++)
		{
			Projeto projeto = candidatos.pegaProjetoIndice(i);
			
			for (int j = 0; j < numeroCenarios; j++)
			{
				impactosCenarios[i][j] = 0.0;
				
				for (int k = 0; k < numeroRiscos; k++)
				{
					if (existeRiscoCenario(j, k))
					{
						Risco risco = candidatos.pegaRiscoIndice(k);
						impactosCenarios[i][j] += risco.pegaImpactoProjeto(projeto);
					}
				}
			}
		}
										
		return impactosCenarios;
	}

	/**
	 * Calcula a matriz de correlações entre os projetos pelo coeficiente de Spearman  
	 */
	private double[][] calculaCorrelacaoSpearman(int numeroCenarios, int numeroRiscos, int numeroProjetos, double[] probabilidadeCenarios, double[][] impactosCenarios) 
	{
		int[] probabilidadeRound = new int[numeroCenarios];
		
		for (int j = 0; j < numeroCenarios; j++) 
			probabilidadeRound[j] = (int) Math.rint(probabilidadeCenarios[j] * 1000);

		double[][] correlacoes = new double[numeroProjetos][numeroProjetos];
		double[][] rank = calculaRankingProjetos(numeroProjetos, numeroCenarios, probabilidadeRound, impactosCenarios);
		
		for (int p1 = 0; p1 < numeroProjetos; p1++)
		{
			correlacoes[p1][p1] = 1.0;

			for (int p2 = p1+1; p2 < numeroProjetos; p2++)
			{
				double somaXY = 0.0;
				double somaX = 0.0;
				double somaY = 0.0;
				double somaX2 = 0.0;
				double somaY2 = 0.0;
				double somaFreq = 0.0;
				double numerador = 0.0;
				double denominadorX = 0.0;
				double denominadorY = 0.0;
				
				for (int j = 0; j < numeroCenarios; j++)
				{
					double rank1 = rank[p1][j];
					double rank2 = rank[p2][j];

					somaXY += rank1 * rank2 * probabilidadeRound[j];
					somaX += rank1 * probabilidadeRound[j];
					somaY += rank2 * probabilidadeRound[j];
					somaX2 += Math.pow(rank1, 2) * probabilidadeRound[j];
					somaY2 += Math.pow(rank2, 2) * probabilidadeRound[j];
					somaFreq += probabilidadeRound[j];
				}
				
				numerador = (somaFreq * somaXY) - (somaX * somaY);
				denominadorX = Math.sqrt(somaFreq * somaX2 - Math.pow(somaX, 2));
				denominadorY = Math.sqrt(somaFreq * somaY2 - Math.pow(somaY, 2));
				
				double c = (denominadorX * denominadorY != 0) ? numerador / (denominadorX * denominadorY) : 0;
				correlacoes[p2][p1] = correlacoes[p1][p2] = c;
			}
		}
		
		return correlacoes;
	}

	/**
	 * Retorna a correlação entre dois projetos
	 * 
	 * @param projeto1		Índice do primeiro projeto
	 * @param projeto2		Índice do segundo projeto
	 */
	public double pegaCorrelacao(int projeto1, int projeto2)
	{
		return correlacoesProjetos[projeto1][projeto2];
	}

	/**
	 * Calcula o ranking de um valor de um projeto em um cenário
	 * 
	 * @param valor				Valor desejado
	 * @param valores			Valores do cenário
	 * @param probabilidade		Probabilidade de ocorrência dos cenários
	 * @return
	 */
	private double calculaRankingValor(double valor, double[] valores, int[] probabilidade)
	{
		int equals = 0;
		int lessers = 0;
		
		for (int i = 0; i < valores.length; i++)
		{
			if (Math.abs(valores[i] - valor) < 0.0001)
				equals += probabilidade[i];
			
			else if (valores[i] < valor)
				lessers += probabilidade[i];
		}
		
		return lessers + equals / 2 + 0.5;
	}

	/**
	 * Calcula o ranking dos cenários em cada projeto
	 * 
	 * @param numeroProjetos	Número de projetos candidatos
	 * @param numeroCenarios	Número de cenários
	 * @param probabilidade		Probabilidade de ocorrência de cada cenário
	 * @param impactos			Impactos dos cenários
	 */
	private double[][] calculaRankingProjetos(int numeroProjetos, int numeroCenarios, int[] probabilidade, double[][] impactos) 
	{   
		double[][] rank = new double[numeroProjetos][numeroCenarios];

		for (int i = 0; i < numeroProjetos; i++)
			for (int j = 0; j < numeroCenarios; j++)
				rank[i][j] = calculaRankingValor(impactos[i][j], impactos[i], probabilidade);
	
		return rank;
	}

	/**
	 * Calcula o retorno esperado dos projetos candidatos
	 * 
	 * @param numeroCenarios		Número de cenários
	 * @param numeroProjetos		Número de projetos candidatos
	 * @param impactoCenarios		Impactos dos cenários
	 * @param probabilidadeCenario	Probabilidade de ocorrência de cada cenário
	 */
	private double[] calculaRetorno(int numeroCenarios, int numeroProjetos, double[][] impactoCenarios, double[] probabilidadeCenario)
	{
		double[] retorno = new double[numeroProjetos];
		
		for (int i = 0; i < numeroProjetos; i++)
		{
			double somaImpacto = 0.0;
			double somaProbabilidade = 0.0;
		
			for (int j = 0; j < numeroCenarios; j++)
			{
				somaImpacto += impactosCenarios[i][j] * probabilidadeCenario[j];
				somaProbabilidade += probabilidadeCenario[j];
			}
			
			retorno[i] = (somaProbabilidade != 0) ? somaImpacto / somaProbabilidade : 0.0; 
		}
		
		return retorno;
	}

	/**
	 * Retorna o retorno esperado de um projeto, dado seu índice
	 * 
	 * @param numeroProjeto		Índice do projeto desejado
	 */
	public double pegaRetornoEsperado(int numeroProjeto)
	{
		return retornoEsperadoProjeto[numeroProjeto];
	}

	/**
	 * Calcula o risco dos projetos candidatos
	 * 
	 * @param numeroCenarios		Número de cenários
	 * @param numeroProjetos		Número de projetos candidatos
	 * @param impactoCenarios		Impactos dos cenários
	 * @param probabilidadeCenario	Probabilidade de ocorrência de cada cenário
	 */
	private double[] calculaRisco(int numeroCenarios, int numeroProjetos, double[][] impactoCenarios, double[] probabilidadeCenario) 
	{
		double[] riscos = new double[numeroProjetos];

		for (int i = 0; i < numeroProjetos; i++)
		{
			double somaImpacto = 0.0;
			double somaImpactoQuadrado = 0.0;
			
			for (int j = 0; j < numeroCenarios; j++)
			{
				somaImpacto += impactosCenarios[i][j] * probabilidadeCenario[j];
				somaImpactoQuadrado += impactosCenarios[i][j] * impactosCenarios[i][j] * probabilidadeCenario[j];
			}
			
			double mediaSimples = somaImpacto;
			double mediaQuadrado = somaImpactoQuadrado;
			riscos[i] = Math.sqrt(mediaQuadrado - mediaSimples * mediaSimples);
		}
		
		return riscos;
	}
	
	/**
	 * Retorna o risco de um projeto, dado seu índice
	 * 
	 * @param numeroProjeto		Índice do projeto desejado
	 */
	public double pegaRisco(int numeroProjeto)
	{
		return riscoProjeto[numeroProjeto];
	}

	/**
	 * Calcula o custo dos projetos candidatos
	 * 
	 * @param numeroProjetos		Número de projetos candidatos
	 */
	private double[] calculaCusto(int numeroProjetos, ProjetosCandidatos candidatos) 
	{
		double[] custos = new double[numeroProjetos];

		for (int i = 0; i < numeroProjetos; i++)
			custos[i] = candidatos.pegaProjetoIndice(i).getCusto();
		
		return custos;
	}
	
	/**
	 * Retorna o custo de um projeto, dado seu índice
	 * 
	 * @param numeroProjeto		Índice do projeto desejado
	 */
	public double pegaCusto(int numeroProjeto)
	{
		return custoProjeto[numeroProjeto];
	}

	/**
	 * Calcula o valor presente dos projetos candidatos
	 * 
	 * @param numeroProjetos		Número de projetos candidatos
	 */
	private double[] calculaValorPresente(int numeroProjetos, ProjetosCandidatos candidatos) 
	{
		double[] valorPresente = new double[numeroProjetos];

		for (int i = 0; i < numeroProjetos; i++)
			valorPresente[i] = candidatos.pegaProjetoIndice(i).getVPL();
		
		return valorPresente;
	}
	
	/**
	 * Retorna o valor presente de um projeto, dado seu índice
	 * 
	 * @param numeroProjeto		Índice do projeto desejado
	 */
	public double pegaValorPresente(int numeroProjeto)
	{
		return valorPresenteProjeto[numeroProjeto];
	}
	
	/**
	 * Calcula o retorno de um portfolio
	 * 
	 * @param projetos		Vetor de booleanos indicando a composição do portfolio
	 */
	public double calculaRetornoPortfolio(boolean[] projetos)
	{
		double retorno = 0.0;
		
		for (int i = 0; i < numeroProjetos; i++)
			if (projetos[i])
				retorno += valorPresenteProjeto[i] + retornoEsperadoProjeto[i];
	
		return retorno;
	}
	
	/**
	 * Calcula o risco de um portfolio
	 * 
	 * @param projetos		Vetor de booleanos indicando a composição do portfolio
	 */
	public double calculaRiscoPortfolio(boolean[] projetos)
	{
		double vetAux[] = new double[numeroProjetos];
		double vetDesvio[] = new double[numeroProjetos];
		
		for (int j = 0; j < numeroProjetos; j++)
		{
			double resultadoParcial = 0.0;
			
			for (int i = 0; i < numeroProjetos; i++)
			{
				if (projetos[i])
				{
					vetDesvio[i] = riscoProjeto[i];
					resultadoParcial += riscoProjeto[i] * correlacoesProjetos[i][j];
				}
			}
			
			vetAux[j] = resultadoParcial;
		}
		
		double resultadoParcial = 0;

		for (int i = 0; i < numeroProjetos; i++)
			if (projetos[i])
				resultadoParcial += vetDesvio[i] * vetAux[i];
		 
		return Math.sqrt(resultadoParcial);
	}

	/**
	 * Calcula o custo de um portfolio
	 * 
	 * @param projetos		Vetor de booleanos indicando a composição do portfolio
	 */
	public double calculaCustoPortfolio(boolean[] projetos)
	{
		double custo = 0.0;
		
		for (int i = 0; i < numeroProjetos; i++)
			if (projetos[i])
				custo += custoProjeto[i];
	
		return custo;
	}
}