package sobol.experiments.multiobjective.generations;

import sobol.base.qualityIndicator.GenerationalDistance;
import sobol.base.qualityIndicator.Spread;
import sobol.experiments.multiobjective.analysis.model.ParetoFront;

public class Generation
{
	private int number;
	private int evaluations;
	private ParetoFront front;
	
	public Generation(int objectiveCount, int solutionSize)
	{
		this.front = new ParetoFront(objectiveCount, solutionSize);
		this.evaluations = 0;
		this.number = 0;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public void setNumber(int number)
	{
		this.number = number;
	}
	
	public int getEvaluations()
	{
		return evaluations;
	}
	
	public void setEvaluations(int evaluations)
	{
		this.evaluations = evaluations;
	}
	
	public ParetoFront getFront()
	{
		return front;
	}

	public double getBestSolutions(ParetoFront bestFront)
	{
		return bestFront.countCommonVertices(front);
	}
	
	public double getSpreads(ParetoFront bestFront)
	{
		Spread spread = new Spread();		
		double[][] bestFrontValues = bestFront.getValues();
		double[][] currentFrontValues = front.getValues();
		return spread.spread(currentFrontValues, bestFrontValues, bestFront.getObjectiveCount());
	}

	public double getGenerationalDistances(ParetoFront bestFront)
	{
		GenerationalDistance gd = new GenerationalDistance();
		double[][] bestFrontValues = bestFront.getValues();
		double[][] currentFrontValues = front.getValues();
		return gd.generationalDistance(currentFrontValues, bestFrontValues, bestFront.getObjectiveCount());
	}
	
	public double getErrorRatios(ParetoFront bestFront)
	{
		double result = getBestSolutions(bestFront);
		return 1.0 - result / front.getVertexCount();
	}
}