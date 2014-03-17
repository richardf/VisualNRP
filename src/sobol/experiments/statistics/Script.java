package sobol.experiments.statistics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.List;

public class Script
{
	private PrintWriter out;
	
	public Script()
	{
	}
	
	public Script(String filename) throws IOException
	{
		out = new PrintWriter(new FileWriter(filename));
		out.println("graphics.off();");
	}
	
	public void writeDataFile(String filename, List<ExperimentalData> columns, int dataSize) throws IOException
	{
		DecimalFormat df4 = new DecimalFormat("0.####");
		FileWriter file = new FileWriter(filename);
		PrintWriter out = new PrintWriter(file);

		ExperimentalData rc = columns.get(0);
		out.print(rc.getName() + "_" + rc.getGroup());
		
		for (int j = 1; j < columns.size(); j++)
		{
			rc = columns.get(j);
			out.print("\t" + rc.getName() + "_" + rc.getGroup());
		}
		
		for (int i = 0; i < dataSize; i++)
		{
			rc = columns.get(0);
			out.println();
			out.print(df4.format(rc.getData()[i]).replace(",", "."));

			for (int j = 1; j < columns.size(); j++)
			{
				rc = columns.get(j);
				out.print("\t" + df4.format(rc.getData()[i]).replace(",", "."));
			}
		}
		
		out.close();
	}

	private int countExperimentalData(List<ExperimentalData> columns, String type)
	{
		int count = 0;
		
		for (ExperimentalData data : columns)
			if (data.getGroup().equals(type))
				count++;

		return count;
	}

	private String getExperimentalDataNames(List<ExperimentalData> columns, String type)
	{
		String names = "";
		
		for (ExperimentalData data : columns)
		{
			if (data.getGroup().equals(type))
			{
				if (names.length() > 0)
					names += ", ";
				
				names += "\"" + data.getName() + "\""; 
			}
		}

		return 	names;
	}

	private String getExperimentalDataVariables(List<ExperimentalData> columns, String type, String[] prefix)
	{
		String variables = "";
		
		for (ExperimentalData data : columns)
		{
			if (data.getGroup().equals(type))
			{
				for (String p : prefix)
				{
					if (variables.length() > 0)
						variables += ", ";
					
					variables += p + "$" + data.getName() + "_" + type;
				}
			}
		}

		return variables;
	}

	public void loadFile(String prefix, String filename)
	{
		out.println(prefix + " <- read.table(\"" + filename + "\", header=TRUE);");
	}

	public void drawBoxPlot(List<ExperimentalData> columns, String type, String[] prefix, String title)
	{
		String names = getExperimentalDataNames(columns, type);
		String variables = getExperimentalDataVariables(columns, type, prefix);
		int count = countExperimentalData(columns, type);

		out.println(); 
		out.println("windows()"); 
		out.println("boxplot(" + variables + ", xaxt='n')");
		out.println("text(0:" + (count-1) + "*2+1, par(\"usr\")[3] - (par(\"usr\")[4] - par(\"usr\")[3]) / 100, srt=45, adj=1, labels=c(" + names + "), xpd=T, cex=0.7)"); 
		out.println("title(main=\"" + title + "\", col.main=\"red\", font.main=4)");
	}

	public void drawBoxPlot(List<ExperimentalData> columns, String type, String prefix, String title)
	{
		String[] prefixes = { prefix };
		drawBoxPlot(columns, type, prefixes, title);
	}

	public void tableMannWhitney(List<ExperimentalData> columns, String type, String prefix1, String prefix2)
	{
		String names = getExperimentalDataNames(columns, type);
		int count = 0;
		String result = "";
		
		for (ExperimentalData rc : columns)
			if (rc.getGroup().equals(type))
				result += "p_" + type + count++ + " <- wilcox.test(" + prefix1 + "$" + rc.getName() + "_" + type + ", " + prefix2 + "$" + rc.getName() + "_" + type + ")\n";
				
		
		result += "names <- c(" + names + ")\n";
		result += "pvalue_" + prefix1 + "_" + prefix2 + "_" + type + " <- c(" + "p_" + type + "0$p.value";
		
		for (int i = 1; i < count; i++)
			result += ", p_" + type + i + "$p.value";
		
		out.println(); 
		out.println(result + ")");
	}

	public void tableEffectSize(List<ExperimentalData> columns, String type, String prefix1, String prefix2)
	{
		String names = getExperimentalDataNames(columns, type);
		String result = "";
		int count = 0;
		
		for (ExperimentalData rc : columns)
		{
			if (rc.getGroup().equals(type))
			{
				String vetor1 = prefix1 + "$" + rc.getName() + "_" + type;
				String vetor2 = prefix2 + "$" + rc.getName() + "_" + type;
				
				result += "rx = sum(rank(c(" + vetor1 + ", " + vetor2 + "))[seq_along(" + vetor1 + ")])\n";
				result += "ry = sum(rank(c(" + vetor2 + ", " + vetor1 + "))[seq_along(" + vetor2 + ")])\n";
				result += "eff1_" + type + count + " = (rx / 30 - (30 + 1) / 2) / 30\n";
				result += "eff2_" + type + count + " = (ry / 30 - (30 + 1) / 2) / 30\n";
				
				count++;
			}
		}
		
		result += "names <- c(" + names + ")\n";
		result += "effectsize1_" + prefix1 + "_" + prefix2 + "_" + type + " <- c(" + "eff1_" + type + "0";
		
		for (int i = 1; i < count; i++)
			result += ", eff1_" + type + i;
		
		result += ")\n";

		result += "effectsize2_" + prefix1 + "_" + prefix2 + "_" + type + " <- c(" + "eff2_" + type + "0";
		
		for (int i = 1; i < count; i++)
			result += ", eff2_" + type + i;
		
		out.println(); 
		out.println(result + ")");
	}

	public void tableMean(List<ExperimentalData> columns, String type, String prefix)
	{
		String names = getExperimentalDataNames(columns, type);
		String result = "";
		int count = 0;
		
		for (ExperimentalData rc : columns)
		{
			if (rc.getGroup().equals(type))
			{
				String vetor1 = prefix + "$" + rc.getName() + "_" + type;
				result += "mean_" + prefix + "_" + type + count + " = mean(" + vetor1 + ")\n";
				count++;
			}
		}
		
		result += "names <- c(" + names + ")\n";
		result += "mean_" + prefix + "_" + type + " <- c(mean_" + prefix + "_" + type + "0";
		
		for (int i = 1; i < count; i++) 
			result += ", mean_" + prefix + "_" + type + i;
		
		result += ")\n";

		out.println(); 
		out.println(result);
	}

	public void tableStandardDeviation(List<ExperimentalData> columns, String type, String prefix)
	{
		String names = getExperimentalDataNames(columns, type);
		String result = "";
		int count = 0;
		
		for (ExperimentalData rc : columns)
		{
			if (rc.getGroup().equals(type))
			{
				String vetor1 = prefix + "$" + rc.getName() + "_" + type;
				result += "sd_" + prefix + "_" + type + count + " = sd(" + vetor1 + ")\n";
				count++;
			}
		}
		
		result += "names <- c(" + names + ")\n";
		result += "sd_" + prefix + "_" + type + " <- c(sd_" + prefix + "_" + type + "0";
		
		for (int i = 1; i < count; i++) 
			result += ", sd_" + prefix + "_" + type + i;
		
		result += ")\n";

		out.println(); 
		out.println(result); 
	}
	
	public void tableSummary(List<ExperimentalData> columns, String type, String prefix)
	{
		tableMean(columns, type, prefix);
		tableStandardDeviation(columns, type, prefix);
	}

	public void meanSummary(List<ExperimentalData> columns, String type, String prefix)
	{
		tableMean(columns, type, prefix);
	}

	public void sendCommand(String s)
	{
		out.println(s);
	}
	
	public void close()
	{
		out.close();
	}
}