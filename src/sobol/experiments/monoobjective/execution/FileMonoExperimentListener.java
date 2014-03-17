package sobol.experiments.monoobjective.execution;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Interface que publica os eventos do ciclo de vida de um experimento em um arquivo
 * 
 * @author Marcio Barros
 */
public class FileMonoExperimentListener extends StreamMonoExperimentListener
{
	/**
	 * Inicializa o analisador de experimentos que publica resultados em arquivos
	 * 
	 * @param filename		Nome do arquivo que será usado como resultado
	 */
	public FileMonoExperimentListener(String filename) throws FileNotFoundException
	{
		this(filename, false);
	}

	/**
	 * Inicializa o analisador de experimentos que publica resultados em arquivos
	 * 
	 * @param filename		Nome do arquivo que será usado como resultado
	 */
	public FileMonoExperimentListener(String filename, boolean details) throws FileNotFoundException
	{
		super(new OutputStreamWriter(new FileOutputStream(filename)), details);
	}

	/**
	 * Termina o experimento
	 */
	public void terminateExperiment() throws Exception
	{
		getWriter().close();
	}
}