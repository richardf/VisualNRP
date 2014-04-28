package sobol.experiments.multiobjective.analysis.reader;

public class MultiExperimentFileReaderException extends Exception
{
	private static final long serialVersionUID = -2117057147814141836L;

	public MultiExperimentFileReaderException(String message)
	{
		super(message);
	}

	public MultiExperimentFileReaderException(int line, String message)
	{
		this("Line " + line + ": " + message);
	}
}