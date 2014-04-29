package sobol.problems.clustering.generic.calculator;

/**
 * Interface abstrata para cálculos incrementais
 * 
 * @author marcio.barros
 */
public interface ICalculadorIncremental
{
	/**
	 * Move uma classe para outro pacote
	 */
	public abstract void moveClass(int classIndex, int packageIndex);
	
	/**
	 * Move todas as classes para um novo conjunto de pacotes
	 */
	public abstract void moveAll(int[] packageIndexes);
	
	/**
	 * Calcula o valor da função objetivo
	 */
	public abstract double evaluate();
}