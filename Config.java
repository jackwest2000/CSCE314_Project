// This class is for all our configuration data. By putting it all in one place, we can easily make changes to the program without having to hunt down where in the code a constant is defined.

public class Config {
  public final static String DATAPATH = "data/"; // If you don't know what the static keyword does, you better go look it up now.
  public final static String APPLICATIONNAME = "Prime Wizard"; // name of the application
  public final static int primesPerLine = 1; // each number in a line is separated by a comma
  public final static int crossesPerLine = 1; // each number in a line is separated by a comma (for crosses, the first two make up the first cross, the next two are the next cross and so on)
  public final static String defaultPrimesFile = "primes.txt"; // default name for the primes file
  public final static String defaultCrossesFile = "crosses.txt"; // default name for the crosses file
}
