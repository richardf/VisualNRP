package sobol.base.random.halton;

class VDCorputSequence
{
	private int base;
	private long counter; 
	
	public VDCorputSequence(int base)
	{
		this.base = base;
		this.counter = 0;
	}
	
	public double next()
	{
	    long n0 = counter;
	    counter++;
	    
	    double c = 0;
	    double ib = 1.0 / base;
	    
	    while (n0 > 0)
	    {
	        long n1 = (long) Math.floor(((double)n0) / base);
	        long i = n0 - n1 * base;
	        c += ib * i;
	        ib /= base;
	        n0 = n1;
	    }
	    
	    return c;
	}
}