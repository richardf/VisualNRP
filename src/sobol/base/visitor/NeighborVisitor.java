package sobol.base.visitor;

import sobol.base.solution.Solution;

public interface NeighborVisitor
{
	int neighborCount(Solution solution);
	
	Solution getNeighbor(Solution solution, int index);
}