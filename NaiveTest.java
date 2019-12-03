import java.math.BigInteger;
import java.math.BigDecimal;
import java.lang.Math;

// This class is used to test whether an integer is prime or not. The test used is a deterministic variant of the
// Miller-Rabin primality test known simply as the Miller test. The pseudo-code for this algorithm was found on 
// the wikipedia page about the Miller-Rabin primality test. The link is here:
// https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test#Deterministic_variants
// All credit for this test goes to Gary L. Miller. The code is of my own writing but it 
// is based on the pseudo-code.
public class NaiveTest
{
	// finds the upper bound of the loop used in the Miller test
	private static BigInteger LoopLimit(BigInteger n)
	{
		BigDecimal hold = new BigDecimal(n);
		
		// an approximation of the mathematical constant e
		BigDecimal e = new BigDecimal("2.718281828459045");
		
		int precision = hold.scale() + 200;
		BigInteger count = BigInteger.ZERO;
		
		// find the ln(hold) by repeated division by e
		while(hold.divide(e, precision, BigDecimal.ROUND_HALF_UP).compareTo(e) > 0)
		{
			hold = hold.divide(e, precision, BigDecimal.ROUND_HALF_UP);
			count = count.add(BigInteger.ONE);
		}
		
		// add the remaining value for the natural log
		BigDecimal left = new BigDecimal(Double.toString(Math.log(hold.doubleValue())));
		left = left.add(new BigDecimal(count));
		
		// raise the ln(hold) to a power of 2 and then multiply it by 2
		left = left.pow(2);
		left = left.multiply(BigDecimal.ONE.add(BigDecimal.ONE));
		
		// finally, find the floor of this function
		BigInteger out = left.toBigInteger();
		
		// return the value
		return out;
	}
	
	// uses the Miller test to determine the primality of a given number.
	// returns true if it is prime and false otherwise.
	public static boolean isPrime(BigInteger candidate)
	{
		BigInteger d = candidate.subtract(BigInteger.ONE);
		BigInteger r = BigInteger.ZERO;
		
		// find the integers d and r such that (2^r)*d = candidate
		while(d.mod(BigInteger.ONE.add(BigInteger.ONE)).compareTo(BigInteger.ZERO) == 0)
		{
			d = d.divide(BigInteger.ONE.add(BigInteger.ONE));
			r = r.add(BigInteger.ONE);
			
		}
		
		// calculate the upper loop limit
		BigInteger upper = LoopLimit(candidate);

		// find the min of candiate - 2 and the upper loop limit
		if(candidate.subtract(BigInteger.ONE).subtract(BigInteger.ONE).compareTo(upper) == -1)
		{
			upper = candidate.subtract(BigInteger.ONE).subtract(BigInteger.ONE);
		}
		
		BigInteger x = new BigInteger("0");
		boolean composite;

		// iterate between 2 and the upper loop limit
		for(BigInteger a = BigInteger.ONE.add(BigInteger.ONE); a.compareTo(upper) < 1; a = a.add(BigInteger.ONE))
		{
			// x = a^d mod candidate
			x = a.modPow(d,candidate);
			
			// if x == 1 or x == candidate - 1
			if(x.compareTo(BigInteger.ONE) == 0 || x.compareTo(candidate.subtract(BigInteger.ONE)) == 0)
			{
				continue;
			}
			composite = true;
			// iterate between 0 and r
			for(BigInteger i = BigInteger.ZERO; i.compareTo(r) < 0; i.add(BigInteger.ONE))
			{
				// x = x^2 mod candidate
				x = x.pow(2).mod(candidate);
				
				// if x == candidate - 1
				if(x.compareTo(candidate.subtract(BigInteger.ONE)) == 0)
				{
					composite = false;
					break;
				}
			}
			
			// if the number was found to be composite
			if(composite)
			{
				return false;
			}
		}
		
		// default return value
		return true;
	}
}
