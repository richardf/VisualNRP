package sobol.experiments.monoobjective.analysis.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import sobol.experiments.monoobjective.analysis.model.MonoExperimentCycle;
import sobol.experiments.monoobjective.analysis.model.MonoExperimentInstance;
import sobol.experiments.monoobjective.analysis.model.MonoExperimentResult;

/**
 * Classe responsável pela leitura dos resultados de um experimento
 * 
 * @author Marcio Barros
 */
public class MonoExperimentFileReader
{
	private int currentLineNumber;

	public MonoExperimentResult execute (String filename, int instanceCount, int cycleCount, int solutionSize) throws MonoExperimentFileReaderException
	{
		try
		{
			return execute(extractFileName(filename), new FileInputStream(filename), instanceCount, cycleCount, solutionSize);
		}
		catch(IOException e)
		{
			throw new MonoExperimentFileReaderException(e.getMessage());
		}
	}

	public MonoExperimentResult execute (String name, InputStream stream, int instanceCount, int cycleCount, int solutionSize) throws MonoExperimentFileReaderException
	{
		currentLineNumber = 0;
		Scanner scanner = new Scanner(stream);
		
		try
		{
			MonoExperimentResult result = new MonoExperimentResult(name);
			
			for (int i = 0; i < instanceCount; i++)
				readInstance(result, i, cycleCount, solutionSize, scanner);

			return result;
		}
		finally
		{
			scanner.close();
		}
	}

	private void readInstance (MonoExperimentResult result, int instanceCount, int cycleCount, int solutionSize, Scanner scanner) throws MonoExperimentFileReaderException
	{
		MonoExperimentInstance instance = new MonoExperimentInstance();
		readInstanceHeader (instance, instanceCount, scanner);
		
		for (int i = 0; i < cycleCount; i++)
			readCycle (instance, i, solutionSize, scanner);

		result.addInstance(instance);
	}
	
	private void readInstanceHeader(MonoExperimentInstance instance, int instanceCount, Scanner scanner) throws MonoExperimentFileReaderException
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

		/*String fourthHeaderLine = readLine(scanner);
		
		if (fourthHeaderLine.length() != 0)
			throwException("expected blank line after header finish");*/
	}
	
	private void readCycle (MonoExperimentInstance instance, int cycleCount, int solutionSize, Scanner scanner) throws MonoExperimentFileReaderException
	{
		MonoExperimentCycle cycle = new MonoExperimentCycle(solutionSize);
		readCycleHeader (cycle, cycleCount, solutionSize, scanner);
		instance.addCycle(cycle);
	}
	
	private void readCycleHeader(MonoExperimentCycle cycle, int cycleCount, int solutionSize, Scanner scanner) throws MonoExperimentFileReaderException
	{
		String line = readLine(scanner);
		String cycleHeader = "Cycle #" + cycleCount;
		
		if (!line.startsWith(cycleHeader))
			throwException("expected 'Cycle #" + cycleCount + "'");
		
		line = line.substring(cycleHeader.length());
		
		if (!line.startsWith("; "))
			throwException("expected semicolon and space after cycle header");

		line = line.substring(2);

		int position = line.indexOf(";");
		
		if (position <= 0)
			throwException("expected execution time in miliseconds");
		
		int executionTime = parseInteger(line.substring(0, position));
		
		line = line.substring(position);
		
		if (!line.startsWith("; "))
			throwException("expected semicolon after execution time");

		line = line.substring(2);

		position = line.indexOf(";");
		
		if (position <= 0)
			throwException("expected solution objective value");
		
		double objective = parseDouble(line.substring(0, position));
		
		line = line.substring(position);
		
		if (!line.startsWith("; "))
			throwException("expected semicolon after objective");

		line = line.substring(2);

		position = line.indexOf(";");
		
		if (position <= 0)
			throwException("expected solution location");
		
		long location = parseLong(line.substring(0, position));
		
		line = line.substring(position);
		
		if (!line.startsWith("; "))
			throwException("expected semicolon after location");
		
		line = line.substring(3);
		
		cycle.setExecutionTime(executionTime);
		cycle.setObjective(objective);
		cycle.setLocation(location);
		parseSolution(line, cycle, solutionSize);
	}
	
	private void parseSolution (String solution, MonoExperimentCycle cycle, int solutionSize) throws MonoExperimentFileReaderException
	{
		if (solution.length() < 2)
			throwException("a solution must have at least two characters");
		
		if (solution.charAt(0) != '[')
			throwException("the first character in a solution must be '['");
		
		if (solution.charAt(solution.length()-1) != ']')
			throwException("the last character in a solution must be ']'");
		
		String sSolution = solution.substring(1, solution.length()-1);
		String[] tokens = sSolution.split(" ");
		
		if (tokens.length != solutionSize)
			throwException("invalid number of itens in a solution (expected: " + solutionSize + "; found: " + tokens.length + ")");

		for (int i = 0; i < tokens.length; i++)
			cycle.setSolution(i, parseInteger(tokens[i]));
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
	private int parseInteger (String s) throws MonoExperimentFileReaderException
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
	private double parseDouble (String s) throws MonoExperimentFileReaderException
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
	 * Processa uma string, convertendo-a para um valor inteiro longo
	 * 
	 * @param s		Texto que será convertido para inteiro longo
	 */
	private long parseLong(String s) throws MonoExperimentFileReaderException
	{
		try
		{
			return Long.parseLong(s.trim());
		}
		catch(Exception e)
		{
			throwException ("invalid long integer value");
		}
		
		return 0;
	}

	/**
	 * Gera uma exceção durante o processo de carga do arquivo
	 * 
	 * @param message		Mensagem que será gerada na exceção
	 */
	private void throwException (String message) throws MonoExperimentFileReaderException
	{
		throw new MonoExperimentFileReaderException(currentLineNumber, message);
	}
}