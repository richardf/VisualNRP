package sobol.experiments.statistics;

public class ExperimentalData
{
	private String name;
	private String group;
	private double[] data;
	
	public ExperimentalData(String name, String group, double[] data)
	{
		this.name = name;
		this.group = group;
		this.data = data;
	}
	
	public ExperimentalData(String name, String group)
	{
		this(name, group, null);
	}
	
	public String getName()
	{
		return name;
	}

	public String getGroup()
	{
		return group;
	}
	
	public double[] getData()
	{
		return data;
	}
	
	public double getData(int index)
	{
		return data[index];
	}

	public void setData(int index, double value)
	{
		if (data == null)
		{
			data = new double[index+1];
		}
		else if (data.length <= index)
		{
			double[] newData = new double[index+1];
			
			for (int i = 0; i < data.length; i++)
				newData[i] = data[i];
			
			data = newData;
		}
		
		data[index] = value;
	}
}