package sobol.base.visitor;

import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.solution.IntSolution;
import sobol.base.solution.Solution;

public class IntNeighborVisitor implements NeighborVisitor
{
	private AbstractRandomGenerator random;
	
	public IntNeighborVisitor()
	{
		this.random = RandomGeneratorFactory.createForOperator(1);
	}
	
	public int neighborCount(Solution solution)
	{
		return solution.numberOfVariables(); 
	}
	
	public Solution getNeighbor(Solution solution, int index)
	{
		IntSolution neighbor = (IntSolution) solution.clone();		
		int originalValue = ((IntSolution)solution).getValue(index);
		int newValue = random.randInt(neighbor.getType().getLowerBound(index), neighbor.getType().getUpperBound(index))[0];
		
		while (newValue == originalValue)
			newValue = random.randInt(neighbor.getType().getLowerBound(index), neighbor.getType().getUpperBound(index))[0];
		
		neighbor.setValue(index, newValue);
		return neighbor;
	}
}