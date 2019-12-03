// This file gives access to the underlying datafile and stores the data in the Workout class.
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Scanner;
import java.io.FileReader;

public class FileAccess {
	
	// returns true if the given char is a numeric character
	// and false in any other case
	private static boolean isNumeric(char c)
	{
		if(c <= '9' && c >= '0')
		{
			return true;
		}
		return false;
	}
  
	// loads primes from the file under the Config.DATAPATH directory with the 
	// given file name into the given Primes instance. 
	// Returns true if there were no issues in loading and 
	// false if there was any issue.
	public static boolean loadPrimes(Primes primes, String filename)
	{
		// opens the file from which the data will be read and returns
		// false if the file is not found.
		FileReader in;
		File file;
		try {
			file = new File(Config.DATAPATH + filename);
			in = new FileReader(file.getAbsolutePath());
			
		} catch(Exception FileNotFoundException) {
			return false;
		}
	  
		// Instantiate a few helpful variables for
		// reading in from a file.
		String input = "";
		int c;
		
		// esnures the program does not terminate from an error in reading 
		// from the file
		try {
			// iterate through all characters in the file
			while((c = in.read()) != -1)
			{
				// add them to the input string
				input += (char)(c);
				
				// if the last character added was a newline character or a comma
				if(input.charAt(input.length() - 1) == ',' || input.charAt(input.length() - 1) == '\n')
				{
					// add the input string (without the last character) as a prime number
					if(input.length() == 2 && !isNumeric(input.charAt(1)))
					{
						primes.addPrime(new BigInteger(String.valueOf(input.charAt(0))));
					}
					else
					{
						primes.addPrime(new BigInteger(input.substring(0, input.length() - 1)));
					}
					input = "";
				}
			}
			
			// add the last prime to primes
			if(input.length() != 0 && isNumeric(input.charAt(0)))
			{
				primes.addPrime(new BigInteger(input));
			}

			// close the output file
			in.close();
		}catch(Exception IOException) {
			return false;
		}
	  
		// default return value
		return true;
	}
  
	// loads hexagon crosses from the file under the Config.DATAPATH directory
	// with the given file name into the given instance of Primes. Returns true
	// if the primes were loaded without issue and false in any other case.
	public static boolean loadCrosses(Primes primes, String filename)
	{
		// attempts to open the file and returns false if it is 
		// not found.
		FileReader in;
		File file;
		try {
			file = new File(Config.DATAPATH + filename);
			in = new FileReader(file.getAbsolutePath());
		} catch(Exception FileNotFoundException) {
			return false;
		}
	  
		// a few helpful variables for reading information
		// from a file
		String input = "";
		int c;
		int commaCount = 0;
		String left = "";
		String right = "";
		
		// ensures the program will not terminate if there is 
		// an error in reading from the file.
		try {
			// iterate through the characters in the file
			while((c = in.read()) != -1)
			{
				
				// add the characters to the input string
				input += (char)(c);
				
				// if the last char added was a comma, increase the comma count
				if(input.charAt(input.length() - 1) == ',')
				{
					commaCount += 1;
				}
				
				// when the comma count is 2 or the comma count is one and the last
				// character added was a newline.
				if((commaCount == 1 && input.charAt(input.length() - 1) == '\n') || commaCount == 2)
				{
					// reset the comma count and add the current hexagon cross to the 
					// given Primes instance
					commaCount = 0;
					
					// get the left hand string
					while(input.charAt(commaCount) != ',')
					{
						left += input.charAt(commaCount);
						commaCount++;
					}
					commaCount++;
					
					// get the right hand string
					while(commaCount < input.length() - 1)
					{
						right += input.charAt(commaCount);
						commaCount++;
					}
					
					// add the pair of strings and reset all variables to their defaults
					primes.addCross(new Pair<BigInteger>(new BigInteger(left),new BigInteger(right)));
					left = "";
					right = "";
					input = "";
					
					commaCount = 0;
				}
			}
			left = "";
			right = "";
			commaCount = 0;
			
			// adds the final string
			if(!input.equals(""))
			{		  
				// get left hand string
				while(input.charAt(commaCount) != ',')
				{
					left += input.charAt(commaCount);
					commaCount++;
				}
				commaCount++;
				
				// get right hand string
				while(commaCount < input.length())
				{
					right += input.charAt(commaCount);
					commaCount++;
				}
				
				primes.addCross(new Pair<BigInteger>(new BigInteger(left),new BigInteger(right)));
			}
			
			// close the input file
			in.close();
		}catch(Exception IOException) {
			return false;
		}
	  
		// default return value
		return true;
	}
  
	// saves the primes in the given instance of the Primes class to the file
	// in the Config.DATAPATH directory with the given filename. Creates the file
	// if it does not already exist. Returns True when successful and false if
	// any errors occurred.
	public static boolean savePrimes(Primes primes, String filename)
	{
		// attempts to open the output file and 
		// creates it if it does not exist.
		FileWriter out;
		File file;
		try {
			file = new File(Config.DATAPATH + filename);
			if(!file.exists()) {
				file.createNewFile();  
			}
			out = new FileWriter(file.getAbsolutePath());
		} catch(Exception FileNotFoundException) {	
			return false;
		}
	  
		// prevents the program from exiting if there is an 
		// issue in writing to the file.
		try {
			
			// for each prime in the primes instance
			for(BigInteger it : primes.iteratePrimes())
			{
				// write that prime to the file
				out.write(it.toString() + '\n');
			}
			
			// close output file
			out.close();
		} catch(Exception IOException) {	
			return false;
		}
		
		// default return case
		return true;
	}
  
	// saves the primes in the given instance of the Primes class to the file
	// in the Config.DATAPATH directory with the given filename. Creates the file
	// if it does not already exist. Returns True when successful and false if
	// any errors occurred.
	public static boolean saveCrosses(Primes primes, String filename)
	{
		// prevents the program from exiting if there is an 
		// issue in writing to the file.
		FileWriter out;
		File file;
		try {
			file = new File(Config.DATAPATH + filename);
			if(!file.exists()) {
				file.createNewFile();  
			}
			out = new FileWriter(file.getAbsolutePath());
		} catch(Exception FileNotFoundException) {
			return false;
		}
	  
		// prevents the program from exiting if there is an issue in writing to the file
		try {
			// for each cross in the primes instance
			for(Pair<BigInteger> it : primes.iterateCrosses())
			{
				// write that cross to the output file
				out.write(it.left().toString() + "," + it.right().toString() + '\n');
			}
			
			// close the output file
			out.close();
		} catch(Exception IOException) {
			return false;
		}
		
		// default return value
		return true;
	}
}
	
