package algorithms;

import java.math.BigInteger;
import java.util.Random;

import algorithms.MathFunctions;

/**
 * Klasa realizujaca algorytm Solovay'a Strassen'a
 *
 */
public class Solovay_Strassen 
{
	SSListener listener = null;
	boolean notifyListener = false;
	boolean verbose = true;
	boolean isprime;
	
	/**
	 * Funkcja pozwalajaca ustawic listener monitorujacy stan w jakim znajduje sie algorym
	 * @param listener - referencja do obiektu implementujacego interfejs SSListener
	 */
	public void setListener(SSListener listener)
	{
		this.listener = listener;
		this.verbose = false;
		this.notifyListener = true;
	}
	
	 /***
	 * Funkcja zwraca odpowiedz na pytanie, czy liczba n jest liczba pierwsza
	 * na podstawie testu Solovaya-Strassena
	 * @param  n	liczba do przetestowania (nieparzysty, n > 2)
	 * @param  iteration 	liczba iteracji testu
	 * @return      czy pierwsza 
	 */
	boolean isPrime(BigInteger prime, int iteration)
	{
		if(this.notifyListener)
			this.listener.setNumber(prime);
		
	    if(prime.compareTo(MathFunctions.toBigInteger(2)) < 0) // ???
	    {
	    	if(this.notifyListener)
	    	{
				this.listener.setNumber(prime);
	    	}
	    	isprime = false;
	    	return isprime;
	    	
	    }
	    if(!prime.equals(MathFunctions.toBigInteger(2)) && prime.mod(MathFunctions.toBigInteger(2)).equals(MathFunctions.toBigInteger(0)))
	    {
	    	if(this.notifyListener)
	    	{
	    		this.listener.isEvenNumber();
				this.listener.setNumber(prime);
	    	}
	    	isprime = false;
	    	return isprime;
	    }
	    
	    for(int i=0 ; i<iteration ; i++)
	    {
	    	BigInteger a = MathFunctions.getRandomBigInteger(prime);
	    	
	    	if (verbose) {System.out.println("Test dla a=" + a); }
	    	
	        
	        BigInteger temp = (prime.subtract(BigInteger.ONE)).divide(MathFunctions.toBigInteger(2));
	        
	        BigInteger r = a.modPow(temp,prime);
	        
	        if (verbose) {System.out.println("r = " + r);}

	        if(!r.equals(BigInteger.ONE))
	        {
	        	if(!r.equals(prime.subtract(BigInteger.ONE)))
	        	{
	        		if(this.notifyListener)
	        		{
	        			this.listener.SSIteration(i, a, r);
	        			this.listener.RNot1And_N_1();
	        			this.listener.isPrime(false);
	        		}
	        		isprime = false;
	    	    	return isprime;
	        	}
	        }
	        
	        BigInteger jacobianSymbol = MathFunctions.calculateJacobian(a,prime, verbose);
	        
	        if(this.notifyListener)
	    		  this.listener.SSIteration(i, a, r, jacobianSymbol);
	        
	        if(!r.equals(jacobianSymbol.mod(prime)))
	        { 
	        	if(this.notifyListener)
	        	{
	        		this.listener.RNotEqualJacobianModeN();
	        		this.listener.SSIteration(i, a, r, jacobianSymbol);
	        	}
	    	    isprime = false;
		        return isprime;
	        }

	    }
	    
	    if(this.notifyListener)
  		  this.listener.isPrime(true);
	    
	    isprime = true;
    	return isprime;
	}


	
	 public static void main(String[] args) {
		
		Solovay_Strassen ss = new Solovay_Strassen();
		AKS _aks = new AKS();
		 
		boolean ans;
		//ans = ss.isPrime(new BigInteger("36653"), 30);
	//	ans = ss.isPrime(new BigInteger("971034308087215866439841492479664676774419087718205580112757800346489383481"), 30);
		ans = _aks.isPrime(new BigInteger("1279"));
	//  ans = _aks.isPrime(new BigInteger("66241160488780141071579864797"));
		
		
		
		System.out.println(ans);
		}
	
}
