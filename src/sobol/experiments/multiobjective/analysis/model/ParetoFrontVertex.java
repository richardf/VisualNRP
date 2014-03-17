package sobol.experiments.multiobjective.analysis.model;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Classe que representa um vértice em uma frente de Pareto
 * 
 * @author Marcio Barros
 */
public class ParetoFrontVertex
{
	private int location;
	private double[] objectives;
	private int[] solution;
	
	/**
	 * Inicializa o vértice, indicando o número de objetivos e o tamanho da solução
	 */
	public ParetoFrontVertex(int objectiveCount, int solutionSize)
	{
		this.location = 0;
		this.objectives = new double[objectiveCount];
		this.solution = new int[solutionSize];
	}
	
	/**
	 * Retorna o número da iteração em que a solução foi encontrada 
	 */
	public int getLocation()
	{
		return location;
	}

	/**
	 * Altera o número da iteração em que a solução foi encontrada 
	 */
	public void setLocation(int location)
	{
		this.location = location;
	}

	/**
	 * Retorna os objetivos registrados no vértice
	 */
	public double[] getObjectives()
	{
		return objectives;
	}
	
	/**
	 * Retorna um objetivo registrado no vértice, dado seu índice
	 */
	public double getObjective(int index)
	{
		return objectives[index];
	}
	
	/**
	 * Altera os objetivos registrados no vértice
	 */
	public void setObjectives(double[] values)
	{
		for (int i = 0; i < this.objectives.length; i++)
			this.objectives[i] = values[i];
	}
	
	/**
	 * Altera um objetivo registrado no vértice, dado seu índice e novo valor
	 */
	public void setObjective(int index, double value)
	{
		this.objectives[index] = value;
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
	
	/**
	 * Verifica se um vértice é idêntico a outro recebido como parâmetro
	 */
	public boolean sameVertex(ParetoFrontVertex vertex)
	{
		for (int i = 0; i < objectives.length; i++)
			if (Math.abs(objectives[i] - vertex.objectives[i]) > 0.001)
				return false;

		return true;
	}

	/**
	 * Gera uma cópia do vértice
	 */
	public ParetoFrontVertex clone()
	{
		ParetoFrontVertex vertex = new ParetoFrontVertex(objectives.length, solution.length);
		vertex.setObjectives(objectives);
		vertex.setLocation(location);
		vertex.setSolution(solution);
		return vertex;
	}

	/**
	 * Remove um objetivo do vértice, dado seu índice
	 */
	public void removeObjective(int index)
	{
		double[] localObjectives = new double[objectives.length-1];
		int walker = 0;
		
		for (int i = 0; i < index; i++)
			localObjectives[walker++] = objectives[i];

		for (int i = index+1; i < objectives.length; i++)
			localObjectives[walker++] = objectives[i];
		
		this.objectives = localObjectives;
	}

	/**
	 * Apresenta o vértice em um stream
	 */
	public void print (OutputStreamWriter stream) throws IOException
	{
		stream.write("" + objectives[0]);

		for (int i = 1; i < objectives.length; i++)
			stream.write(";" + objectives[i]);
		
		stream.write("\n");
		stream.flush();
	}

	/**
	 * Apresenta o vértice em um stream
	 */
	public void print (PrintWriter out) throws IOException
	{
		out.print("" + objectives[0]);

		for (int i = 1; i < objectives.length; i++)
			out.print(";" + objectives[i]);
		
		out.println();
	}

	/**
	 * Publica o vértice em uma string
	 */
	public String toString()
	{
		String s = "[" + objectives[0];

		for (int i = 1; i < objectives.length; i++)
			s += "; " + objectives[i];
		
		return s + "]";
	}
}