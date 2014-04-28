package sobol.problems.clustering.test;

import junit.framework.TestCase;
import sobol.problems.clustering.generic.calculator.CalculadorIncrementalMQ;
import sobol.problems.clustering.generic.model.Project;
import sobol.problems.clustering.generic.model.ProjectPackage;

public class TestIncrementalMQ extends TestCase
{
	public void testBasico()
	{
		Project project = new Project("Teste");
		ProjectPackage p1 = project.addPackage("p1");
		project.addClass("c1.1", p1).addDependency("c2.2");
		project.addClass("c1.2", p1).addDependency("c3.2");
		
		ProjectPackage p2 = project.addPackage("p2");
		project.addClass("c2.1", p2).addDependency("c1.1").addDependency("c2.3");
		project.addClass("c2.2", p2);
		project.addClass("c2.3", p2).addDependency("c3.1");
		
		ProjectPackage p3 = project.addPackage("p3");
		project.addClass("c3.1", p3).addDependency("c1.2").addDependency("c3.2");
		project.addClass("c3.2", p3).addDependency("c3.1");
		
		CalculadorIncrementalMQ cc = new CalculadorIncrementalMQ(project, 3);
		cc.evaluate();
		cc.removeClassInfluence(5);
		cc.evaluate();
	}
}