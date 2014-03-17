/**
 * SinglePointCrossover.java
 * Class representing a single point crossover operator
 * @author Juan J. Durillo
 * @author Antonio J. Nebro
 * @version 1.1
 */
package sobol.base.crossover;

import sobol.base.solution.Solution;

public interface CrossoverOperator
{
	Solution[] execute(Solution parent0, Solution parent1);
}