package sobol.experiments.utils;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SeparatedFile
{
	public static final char DEFAULT_SEPARATOR = '\t';
	public static final String DEFAULT_NUMBER_FORMAT = "0.0000";

	private NumberFormat formatter;
	private char separator;
	private Writer out;

	/**
	 * Inicializa um arquivo com separador
	 * 
	 * @param filename Nome do arquivo
	 * @param separator Caractere utilizado como separador
	 * @param format Formato de apresentação de números
	 */
	public SeparatedFile(String filename, char separator, String format)
	{
		this.separator = separator;
		this.formatter = new DecimalFormat(format);

		try
		{
			out = new OutputStreamWriter(new FileOutputStream(filename));
		}
		catch (Exception e)
		{
			out = null;
		}
	}

	/**
	 * Inicializa um arquivo com separador
	 * 
	 * @param filename Nome do arquivo
	 * @param separator Caractere utilizado como separador
	 */
	public SeparatedFile(String filename, char separator)
	{
		this(filename, separator, DEFAULT_NUMBER_FORMAT);
	}

	/**
	 * Inicializa um arquivo com separador
	 * 
	 * @param filename Nome do arquivo
	 */
	public SeparatedFile(String filename)
	{
		this(filename, DEFAULT_SEPARATOR, DEFAULT_NUMBER_FORMAT);
	}

	/**
	 * Verifica se o arquivo foi aberto com sucesso
	 */
	public boolean isValid()
	{
		return out != null;
	}

	/**
	 * Escreve um campo no arquivo
	 * 
	 * @param field Valor do campo que será escrito
	 */
	public void write(String field)
	{
		try
		{
			if (out != null)
				out.write(field + separator);
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * Escreve um campo numérico no arquivo
	 * 
	 * @param field Valor do campo que será escrito
	 */
	public void write(double field)
	{
		write(formatter.format(field));
	}

	/**
	 * Escreve um campo inteiro no arquivo
	 * 
	 * @param field Valor do campo que será escrito
	 */
	public void write(int field)
	{
		write(Integer.toString(field));
	}

	/**
	 * Salta uma linha no arquivo
	 */
	public void newLine()
	{
		try
		{
			if (out != null)
				out.write("\r\n");
		}
		catch (Exception e)
		{
		}
	}

	/**
	 * Fecha o arquivo
	 */
	public void close()
	{
		try
		{
			if (out != null)
				out.close();
		}
		catch (Exception e)
		{
		}
	}
}