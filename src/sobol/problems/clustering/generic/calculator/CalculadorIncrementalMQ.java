package sobol.problems.clustering.generic.calculator;

import sobol.problems.clustering.generic.model.Project;
import sobol.problems.clustering.generic.model.ProjectClass;

/**
 * Classe que calcula o MQ de forma incremental para a otimização baseada em um ponto
 * 
 * @author marcio.barros
 */
public class CalculadorIncrementalMQ implements ICalculadorIncremental
{
	/**
	 * Número de classes no problema
	 */
	private int classCount;
	
	/**
	 * Número de pacotes que serão considerados no problema
	 */
	private int packageCount;
	
	/**
	 * Matriz onde a célula [i, j] possui 1 se a classe I depende da classe J 
	 */
	private int[][] classEdges;

	/**
	 * Lista de classes que prestam serviços para uma classe índice 
	 */
	private int[][] classDependsOn;

	/**
	 * Lista de classes que utilizam serviços da classe índice
	 */
	private int[][] classProvidesTo;
	
	/**
	 * Número de arestas externas de cada pacote
	 */
	private int[] packageInterEdges;

	/**
	 * Número de arestas internas de cada pacote
	 */
	private int[] packageIntraEdges;
	
	/**
	 * Pacote em que é mantida cada classe
	 */
	private int[] newPackage;

	/**
	 * Inicializa o calculador incremental
	 */
	public CalculadorIncrementalMQ(Project project, int packageCount)
	{
		this.classCount = project.getClassCount();
		this.packageCount = packageCount;

		this.packageInterEdges = new int[packageCount];
		this.packageIntraEdges = new int[packageCount];
		
		prepareClassPackages(project);
		prepareClassDependencies(project);
		prepareClassProviders();
		prepareClassClients();
		preparePackageDependencies();
	}
	
	/**
	 * Monta o indicador do pacote ocupado por cada classe
	 */
	private void prepareClassPackages(Project project)
	{
		this.newPackage = new int[classCount];

		for (int i = 0; i < classCount; i++)
		{
			ProjectClass _class = project.getClassIndex(i);
			int sourcePackageIndex = project.getIndexForPackage(_class.getPackage());
			this.newPackage[i] = sourcePackageIndex;
		}
	}
	
	/**
	 * Monta a matriz de dependências entre classes
	 */
	private void prepareClassDependencies(Project project)
	{
		this.classEdges = new int[classCount][classCount];
		
		for (int i = 0; i < classCount; i++)
		{
			ProjectClass _class = project.getClassIndex(i);
			
			for (int j = 0; j < _class.getDependencyCount(); j++)
			{
				String targetName = _class.getDependencyIndex(j).getElementName();
				int classIndex = project.getClassIndex(targetName);
				
				if (classIndex == -1)
					System.out.println("*** Class not registered in project: " + targetName);
				
				classEdges[i][classIndex] = 1;
			}
		}
	}

	/**
	 * Monta a lista de classes que oferecem serviços para a classe índice
	 */
	private void prepareClassProviders()
	{
		this.classDependsOn = new int[classCount][classCount+1];
		
		for (int i = 0; i < classCount; i++)
		{
			int walker = 0;
			
			for (int j = 0; j < classCount; j++)
			{
				if (classEdges[i][j] > 0)
					classDependsOn[i][walker++] = j;
			}
			
			classDependsOn[i][walker] = -1;
		}
	}
	
	/**
	 * Monta a lista de classes que utilizam serviços da classe índice
	 */
	private void prepareClassClients()
	{
		this.classProvidesTo = new int[classCount][classCount+1];
		int[] walkers = new int[classCount];
		
		for (int i = 0; i < classCount; i++)
		{
			for (int j = 0; j < classCount; j++)
			{
				if (classEdges[i][j] > 0)
					classProvidesTo[j][walkers[j]++] = i;
			}
		}
		
		for (int i = 0; i < classCount; i++)
			classProvidesTo[i][walkers[i]] = -1;
	}
	
	/**
	 * Calcula o número de arestas internas e externas de cada pacote
	 */
	private void preparePackageDependencies()
	{
		for (int i = 0; i < packageCount; i++)
			this.packageInterEdges[i] = this.packageIntraEdges[i] = 0;

		for (int i = 0; i < classCount; i++)
		{
			int sourcePackage = newPackage[i];
			int classIndex;
			
			for (int j = 0; (classIndex = classDependsOn[i][j]) >= 0; j++)
			{
				int targetPackage = newPackage[classIndex];
				
				if (targetPackage != sourcePackage)
				{
					packageInterEdges[sourcePackage]++;
					packageInterEdges[targetPackage]++;
				}
				else
					packageIntraEdges[sourcePackage]++;
			}
		}
	}
	
	/**
	 * Contabiliza a influência de uma classe nas arestas de cada pacote
	 */
	private void addClassInfluence(int classIndex)
	{
		int sourcePackage = newPackage[classIndex];
		int secondClassIndex;
		
		for (int i = 0; (secondClassIndex = classDependsOn[classIndex][i]) >= 0; i++)
		{
			int targetPackage = newPackage[secondClassIndex];
			
			if (targetPackage != sourcePackage)
			{
				packageInterEdges[sourcePackage]++;
				packageInterEdges[targetPackage]++;
			}
			else
				packageIntraEdges[sourcePackage]++;
		}
		
		for (int i = 0; (secondClassIndex = classProvidesTo[classIndex][i]) >= 0; i++)
		{
			int targetPackage = newPackage[secondClassIndex];
			
			if (targetPackage != sourcePackage)
			{
				packageInterEdges[sourcePackage]++;
				packageInterEdges[targetPackage]++;
			}
			else
				packageIntraEdges[sourcePackage]++;
		}
	}
	
	/**
	 * Descontabiliza a influência de uma classe nas arestas de cada pacote
	 */
	public void removeClassInfluence(int classIndex)
	{
		int sourcePackage = newPackage[classIndex];
		int secondClassIndex;
		
		for (int i = 0; (secondClassIndex = classDependsOn[classIndex][i]) >= 0; i++)
		{
			int targetPackage = newPackage[secondClassIndex];

			if (targetPackage != sourcePackage)
			{
				packageInterEdges[sourcePackage]--;
				packageInterEdges[targetPackage]--;
			}
			else
				packageIntraEdges[sourcePackage]--;
		}
		
		for (int i = 0; (secondClassIndex = classProvidesTo[classIndex][i]) >= 0; i++)
		{
			int targetPackage = newPackage[secondClassIndex];

			if (targetPackage != sourcePackage)
			{
				packageInterEdges[sourcePackage]--;
				packageInterEdges[targetPackage]--;
			}
			else
				packageIntraEdges[sourcePackage]--;
		}
	}
	
	/**
	 * Move uma classe para outro pacote
	 */
	@Override
	public void moveClass(int classIndex, int packageIndex)
	{
		int actualPackage = newPackage[classIndex];
		
		if (actualPackage != packageIndex)
		{
			removeClassInfluence(classIndex);
			newPackage[classIndex] = packageIndex;
			addClassInfluence(classIndex);
		}
	}
	
	/**
	 * Move todas as classes para um novo conjunto de pacotes
	 */
	@Override
	public void moveAll(int[] packageIndexes)
	{
		for (int i = 0; i < classCount; i++)
			newPackage[i] = packageIndexes[i];
		
		preparePackageDependencies();
	}
	
	/**
	 * Calcula o valor do MQ para a distribuição atual de classes em pacotes
	 */
	@Override
	public double evaluate()
	{
		double result = 0.0;
		
		for (int i = 0; i < packageCount; i++)
		{
			int inter = packageInterEdges[i];
			int intra = packageIntraEdges[i];
			double mf = (intra != 0 && inter != 0) ? intra / (intra + 0.5 * inter) : 0.0;
			result += mf;
		}
		
		return result;
	}
}