package sobol.problems.requirements.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sobol.problems.requirements.model.Project;

/**
 * Class that reads data from a requirements file
 * 
 * @author marcio.barros
 */
public class RequirementReader
{
	private String filename;
	private Scanner scanner;
	private int lineCounter;
	private List<String> errors;
	
	/**
	 * Initializes the requirement file reader
	 */
	public RequirementReader(String filename)
	{
		this.filename = filename;
		this.lineCounter = 0;
		this.scanner = null;
		this.errors = new ArrayList<String>();
	}
	
	/**
	 * Loads a file containing requirements
	 */
	public Project execute()
	{
		try
		{
			this.scanner = new Scanner(new FileInputStream(filename));
			Project project = new Project(filename);		
			loadRequirements(project);		
			loadRequirementDependencies(project);		
			loadCustomers(project);
			scanner.close();
			return (errors.size() == 0) ? project : null;
		}
		catch(IOException e)
		{
			addError(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Gets the next line from the file
	 */
	private String nextLine()
	{
		lineCounter++;
		return scanner.nextLine();
	}

	/**
	 * Registers an error during file processing
	 */
	private void addError(String message)
	{
		this.errors.add(filename + " (" + lineCounter + "): " + message);
	}

	/**
	 * Load data related to requirements
	 */
	private void loadRequirements(Project project) 
	{
		String line = nextLine();
		int numberOfLevels = Integer.parseInt(line);

		for (int i = 0; i < numberOfLevels; i++)
		{
			line = nextLine();
			int requirementsOnLevel = Integer.parseInt(line);

			line = nextLine();
			String[] tokens = line.split(" ");
			
			if (requirementsOnLevel == tokens.length)
			{			
				int count = project.getRequirementCount();
				project.addRequirements(requirementsOnLevel);
				
				for (int j = 0; j < requirementsOnLevel; j++)
					project.setRequirementCost(count + j, Integer.parseInt(tokens[j]));
			}
			else
				addError("Invalid number of cost items for level " + (i+1));
		}
	}

	/**
	 * Loads data related to requirement dependencies
	 */
	private void loadRequirementDependencies(Project project) 
	{
		String line = nextLine();
		int numberOfDependencies = Integer.parseInt(line);

		for (int i = 0; i < numberOfDependencies; i++)
		{
			line = nextLine();
			String[] tokens = line.split(" ");
			
			if (tokens.length == 2)
			{
				int source = Integer.parseInt(tokens[0]);
				int target = Integer.parseInt(tokens[1]);
				project.addRequirementDependency(source-1, target-1);
			}
			else
				addError("Invalid format in dependency");
		}
	}

	/**
	 * Loads data related to customers
	 */
	private void loadCustomers(Project project)
	{
		String line = nextLine();
		int numberOfCustomers = Integer.parseInt(line);
		project.setCustomerCount(numberOfCustomers);

		for (int i = 0; i < numberOfCustomers; i++)
		{
			line = nextLine();
			String[] tokens = line.split(" ");
			
			if (tokens.length > 2)
			{
				int profit = Integer.parseInt(tokens[0]);
				project.setCustomerProfit(i, profit);
				
				int requirementCount = Integer.parseInt(tokens[1]);
				
				if (tokens.length == 2 + requirementCount)
				{
					int[] requirements = new int[requirementCount];
					
					for (int j = 0; j < requirementCount; j++)
						requirements[j] = Integer.parseInt(tokens[j+2])-1;
					
					project.setCustomerRequirements(i, requirements);
				}
				else
					addError("Invalid format in customer - not enough requirement references");
			}
			else
				addError("Invalid format in customer - less than 3 fields");
		}
	}
}