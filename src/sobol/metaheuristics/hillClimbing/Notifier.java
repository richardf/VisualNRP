package sobol.metaheuristics.hillClimbing;

public interface Notifier
{
	void newIteration(int evaluations, double objective);
}