package sobol.problems.requirements.test;

import junit.framework.TestCase;
import sobol.problems.requirements.model.Project;
import sobol.problems.requirements.reader.RequirementReader;

public class TestRequirementReader extends TestCase
{
	public void testProblem1() throws Exception
	{
		Project project = new RequirementReader("data\\requirements\\classic\\nrp1.txt").execute();
		
		assertEquals(140, project.getRequirementCount());
		assertEquals(4, project.getRequirementCost(0));
		assertEquals(2, project.getRequirementCost(18));
		assertEquals(3, project.getRequirementCost(19));
		assertEquals(8, project.getRequirementCost(20));
		assertEquals(7, project.getRequirementCost(59));
		assertEquals(9, project.getRequirementCost(60));
		assertEquals(7, project.getRequirementCost(139));
		
		assertEquals(2, project.getRequirementDependencySourcesCount(84));
		assertEquals(0, project.getRequirementDependencySources(84)[0]);
		assertEquals(59, project.getRequirementDependencySources(84)[1]);
		
		assertEquals(1, project.getRequirementDependencySourcesCount(129));
		assertEquals(5, project.getRequirementDependencySources(129)[0]);
		
		assertEquals(100, project.getCustomerCount());
		assertEquals(36, project.getCustomerProfit(0));
		assertEquals(1, project.getCustomerRequirements(0).length);
		assertEquals(65, project.getCustomerRequirements(0)[0]);
		
		assertEquals(27, project.getCustomerProfit(96));
		assertEquals(5, project.getCustomerRequirements(96).length);
		assertEquals(78, project.getCustomerRequirements(96)[0]);
		assertEquals(86, project.getCustomerRequirements(96)[4]);
		
		assertEquals(22, project.getCustomerProfit(99));
		assertEquals(1, project.getCustomerRequirements(99).length);
		assertEquals(84, project.getCustomerRequirements(99)[0]);
	}

	public void testProblem2() throws Exception
	{
		Project project = new RequirementReader("data\\requirements\\realistic\\nrp-e1.txt").execute();
		
		assertEquals(3502, project.getRequirementCount());
		assertEquals(4, project.getRequirementCost(0));
		assertEquals(4, project.getRequirementCost(1));
		assertEquals(4, project.getRequirementCost(2));
		assertEquals(6, project.getRequirementCost(3));
		assertEquals(4, project.getRequirementCost(4));
		assertEquals(5, project.getRequirementCost(3495));
		assertEquals(4, project.getRequirementCost(3501));
		
		for (int i = 0; i < 3502; i++)
			assertEquals(0, project.getRequirementDependencySourcesCount(i));
		
		assertEquals(536, project.getCustomerCount());
		assertEquals(38, project.getCustomerProfit(0));
		assertEquals(16, project.getCustomerRequirements(0).length);
		assertEquals(0, project.getCustomerRequirements(0)[0]);
		assertEquals(3260, project.getCustomerRequirements(0)[15]);
		
		assertEquals(36, project.getCustomerProfit(535));
		assertEquals(4, project.getCustomerRequirements(535).length);
		assertEquals(3448, project.getCustomerRequirements(535)[0]);
		assertEquals(3451, project.getCustomerRequirements(535)[3]);
	}
}