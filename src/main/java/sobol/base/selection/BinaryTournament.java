/**
 * BinaryTournament.java
 * @author Juan J. Durillo
 * @version 1.0
 */
package sobol.base.selection;

import java.util.Comparator;
import sobol.base.comparator.BinaryTournamentComparator;
import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.solution.Solution;
import sobol.base.solution.SolutionSet;

/**
 * This class implements an operator for binary selections
 */
public class BinaryTournament implements SelectionOperator
{
	private AbstractRandomGenerator randomPopulation;
	private AbstractRandomGenerator randomSelector;
	private Comparator<Solution> comparator_;

	public BinaryTournament()
	{
		this.randomPopulation = RandomGeneratorFactory.createForOperator(2);
		this.randomSelector = RandomGeneratorFactory.createForOperator(1);
		comparator_ = new BinaryTournamentComparator();
	}

	public Solution execute(SolutionSet population)
	{
		int[] index = randomPopulation.randInt(0, population.size() - 1);
		
		Solution solution1 = population.get(index[0]);
		Solution solution2 = population.get(index[1]);
		int flag = comparator_.compare(solution1, solution2);
		
		if (flag == -1)
			return solution1;

		if (flag == 1)
			return solution2;
		
		if (randomSelector.singleDouble() < 0.5)
			return solution1;

		return solution2;
	}
}