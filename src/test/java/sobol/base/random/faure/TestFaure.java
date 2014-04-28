package sobol.base.random.faure;

import junit.framework.TestCase;

public class TestFaure extends TestCase
{
	public void testFaureBase2()
	{
		Faure f = new Faure(2, 16);
		
		double[] result = f.next();
		assertEquals(0.03125, result[0], 0.001);
		assertEquals(0.53125, result[1], 0.001);
		
		result = f.next();
		assertEquals(0.53125, result[0], 0.001);
		assertEquals(0.03125, result[1], 0.001);
		
		result = f.next();
		assertEquals(0.28125, result[0], 0.001);
		assertEquals(0.28125, result[1], 0.001);
		
		result = f.next();
		assertEquals(0.78125, result[0], 0.001);
		assertEquals(0.78125, result[1], 0.001);
		
		result = f.next();
		assertEquals(0.15625, result[0], 0.001);
		assertEquals(0.15625, result[1], 0.001);
		
		result = f.next();
		assertEquals(0.65625, result[0], 0.001);
		assertEquals(0.65625, result[1], 0.001);
		
		result = f.next();
		assertEquals(0.40625, result[0], 0.001);
		assertEquals(0.90625, result[1], 0.001);
		
		result = f.next();
		assertEquals(0.90625, result[0], 0.001);
		assertEquals(0.40625, result[1], 0.001);
		
		result = f.next();
		assertEquals(0.09375, result[0], 0.001);
		assertEquals(0.46875, result[1], 0.001);
	}
	
	public void testFaureBase3()
	{
		Faure f = new Faure(3, 80);
		
		double[] result = f.next();
		assertEquals(0.987654, result[0], 0.001);
		assertEquals(0.765432, result[1], 0.001);
		assertEquals(0.209877, result[2], 0.001);
		
		result = f.next();
		assertEquals(0.00411523, result[0], 0.001);
		assertEquals(0.460905, result[1], 0.001);
		assertEquals(0.584362, result[2], 0.001);
		
		result = f.next();
		assertEquals(0.337449, result[0], 0.001);
		assertEquals(0.794239, result[1], 0.001);
		assertEquals(0.917695, result[2], 0.001);
		
		result = f.next();
		assertEquals(0.670782, result[0], 0.001);
		assertEquals(0.127572, result[1], 0.001);
		assertEquals(0.251029, result[2], 0.001);
		
		result = f.next();
		assertEquals(0.115226, result[0], 0.001);
		assertEquals(0.90535, result[1], 0.001);
		assertEquals(0.0288066, result[2], 0.001);
		
		result = f.next();
		assertEquals(0.44856, result[0], 0.001);
		assertEquals(0.238683, result[1], 0.001);
		assertEquals(0.36214, result[2], 0.001);
		
		result = f.next();
		assertEquals(0.781893, result[0], 0.001);
		assertEquals(0.572016, result[1], 0.001);
		assertEquals(0.695473, result[2], 0.001);
		
		result = f.next();
		assertEquals(0.226337, result[0], 0.001);
		assertEquals(0.0164609, result[1], 0.001);
		assertEquals(0.806584, result[2], 0.001);
		
		result = f.next();
		assertEquals(0.559671, result[0], 0.001);
		assertEquals(0.349794, result[1], 0.001);
		assertEquals(0.139918, result[2], 0.001);
	}
	
	public void testFaureBase4()
	{
		Faure f = new Faure(4, 625);
		
		double[] result = f.next();
		assertEquals(0.00032, result[0], 0.00001);
		assertEquals(0.37472, result[1], 0.00001);
		assertEquals(0.31712, result[2], 0.00001);
		assertEquals(0.35552, result[3], 0.00001);
		
		result = f.next();
		assertEquals(0.20032, result[0], 0.00001);
		assertEquals(0.57472, result[1], 0.00001);
		assertEquals(0.51712, result[2], 0.00001);
		assertEquals(0.55552, result[3], 0.00001);
		
		result = f.next();
		assertEquals(0.40032, result[0], 0.00001);
		assertEquals(0.77472, result[1], 0.00001);
		assertEquals(0.71712, result[2], 0.00001);
		assertEquals(0.75552, result[3], 0.00001);
	}
}