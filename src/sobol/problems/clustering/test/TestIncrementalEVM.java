package sobol.problems.clustering.test;

import javax.management.modelmbean.XMLParseException;
import junit.framework.TestCase;
import sobol.problems.clustering.generic.calculator.CalculadorIncrementalEVM;
import sobol.problems.clustering.generic.model.Project;
import sobol.problems.clustering.generic.model.ProjectPackage;
import sobol.problems.clustering.generic.reader.CDAReader;

public class TestIncrementalEVM extends TestCase
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
		
		CalculadorIncrementalEVM cc = new CalculadorIncrementalEVM(project, 3);
		cc.evaluate();
		cc.removeClassInfluence(5);
		cc.evaluate();
	}
	
	public void testJodamoney() throws XMLParseException
	{
		CDAReader reader = new CDAReader();
		Project project = reader.execute("data\\cluster\\jodamoney 26C.odem");
		
		CalculadorIncrementalEVM cc = new CalculadorIncrementalEVM(project, project.getClassCount());
		cc.moveAll(new int[] {2, 2, 2, 2, 1, 1, 7, 2, 2, 2, 3, 5, 3, 4, 4, 0, 5, 7, 0, 0, 0, 0, 4, 4, 5, 5});
		
		assertEquals(28.0, cc.evaluate(), 0.001);
	}
	
	public void testTinytim() throws XMLParseException
	{
		CDAReader reader = new CDAReader();
		Project project = reader.execute("data\\cluster\\tinytim 134C.odem");
		
		CalculadorIncrementalEVM cc = new CalculadorIncrementalEVM(project, project.getClassCount());
		cc.moveAll(new int[] {52, 6, 4, 6, 6, 16, 38, 1, 47, 36, 26, 27, 1, 1, 1, 1, 1, 1, 19, 19, 6, 4, 3, 5, 6, 13, 16, 6, 44, 45, 4, 6, 3, 42, 7, 7, 7, 7, 23, 11, 50, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 10, 28, 10, 30, 10, 32, 10, 10, 10, 1, 29, 40, 1, 1, 11, 11, 22, 22, 8, 8, 23, 12, 3, 5, 12, 16, 29, 16, 6, 3, 6, 2, 34, 31, 31, 2, 33, 35, 2, 2, 31, 2, 13, 14, 41, 14, 15, 37, 39, 15, 41, 14, 17, 17, 37, 15, 43, 18, 18, 20, 20, 46, 14, 14, 21, 21, 48, 2, 49, 51, 22, 22, 24, 24, 53, 54, 25, 53, 0, 7, 0});
		
		assertEquals(133.0, cc.evaluate(), 0.001);
	}
}