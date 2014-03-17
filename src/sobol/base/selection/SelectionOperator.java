/**
 * BinaryTournament.java
 * @author Juan J. Durillo
 * @version 1.0
 */
package sobol.base.selection;

import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;

public interface SelectionOperator
{
	Solution execute(SolutionSet population);
}