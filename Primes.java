import java.util.ArrayList;
import java.util.Iterator;
import java.math.BigInteger;

/*
 *  Desc: This class generates primes, twin primes, and hexagon crosses using BigInteger data types.
 */
public class Primes {
	
	// Member variables for containing out lists of integers, twin primes, hexagon crosses, and the pairs of twin primes that make up the hex crosses.
	private ArrayList<BigInteger> primeList = new ArrayList<BigInteger>();
	private ArrayList<Pair<BigInteger>> twinPrimeList = new ArrayList<Pair<BigInteger>>();
	private ArrayList<Pair<BigInteger>> crossList = new ArrayList<Pair<BigInteger>>();
		

	// Add a prime to the prime list if and only iff it is not already in the list. (ignore duplicates)
	public void addPrime(BigInteger primeNumber)
	{
		if (!primeList.contains(primeNumber))
			primeList.add(primeNumber);
	}

	// Add a pair to the pair list if the pair is not already present
	public void addPair(Pair<BigInteger> pair)
	{
		for(int i = 0; i < twinPrimeList.size(); i++)
		{
			if(pair.left().compareTo(twinPrimeList.get(i).left()) == 0 && pair.right().compareTo(twinPrimeList.get(i).right()) == 0)
			{
				return;
			}
		}
		twinPrimeList.add(pair);
	}

	// Adds a pair of BigIntegers that represent a Hexagonal Cross if it is not already present.
	public void addCross(Pair<BigInteger> pair)
	{
		for(int i = 0; i < crossList.size(); i++)
		{
			if(pair.left().compareTo(crossList.get(i).left()) == 0 && pair.right().compareTo(crossList.get(i).right()) == 0)
			{
				return;
			}
		}
		crossList.add(pair);
	}
	
	
	// Empties the list of primes.
	public void clearPrimes()
	{
		primeList = new ArrayList<BigInteger>();
	}
	
	// Empties the list of crosses.
	public void clearCrosses()
	{
		crossList = new ArrayList<Pair<BigInteger>>();
	}
	
	// Output the prime list. Each prime should be on a separate line and the total number of primes should be on the following line.
	public void printPrimes()
	{
		for(BigInteger p : primeList)
		{
			System.out.println(p);
		}
		System.out.println("Total Primes: " + primeList.size());
	}
		
	// Output the twin prime list. Each twin prime should be on a separate line with a comma separating them, and the total number of twin primes should be on the following line.
	public void printTwins()
	{
		for(Pair<BigInteger> p : twinPrimeList)
		{
			System.out.println(p.left() + ", " + p.right());
		}
		System.out.println("Total Twins: " + twinPrimeList.size());
	}
		
	// Output the hexagon cross list. Each should be on a separate line listing the two twin primes and the corresponding hexagon cross, with the total number on the following line.
	public void printHexes()
	{
		for(int i = 0; i< crossList.size(); i++)
		{
			System.out.println("Hexagon Cross: " + crossList.get(i).left() + ", " + crossList.get(i).right());
		}
		System.out.println("Total Hexes: " + crossList.size());
	}
	
//Generate and store a list of primes from a given starting point.
	public void generatePrimes(BigInteger candidate, int count)
	{
		// clear the current prime list
		primeList.clear();
		
		// if the candidate is 0 add one
		if(candidate.compareTo(BigInteger.ONE) == 0)
		{
			candidate = candidate.add(BigInteger.ONE);
		}

		// do nothing if the count is less than one
		if (count < 1) return;
		
		// generate count number of primes
		for (int i=0; i < count; i++)
		{
			boolean found = false;
			
			// while a prime has not been found
			while(!found)
			{
				// if the candidate is prime
				if(NaiveTest.isPrime(candidate))
				{
					// add the candidate
					primeList.add(candidate);
					found = true;
				}
				// change the candidate to the next probable prime (this function never skips a prime).
				candidate = candidate.nextProbablePrime();
			}
		}

	}
	
	// Generate and store a list of twin primes from the current list of primes.
	public void generateTwinPrimes()
	{
		twinPrimeList.clear();
		// iterate through the prime list
		for (int i = 0; i < primeList.size()-1; i++)
		{
			// if two adjacent primes are seperated by exactly one integer
			if (primeList.get(i+1).equals((primeList.get(i).add(BigInteger.ONE.add(BigInteger.ONE)))) )
			{
				// add the two primes as a pair to the twin primes list
				twinPrimeList.add(new Pair<BigInteger>(primeList.get(i), (primeList.get(i+1))));
			}
		}
	}
	
	// Generate and store the hexagon crosses, using the list of twin primes.
	public void generateHexPrimes()
	{
		crossList.clear();
		
		// iterate through the list of twin primes
		for (int i=0; i < twinPrimeList.size()-1; i++)
		{
			BigInteger n = twinPrimeList.get(i).left().add(BigInteger.ONE);
			
			// iterate from the current twin to the end of the list of twins
			for (int j=i+1; j < twinPrimeList.size(); j++)
			{
				BigInteger twoN = twinPrimeList.get(j).left().add(BigInteger.ONE);
				
				// if the second is exactly twice the first, add the pair as a hexagon cross
				if (n.multiply(BigInteger.ONE.add(BigInteger.ONE)).compareTo(twoN) == 0)
				{
					crossList.add(new Pair<BigInteger>(n, twoN));				
				}		
			}
		}
	}

	// Count the number of digits in the last (and thus largest) prime.
	public int sizeofLastPrime()
	{
		// defaults to returning zero if there is no prime currently stored
		if(primeList.size() == 0)
		{
			return 0;
		}
		return primeList.get(primeList.size() - 1).toString().length();
	}
	
	// Count the number of digits in the two entries in the last (and thus largest) hexagon cross
	public Pair<Integer> sizeofLastCross()
	{
		// defaults to returning zeros if there is no current hexagon cross
		if(crossList.size() == 0)
		{
			return new Pair<Integer>(0,0);
		}
		return new Pair<Integer>(crossList.get(crossList.size() - 1).left().toString().length(), crossList.get(crossList.size() - 1).right().toString().length());
	}
	
	// Return the number of primes
	public int primeCount()
	{
		return primeList.size();
	}
	
	// Return the number of crosses.
	public int crossesCount()
	{
		return crossList.size();
	}
	
	// creates an iterator for traversing the list of primes
	public class IterablePrimes implements Iterable<BigInteger>
	{

		@Override
		public Iterator<BigInteger> iterator() {
			 Iterator<BigInteger> it = new Iterator<BigInteger>() {

		            private int currentIndex = 0;

		            // returns true if there are additional elements in the prime list and false otherwise
		            @Override
		            public boolean hasNext() {
		                return currentIndex < primeList.size() && primeList.get(currentIndex) != null;
		            }

		            // returns the next prime in the prime list
		            @Override
		            public BigInteger next() {
		                return primeList.get(currentIndex++);
		            }

		            // an unsupported operation
		            @Override
		            public void remove() {
		                throw new UnsupportedOperationException();
		            }
		        };
		        // return the iterator
		        return it;
		}
	}
	
	// returns an iterator for the prime list
	public IterablePrimes iteratePrimes() { return new IterablePrimes();}

	// creates an iterator for the hexagon cross list
	public class IterableCrosses implements Iterable<Pair<BigInteger>>
	{

		@Override
		public Iterator<Pair<BigInteger>> iterator() {
			Iterator<Pair<BigInteger>> it = new Iterator<Pair<BigInteger>>() {

	            private int currentIndex = 0;

	            // returns true if there are additional crosses in the cross list and false otherwise
	            @Override
	            public boolean hasNext() {
	                return currentIndex < crossList.size() && crossList.get(currentIndex) != null;
	            }

	            // gives the pair containing the next cross in the cross list
	            @Override
	            public Pair<BigInteger> next() {
	                return crossList.get(currentIndex++);
	            }

	            // an unsupported operation
	            @Override
	            public void remove() {
	                throw new UnsupportedOperationException();
	            }
	        };
	        // return the iterator
	        return it;
		}
	}		
	
	// returns an iterator for the cross list
	public IterableCrosses iterateCrosses() { return new IterableCrosses();}


}
