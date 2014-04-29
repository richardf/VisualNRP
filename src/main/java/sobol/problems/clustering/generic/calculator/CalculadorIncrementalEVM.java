package sobol.problems.clustering.generic.calculator;

import sobol.problems.clustering.generic.model.Project;
import sobol.problems.clustering.generic.model.ProjectClass;

/**
 * Classe que calcula o EVM de forma incremental para a otimização baseada em um ponto
 * 
 * @author marcio.barros
 */
public class CalculadorIncrementalEVM implements ICalculadorIncremental
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
	 * Lista de classes com quem uma classe está associada 
	 */
	private int[][] classAssociations;
	
	/**
	 * Número de classes em cada pacote
	 */
	private int[] packageClassCount;

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
	public CalculadorIncrementalEVM(Project project, int packageCount)
	{
		this.classCount = project.getClassCount();
		this.packageCount = packageCount;

		this.packageIntraEdges = new int[packageCount];
		this.packageClassCount = new int[packageCount];
		
		prepareClassPackages(project);
		prepareClassDependencies(project);
		prepareClassAssociations();
		
		preparePackageClasses();
		preparePackageAssociations();
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
	 * Monta a lista de classes associadas a uma classe
	 */
	private void prepareClassAssociations()
	{
		this.classAssociations = new int[classCount][classCount+1];
		
		for (int i = 0; i < classCount; i++)
		{
			int walker = 0;
			
			for (int j = 0; j < classCount; j++)
			{
				if (classEdges[i][j] > 0 || classEdges[j][i] > 0)
					classAssociations[i][walker++] = j;
			}

			classAssociations[i][walker] = -1;
		}
	}
	
	/**
	 * Calcula o número de classes de cada pacote
	 */
	private void preparePackageClasses()
	{
		for (int i = 0; i < packageCount; i++)
			this.packageClassCount[i] = 0;

		for (int i = 0; i < classCount; i++)
		{
			int sourcePackage = newPackage[i];
			this.packageClassCount[sourcePackage]++;
		}
	}
	
	/**
	 * Calcula o número de arestas internas de cada pacote
	 */
	private void preparePackageAssociations()
	{
		for (int i = 0; i < packageCount; i++)
			this.packageIntraEdges[i] = 0;

		for (int i = 0; i < classCount; i++)
		{
			int sourcePackage = newPackage[i];			
			int classIndex;
			
			for (int j = 0; (classIndex = classAssociations[i][j]) >= 0; j++)
			{
				if (classIndex > i)
				{
					int targetPackage = newPackage[classIndex];
				
					if (targetPackage == sourcePackage)
						packageIntraEdges[sourcePackage]++;
				}
			}
		}
	}
	
	/**
	 * Contabiliza a influência de uma classe nas arestas de cada pacote
	 */
	private void addClassInfluence(int classIndex)
	{
		int sourcePackage = newPackage[classIndex];
		this.packageClassCount[sourcePackage]++;

		int secondClassIndex;
		
		for (int i = 0; (secondClassIndex = classAssociations[classIndex][i]) >= 0; i++)
		{
			int targetPackage = newPackage[secondClassIndex];
			
			if (targetPackage == sourcePackage)
				packageIntraEdges[sourcePackage]++;
		}
	}
	
	/**
	 * Descontabiliza a influência de uma classe nas arestas de cada pacote
	 */
	public void removeClassInfluence(int classIndex)
	{
		int sourcePackage = newPackage[classIndex];
		this.packageClassCount[sourcePackage]--;

		int secondClassIndex;
		
		for (int i = 0; (secondClassIndex = classAssociations[classIndex][i]) >= 0; i++)
		{
			int targetPackage = newPackage[secondClassIndex];

			if (targetPackage == sourcePackage)
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
		
		preparePackageClasses();
		preparePackageAssociations();
	}
	
	/**
	 * Calcula o valor do EVM para a distribuição atual de classes em pacotes
	 */
	@Override
	public double evaluate()
	{
		double result = 0.0;
		
		for (int i = 0; i < packageCount; i++)
		{
			int nc = packageClassCount[i];
			int nl = packageIntraEdges[i];
			result += 2 * nl - nc * (nc - 1) / 2;
		}
		
		return result;
	}
}