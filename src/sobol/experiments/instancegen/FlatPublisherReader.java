package sobol.experiments.instancegen;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Classe responsável por ler um arquivo criado pelo gerador de instâncias
 * 
 * @author Marcio Barros
 */
public abstract class FlatPublisherReader
{
	private int currentLineNumber;

	/**
	 * Gera uma exceção de leitura do arquivo, dada a mensagem
	 */
	protected void generateException (String message) throws FlatPublisherReaderException
	{
		throw new FlatPublisherReaderException(message + " in line " + currentLineNumber);
	}
	
	/**
	 * Método abstrato para registrar uma entidade vinda do arquivo
	 */
	protected abstract void registerEntity(String type, String name, Hashtable<String, String> attributes) throws FlatPublisherReaderException;
	
	/**
	 * Carrega uma entidade do arquivo
	 */
	private void readEntity(StringTokenizer tokenizer) throws FlatPublisherReaderException
	{
		Hashtable<String, String> attributes = new Hashtable<String, String>();
		
		if (!tokenizer.hasMoreElements())
			generateException("missing entity type");
		
		String entityType = tokenizer.nextToken().trim();
		
		if (!tokenizer.hasMoreElements())
			generateException("missing entity name");

		String entityName = tokenizer.nextToken().trim();
		
		while (tokenizer.hasMoreElements())
		{
			String attributeName = tokenizer.nextToken().trim();
			
			if (!tokenizer.hasMoreElements())
				generateException("missing relation attribute value");

			String attributeValue = tokenizer.nextToken().trim();
			attributes.put(attributeName, attributeValue);
		}
		
		registerEntity(entityType, entityName, attributes);
	}
	
	/**
	 * Método abstrato para registrar um relacionamento vindo do arquivo
	 */
	protected abstract void registerRelationship(String type, String source, String target, Hashtable<String, String> attributes) throws FlatPublisherReaderException;
	
	/**
	 * Carrega um relacionamento vindo do arquivo
	 */
	private void readRelationship(StringTokenizer tokenizer) throws FlatPublisherReaderException
	{
		Hashtable<String, String> attributes = new Hashtable<String, String>();
		
		if (!tokenizer.hasMoreElements())
			generateException("missing relationship type");
		
		String relationshipType = tokenizer.nextToken().trim();
		
		if (!tokenizer.hasMoreElements())
			generateException("missing relationship source");

		String relationshipSource = tokenizer.nextToken().trim();
		
		if (!tokenizer.hasMoreElements())
			generateException("missing relationship target");

		String relationshipTarget = tokenizer.nextToken().trim();
		
		while (tokenizer.hasMoreElements())
		{
			String attributeName = tokenizer.nextToken().trim();
			
			if (!tokenizer.hasMoreElements())
				generateException("missing relationship attribute value");

			String attributeValue = tokenizer.nextToken().trim();
			attributes.put(attributeName, attributeValue);
		}
		
		registerRelationship(relationshipType, relationshipSource, relationshipTarget, attributes);
	}
	
	/**
	 * Processa uma linha do arquivo
	 */
	private void processLine(String line) throws FlatPublisherReaderException
	{
		StringTokenizer tokenizer = new StringTokenizer(line, ";");

		if (tokenizer.countTokens() == 0)
			return;
		
		String lineType = tokenizer.nextToken().trim();
		
		if (lineType.compareToIgnoreCase("E") == 0)
			readEntity(tokenizer);
		
		else if (lineType.compareToIgnoreCase("R") == 0)
			readRelationship(tokenizer);
		
		else
			generateException("Invalid token in line start (found: " + lineType + "; expected 'E' or 'R'");
	}

	/**
	 * Encerra o processamento, ao final da leitura do arquivo
	 */
	public void cleanUp() throws FlatPublisherReaderException
	{		
	}
	
	/**
	 * Carrega um arquivo flat criado pelo gerador de instâncias
	 */
	public void execute(String filename) throws FlatPublisherReaderException
	{
		currentLineNumber = 0;
		Scanner scanner;
		
		try
		{
			scanner = new Scanner(new FileInputStream(filename));
		}
		catch(IOException e)
		{
			throw new FlatPublisherReaderException(e.getMessage());
		}
		
		try
		{
			while (scanner.hasNextLine())
			{
				currentLineNumber++;
				processLine(scanner.nextLine());
			}

			cleanUp();
		}
		finally
		{
			scanner.close();
		}
	}
}