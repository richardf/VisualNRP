package sobol.problems.requirements.hc;

import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.faure.FaureRandomGeneratorFactory;
import sobol.base.random.halton.HaltonRandomGeneratorFactory;
import sobol.base.random.pseudo.PseudoRandomGeneratorFactory;
import sobol.base.random.sobol.SobolRandomGeneratorFactory;
import sobol.problems.requirements.model.Project;
import sobol.problems.requirements.reader.RequirementReader;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class VisualizationMainProgram
{
	private static int CICLOS = 1;
	
	private static String[] instanceFilenamesClassic =
	{
                "data/requirements/padilha_1req_30cust.txt",
                "data/requirements/padilha_3req_30cust.txt",
                "data/requirements/padilha_5req_30cust.txt",
                "data/requirements/padilha_1req_50cust.txt",
                "data/requirements/padilha_3req_50cust.txt",
                "data/requirements/padilha_5req_50cust.txt",
                "data/requirements/padilha_1req_100cust.txt",
                "data/requirements/padilha_3req_100cust.txt",
                "data/requirements/padilha_5req_100cust.txt",
//		"data/requirements/classic/nrp1.txt",
//		"data/requirements/classic/nrp2.txt",
//		"data/requirements/classic/nrp3.txt",
//		"data/requirements/classic/nrp4.txt",
//		"data/requirements/classic/nrp5.txt",
		""
	};
	
	private static String[] instanceFilenamesRealistic =
	{
		/*"data/requirements/realistic/nrp-e1.txt",
		"data/requirements/realistic/nrp-e2.txt",
		"data/requirements/realistic/nrp-e3.txt",
		"data/requirements/realistic/nrp-e4.txt",
		"data/requirements/realistic/nrp-g1.txt",
		"data/requirements/realistic/nrp-g2.txt",
		"data/requirements/realistic/nrp-g3.txt",
		"data/requirements/realistic/nrp-g4.txt",
		"data/requirements/realistic/nrp-m1.txt",
		"data/requirements/realistic/nrp-m2.txt",
		"data/requirements/realistic/nrp-m3.txt",
		"data/requirements/realistic/nrp-m4.txt",*/
		""
	};
	
	private List<Project> readInstances(String[] filenames) throws Exception
	{
		List<Project> instances = new ArrayList<Project>();
		
		for (String filename : filenames)
			if (filename.length() > 0)
			{
				RequirementReader reader = new RequirementReader(filename);
				Project project = reader.execute();
				instances.add (project);
			}
		
		return instances;
	}
	
	private void runInstance(PrintWriter out, PrintWriter details, String tipo, Project instance, int cycles, double budgetFactor) throws Exception
	{
		for (int i = 0; i < cycles; i++)
		{
			int budget = (int)(budgetFactor * instance.getTotalCost());
			Visualization hcr = new Visualization(details, instance, budget, 100);
			
			details.println(tipo + " " + instance.getName() + " #" + cycles);
			boolean[] solution = hcr.execute();
			details.println();
		}
	}
	
	public static final void main(String[] args) throws Exception
	{
		VisualizationMainProgram mp = new VisualizationMainProgram();

		Vector<Project> instances = new Vector<Project>();
		instances.addAll(mp.readInstances(instanceFilenamesClassic));
		instances.addAll(mp.readInstances(instanceFilenamesRealistic));
		
		FileWriter outFile = new FileWriter("saida.txt");
		PrintWriter out = new PrintWriter(outFile);
		
		FileWriter detailsFile = new FileWriter("saida details.txt");
		PrintWriter details = new PrintWriter(detailsFile);
		
		for (Project instance : instances)
		{
			RandomGeneratorFactory.setRandomFactoryForPopulation(new PseudoRandomGeneratorFactory());
			mp.runInstance(out, details, "PSEUDO", instance, CICLOS, 0.7);
		}
		
		out.close();
		details.close();
	}
}