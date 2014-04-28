package sobol.experiments.multiobjective.analysis.model;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Vector;

/**
 * Classe que representa uma frente de Pareto
 * 
 * @author Marcio Barros
 */
public class ParetoFront
{
	private int objectiveCount;
	private int solutionSize;
	private Vector<ParetoFrontVertex> vertices;
	
	/**
	 * Inicializa a frente de Pareto, indicando seu número de objetivos e tamanho de solução
	 */
	public ParetoFront(int objectiveCount, int solutionSize)
	{
		this.objectiveCount = objectiveCount;
		this.solutionSize = solutionSize;
		this.vertices = new Vector<ParetoFrontVertex>();
	}

	/**
	 * Retorna o número de objetivos da frente
	 */
	public int getObjectiveCount()
	{
		return objectiveCount;
	}
	
	/**
	 * Retorna o tamanho das soluções representadas nos vértices da frente
	 */
	public int getSolutionSize()
	{
		return solutionSize;
	}
	
	/**
	 * Retorna o número de vértices da frente
	 */
	public int getVertexCount()
	{
		return vertices.size();
	}

	/**
	 * Retorna um vértice da frente, dado seu índice
	 */
	private ParetoFrontVertex getVertexIndex(int index)
	{
		return vertices.elementAt(index);
	}

	/**
	 * Retorna os valores dos objetivos de um vértice da frente, dado o índice do vértice
	 */
	private double[] getVertex(int index)
	{
		ParetoFrontVertex vertex = getVertexIndex(index); 
		return vertex.getObjectives();
	}
	
	/**
	 * Retorna o valores de um objetivo de um vértice da frente, dados o índice do vértice e do objetivo
	 */
	public double getVertex(int index, int cellIndex)
	{
		return getVertex(index)[cellIndex];
	}

	/**
	 * Altera os valores dos objetivos de m vértice da frente, dado o índice do vértice
	 */
	public void setVertex (int index, double[] values)
	{
		ParetoFrontVertex vertex = getVertexIndex(index); 
		
		for (int i = 0; i < objectiveCount; i++)
			vertex.setObjective(i, values[i]);
	}
	
	/**
	 * Altera o valor de um objetivo de um vértice da frente, dados o índice do vértice e do objetivo
	 */
	public void setVertex (int index, int cellIndex, double value)
	{
		ParetoFrontVertex vertex = getVertexIndex(index); 
		vertex.setObjective(cellIndex, value);
	}
	
	/**
	 * Retorna todos os objetivos da frente de Pareto
	 */
	public double[][] getValues()
	{
		double[][] vertex = new double[getVertexCount()][];
		
		for (int i = 0; i < getVertexCount(); i++)
		{
			double[] v = getVertexIndex(i).getObjectives();
			vertex[i] = new double[v.length];
			
			for (int j = 0; j < v.length; j++)
				vertex[i][j] = v[j];
		}
		
		return vertex;
	}
	
	/**
	 * Retorna a solução associada a um vértice da frente de Pareto
	 * 
	 * @param vertexIndex		Índice do vértice desejado
	 */
	public int[] getSolution(int vertexIndex)
	{
		return getVertexIndex(vertexIndex).getSolution();
	}
	
	/**
	 * Verifica se um vértice (corrente) domina um vértice (novo)
	 */
	private DominationStatus dominates(ParetoFrontVertex currentVertex, ParetoFrontVertex newVertex)
	{
		boolean currentAlwaysDominated = true;
		boolean newAlwaysDominated = true;
		
		for (int i = 0; i < objectiveCount; i++)
		{
			double currentObjective = currentVertex.getObjective(i);
			double newObjective = newVertex.getObjective(i); 
		
			if (currentObjective < newObjective)
				newAlwaysDominated = false;

			if (currentObjective > newObjective)
				currentAlwaysDominated = false;
				
			if (!currentAlwaysDominated && !newAlwaysDominated)
				return DominationStatus.NON_DOMINATING;
		}
				
		if (currentAlwaysDominated)
			return DominationStatus.DOMINATED;
				
		return DominationStatus.DOMINATOR;
	}

	/**
	 * Adiciona um vértice na frente
	 */
	public void addVertex(ParetoFrontVertex vertex)
	{
		DominationStatus newStatus = DominationStatus.NON_DOMINATING;
		
		for (int j = vertices.size()-1; j >= 0; j--)
		{
			ParetoFrontVertex current = vertices.get(j);
			DominationStatus status = dominates(current, vertex);
			
			if (status == DominationStatus.DOMINATOR)
				vertices.remove(j);
			
			if (status == DominationStatus.DOMINATED)
				newStatus = DominationStatus.DOMINATED;
		}
			
		if (newStatus != DominationStatus.DOMINATED)
			vertices.add(vertex);
	}
	
	/**
	 * Remove um vértice da frente, dado seu índice
	 */
	public void removeVertex(int index)
	{
		vertices.remove(index);
	}
	
	/**
	 * Remove todos os vértices da frente
	 */
	public void clear()
	{
		vertices.clear();
	}

	/**
	 * Junta a frente atual com uma frente recebida por parâmetro
	 */
	public void merge(ParetoFront second)
	{
		for (int i = 0; i < second.getVertexCount(); i++)
			addVertex(second.getVertexIndex(i));
	}

	/**
	 * Gera uma cópia da frente de Pareto
	 */
	public ParetoFront clone()
	{
		ParetoFront _copy = new ParetoFront(objectiveCount, solutionSize);
		
		for (int i = 0; i < getVertexCount(); i++)
		{
			ParetoFrontVertex v = getVertexIndex(i);
			ParetoFrontVertex _vcopy = new ParetoFrontVertex(objectiveCount, solutionSize);
			_vcopy.setObjectives(v.getObjectives());
			_vcopy.setLocation(v.getLocation());
			_vcopy.setSolution(v.getSolution());
			_copy.vertices.add(_vcopy);
		}
		
		return _copy;
	}
	
	/**
	 * Verifica se duas frentes são idênticas, ou seja, se a primeira tem todos
	 * os vértices da segunda e vice-versa
	 */
	public boolean isEqual(ParetoFront second, List<String> messages)
	{
		boolean identicas = true;
		
		if (getObjectiveCount() != second.getObjectiveCount())
		{
			messages.add("Número de vértices distinto nas duas fronteiras");
			identicas = false;
		}
		
		for (int i = 0; i < second.getVertexCount(); i++)
			if (!this.hasVertex(second.getVertexIndex(i)))
			{
				messages.add("Vertex #" + i + " from the second front is not present on the first " + second.getVertexIndex(i).toString());
				identicas = false;
			}
		
		for (int i = 0; i < this.getVertexCount(); i++)
			if (!second.hasVertex(this.getVertexIndex(i)))
			{
				messages.add("Vertex #" + i + " from the first front is not present on the second " + this.getVertexIndex(i).toString());
				identicas = false;
			}
		
		return identicas;
	}

	/**
	 * Conta o número de vértices idênticos entre a frente atual e a recebida por parâmetro
	 */
	public int countCommonVertices(ParetoFront f2)
	{
		int count = 0;
		
		for (int i = 0; i < f2.getVertexCount(); i++)
		{
			ParetoFrontVertex vertex = f2.getVertexIndex(i);
			
			if (hasVertex(vertex))
				count++;
		}

		return count;
	}

	/**
	 * Verifica se a frente possui um determinado vértice
	 */
	private boolean hasVertex(ParetoFrontVertex vertex)
	{
		for (int i = 0; i < getVertexCount(); i++)
		{
			ParetoFrontVertex v = getVertexIndex(i);
			
			if (v.sameVertex(vertex))
				return true;
		}
		
		return false;
	}

	/**
	 * Retorna o valor mínimo de um objetivo na frente de Pareto
	 * 
	 * @param numeroObjetivo		Número do objetivo desejado
	 */
	public double getMinimumObjective(int numeroObjetivo)
	{
		if (getVertexCount() == 0)
			return Double.MAX_VALUE;
		
		double min = getVertexIndex(0).getObjective(numeroObjetivo);
		
		for (int i = 0; i < getVertexCount(); i++)
		{
			ParetoFrontVertex v = getVertexIndex(i);
			double value = v.getObjective(numeroObjetivo);
			
			if (value < min)
				min = value;
		}
		
		return min;
	}

	/**
	 * Remove um objetivo da frente, dado seu índice
	 */
	public void removeObjective(int index)
	{
		ParetoFront pf = clone();
		
		for (int i = 0; i < pf.getVertexCount(); i++)
			pf.getVertexIndex(i).removeObjective(index);
		
		vertices.clear();
		objectiveCount--;
		
		for (int i = 0; i < pf.getVertexCount(); i++)
			addVertex(pf.getVertexIndex(i));
	}

	/**
	 * Apresenta a frente em um stream
	 */
	public void print (OutputStreamWriter stream) throws IOException
	{
		for (int i = 0; i < vertices.size(); i++)
			vertices.get(i).print(stream);
	}

	/**
	 * Apresenta a frente em um stream
	 */
	public void print (PrintWriter out) throws IOException
	{
		for (int i = 0; i < vertices.size(); i++)
			vertices.get(i).print(out);
	}
}

enum DominationStatus
{
	NON_DOMINATING,
	DOMINATED, 
	DOMINATOR
}