package mkoi.algorithms;

import java.math.BigInteger;
import nuim.cs.crypto.polynomial.PolynomialException;
import nuim.cs.crypto.polynomial.big.BigPolynomial;
import nuim.cs.crypto.polynomial.big.field.BigFieldPolynomial;

/**
 * Klasa realizujaca algorytm AKS
 *
 */
public class AKS {
	

    boolean verbose = true;
    boolean isprime;
    boolean notifyListeners = false;
    BigInteger podstawa;
    AKSListener listener;
    
    /**
	 * Funkcja pozwalajaca ustawic listener monitorujacy stan w jakim znajduje sie algorym
	 * @param listener - referencja do obiektu implementujacego interfejs AKSListener
	 */
    public void setListener(AKSListener listener)
    {
    	this.listener = listener;
    	this.notifyListeners=true;
    	this.verbose=false;
    }
    
    /***
	 * Funkcja zwraca odpowiedz na pytanie, czy liczba n jest liczba pierwsza na podstawie
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
			if (notifyListeners) 
				this.listener.setNumber(n);
		
            BigInteger a = BigInteger.valueOf(2);

            //pierwszy warunek - sprawdzamy czy n jest perfect power a -> dla a>1, b>1
            do
            {
                    BigInteger result;

                    int power = 1; 
                    int comp_result;
       
                    do // sprawdz dla a^2, a^3....
                    {
                            power++;
                            result = a.pow(power);
                            comp_result = n.compareTo(result);
                    }
                    while( comp_result > 0);
                    
                    if( comp_result == 0 )
                    {
                            if (this.verbose) { System.out.println(n + " jest potega liczby calkowitej " + a); }
                            if (notifyListeners) 
                            {
                            	this.listener.powerOfInteger(a,power);
                            	this.listener.isPrime(false);
                            }
                            
                            podstawa = a;
                            isprime = false;
                            return isprime;
                    }
                    
                    if (this.verbose) { System.out.println(n + " nie jest potega liczby calkowitej " + a); }
                    if (notifyListeners) this.listener.isNotPowerOfInteger(a);
                    
                    a = a.add(BigInteger.ONE);
            }
            while (a.pow(2).compareTo(n) <= 0); // nie ma sensu jesli kwadrat > n
            if (this.verbose) { System.out.println(n + " nie jest potega zadnej liczby calkowitej mniejszej niz swoj kwadrat"); }
            
           //znajdz takie r, ze o_r(n) > log^2 (n) -> o_r(*) - porzadek multiplikatywny
            double logSquared = MathFunctions.log(n)*MathFunctions.log(n);
            if (notifyListeners) this.listener.isNotPowerOfAnyInteger();

            BigInteger m = BigInteger.ONE;
            BigInteger r = BigInteger.ONE;
            do
            {
            	if (this.verbose) { System.out.println("Porzadek multiplikatywny:"); }
                    r = r.add(BigInteger.ONE);
                    m = MathFunctions.mOrder(n,r,this.verbose);
                	
            }
            while( m.doubleValue() < logSquared );
            if (this.verbose) { System.out.println("r = " + r); }
            if (notifyListeners) this.listener.properRParamFound(r);
            
            // jesli 1 < (a,n) < n dla pewnych a <= r, zlozona, przy czym (a,n) - wzgledna pierwszosc (NWD(a,n) = 1)
            for( BigInteger i = BigInteger.valueOf(2); i.compareTo(r) <= 0; i = i.add(BigInteger.ONE) )
            {
                    BigInteger nwd = n.gcd(i);
                    if (this.verbose) { System.out.println("NWD(" + n + "," + i + ") = " + nwd); }
                    
                    if ( nwd.compareTo(BigInteger.ONE) > 0 && nwd.compareTo(n) < 0 )
                    {
                    	if (notifyListeners) 
                    	{
                    		this.listener.hasNWDWithInteger(i,nwd);
                    		this.listener.isPrime(false);
                    	}
                        
                        podstawa = i;
                        isprime = false;
                        return false;
                    }
            }
            
            
            // jesli n <= r, pierwsza
            if( n.compareTo(r) <= 0 )
            {
            	if (notifyListeners)
            	{
            		this.listener.paramRBiggerThanN();
            		this.listener.isPrime(true);
            	}
                isprime = true;
                return true;
            }
            
            // sqrt(totient)log(n)
            long to = (long) Math.floor((Math.sqrt(MathFunctions.totient(r).doubleValue()) * MathFunctions.log(n)));
            // X^r - 1
            BigFieldPolynomial mod = new BigFieldPolynomial(new BigPolynomial("x^" + r.toString() + "-1"), n);
           
            // if (X+i)^n <> X^n + i (mod X^r - 1,n), zlozona - najbardziej czasochlonny krok;
            if (notifyListeners) this.listener.startLittleFermat(to,mod);
            
            // For i = 1 to sqrt(totient)log(n) do
            for( long i = 1; i <= to; i++ )
            {
                //x+a mod n
                BigFieldPolynomial left_eq = new BigFieldPolynomial(new BigPolynomial("x+" + i), n);
                //x^n+a mod n
                BigFieldPolynomial right_eq = new BigFieldPolynomial(new BigPolynomial("x^" + n.toString() + "+" + i), n);
                
                String equation = "(x + " + Long.toString(i)+")^"+n.toString();//left_eq
                equation += " = [x^("+n.toString()+") + " + Long.toString(i)+"]";
                equation += " mod("+n.toString()+")";
                
                if (notifyListeners) this.listener.iteration(i,equation);
                
                try 
                {
                    //(x+a)^n mod(x^r-1,n)
                    BigFieldPolynomial left_eq_mod = new BigFieldPolynomial(left_eq.modPow(n, mod), n);
                    //(x^n+a) mod(x^r-1,n)
                    BigFieldPolynomial right_eq_mod = new BigFieldPolynomial(right_eq.mod(mod).modCoefficient(n), n);
               
                    if (!(left_eq_mod.equals(right_eq_mod))) 
                    {
                    	if (notifyListeners) 
                    	{
                    		this.listener.equationNotTrue();
                    		this.listener.isPrime(false);
                    	}
                    	isprime = false;
                        return isprime;
                    }
                  
                    if (this.verbose) { System.out.println(equation+"- warunek wielomianowy nie spelniony"); } 
                
                } 
                catch (PolynomialException e) 
                {
                    System.out.println(e.getMessage());
                }
            }
            
        if (notifyListeners) this.listener.isPrime(true);
        isprime = true;
        return isprime;
    }
	
   
}
