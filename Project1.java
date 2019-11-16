// My github repo address is https://github.com/jackwest2000/CSCE314_Project

public class Project1 {
	public static void main(String[] args) 
	{
		// Instantiate Primes Class
		PrimeOperations testOne = new PrimeOperations();
		
		// Generate Primes and test.
		testOne.generatePrimes(21);
		testOne.printPrimes();
		
		// Generate and test Twin Primes
		PrimeOperations testTwo = new PrimeOperations();
		testTwo.generatePrimes(100);
		testTwo.generateTwinPrimes();
		testTwo.printTwins();
\
		
		// Generate and test Hexagonal crosses
		PrimeOperations testThree = new PrimeOperations();
		testThree.generatePrimes(2000);
		testThree.generateTwinPrimes();
		testThree.generateHexPrimes();
		testThree.printHexes();
	}
}
