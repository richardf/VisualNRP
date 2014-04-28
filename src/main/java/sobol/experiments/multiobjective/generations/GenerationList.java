package sobol.experiments.multiobjective.generations;

import java.util.ArrayList;
import java.util.List;
import sobol.base.qualityIndicator.GenerationalDistance;
import sobol.base.qualityIndicator.Spread;
import sobol.experiments.multiobjective.analysis.model.ParetoFront;

public class GenerationList
{
	private List<Generation> generations;
	
	public GenerationList()
	{
		this.generations = new ArrayList<Generation>();
	}
	
	public int countGenerations()
	{
		return this.generations.size();
	}
	
	public Generation getGenerationIndex(int index)
	{
		return this.generations.get(index);
	}
	
	public void addGeneration(Generation generation)
	{
		this.generations.add(generation);
	}

	public double[] getBestSolutions(ParetoFront bestFront)
	{
		double[] results = new double[generations.size()];
		
		for (int i = 0; i < generations.size(); i++)
		{
			Generation generation = getGenerationIndex(i);
			results[i] = bestFront.countCommonVertices(generation.getFront());
		}
		
		return results;
	}
	
	public double[] getSpreads(ParetoFront bestFront)
	{
		double[] results = new double[generations.size()];
		double[][] bestFrontValues = bestFront.getValues();
		Spread spread = new Spread();
		
		for (int i = 0; i < generations.size(); i++)
		{
			Generation generation = getGenerationIndex(i);
			double[][] currentFrontValues = generation.getFront().getValues();
			results[i] = spread.spread(currentFrontValues, bestFrontValues, bestFront.getObjectiveCount());
		}
		
		return results;
	}

	public double[] getGenerationalDistances(ParetoFront bestFront)
	{
		double[] results = new double[generations.size()];
		double[][] bestFrontValues = bestFront.getValues();
		GenerationalDistance gd = new GenerationalDistance();
		
		for (int i = 0; i < generations.size(); i++)
		{
			Generation generation = getGenerationIndex(i);
			double[][] currentFrontValues = generation.getFront().getValues();
			results[i] = gd.generationalDistance(currentFrontValues, bestFrontValues, bestFront.getObjectiveCount());
		}
		
		return results;
	}
	
	public double[] getErrorRatios(ParetoFront bestFront)
	{
		double[] results = getBestSolutions(bestFront);
		
		for (int i = 0; i < generations.size(); i++)
		{
			Generation generation = getGenerationIndex(i);
			results[i] = 1.0 - results[i] / generation.getFront().getVertexCount();
		}
		
		return results;
	}
}