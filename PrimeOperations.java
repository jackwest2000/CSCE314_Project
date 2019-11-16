import java.util.ArrayList;
import java.math.BigInteger;
//My github repo address is https://github.com/jackwest2000/CSCE314_Project

/*
 *  Desc: This class generates primes, twin primes, and hexagon crosses using BigInteger data types.
 */
public class PrimeOperations {
	
	// Pair class implementation.
	private class Pair<T> {
		private T first;
		private T second;
		
		// gets the first item in the pair
		public T getFirst() 
		{
			return first;
		}
		
		// sets the first item in the pair
		public void setFirst(T first) 
		{
			this.first = first;
		}
		
		// gets the second item in the pair
		public T getSecond() 
		{
			return second;
		}
		
		// sets the second item in the pair
		public void setSecond(T second) 
		{
			this.second = second;
		}
		
		@Override
		public String toString() 
		{
			return first + ", " + second;
		}
	}
	
	// Member variables for containing out lists of integers, twin primes, hexagon crosses, 
	// and the pairs of twin primes that make up the hex crosses.
	private ArrayList<BigInteger> primeList;
	private ArrayList<Pair<BigInteger>> twinPrimeList;
	private ArrayList<Pair<BigInteger>> hexagonCrossList;
	
	// Add a prime to the prime list if and only iff it is not already in the list. (ignore duplicates)
	public void addPrime(BigInteger x)
	{
		if(!(primeList.contains(x)))
		{
			// increase size of the list by one
			primeList.ensureCapacity(primeList.size() + 1);
			
			// add the prime to the end of the list
			primeList.add(x);
		}
	}
	
	// Output the prime list. Each prime should be on a separate line and the total number of primes should be on the following line.
	public void printPrimes()
	{
		// iterate through the list
		for(int i = 0; i < primeList.size(); i++)
		{
			System.out.println(primeList.get(i));
		}
		System.out.println("Total Primes: " + primeList.size());
	}
		
	// Output the twin prime list. Each twin prime should be on a separate line with a comma separating them, and the total number of twin primes should be on the following line.
	public void printTwins()
	{
		// iterate through the list
		for(int i = 0; i < twinPrimeList.size(); i++)
		{
			System.out.println(twinPrimeList.get(i));
		}
		System.out.println("Total Twins: " + twinPrimeList.size());
	}
		
	// Output the hexagon cross list. Each should be on a separate line listing the two twin primes and the corresponding hexagon cross, with the total number on the following line.
	public void printHexes()
	{
		// iterate through the list
		for(int i = 0; i < hexagonCrossList.size(); i++)
		{
			System.out.println("Prime Pairs: " + hexagonCrossList.get(i).getFirst().subtract(BigInteger.ONE) + ", " + hexagonCrossList.get(i).getFirst().add(BigInteger.ONE) + " and " + hexagonCrossList.get(i).getSecond().subtract(BigInteger.ONE) + ", " + hexagonCrossList.get(i).getSecond().add(BigInteger.ONE) + " seperated by " + hexagonCrossList.get(i));
		}
		System.out.println("Total Hexes: " + hexagonCrossList.size());
	}
		
	// Generate and store a list of primes.
	public void generatePrimes(int count)
	{
		// instantiates primeList (clearing if it has already been instantiated)
		primeList = new ArrayList<BigInteger>(count);
		
		
		// bool used to indicate if the prime factorization of a 
		// possible prime contains numbers other than itself and 1
		boolean unique = true;
		
		BigInteger hold = new BigInteger("1");
		if(count > 0)
		{
		//	hold = hold.add(primeList.get(primeList.size() - 1));
		}
		// generate the necessary number of primes
		while(count > 0)
		{
			// find next possible prime
			hold = hold.nextProbablePrime();
			
			// ensure it is actually prime
			for(int i = 0; i < primeList.size(); i++)
			{
				// in the case that it is not prime
				if(hold.mod(primeList.get(i)) == BigInteger.ZERO)
				{
					unique = false;
					break;
				}
			}
			// add if it is in fact prime
			if(unique)
			{
				primeList.add(hold);
				count--;
			}
		}
	}
	
	// Generate and store a list of twin primes.
	public void generateTwinPrimes()
	{
		// allocate a new array
		twinPrimeList = new ArrayList<Pair<BigInteger>>();

		Pair<BigInteger> hold;
		for(int i = 1; i < primeList.size(); i++)
		{
			// if the current prime - 2 is equal to the previous prime
			if(primeList.get(i).subtract(BigInteger.ONE).subtract(BigInteger.ONE).equals(primeList.get(i - 1)))
			{
				// increase capacity of the array
				twinPrimeList.ensureCapacity(twinPrimeList.size() + 1);
				
				// insert new prime pair into array
				hold = new Pair<BigInteger>();
				hold.setFirst(primeList.get(i - 1));
				hold.setSecond(primeList.get(i));
				twinPrimeList.add(hold);
			}
		}
	}
	
	// Generate and store the hexagon crosses, along with the two twin primes that generate the hexagon cross.
	public void generateHexPrimes()
	{
		// allocate new array

		hexagonCrossList = new ArrayList<Pair<BigInteger>>();
		
		Pair<BigInteger> hold;
		
		// iterate through the list of twin primes
		for(int i = 0; i < twinPrimeList.size() - 1; i++)
		{
			// once again, iterate through the list of twin primes
			for(int j = i + 1; j < twinPrimeList.size(); j++)
			{
				// if the current twin primes form a hexagon cross 
				if(twinPrimeList.get(i).getFirst().multiply(BigInteger.ONE.add(BigInteger.ONE)).add(BigInteger.ONE).equals(twinPrimeList.get(j).getFirst()))
				{
					// increase the size of the array
					hexagonCrossList.ensureCapacity(hexagonCrossList.size() + 1);
					
					// insert the new pair into the array
					hold = new Pair<BigInteger>();
					hold.setFirst(twinPrimeList.get(i).getFirst().add(BigInteger.ONE));
					hold.setSecond(twinPrimeList.get(j).getFirst().add(BigInteger.ONE));
					hexagonCrossList.add(hold);
				}
				// if the pairs pass one another in value then break
				else if(twinPrimeList.get(i).getFirst().multiply(BigInteger.ONE.add(BigInteger.ONE)).add(BigInteger.ONE).compareTo(twinPrimeList.get(j).getFirst()) == -1)
				{
					break;
				}
			}
		}
	}
}
