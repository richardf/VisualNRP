package sobol.base.visitor;

import sobol.base.solution.BinarySolution;
import sobol.base.solution.Solution;

public class BinaryNeighborVisitor implements NeighborVisitor
{
	public BinaryNeighborVisitor()
	{
	}

	@Override
	public int neighborCount(Solution solution)
	{
 		return ((BinarySolution)solution).numberOfVariables();
	}

	@Override
	public Solution getNeighbor(Solution solution, int index)
	{
		BinarySolution copy = (BinarySolution) solution.clone();
		copy.flip(index);
		return copy;
	}
}