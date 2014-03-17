package sobol.experiments.multiobjective.generations;

public interface GenerationListener
{
	public void processGeneration(int number, Generation generation);
}