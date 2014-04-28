package sobol.experiments.monoobjective.analysis.reader;

public class MonoExperimentFileReaderException extends Exception
{
	private static final long serialVersionUID = -2117057147814141836L;

	public MonoExperimentFileReaderException(String message)
	{
		super(message);
	}

	public MonoExperimentFileReaderException(int line, String message)
	{
		this("Line " + line + ": " + message);
	}
}