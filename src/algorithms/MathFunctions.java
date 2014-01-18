package algorithms;

import java.math.BigInteger;
import java.util.Random;

public class MathFunctions {

	/***
	 *  Funkcja oblicza symbol Jakobiego dla liczb a,n. Symbol Jakobiego jest uog�lnieniem symbolu Lagendre'a
	 * Zwraca -1, 0, 1 w zale�no�ci od zadanych parametr�w. Do oblicze� wykorzystywana jest klasa BigInteger
	 * 
	 * @param  a	parametr a symbolu Jakobiego
	 * @param  n	parametr n symbolu Jakobiego (nieparzysty, n > 2)
	 * @param verbose tryb verbose
	 * @return      warto�� funkcji 
	 */
	static BigInteger calculateJacobian(BigInteger a,BigInteger n, boolean verbose){
		
	   BigInteger ans= BigInteger.ONE;
	   BigInteger temp;
	   
	   /*
	    * W�a�ciwo��: 1) (0/n) = 0 
	    */

	   if(a.equals(BigInteger.ZERO)) 
	    	return BigInteger.ZERO;
	   
	
	   /*
	    * W�a�ciwo��: 2) (a/n) = (-a/n)*(-1/n)
	    * W�a�ciwo��: 3) (-1/n) = -1 if n = 3 ( mod 4 )
	    */
	   
	   if(a.compareTo(BigInteger.ZERO) < 0)
	   {
	        a = a.negate();    
	        
	        if(bigIntegerMod(n,4).equals(BigInteger.valueOf(3))) 
	        	ans = ans.negate();  
	   }
	   
	   /*
	    * W�a�ciwo��: 4) (1/n) = 1 
	    */
	    
	   if(a.equals(BigInteger.ONE)) 
		   return ans; 
	 
	    
	   while(!a.equals(BigInteger.ZERO)){
	        
		   if(a.compareTo(BigInteger.ZERO) < 0)
		   {
	            a=a.negate();   
	            
	            /*
		     	* W�a�ciwo��: 5) (-1/n) = -1 if n = 3 (mod 4)
		     	*/
	          
	            if(bigIntegerMod(n,4).equals(BigInteger.valueOf(3))) 
	            	ans = ans.negate();   
	       }
	        
		   while(bigIntegerMod(a,2).equals(BigInteger.ZERO)){
	            a=a.divide(BigInteger.valueOf(2));
	            
	            /*
	     	    * W�a�ciwo��: 5) (2/n) = -1 if n = 3 ( mod 8 ) lub n = 5 (mod 8)
	     	    */
	            
	            if(	bigIntegerMod(n,8).equals(BigInteger.valueOf(3)) ||
	            	bigIntegerMod(n,8).equals(BigInteger.valueOf(5)))
	            	
	            	ans = ans.negate();
	            
	         if (verbose) {   System.out.print("(" + (a) + "/" + (n) + ")" + " "); }
		        
	        }
		   
		   /*
     	    * Quadratic reciprocity - wzajemno�� reszt kwadratowych, je�li a,n = 3 ( mod 4 ) 
     	    */
		   		   
	        temp = n;
	        n = a;
	        a = temp;

	        
	        
	        if(	bigIntegerMod(a,4).equals(BigInteger.valueOf(3)) &&
            	bigIntegerMod(n,4).equals(BigInteger.valueOf(3))) 
	        	
	        		ans = ans.negate(); 
	        
	        	a=a.mod(n); 
	        
	        if(a.compareTo(n.divide(BigInteger.valueOf(2))) > 0) 
	        	
	        	a = a.subtract(n); 
	        
	     if(verbose) {  System.out.print("(" + (a) + "/" + (n) + ")" + " "); }
	        
	    }
	   
	  
	    
	   if(n.equals(BigInteger.ONE)) {
		   if (verbose) {System.out.println("= "+ans);}
		   return ans;
	   }

	   if (verbose) {System.out.println();}
	   return BigInteger.ZERO; 
	}
	
	/***
	 * Funkcja obliczaj�ca modulo dla klasy BigInteger
	 * 
	 * @param a liczba do operacji modulo
	 * @param b parametrf funkcji modulo 
	 * @return  BigInteger reprezentujacy liczb� a%b
	 */

	
	public static BigInteger bigIntegerMod(BigInteger a, int b){
		
		return a.mod(BigInteger.valueOf(b));
				
	}

	/***
	 * Funkcja oblicza symbol Jakobiego dla liczb a,n. Symbol Jakobiego jest uog�lnieniem symbolu Lagendre'a
	 * Zwraca -1, 0, 1 w zale�no�ci od zadanych parametr�w. Do oblicze� wykorzystywana jest klasa BigInteger
	 * 
	 * @param  a	parametr a symbolu Jakobiego
	 * @param  n	parametr n symbolu Jakobiego (nieparzysty, n > 2)
	 * @param verbose tryb verbose
	 * @return      warto�� funkcji 
	 */
	
	static int calculateJacobian(int a, int n, boolean verbose){
		
		BigInteger ans;
		ans = calculateJacobian(BigInteger.valueOf(a), BigInteger.valueOf(n),verbose);
		System.out.println(ans);
		return ans.intValue();
	}
	
	
    
    /***
     * Oblicza funkcje Eulera (tocjent)
     *      * 
     * http://en.wikipedia.org/wiki/Euler%27s_totient_function
     * 
     * Algorytm:
     * 
     * http://community.topcoder.com/tc?module=Static&d1=tutorials&d2=primeNumbers
     * 
     * @param r BigInteger argument funkcji
     * @return phi(r) funkcja zwraca warto�� m�wi�c� ile jest liczb pierwszych nie wi�kszych od argument�w
     */
	public static BigInteger totient(BigInteger n) 
{ 
    BigInteger result = n; 
  
    for( BigInteger i = BigInteger.valueOf(2); n.compareTo(i.multiply(i)) > 0; i = i.add(BigInteger.ONE) ) 
    { 
            if (n.mod(i).compareTo(BigInteger.ZERO) == 0) 
                    result = result.subtract(result.divide(i));
            
            while (n.mod(i).compareTo(BigInteger.ZERO) == 0)
                    n = n.divide(i); 
    }
    
    if (n.compareTo(BigInteger.ONE) > 0) 
            result = result.subtract(result.divide(n));
    
    return result;
}
	
	    
	    /**
	     * Loagrytm o podstawie 2 dla du�ych liczb
	     * http://world.std.com/~reinhold/BigNumCalcSource/BigNumCalc.java
	     * @param n BigInteger reprezentuj�cy liczb� do zlogarytmowania
	     * @return
	     */
	public static double log(BigInteger n)
	    {
	        BigInteger b;
	            
	        int temp = n.bitLength() - 1000;
	        if (temp > 0) 
	        {
	            b=n.shiftRight(temp); 
	            return (Math.log(b.doubleValue()) + temp)*Math.log(2);
	        }
	        else 
	            return (Math.log(n.doubleValue())*Math.log(2));
	    }
	    
	    /***
	     * Funkcja liczy (ang. multiplicative order) dla zadanej liczby
	     * http://www.answers.com/topic/multiplicative-order
	     * @param r jakie modulo ma by� u�yte
	     * @return warto�� rz�du mno�enia lub -1 je�li brak
	     */
	   public static BigInteger mOrder(BigInteger n, BigInteger r, boolean verbose)
	    {
	         
	            BigInteger power = BigInteger.ONE;
	            BigInteger result;
	            
	            do
	            {
	                    result = n.modPow(power,r);
	                    power = power.add(BigInteger.ONE);
	            }
	            while( result.compareTo(BigInteger.ONE) != 0 && r.compareTo(power) > 0);
	            
	            if (r.compareTo(power) <= 0)
	                    return BigInteger.ONE.negate();
	            else
	            {
	                    if (verbose) System.out.println(n + "^" + power + " mod " + r + " = " + result);
	                    return power;
	            }
	    }

	   
		/***
		 * Funkcja konwertuj�ca liczb� do BigInteger
		 * 
		 * @param  a liczba do konwersji
		 * @return  BigInteger reprezentujacy liczb� a
		 */

		public static BigInteger toBigInteger(long a){
			return BigInteger.valueOf(a);
		}
		
		 	/***
			 * Funkcja zwraca losow� warto�� BigInteger z przedzia�u [2,n-2]
			 * 
			 * @param  uplimit	g�rny limit
			 * @return  BigInteger reprezentujacy warto�� z przedzia�u [2,n-2]
			 */
		
		public static BigInteger getRandomBigInteger(BigInteger uplimit) {
	        Random rand = new Random();
	        BigInteger upperLimit = uplimit.subtract(toBigInteger(2));
	        BigInteger temp, result;
	        do {
	            temp = new BigInteger(upperLimit.bitLength(), rand); 
	        }while(temp.compareTo(upperLimit) >= 0); 
	        if (temp.compareTo(toBigInteger(2)) < 0) {
	        	result = temp.add(toBigInteger(2));
	        	return result;
	        	}
	        return temp;
	    }
	
}
