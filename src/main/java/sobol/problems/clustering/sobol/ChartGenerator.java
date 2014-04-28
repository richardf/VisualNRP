package sobol.problems.clustering.sobol;

import sobol.base.random.faure.FaureRandomGeneratorFactory;
import sobol.base.random.generic.AbstractRandomGenerator;
import sobol.base.random.halton.HaltonRandomGeneratorFactory;
import sobol.base.random.sobol.SobolRandomGeneratorFactory;

public class ChartGenerator
{
	public static final void main(String[] args)
	{
		AbstractRandomGenerator geradorSobol = new SobolRandomGeneratorFactory().create(2);
		AbstractRandomGenerator geradorFaure = new FaureRandomGeneratorFactory().create(2);
		AbstractRandomGenerator geradorHalton = new HaltonRandomGeneratorFactory().create(2);
		
		System.out.println("SOBOL X\tSOBOL Y\tFAURE X\tFAURE Y\tHALTON X\tHALTON Y");

		for (int i = 0; i < 2000; i++)
		{
			int[] resultSobol = geradorSobol.randInt(0, 99);
			int[] resultFaure = geradorFaure.randInt(0, 99);
			int[] resultHalton = geradorHalton.randInt(0, 99);			
			System.out.println(resultSobol[0] + "\t" + resultSobol[1] + "\t" + resultFaure[0] + "\t" + resultFaure[1] + "\t" + resultHalton[0] + "\t" + resultHalton[1]);
		}			
	}
}