package sobol.problems.portfolio.mono;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import sobol.base.random.RandomGeneratorFactory;
import sobol.base.random.faure.FaureRandomGeneratorFactory;
import sobol.base.random.halton.HaltonRandomGeneratorFactory;
import sobol.base.random.pseudo.PseudoRandomGeneratorFactory;
import sobol.base.random.sobol.SobolRandomGeneratorFactory;
import sobol.problems.portfolio.general.model.ProjetosCandidatos;
import sobol.problems.portfolio.general.reader.PortfolioReader;

public class MainProgram
{
	private static String GERADOR = "pseudo";
	private static int CICLOS = 10;

	private static void run(PrintWriter out, PrintWriter details, String tipo, String instancia, double orcamento, int ciclos, int tamanho) throws Exception
	{
		PortfolioReader reader = new PortfolioReader();
		reader.execute(instancia);
		
		ProjetosCandidatos candidatos = reader.getCandidatos();
		candidatos.setLimiteOrcamento(orcamento);
		String nome = "Instancia " + tamanho;

		for (int i = 0; i < ciclos; i++)
		{
			int maxEvaluations = 20 * candidatos.pegaNumeroProjetos();
			HillClimbingPortfolio hcp = new HillClimbingPortfolio(details, candidatos, maxEvaluations);
			
			long initTime = System.currentTimeMillis();
			details.println(tipo + " " + nome + " #" + i);
			boolean[] solution = hcp.execute();
			details.println();
			long executionTime = (System.currentTimeMillis() - initTime);
			
			NumberFormat nf = new DecimalFormat("0.00");
			String s = tipo + "; " + nome + " #" + i + "; " + executionTime + "; " + nf.format(hcp.getFitness()) + "; " + hcp.getRandomRestarts() + "; " + hcp.getRandomRestartBestFound() + "; " + hcp.printSolution(solution);
			System.out.println(s);
			out.println(s);
		}
	}
	
	private static void prepareRandomizer()
	{
		if (GERADOR.compareTo("pseudo") == 0)
			RandomGeneratorFactory.setRandomFactoryForPopulation(new PseudoRandomGeneratorFactory());

		if (GERADOR.compareTo("faure") == 0)
			RandomGeneratorFactory.setRandomFactoryForPopulation(new FaureRandomGeneratorFactory());

		if (GERADOR.compareTo("halton") == 0)
			RandomGeneratorFactory.setRandomFactoryForPopulation(new HaltonRandomGeneratorFactory());

		if (GERADOR.compareTo("sobol") == 0)
			RandomGeneratorFactory.setRandomFactoryForPopulation(new SobolRandomGeneratorFactory());
	}
	
	public static final void main(String[] args) throws Exception
	{
		prepareRandomizer();

		FileWriter outFile = new FileWriter("saida.txt");
		PrintWriter out = new PrintWriter(outFile);
		
		FileWriter detailsFile = new FileWriter("saida details.txt");
		PrintWriter details = new PrintWriter(detailsFile);
		
		run(out, details, GERADOR, "data\\portfolio\\InstanciaReal_25P_10R.txt", 64980000.0, CICLOS, 25);
		run(out, details, GERADOR, "data\\portfolio\\InstanciaReal_50P_10R.txt", 7680000.0, CICLOS, 50);
		run(out, details, GERADOR, "data\\portfolio\\InstanciaReal_75P_10R.txt", 50000000.0, CICLOS, 75);
		run(out, details, GERADOR, "data\\portfolio\\InstanciaReal_100P_10R.txt", 1200000000.0, CICLOS, 100);
		
		out.close();
		details.close();
	}
}