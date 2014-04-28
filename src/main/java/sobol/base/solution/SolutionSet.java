/**
 * SolutionSet.java
 * 
 * @author Juan J. Durillo
 * @version 1.0
 */
package sobol.base.solution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing a SolutionSet (a set of solutions)
 */
public class SolutionSet implements Serializable
{
	private static final long serialVersionUID = 7412358376942363779L;
	
	protected List<Solution> solutionsList_;
	private int capacity_ = 0;

	/**
	 * Constructor. Creates an unbounded solution set.
	 */
	public SolutionSet()
	{
		solutionsList_ = new ArrayList<Solution>();
	}

	/**
	 * Creates a empty solutionSet with a maximum capacity.
	 * 
	 * @param maximumSize Maximum size.
	 */
	public SolutionSet(int maximumSize)
	{
		solutionsList_ = new ArrayList<Solution>();
		capacity_ = maximumSize;
	}

	/**
	 * Inserts a new solution into the SolutionSet.
	 * 
	 * @param solution The <code>Solution</code> to store
	 * @return True If the <code>Solution</code> has been inserted, false
	 *         otherwise.
	 */
	public boolean add(Solution solution)
	{
		if (solutionsList_.size() == capacity_)
			return false;

		solutionsList_.add(solution);
		return true;
	}

	/**
	 * Returns the ith solution in the set.
	 * 
	 * @param i Position of the solution to obtain.
	 * @return The <code>Solution</code> at the position i.
	 * @throws IndexOutOfBoundsException.
	 */
	public Solution get(int i)
	{
		if (i >= solutionsList_.size())
			throw new IndexOutOfBoundsException("Index out of Bound " + i);

		return solutionsList_.get(i);
	}

	/**
	 * Returns the maximum capacity of the solution set
	 * 
	 * @return The maximum capacity of the solution set
	 */
	public int getMaxSize()
	{
		return capacity_;
	}

	/**
	 * Sorts a SolutionSet using a <code>Comparator</code>.
	 * 
	 * @param comparator <code>Comparator</code> used to sort.
	 */
	public void sort(Comparator<Solution> comparator)
	{
		if (comparator == null)
			return;

		Collections.sort(solutionsList_, comparator);
	}

	/**
	 * Returns the number of solutions in the SolutionSet.
	 * 
	 * @return The size of the SolutionSet.
	 */
	public int size()
	{
		return solutionsList_.size();
	}

	/**
	 * Empties the SolutionSet
	 */
	public void clear()
	{
		solutionsList_.clear();
	}

	/**
	 * Deletes the <code>Solution</code> at position i in the set.
	 * 
	 * @param i The position of the solution to remove.
	 */
	public void remove(int i)
	{
		solutionsList_.remove(i);
	}

	/**
	 * Returns an <code>Iterator</code> to access to the solution set list.
	 * 
	 * @return the <code>Iterator</code>.
	 */
	public Iterator<Solution> iterator()
	{
		return solutionsList_.iterator();
	}

	/**
	 * Returns a new <code>SolutionSet</code> which is the result of the union
	 * between the current solution set and the one passed as a parameter.
	 * 
	 * @param solutionSet SolutionSet to join with the current solutionSet.
	 * @return The result of the union operation.
	 */
	public SolutionSet union(SolutionSet solutionSet)
	{
		int newSize = this.size() + solutionSet.size();

		if (newSize < capacity_)
			newSize = capacity_;

		SolutionSet union = new SolutionSet(newSize);
		
		for (int i = 0; i < this.size(); i++)
			union.add(this.get(i));

		for (int i = this.size(); i < (this.size() + solutionSet.size()); i++)
			union.add(solutionSet.get(i - this.size()));

		return union;
	}

	/**
	 * Replaces a solution by a new one
	 * 
	 * @param position The position of the solution to replace
	 * @param solution The new solution
	 */
	public void replace(int position, Solution solution)
	{
		if (position > this.solutionsList_.size())
			solutionsList_.add(solution);

		solutionsList_.remove(position);
		solutionsList_.add(position, solution);
	}
}