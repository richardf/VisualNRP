package sobol.problems.requirements.hc;

import sobol.problems.requirements.model.Project;

/**
 * Class the represents a solution for the hill climbing search for requirements
 * 
 * @author Marcio
 */
public class Solution
{
	private Project project;
	private int[][] customerRequirementsPrecedences;
	private int[] currentRequirementSelection;
	private boolean[] currentCustomerSelection;
	private int cost;
	private int profit;
	
	/**
	 * Initializes the solution, given a requirements project
	 */
	public Solution(Project project)
	{
		this.project = project;

		prepareCustomersRequirementPrecedences();
		new ConsistencyPrecedences().executa(project, this);
		
		int customerCount = project.getCustomerCount();
		this.currentCustomerSelection = new boolean[customerCount];
		
		int requirementCount = project.getRequirementCount();
		this.currentRequirementSelection = new int[requirementCount];
		
		this.cost = 0;
		this.profit = 0;
	}
	
	/**
	 * Prepare the lists of precedent requirements for all customers
	 */
	private void prepareCustomersRequirementPrecedences()
	{
		int customerCount = project.getCustomerCount();
		int requirementCount = project.getRequirementCount();
		this.customerRequirementsPrecedences = new int[customerCount][requirementCount+1];
		
		for (int i = 0; i < customerCount; i++)
			prepareCustomerRequirementPrecedence(project, i);
	}
	
	/**
	 * Prepare the list of precedent requirements for a given customer
	 */
	private void prepareCustomerRequirementPrecedence(Project project, int customerIndex)
	{
		int customerRequirementCount = project.getCustomerRequirementsCount(customerIndex);
		int position = 0;
		
		for (int i = 0; i < customerRequirementCount; i++)
		{
			int requirementIndex = project.getCustomerRequirementIndex(customerIndex, i);

			if (requirementIndex >= 0)
				position = prepareCustomerRequirementAndPrecedents(project, customerIndex, requirementIndex, position);
		}
		
		this.customerRequirementsPrecedences[customerIndex][position] = -1;
	}
	
	/**
	 * Prepare the list of precedent requirements for a given customer from a given requirement
	 */
	private int prepareCustomerRequirementAndPrecedents(Project project, int customerIndex, int requirementIndex, int position)
	{
		if (checkCustomerHasRequirement(customerIndex, requirementIndex, position))
			return position;
		
		this.customerRequirementsPrecedences[customerIndex][position] = requirementIndex;
		position++;

		if (project.requirementHasPrecedents(requirementIndex))
		{
			int dependencies = project.getRequirementPrecedentsCount(requirementIndex);
			
			for (int i = 0; i < dependencies; i++)
			{
				int source = project.getRequirementPrecedentIndex(requirementIndex, i);
				
				if (source >= 0)
					position = prepareCustomerRequirementAndPrecedents(project, customerIndex, source, position);
			}
		}
		
		return position;
	}
	
	/**
	 * Checks whether a customer has a given requirement in the precedence list being built
	 */
	private boolean checkCustomerHasRequirement(int customerIndex, int requirementIndex, int maxPosition)
	{
		int requirement;
		
		for (int i = 0; i < maxPosition && (requirement = this.customerRequirementsPrecedences[customerIndex][i]) >= 0; i++)
			if (requirement == requirementIndex)
				return true;

		return false;
	}
	
	/**
	 * Return an array representing the current solution
	 */
	public boolean[] getSolution()
	{
		return currentCustomerSelection;
	}
	
	/**
	 * Returns the cost of the current solution
	 */
	public int getCost()
	{
		return cost;
	}
	
	/**
	 * Returns the profit of the current solution
	 */
	public int getProfit()
	{
		return profit;
	}

	/**
	 * Returns the number of requirements and precedences of a customer
	 */
	public int getCustomerRequirementWithPrecedenceCount(int customerIndex)
	{
		return customerRequirementsPrecedences[customerIndex].length;
	}

	/**
	 * Returns a customer's requirement or precedence, given its indexes
	 */
	public int getCustomerRequirementWithPrecedenceIndex(int customerIndex, int index)
	{
		return customerRequirementsPrecedences[customerIndex][index];
	}
	
	/**
	 * Sets the presence of all customers in the solution
	 */
	public void setAllCustomers(boolean[] newSelection)
	{
		int customerCount = project.getCustomerCount();
		
		for (int i = 0; i < customerCount; i++)
			currentCustomerSelection[i] = newSelection[i];
		
		int requirementCount = project.getRequirementCount();
		
		for (int i = 0; i < requirementCount; i++)
			currentRequirementSelection[i] = 0;
		
		this.cost = 0;
		
		for (int i = 0; i < customerCount; i++)
			if (currentCustomerSelection[i])
				addCustomerRequirementsCost(i);

		this.profit = 0;
		
		for (int i = 0; i < customerCount; i++)
			if (currentCustomerSelection[i])
				this.profit += project.getCustomerProfit(i);
	}
	
	/**
	 * Flips the presence of a customer in the solution
	 */
	public void flipCustomer(int customerIndex)
	{
		if (this.currentCustomerSelection[customerIndex])
		{
			this.currentCustomerSelection[customerIndex] = false;
			removeCustomerRequirementsCost(customerIndex);
			this.profit -= project.getCustomerProfit(customerIndex);
		}
		else
		{
			this.currentCustomerSelection[customerIndex] = true;
			addCustomerRequirementsCost(customerIndex);
			this.profit += project.getCustomerProfit(customerIndex);
		}
	}

	/**
	 * Adds the effects of a customer's requirements over cost
	 */
	private void addCustomerRequirementsCost(int customerIndex)
	{
		int requirementIndex;

		for (int i = 0; (requirementIndex = customerRequirementsPrecedences[customerIndex][i]) >= 0; i++)
		{
			if (currentRequirementSelection[requirementIndex] == 0)
				cost += project.getRequirementCost(requirementIndex);
			
			currentRequirementSelection[requirementIndex]++;
		}
	}

	/**
	 * Removes the effects of a customer's requirements over cost
	 */
	private void removeCustomerRequirementsCost(int customerIndex)
	{
		int requirementIndex;

		for (int i = 0; (requirementIndex = customerRequirementsPrecedences[customerIndex][i]) >= 0; i++)
		{
			currentRequirementSelection[requirementIndex]--;

			if (currentRequirementSelection[requirementIndex] == 0)
				cost -= project.getRequirementCost(requirementIndex);
		}
	}
}