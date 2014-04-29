package sobol.base.random.faure;

import sobol.base.random.halton.Primes;

public class Faure
{
	private int dimensions;
	private int dimensionPrime;
	private int[] coef = null;
	private int[] ytemp = null;
	private int hisum_save;
	private int seed;
	
	public Faure(int dimensions)
	{
		this.dimensions = dimensions;
		this.dimensionPrime = primeGreaterEqual(dimensions);
		this.hisum_save = -1;
		this.seed = 0;
	}

	public Faure(int dimensions, int seed)
	{
		this(dimensions);
		this.seed = seed;
	}

	public double[] next()
	{
		  /*for ( k = 1; k < dim_num; k++ )
		  {
		    quasi[k] = 0.0;
		    r = 1.0 / ( ( double ) qs );

		    for ( j = 0; j <= hisum; j++ )
		    {
		      ztemp = 0;
		      for ( i = j; i <= hisum; i++ )
		      {
		        ztemp = ztemp + ytemp[i] * coef[i+j*(hisum+1)];
		      }

		      ytemp[j] = ztemp % qs;
		      quasi[k] = quasi[k] + ( ( double ) ytemp[j] ) * r;
		      r = r / ( ( double ) qs );
		    }
		  }*/

		  
		  
		double[] quasi = new double[dimensions];
		int hisum = (seed == 0) ? 0 : i4_log_i4(seed, dimensionPrime);

		if (hisum_save != hisum)
		{
			hisum_save = hisum;
			coef = binomialTable(dimensionPrime, hisum, hisum);
			ytemp = new int[hisum + 1];
		}

		double p = Math.pow(dimensionPrime, hisum);
		int ltemp = seed;

		for (int i = hisum; i >= 0; i--)
		{
			int fator = ltemp;
			
			for (int k = 0; k < i; k++)
				fator /= dimensionPrime;
			
			ytemp[i] = fator;
			
			double d = ltemp - fator * p;
			p /= dimensionPrime;
			ltemp = (int)d;
		}

		double r = ytemp[hisum];
		
		for (int i = hisum - 1; 0 <= i; i--)
			r = ytemp[i] + r / dimensionPrime;

		quasi[0] = r / dimensionPrime;

		for (int k = 1; k < dimensions; k++)
		{
			quasi[k] = 0.0;
			r = 1.0 / dimensionPrime;

			for (int j = 0; j <= hisum; j++)
			{
				int z = 0;
				
				for (int i = j; i <= hisum; i++)
					z += ytemp[i] * coef[i + j * (hisum + 1)];

				ytemp[j] = z % dimensionPrime;
				quasi[k] += ytemp[j] * r;
				r /= dimensionPrime;
			}
		}

		seed++;
		return quasi;
	}

	private int[] binomialTable(int qs, int m, int n)
	{
		int[] coef = new int[(m + 1) * (n + 1)];

		for (int j = 0; j <= n; j++)
			for (int i = 0; i <= m; i++)
				coef[i + j * (m + 1)] = 0;

		coef[0] = 1;

		for (int i = 1; i <= m; i++)
			coef[i] = 1;

		for (int i = 1; i <= i4_min(m, n); i++)
			coef[i + i * (m + 1)] = 1;

		for (int j = 1; j <= n; j++)
			for (int i = j + 1; i <= m; i++)
				coef[i + j * (m + 1)] = (coef[i - 1 + j * (m + 1)] + coef[i - 1 + (j - 1) * (m + 1)]) % qs;

		return coef;
	}

	private int i4_log_i4(int i4, int j4)
	{
		int value = 0;
		int i4_abs = Math.abs(i4);

		if (i4_abs >= 2)
		{
			int j4_abs = Math.abs(j4);

			if (j4_abs >= 2)
			{
				while (j4_abs <= i4_abs)
				{
					i4_abs /= j4_abs;
					value++;
				}
			}
		}
		
		return value;
	}

	private int i4_min(int i1, int i2)
	{
		return (i1 < i2) ? i1 : i2;
	}

	private int primeGreaterEqual(int n)
	{
		if (n <= 2)
			return 2;

		int i_lo = 1;
		int i_hi = Primes.PRIME.length;
		int p_hi = Primes.PRIME[i_hi - 1];

		if (p_hi < n)
			return -p_hi;

		while (i_lo + 1 != i_hi)
		{
			int i_mid = (i_lo + i_hi) / 2;
			int p_mid = Primes.PRIME[i_mid - 1];

			if (p_mid < n)
			{
				i_lo = i_mid;
			}
			else if (n <= p_mid)
			{
				i_hi = i_mid;
				p_hi = p_mid;
			}
		}

		return p_hi;
	}
}