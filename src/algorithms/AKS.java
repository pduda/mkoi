package algorithms;

import java.math.BigInteger;
import nuim.cs.crypto.polynomial.PolynomialException;
import nuim.cs.crypto.polynomial.big.BigPolynomial;
import nuim.cs.crypto.polynomial.big.field.BigFieldPolynomial;

public class AKS {
	

    static boolean verbose = true;
  
    
    boolean isprime;
    BigInteger podstawa;
    
    /***
	 * Funkcja zwraca odpowiedŸ na pytanie, czy liczba n jest liczb¹ pierwsz¹ na podstawie
	 * testu AKS
	 * 
	 * Korzysta z BigFieldPolynomial 
	 * http://www-ti.informatik.uni-tuebingen.de/~reinhard/krypto/AKSTest/nuim/cs/crypto/polynomial/big/field/BigFieldPolynomial.java
	 * 
	 * @param  n	liczba do przetestowania (nieparzysty, n > 2)
	 * @return      czy pierwsza 
	 */

	public boolean isPrime(BigInteger n) 
    {
          
            BigInteger a = BigInteger.valueOf(2);
            
            
            //pierwszy warunek - sprawdzamy czy n jest perfect power a -> dla a>1, b>1
            do
            {
                    BigInteger result;

                    int power = 1; 
                    int comp_result;
       
                    do // sprawdŸ dla a^2, a^3....
                    {
                            power++;
                            result = a.pow(power);
                            comp_result = n.compareTo(result);
                    }
                    while( comp_result > 0);
                    
                    if( comp_result == 0 )
                    {
                            if (verbose) { System.out.println(n + " jest potêg¹ liczby ca³kowitej " + a); }
                            podstawa = a;
                            isprime = false;
                            return isprime;
                    }
                    
                    if (verbose) { System.out.println(n + " nie jest potêg¹ liczby ca³kowitej " + a); }

                    a = a.add(BigInteger.ONE);
            }
            while (a.pow(2).compareTo(n) <= 0); // nie ma sensu jeœli kwadrat > n
            if (verbose) { System.out.println(n + " nie jest potêg¹ ¿adnej liczby ca³kowitej mniejszej ni¿ swój kwadrat"); }
            

           //znajdz takie r, ¿e o_r(n) > log^2 (n) -> o_r(*) - porz¹dek multiplikatywny
            double logSquared = MathFunctions.log(n)*MathFunctions.log(n);
            BigInteger m = BigInteger.ONE;
            BigInteger r = BigInteger.ONE;
            do
            {
            	if (verbose) { System.out.println("Porz¹dek multiplikatywny:"); }
                    r = r.add(BigInteger.ONE);
                    m = MathFunctions.mOrder(n,r,verbose);
                	
            }
            while( m.doubleValue() < logSquared );
            if (verbose) { System.out.println("r = " + r); }

            
            // jeœli 1 < (a,n) < n dla pewnych a <= r, zlozona, przy czym (a,n) - wzgledna pierwszosc (NWD(a,n) = 1)
            for( BigInteger i = BigInteger.valueOf(2); i.compareTo(r) <= 0; i = i.add(BigInteger.ONE) )
            {
                    BigInteger nwd = n.gcd(i);
                    if (verbose) { System.out.println("NWD(" + n + "," + i + ") = " + nwd); }
                    if ( nwd.compareTo(BigInteger.ONE) > 0 && nwd.compareTo(n) < 0 )
                    {
                            podstawa = i;
                            isprime = false;
                            return false;
                    }
            }
            
            
            // jeœli n <= r, pierwsza
            if( n.compareTo(r) <= 0 )
            {
                    isprime = true;
                    return true;
            }

            
            // For i = 1 to sqrt(totient)log(n) do
            // if (X+i)^n <> X^n + i (mod X^r - 1,n), z³o¿ona - najbardziej czasochlonny krok;
            
            //ew tutaj dodac wypisywanie wielomianow, ale nie wiem czy nie zbede

            // sqrt(totient)log(n)
            long to = (long) Math.floor((Math.sqrt(MathFunctions.totient(r).doubleValue()) * MathFunctions.log(n)));
            // X^r - 1
            BigFieldPolynomial mod = new BigFieldPolynomial(new BigPolynomial("x^" + r.toString() + "-1"), n);
            for( long i = 1; i <= to; i++ ){
                //x+a mod n
                BigFieldPolynomial left_eq = new BigFieldPolynomial(new BigPolynomial("x+" + i), n);
                //x^n+a mod n
                BigFieldPolynomial right_eq = new BigFieldPolynomial(new BigPolynomial("x^" + n.toString() + "+" + i), n);
                try {
                    //(x+a)^n mod(x^r-1,n)
                    BigFieldPolynomial left_eq_mod = new BigFieldPolynomial(left_eq.modPow(n, mod), n);
                    //(x^n+a) mod(x^r-1,n)
                    BigFieldPolynomial right_eq_mod = new BigFieldPolynomial(right_eq.mod(mod).modCoefficient(n), n);
               
                    if (!(left_eq_mod.equals(right_eq_mod))) {
               
                    	isprime = false;
                        return isprime;
                  }
                  
                    if (verbose) { System.out.println("Warunek wielomianowy nie spe³niony"); }
                } catch (PolynomialException e) {
                    System.out.println(e.getMessage());
                }
            }
            
            isprime = true;
        return isprime;
    }
	
   
}
