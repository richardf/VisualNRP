package sobol.experiments.multiobjective.generations;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import sobol.experiments.multiobjective.analysis.model.ParetoFront;
import sobol.experiments.multiobjective.analysis.model.ParetoFrontVertex;

/**
 * Classe responsável pela leitura de um arquivo com múltiplas gerações de resultados
 * 
 * @author Marcio Barros
 */
public class GenerationsFileReader
{
	@SuppressWarnings("unused")
	private int currentLineNumber;

	/**
	 * Carrega um arquivo com múltiplas gerações de resultados para uma instância
	 * 
	 * @param filename			Nome do arquivo que será carregado
	 * @param objectiveCount	Número de objetivos por instância
	 * @param solutionSize		Número de entradas da solução
	 * @param listener			Listener para processar as gerações
	 */
	public void execute (String filename, int objectiveCount, int solutionSize, boolean frontDetails, GenerationListener listener) throws Exception
	{
		try
		{
			execute(extractFileName(filename), new FileInputStream(filename), objectiveCount, solutionSize, frontDetails, listener);
		}
		catch(IOException e)
		{
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Carrega um arquivo com múltiplas gerações de resultados para uma instância
	 *  
	 * @param name				Nome do experimento
	 * @param stream			Stream de onde os dados serão carregados
	 * @param objectiveCount	Número de objetivos por instância
	 * @param solutionSize		Número de entradas da solução
	 * @param listener			Listener para processar as gerações
	 */
	public void execute (String name, InputStream stream, int objectiveCount, int solutionSize, boolean frontDetails, GenerationListener listener) throws Exception
	{
		currentLineNumber = 0;
		Scanner scanner = new Scanner(stream);
		
		try
		{
			int count = 0;
			
			while (scanner.hasNext())
			{
				Generation g = readGeneration(objectiveCount, solutionSize, frontDetails, scanner);
				listener.processGeneration(count++, g);
			}
		}
		finally
		{
			scanner.close();
		}
	}

	/**
	 * Retorna o nome do arquivo a partir do seu caminho completo
	 * 
	 * @param path		Caminho completo para o arquivo
	 */
	private String extractFileName (String path)
	{
		int barPosition = path.lastIndexOf('\\');
		
		if (barPosition >= 0)
			path = path.substring(barPosition + 1);
		
		int pointPosition = path.lastIndexOf('.');
		
		if (pointPosition >= 0)
			path = path.substring(0, pointPosition);
		
		return path;
	}

	/**
	 * Carrega uma linha do arquivo
	 * 
	 * @param scanner		Classe responsável pela leitura do arquivo
	 */
	private String readLine (Scanner scanner)
	{
		String result = scanner.nextLine();
		currentLineNumber++;
		return result;
	}

	/**
	 * Processa uma string, convertendo-a para um valor inteiro
	 * 
	 * @param s		Texto que será convertido para inteiro
	 */
	private int parseInteger (String s) throws Exception
	{
		try
		{
			return Integer.parseInt(s.trim());
		}
		catch(Exception e)
		{
			throwException ("invalid integer value");
		}
		
		return 0;
	}

	/**
	 * Processa uma string, convertendo-a para um valor numérico
	 * 
	 * @param s		Texto que será convertido para número
	 */
	private double parseDouble (String s) throws Exception
	{
		try
		{
			s = s.replace(',', '.');
			return Double.parseDouble(s.trim());
		}
		catch(Exception e)
		{
			throwException ("invalid double value");
		}
		
		return 0;
	}

	/**
	 * Gera uma exceção durante o processo de carga do arquivo
	 * 
	 * @param message		Mensagem que será gerada na exceção
	 */
	protected void throwException (String message, List<String> messages) throws Exception
	{
		String s = message + "\n";
		
		for (int i = 0; i < messages.size(); i++)
			s += messages.get(i) + "\n";		
		
		throw new Exception(s);
	}

	/**
	 * Gera uma exceção durante o processo de carga do arquivo
	 * 
	 * @param message		Mensagem que será gerada na exceção
	 */
	private void throwException (String message) throws Exception
	{
		throw new Exception(message);
	}

	/**
	 * Carrega os dados de uma geração do arquivo 
	 *  
	 * @param objectiveCount	Número de objetivos por instância
	 * @param solutionSize		Número de entradas da solução
	 * @param scanner			Classe responsável pela leitura do arquivo
	 */
	private Generation readGeneration (int objectiveCount, int solutionSize, boolean frontDetails, Scanner scanner) throws Exception
	{
		Generation generation = new Generation(objectiveCount, solutionSize);
		readGenerationHeader(generation, scanner);
		readParetoFrontier(generation.getFront(), objectiveCount, solutionSize, frontDetails, scanner);
		return generation;
	}
	
	/**
	 * Carrega o cabeçalho de uma geração em um experimento
	 * 
	 * @param scanner			Classe responsável pela leitura do arquivo
	 */
	private void readGenerationHeader(Generation generation, Scanner scanner) throws Exception
	{
		String headerLine = readLine(scanner);
		
		if (!headerLine.startsWith("Generation #"))
			throwException("expected generation number in generation header");
		
		if (!headerLine.endsWith(")"))
			throwException("generation header should terminate with closing parentheses");

		int pos = headerLine.indexOf('(');
		
		if (pos == -1)
			throwException("generation header should have openning parentheses");
		
		int number = parseInteger(headerLine.substring(12, pos-1));
		generation.setNumber(number);
		
		int evaluations = parseInteger(headerLine.substring(pos + 1, headerLine.length() - 1));
		generation.setEvaluations(evaluations);
	}
	
	/**
	 * Carrega uma frente de Pareto
	 * 
	 * @param front				Frente de Pareto que será carregada
	 * @param objectiveCount	Número de objetivos por instância
	 * @param solutionSize		Número de entradas da solução
	 * @param scanner			Classe responsável pela leitura do arquivo
	 */
	private void readParetoFrontier(ParetoFront front, int objectiveCount, int solutionSize, boolean frontDetails, Scanner scanner) throws Exception
	{
		String s = readLine(scanner);
		
		while (s.length() > 0)
		{
			ParetoFrontVertex vertex = new ParetoFrontVertex(objectiveCount, solutionSize);

			String[] tokens = s.split(";");
			
			int expectedSize = frontDetails ? objectiveCount + 2 : objectiveCount;
			
			if (tokens.length != expectedSize)
				throwException("number of tokens in Pareto frontier entry different from expected");
			
			for (int i = 0; i < objectiveCount; i++)
			{
				double value = parseDouble(tokens[i]);
				vertex.setObjective(i, value);
			}

			if (frontDetails)
			{
				int location = parseInteger(tokens[objectiveCount]);
				vertex.setLocation(location);
				parseSolution(tokens[objectiveCount + 1].trim(), vertex, solutionSize);
			}
			
			front.addVertex(vertex);
			s = (scanner.hasNextLine()) ? readLine(scanner) : "";
		}
	}
	
	/**
	 * Carrega os dados de uma solução associada a um vértice de uma frente de Pareto
	 * 
	 * @param solution			Representação textual da solução
	 * @param vertex			Vértice da frente de Pareto onde a solução será adicionada
	 * @param solutionSize		Número de entradas da solução
	 */
	private void parseSolution (String solution, ParetoFrontVertex vertex, int solutionSize) throws Exception
	{
		if (solution.length() < 2)
			throwException("a solution must have at least two characters");
		
		if (solution.charAt(0) != '[')
			throwException("the first character in a solution must be '['");
		
		if (solution.charAt(solution.length()-1) != ']')
			throwException("the last character in a solution must be ']'");
		
		String sSolution = solution.substring(1, solution.length()-1);
		String[] tokens = sSolution.split(" ");
		
		if (tokens.length == 1)		// binary solution
		{
			if (tokens[0].length() != solutionSize)
				throwException("invalid number of itens in a binary solution (expected: " + solutionSize + "; found: " + tokens[0].length() + ")");

			for (int i = 0; i < solutionSize; i++)
				vertex.setSolution(i, (tokens[0].charAt(i) == '0') ? 0 : 1);
		}
		else
		{		
			if (tokens.length != solutionSize)
				throwException("invalid number of itens in a solution (expected: " + solutionSize + "; found: " + tokens.length + ")");
	
			for (int i = 0; i < tokens.length; i++)
				vertex.setSolution(i, parseInteger(tokens[i]));
		}
	}
}