package mkoi.algorithms;

import java.math.BigInteger;

import nuim.cs.crypto.polynomial.big.field.BigFieldPolynomial;

public interface AKSListener 
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
	 * Funkcja pozwalajaca przekazac listenerowi informacje ze badana liczba n jest pewna potega liczby a
	 * @param a - pewna liczba calkowita
	 * @param power - potega
	 */
	public void powerOfInteger(BigInteger a,int power);
	
	/**
	 * Funkcja pozwalajaca przekazac listenerowi informacje ze n nie jest potega liczy a
	 * @param a - pewna liczba calkowita
	 */
	public void isNotPowerOfInteger(BigInteger a);
	
	/**
	 * Funkcja pozwalajaca przekazac listenerowi informacje ze badana liczba n nie jest potega zadnej liczby calkowitej mniejszej niz n
	 * 
	 */
	public void isNotPowerOfAnyInteger();
	
	
	/**
	 * Funkcja pozwalajaca na poinformowanie listenera o wyliczonej wartosci parametru r = 
	 * @param r
	 */
	public void properRParamFound(BigInteger r);
	
	/**
	 * Funkcja pozwalajaca na poinformowanie listenera ze parametr R jest wiekszy niz badana liczba N
	 */
	public void paramRBiggerThanN();
	
	/**
	 * Funkcja informujaca listener ze badana liczba n ma wspolny dzielnik z liczba i
	 * @param i - pewna liczba
	 * @param nwd - wartosc wspolnego dzielnika
	 */
	public void hasNWDWithInteger(BigInteger i,BigInteger nwd);
	
	/**
	 * Funkcja informujaca o tym iz uruchomiona zostala procedura sprawdzania rownania na ktorym opiera sie algorytm AKS
	 * @param to - maksymalna liczba iteracji. 
	 * @param mod - cialo dla aktualnie badanego wielomianu
	 */
	public void startLittleFermat(long to,BigFieldPolynomial mod);
	
	/**
	 * Funkcja informujaca listener, ze rownanie w oparciu o ktore dziala algorytm AKS nie zostalo spelnione
	 */
	public void equationNotTrue();
	
	/**
	 * Funkcja informujaca listener o wykonaniu i-tej iteracji algorytmu AKS
	 * @param i - numer iteracji
	 * @param equation - badane rownanie
	 */
	public void iteration(long i,String equation);
}
