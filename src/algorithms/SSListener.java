package algorithms;
import java.math.BigInteger;

/**
 * Interfejs listenera obslugujacego algorytm Solovay'a Strassen'a
 *
 */
public interface SSListener 
{
	/**
	 * Funkcja do ustawiania badanej liczby w Listenerze algorytmu Solovay_Strassen
	 * @param n - badana liczba
	 */
	public void setNumber(BigInteger n);
	
	/**
	 * Funkcja pozwalajaca przekazac listenerowi czy badana liczba zostala oznaczona jako pierwsz
	 * @param isPrime -zmienna okreslajaca czy badana liczba jest pierwsza
	 */
	public void isPrime(boolean isPrime);
	
	/**
	 * Funkcja pozwalajaca poinformowac listener, ze badana liczba jest parzysta
	 */
	public void isEvenNumber();
	
	/**
	 * Funkcja pozwalajaca przekazac wartosci charakterystyczne dla pojedynczej iteracji algorytmu Solvay_Strassen
	 * @param k - numer iteracji
	 * @param a - wylosowa liczba
	 * @param r - parametr r algorytmu SS. r = a.modPow(temp,prime), gdzie temp = (prime-1)/2
	 */
	public void SSIteration(int k,BigInteger a,BigInteger r);
	
	/**
	 * Funkcja pozwalajaca przekazac wartosci charakterystyczne dla pojedynczej iteracji algorytmu Solvay_Strassen
	 * @param k - numer iteracji
	 * @param a - wylosowa liczba
	 * @param r - parametr r algorytmu SS. r = a.modPow(temp,n), gdzie temp = (prime-1)/2 
	 * @param jacobian - symbol Jakobiego dla liczb a i n, gdzie n jest badana liczba
	 */
	public void SSIteration(int k,BigInteger a,BigInteger r,BigInteger jacobian);
	
	/**
	 * Funkcja pozwalajaca poinformowac listener, ze parametr r jest rozny od wyliczonego jakobianu
	 */
	public void RNotEqualJacobianModeN();
	
	/**
	 * Funkcja pozwalajaca poinformowac listener, ze parametr r jest rozny 1 i n-1, gdzie n jest badana liczba
	 */
	public void RNot1And_N_1();
}
