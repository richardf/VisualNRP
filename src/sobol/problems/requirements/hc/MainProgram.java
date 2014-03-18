package sobol.problems.requirements.hc;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.faure.FaureRandomGeneratorFactory;
import sobol.base.random.halton.HaltonRandomGeneratorFactory;
import sobol.base.random.pseudo.PseudoRandomGeneratorFactory;
import sobol.base.random.sobol.SobolRandomGeneratorFactory;
import sobol.problems.requirements.model.Project;
import sobol.problems.requirements.reader.RequirementReader;

public class MainProgram
{
	private static int CICLOS = 30;
	
	private static String[] instanceFilenamesClassic =
	{
		"data/requirements/classic/nrp1.txt",
		"data/requirements/classic/nrp2.txt",
		"data/requirements/classic/nrp3.txt",
		"data/requirements/classic/nrp4.txt",
		"data/requirements/classic/nrp5.txt",
		""
	};
	
	private static String[] instanceFilenamesRealistic =
	{
		/*"data\\requirements\\realistic\\nrp-e1.txt",
		"data\\requirements\\realistic\\nrp-e2.txt",
		"data\\requirements\\realistic\\nrp-e3.txt",
		"data\\requirements\\realistic\\nrp-e4.txt",
		"data\\requirements\\realistic\\nrp-g1.txt",
		"data\\requirements\\realistic\\nrp-g2.txt",
		"data\\requirements\\realistic\\nrp-g3.txt",
		"data\\requirements\\realistic\\nrp-g4.txt",
		"data\\requirements\\realistic\\nrp-m1.txt",
		"data\\requirements\\realistic\\nrp-m2.txt",
		"data\\requirements\\realistic\\nrp-m3.txt",
		"data\\requirements\\realistic\\nrp-m4.txt",*/
		""
	};
        
        private static List<MinMaxCust> limits = new LinkedList<MinMaxCust>() {
            {//30 - 5% antes 20% depois
//                add(new MinMaxCust(17, 100)); //17
//                add(new MinMaxCust(53, 500)); //53
//                add(new MinMaxCust(124, 500)); //124
//                add(new MinMaxCust(146, 750)); //146
//                add(new MinMaxCust(232, 1000)); //232
                //50
//                add(new MinMaxCust(34, 100)); //34
//                add(new MinMaxCust(109, 500)); //109
//                add(new MinMaxCust(252, 500)); //252
//                add(new MinMaxCust(308, 750)); //308
//                add(new MinMaxCust(494, 1000)); //494
                //70
                add(new MinMaxCust(58, 98)); //58
                add(new MinMaxCust(209, 409)); //209
                add(new MinMaxCust(439, 500)); //439
                add(new MinMaxCust(608, 750)); //608
                add(new MinMaxCust(944, 1000)); //944
            };
        };
        
        private static List<SolCust> customersInSol = new LinkedList<SolCust>();
        
        
	private List<Project> readInstances(String[] filenames) throws Exception
	{
		List<Project> instances = new ArrayList<Project>();
		
		for (String filename : filenames)
			if (filename.length() > 0)
			{
				System.out.print(filename + " ");
				RequirementReader reader = new RequirementReader(filename);
				Project project = reader.execute();
				System.out.println(project.getRequirementCount() + " reqs " + project.getCustomerCount() + " cust");
				instances.add (project);
			}
		
		return instances;
	}
	
	private void runInstance(PrintWriter out, PrintWriter details, String tipo, Project instance, int cycles, double budgetFactor) throws Exception
	{
                customersInSol.clear();
		for (int i = 0; i < cycles; i++)
		{
			int budget = (int)(budgetFactor * instance.getTotalCost());
			HillClimbing hcr = new HillClimbing(details, instance, budget, 10000000);
			
			long initTime = System.currentTimeMillis();
			details.println(tipo + " " + instance.getName() + " #" + cycles);
			boolean[] solution = hcr.execute();
			details.println();
			long executionTime = (System.currentTimeMillis() - initTime);
			
                        customersInSol.add(new SolCust(countCustomersInSolution(solution), hcr.getFitness()));
//			String s = tipo + "; " + instance.getName() + " #" + i + "; " + executionTime + "; " + hcr.getFitness() + "; " + hcr.getRandomRestarts() + "; " + hcr.getRandomRestartBestFound() + "; " + hcr.printSolution(solution);
//                        System.out.println(countCustomersInSolution(solution));
//                        System.out.println(s);
//			out.println(s);
		}
	}
	
	private void runVisualInstance(PrintWriter out, PrintWriter details, String tipo, Project instance, int cycles, double budgetFactor) throws Exception
	{
                MinMaxCust limit = limits.remove(0);
		for (int i = 0; i < cycles; i++)
		{
			int budget = (int)(budgetFactor * instance.getTotalCost());
			VisHillClimbing hcr = new VisHillClimbing(details, instance, budget, 10000000, limit.min, limit.max);
			
			long initTime = System.currentTimeMillis();
			details.println(tipo + " " + instance.getName() + " #" + cycles);
			boolean[] solution = hcr.execute();
			details.println();
			long executionTime = (System.currentTimeMillis() - initTime);
			
			String s = tipo + "; " + instance.getName() + " #" + i + "; " + executionTime + "; " + hcr.getFitness() + "; " + hcr.getRandomRestarts() + "; " + hcr.getRandomRestartBestFound() + "; " + hcr.printSolution(solution);
                        System.out.println(s);
                        System.out.println(countCustomersInSolution(solution));

			out.println(s);
		}
	}        
        
	public static final void main(String[] args) throws Exception
	{
		MainProgram mp = new MainProgram();

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
			mp.runVisualInstance(out, details, "VISUAL", instance, CICLOS, 0.3);
//			mp.runVisualInstance(out, details, "VISUAL", instance, CICLOS, 0.5);
//			mp.runVisualInstance(out, details, "VISUAL", instance, CICLOS, 0.7);
                        
//			mp.runInstance(out, details, "PSEUDO", instance, CICLOS, 0.3);
//			mp.runInstance(out, details, "PSEUDO", instance, CICLOS, 0.5);
//			mp.runInstance(out, details, "PSEUDO", instance, CICLOS, 0.7);

//                        Collections.sort(customersInSol);
//                        System.out.println(instance.getName() + ";" + customersInSol.get(customersInSol.size()-1).nOfCustomers + ";" + customersInSol.toString());
                        
//			RandomGeneratorFactory.setRandomFactoryForPopulation(new SobolRandomGeneratorFactory());
//			mp.runInstance(out, details, "SOBOL", instance, CICLOS, 0.3);
//			mp.runInstance(out, details, "SOBOL", instance, CICLOS, 0.5);
//			mp.runInstance(out, details, "SOBOL", instance, CICLOS, 0.7);
			
//			RandomGeneratorFactory.setRandomFactoryForPopulation(new HaltonRandomGeneratorFactory());
//			mp.runInstance(out, details, "HALTON", instance, CICLOS, 0.3);
//			mp.runInstance(out, details, "HALTON", instance, CICLOS, 0.5);
//			mp.runInstance(out, details, "HALTON", instance, CICLOS, 0.7);

//			RandomGeneratorFactory.setRandomFactoryForPopulation(new FaureRandomGeneratorFactory());
//			mp.runInstance(out, details, "FAURE", instance, CICLOS, 0.3);
//			mp.runInstance(out, details, "FAURE", instance, CICLOS, 0.5);
//			mp.runInstance(out, details, "FAURE", instance, CICLOS, 0.7);
		}
		
		out.close();
		details.close();
	}
        
        public static int countCustomersInSolution(boolean[] solution) {
            int count = 0;
            for(int i=0; i < solution.length; i++) {
                if(solution[i] == true) {
                    count++;
                }
            }
            return count;
        }
}

class MinMaxCust {
    public int min;
    public int max;
    
    MinMaxCust(int min, int max) {
        this.min = min;
        this.max = max;
    }
}

class SolCust implements Comparable<SolCust> {
    public Integer nOfCustomers;
    public Double fitness;

    public SolCust(Integer nOfCustomers, Double fitness) {
        this.nOfCustomers = nOfCustomers;
        this.fitness = fitness;
    }

    @Override
    public int compareTo(SolCust o) {
        return this.fitness.compareTo(o.fitness);
    }

    @Override
    public String toString() {
        return nOfCustomers + "";
    }
}