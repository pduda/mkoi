package mkoi;

import java.math.BigInteger;

import mkoi.algorithms.SSListener;

public class SSLogger extends Logger implements SSListener
{
	private long currentIteration=0;
	
	public SSLogger(int mode)
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
	public void isEvenNumber() 
	{
		if(consoleMode)
			System.out.println("Liczba " + this.n.toString() + " jest liczbą parzystą");
	}

	@Override
	public void SSIteration(int k, BigInteger a, BigInteger r) 
	{
		if(consoleMode)
			System.out.println(Integer.toString(k)+" iteracja algorytmu SS. a = " + a.toString() + " r = "+r.toString()+ " jakobian = NaN");
	}

	@Override
	public void SSIteration(int k, BigInteger a, BigInteger r,BigInteger jacobian) 
	{
		if(consoleMode)
			System.out.println(Integer.toString(k)+" iteracja algorytmu SS: a = " + a.toString() + " r = "+r.toString()+ " jakobian = " + jacobian.toString());
	
	}

	@Override
	public void RNotEqualJacobianModN() 
	{
		if(consoleMode)
			System.out.println("Parametr R jest różny od wartości symbolu Jacobiana mod N");
	
	}

	@Override
	public void RNot1And_N_1() 
	{
		if(consoleMode)
			System.out.println("Parametr R jest różny od 1 i od N-1");
	}

}
