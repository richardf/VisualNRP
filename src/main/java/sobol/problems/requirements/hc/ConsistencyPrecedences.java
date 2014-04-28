package sobol.problems.requirements.hc;

import sobol.problems.requirements.model.Project;

public class ConsistencyPrecedences
{
	public void executa(Project project, Solution solution)
	{
		int count = project.getCustomerCount();
		
		for (int i = 0; i < count; i++)
			checkCustomerCompleteRequirements(project, solution, i);
	}
	
	private void checkCustomerCompleteRequirements(Project project, Solution solution, int customerIndex)
	{
		int count = project.getCustomerRequirementsCount(customerIndex);
		
		for (int i = 0; i < count; i++)
		{
			int requirementIndex = project.getCustomerRequirementIndex(customerIndex, i);
			
			if (requirementIndex >= 0)
				checkCustomerRequirementAndPrecedents(project, solution, customerIndex, requirementIndex);
		}
	}
	
	private void checkCustomerRequirementAndPrecedents(Project project, Solution solution, int customerIndex, int requirementIndex)
	{
		if (!checkCustomerHasRequirement(solution, customerIndex, requirementIndex))
			System.out.println("Customer #" + customerIndex + " does not have requirement #" + requirementIndex);
		
		if (!project.requirementHasPrecedents(requirementIndex))
			return;

		int source;
				
		for (int i = 0; (source = project.getRequirementPrecedentIndex(requirementIndex, i)) >= 0; i++)
			checkCustomerRequirementAndPrecedents(project, solution, customerIndex, source);
	}
	
	private boolean checkCustomerHasRequirement(Solution solution, int customerIndex, int requirementIndex)
	{
		int requirement;
		
		for (int i = 0; (requirement = solution.getCustomerRequirementWithPrecedenceIndex(customerIndex, i)) >= 0; i++)
			if (requirement == requirementIndex)
				return true;

		return false;
	}
}