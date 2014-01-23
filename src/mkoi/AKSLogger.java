package mkoi;

import java.math.BigInteger;

import nuim.cs.crypto.polynomial.big.field.BigFieldPolynomial;
import mkoi.algorithms.AKSListener;

public class AKSLogger extends Logger implements AKSListener
{
	private String currentEquation="";
	private long currentIteration =0;
	
	public AKSLogger(int mode)
	{
		switch(mode)
		{
			case Logger.CONSOLE_MODE:
				this.consoleMode=true;
				break;
			case Logger.GUI_MODE:
				break;
		}
	}
	
	@Override
	public void setNumber(BigInteger n) 
	{
		this.n = n;	
	}

	@Override
	public void isPrime(boolean isPrime) 
	{
		if(isPrime)
		{
			if(consoleMode)
				System.out.println("Liczba " + this.n.toString() + " jest liczbą pierwszą");
		}
		else
		{
			if(consoleMode)
				System.out.println("Liczba " + this.n.toString() + " nie jest liczbą pierwszą");
		}
		
	}

	@Override
	public void powerOfInteger(BigInteger a, int power) 
	{
		if(consoleMode)
			System.out.println("Liczba a^(" + Integer.toString(power) + ") = "+this.n.toString());
	}

	@Override
	public void isNotPowerOfInteger(BigInteger a) 
	{
		if(consoleMode)
			System.out.println("Liczba a = "+a.toString()+" nie jest pierwiastkiem liczby n = " + this.n.toString());
	}

	@Override
	public void isNotPowerOfAnyInteger() 
	{
		if(consoleMode)
			System.out.println("Liczba " + this.n.toString() + " nie jest potęgą żadnej liczby całkowitej");
	}

	@Override
	public void properRParamFound(BigInteger r) 
	{
		if(consoleMode)
			System.out.println("r = " + r.toString());
	}

	@Override
	public void paramRBiggerThanN() 
	{
		if(consoleMode)
			System.out.println("Parametr r jest większy od badanej liczby n");
	}

	@Override
	public void hasNWDWithInteger(BigInteger i, BigInteger nwd) 
	{
		if(consoleMode)
			System.out.println("Liczby n =" + this.n.toString() + " oraz i = "+i.toString() + " nie sa wzajemnie pierwsze");
	}

	@Override
	public void startLittleFermat(long to, BigFieldPolynomial mod) 
	{
		if(consoleMode)
			System.out.println("Liczba" + this.n.toString() + " jest liczbą pierwszą");
	}

	@Override
	public void equationNotTrue() 
	{
		if(consoleMode)
			System.out.println("Równanie: " + this.currentEquation + " jest nieprawdziwe");
	}

	@Override
	public void iteration(long i, String equation) 
	{
		this.currentIteration = i;
		this.currentEquation = equation;
		
		if(consoleMode)
			System.out.println(Long.toString(i)+ " iteracja dla rownania " + equation);
	}

}
