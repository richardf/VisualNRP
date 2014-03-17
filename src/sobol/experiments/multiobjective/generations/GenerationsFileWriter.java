package sobol.experiments.multiobjective.generations;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import sobol.experiments.multiobjective.analysis.model.ParetoFront;

public class GenerationsFileWriter
{
	public void execute (String filename, GenerationList generations) throws IOException
	{
		FileWriter outFile = new FileWriter(filename);
		PrintWriter out = new PrintWriter(outFile);
		
		for (int i = 0; i < generations.countGenerations(); i++)
		{
			Generation g = generations.getGenerationIndex(i);
			out.println("Generation #" + g.getNumber() + " (" + g.getEvaluations() + ")");
			printParetoFrontier(out, g.getFront());
		}

		out.close();
	}

	private void printParetoFrontier(PrintWriter out, ParetoFront frontier)
	{
		DecimalFormat dc = new DecimalFormat("0.####");
	
		for (int j = 0; j < frontier.getVertexCount(); j++)
		{
			out.print(dc.format (frontier.getVertex(j, 0)));
			
			for (int k = 1; k < frontier.getObjectiveCount(); k++)
				out.print("; " + dc.format(frontier.getVertex(j, k)));
			
			out.println();
		}

		out.println();
	}
}