package sobol.experiments.multiobjective.analysis.test;

import junit.framework.TestCase;
import sobol.experiments.multiobjective.analysis.model.ParetoFront;
import sobol.experiments.multiobjective.analysis.model.ParetoFrontVertex;

public class TestFrontier extends TestCase
{
	private double[][] frontier1 =
	{
		{110,	-129},
		{114,	-127},
		{118,	-125},
		{122,	-123},
		{128,	-120},
		{132,	-118},
		{136,	-116},
		{142,	-113},
		{144,	-112},
		{154,	-107},
		{156,	-106},
		{164,	-102},
		{166,	-101}		
	};

	private double[][] frontier2 =
	{
		{110,	-101},
		{114,	-102},
		{118,	-106},
		{122,	-107},
		{128,	-112},
		{132,	-113},
		{136,	-116},
		{142,	-118},
		{144,	-120},
		{154,	-123},
		{156,	-125},
		{164,	-127},
		{166,	-129}	
	};

	private ParetoFront buildFrontier(double[][] frontier)
	{
		int numObjectives = frontier[0].length;
		ParetoFront f = new ParetoFront(numObjectives, 0);
		
		for (int i = 0; i < frontier.length; i++)
		{
			ParetoFrontVertex v = new ParetoFrontVertex(numObjectives, 0);
			v.setObjectives(frontier[i]);
			f.addVertex(v);
		}
		
		return f;
	}

	public void testFrontier()
	{
		ParetoFront f1 = buildFrontier(frontier1);
		assertEquals(1, f1.getVertexCount());

		ParetoFront f2 = buildFrontier(frontier2);
		assertEquals(13, f2.getVertexCount());
	}
}