package sobol.experiments.multiobjective.analysis.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import sobol.experiments.multiobjective.analysis.model.MultiExperimentCycle;
import sobol.experiments.multiobjective.analysis.model.MultiExperimentInstance;
import sobol.experiments.multiobjective.analysis.model.MultiExperimentResult;
import sobol.experiments.multiobjective.analysis.model.ParetoFront;
import sobol.experiments.multiobjective.analysis.model.ParetoFrontVertex;

/**
 * Classe responsável pela leitura dos resultados de um experimento
 * 
 * @author Marcio Barros
 */
public class MultiExperimentFileReader
{
	private int currentLineNumber;

	/**
	 * Carrega um arquivo com os resultados de um experimento
	 * 
	 * @param filename			Nome do arquivo que será carregado
	 * @param instanceCount		Número de instâncias do arquivo
	 * @param cycleCount		Número de ciclos por instância
	 * @param objectiveCount	Número de objetivos por instância
	 * @param solutionSize		Número de entradas da solução
	 */
	public MultiExperimentResult execute (String filename, int instanceCount, int cycleCount, int objectiveCount, int solutionSize) throws MultiExperimentFileReaderException
	{
		try
		{
			return execute(extractFileName(filename), new FileInputStream(filename), instanceCount, cycleCount, objectiveCount, solutionSize);
		}
		catch(IOException e)
		{
			throw new MultiExperimentFileReaderException(e.getMessage());
		}
	}

	/**
	 * Carrega um arquivo com os resultados do experimento a partir de um stream
	 *  
	 * @param name				Nome do experimento
	 * @param stream			Stream de onde os dados serão carregados
	 * @param instanceCount		Número de instâncias do arquivo
	 * @param cycleCount		Número de ciclos por instância
	 * @param objectiveCount	Número de objetivos por instância
	 * @param solutionSize		Número de entradas da solução
	 */
	public MultiExperimentResult execute (String name, InputStream stream, int instanceCount, int cycleCount, int objectiveCount, int solutionSize) throws MultiExperimentFileReaderException
	{
		currentLineNumber = 0;
		Scanner scanner = new Scanner(stream);
		
		try
		{
			MultiExperimentResult result = new MultiExperimentResult(name);
			
			for (int i = 0; i < instanceCount; i++)
				readInstance(result, i, cycleCount, objectiveCount, solutionSize, scanner);

			return result;
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
	private int parseInteger (String s) throws MultiExperimentFileReaderException
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
	private double parseDouble (String s) throws MultiExperimentFileReaderException
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
	protected void throwException (String message, List<String> messages) throws MultiExperimentFileReaderException
	{
		String s = message + "\n";
		
		for (int i = 0; i < messages.size(); i++)
			s += messages.get(i) + "\n";		
		
		throw new MultiExperimentFileReaderException(currentLineNumber, s);
	}

	/**
	 * Gera uma exceção durante o processo de carga do arquivo
	 * 
	 * @param message		Mensagem que será gerada na exceção
	 */
	private void throwException (String message) throws MultiExperimentFileReaderException
	{
		throw new MultiExperimentFileReaderException(currentLineNumber, message);
	}

	/**
	 * Carrega os dados de uma instância do arquivo com o resultado do experimento
	 *  
	 * @param result			Resultado de experimento onde a instância será adicionada
	 * @param instanceCount		Número de instâncias do arquivo
	 * @param cycleCount		Número de ciclos por instância
	 * @param objectiveCount	Número de objetivos por instância
	 * @param solutionSize		Número de entradas da solução
	 * @param scanner			Classe responsável pela leitura do arquivo
	 */
	private void readInstance (MultiExperimentResult result, int instanceCount, int cycleCount, int objectiveCount, int solutionSize, Scanner scanner) throws MultiExperimentFileReaderException
	{
		MultiExperimentInstance instance = new MultiExperimentInstance(objectiveCount, solutionSize);
		readInstanceHeader (instance, instanceCount, scanner);
		
		for (int i = 0; i < cycleCount; i++)
			readCycle (instance, i, objectiveCount, solutionSize, scanner);

		readBestFrontierHeader(instance, objectiveCount, solutionSize, scanner);
		result.addInstance(instance);
	}
	
	/**
	 * Carrega a melhor fronteira eficiente de uma instância do arquivo com o resultado de um experimento
	 * 
	 * @param instance			Instância que está sendo carregada
	 * @param objectiveCount	Número de objetivos por instância
	 * @param solutionSize		Número de entradas da solução
	 * @param scanner			Classe responsável pela leitura do arquivo
	 */
	private void readBestFrontierHeader(MultiExperimentInstance instance, int objectiveCount, int solutionSize, Scanner scanner) throws MultiExperimentFileReaderException
	{
		boolean mustCalculate = true;
		boolean isPresented = false;

		if (scanner.hasNextLine())
		{
			String firstHeaderLine = readLine(scanner);		
			mustCalculate = (firstHeaderLine.compareTo("Calculate Best Frontier:") == 0);
			isPresented = firstHeaderLine.compareTo("Final Pareto Frontier:") == 0;
		}

		if (!mustCalculate && !isPresented)
			throwException("expected final Pareto Frontier header or calculation header");

		ParetoFront calculatedBest = instance.calculateBestFront();

		if (isPresented)
		{
			readParetoFrontier(instance.getBestFront(), objectiveCount, solutionSize, scanner);
		}
		else
		{
			instance.getBestFront().merge(calculatedBest);

			if (scanner.hasNextLine())
				readLine(scanner);
		}
		
		// Em algumas situações, as fronteiras podem ficar diferentes porque o MQ varia na 5a casa decimal
		// Daí, o melhor a fazer é calcular a melhor fronteira eficiente aqui no programa de análise ...  
		instance.getBestFront().clear();
		instance.getBestFront().merge(calculatedBest);
		
		//if (!calculatedBest.isEqual(instance.getBestFront(), messages))
		//	throwException("the calculated best frontier differs from the estimated one", messages);
	}

	/**
	 * Carrega o cabeçalho de uma instância do arquivo com o resultado de um experimento
	 * 
	 * @param instance			Instância que está sendo carregada
	 * @param instanceCount		Número de instâncias carregadas
	 * @param scanner			Classe responsável pela leitura do arquivo
	 */
	private void readInstanceHeader(MultiExperimentInstance instance, int instanceCount, Scanner scanner) throws MultiExperimentFileReaderException
	{
		String firstHeaderLine = readLine(scanner);
		
		if (firstHeaderLine.compareTo("=============================================================") != 0)
			throwException("expected header start");

		String secondHeaderLine = readLine(scanner);
		
		String headerStart = "Instance #" + instanceCount;
		
		if (!secondHeaderLine.startsWith(headerStart))
			throwException("expected header instance count");
		
		if (secondHeaderLine.length() > headerStart.length())
			instance.setName(secondHeaderLine.substring(headerStart.length()).trim());
		else
			instance.setName("#" + instanceCount);

		String thirdHeaderLine = readLine(scanner);
		
		if (thirdHeaderLine.compareTo("=============================================================") != 0)
			throwException("expected header finish");

		String fourthHeaderLine = readLine(scanner);
		
		if (fourthHeaderLine.length() != 0)
			throwException("expected blank line after header finish");
	}
	
	/**
	 * Carrega um ciclo de uma instância do arquivo de resultados de um experimento
	 * 
	 * @param instance			Instância que está sendo carregada
	 * @param cycleCount		Número de ciclos por instância
	 * @param objectiveCount	Número de objetivos por instância
	 * @param solutionSize		Número de entradas da solução
	 * @param scanner			Classe responsável pela leitura do arquivo
	 */
	private void readCycle (MultiExperimentInstance instance, int cycleCount, int objectiveCount, int solutionSize, Scanner scanner) throws MultiExperimentFileReaderException
	{
		MultiExperimentCycle cycle = new MultiExperimentCycle(objectiveCount, solutionSize);;
		readCycleHeader (cycle, cycleCount, scanner);
		readParetoFrontier(cycle.getFront(), objectiveCount, solutionSize, scanner);
		instance.addCycle(cycle);
	}
	
	/**
	 * Carrega o cabeçalho de um ciclo de instância do arquivo de resultado de experimento
	 * 
	 * @param cycle				Número de ciclos carregados
	 * @param cycleCount		Número de ciclos por instância
	 * @param scanner			Classe responsável pela leitura do arquivo
	 */
	private void readCycleHeader(MultiExperimentCycle cycle, int cycleCount, Scanner scanner) throws MultiExperimentFileReaderException
	{
		String line = readLine(scanner);
		String cycleHeader = "Cycle #" + cycleCount;
		
		if (!line.startsWith(cycleHeader))
			throwException("expected 'Cycle #" + cycleCount + "'");
		
		line = line.substring(cycleHeader.length());
		
		if (!line.startsWith(" ("))
			throwException("expected opening parenthesis after cycle header");

		line = line.substring(2);

		int position = line.indexOf(" ms");
		
		if (position <= 0)
			throwException("expected execution time in miliseconds");
		
		int executionTime = parseInteger(line.substring(0, position));
		
		line = line.substring(position + 3);
		
		if (!line.startsWith("; "))
			throwException("expected semicolon after execution time");

		line = line.substring(2);

		position = line.indexOf(" best solutions");
		
		if (position <= 0)
			throwException("expected number of best solutions");
		
		//int bestSolutions = parseInteger(line.substring(0, position));
		parseInteger(line.substring(0, position));
		
		cycle.setExecutionTime(executionTime);
		//cycle.setBestSolutions(bestSolutions);
	}
	
	/**
	 * Carrega uma frente de Pareto
	 * 
	 * @param front				Frente de Pareto que será carregada
	 * @param objectiveCount	Número de objetivos por instância
	 * @param solutionSize		Número de entradas da solução
	 * @param scanner			Classe responsável pela leitura do arquivo
	 */
	private void readParetoFrontier(ParetoFront front, int objectiveCount, int solutionSize, Scanner scanner) throws MultiExperimentFileReaderException
	{
		String s = readLine(scanner);
		
		while (s.length() > 0)
		{
			ParetoFrontVertex vertex = new ParetoFrontVertex(objectiveCount, solutionSize);

			String[] tokens = s.split(";");
			
			if (tokens.length != objectiveCount + 2)
				throwException("number of tokens in Pareto frontier entry different from expected");
			
			for (int i = 0; i < objectiveCount; i++)
			{
				double value = parseDouble(tokens[i]);
				vertex.setObjective(i, value);
			}

			int location = parseInteger(tokens[objectiveCount]);
			vertex.setLocation(location);
			
			parseSolution(tokens[objectiveCount + 1].trim(), vertex, solutionSize);
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
	private void parseSolution (String solution, ParetoFrontVertex vertex, int solutionSize) throws MultiExperimentFileReaderException
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
			//if (tokens[0].length() != solutionSize)
			//	throwException("invalid number of itens in a binary solution (expected: " + solutionSize + "; found: " + tokens[0].length() + ")");

			for (int i = 0; i < solutionSize; i++)
				vertex.setSolution(i, (tokens[0].charAt(i) == '0') ? 0 : 1);
		}
		else
		{		
			//if (tokens.length != solutionSize)
			//	throwException("invalid number of itens in a solution (expected: " + solutionSize + "; found: " + tokens.length + ")");
	
			for (int i = 0; i < solutionSize; i++)
				vertex.setSolution(i, parseInteger(tokens[i]));
		}
	}
}