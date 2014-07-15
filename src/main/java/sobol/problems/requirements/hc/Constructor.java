/*
 * This interface represents an algorithm that generates an initial
 * solution for the problem.
 */
package sobol.problems.requirements.hc;

import sobol.base.random.generic.AbstractRandomGenerator;

public interface Constructor {
    boolean[] generateSolution();
    boolean[] generateSolutionWith(int numberOfCustomers);
    boolean[] generateSolutionInInterval(int minCustomers, int maxCustomers);
    void setRandomGenerator(AbstractRandomGenerator random);
}
