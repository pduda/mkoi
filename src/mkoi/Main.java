package mkoi;

import java.math.BigInteger;

import mkoi.algorithms.AKS;
import mkoi.algorithms.Solovay_Strassen;

public class Main
{


	
	 public static void main(String[] args)
	 {
		 SSLogger ssLogger = new SSLogger(Logger.CONSOLE_MODE);
		 AKSLogger aksLogger = new AKSLogger(Logger.CONSOLE_MODE);
		 
		 Solovay_Strassen ss = new Solovay_Strassen();
		 ss.setListener(ssLogger);
		 
		 AKS aks = new AKS();
		 aks.setListener(aksLogger);
		 
		 boolean ans;
		 
		 //ans = ss.isPrime(new BigInteger("8"),30);
		 ans = aks.isPrime(new BigInteger("10"));

		 System.out.println(ans);
		 
		 //  ans = _aks.isPrime(new BigInteger("66241160488780141071579864797"));
			
		 //ans = ss.isPrime(new BigInteger("36653"), 30);
		 //	ans = ss.isPrime(new BigInteger("971034308087215866439841492479664676774419087718205580112757800346489383481"), 30);
	}
}