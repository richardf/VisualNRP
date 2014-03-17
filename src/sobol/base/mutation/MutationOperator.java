package sobol.base.mutation;

import sobol.base.solution.Solution;

public interface MutationOperator
{
	Solution execute(Solution object);
}