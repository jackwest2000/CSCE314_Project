import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.math.BigInteger;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import java.io.IOException;

public class MainWindow extends JFrame
{
	private static final long serialVersionUID = -3880026026104218593L;
	private Primes m_Primes;
	private JTextField tfPrimeFileName;
	private JTextField tfCrossFileName;
	private JLabel lblPrimeCount;
	private JLabel lblCrossCount;
	private JLabel lblLargestPrime;
	private JLabel lblLargestCross;
	private JLabel lblStatus;
	
	// Constructor which opens a main window for the application named (name)
	// using the given instance of the primes class. This application is
	// used to generate prime numbers and hexagon crosses.
	MainWindow(String name, Primes p)
	{
		// sets the associated Primes instance to the given instance
		m_Primes = p;
		
		// creates a new dialog box of size 1000x400 and with the 
		// TAMU maroon background color
		JDialog window = new JDialog(this, name);
		window.getContentPane().setBackground(new Color(52, 0, 0));
		window.setSize(1000,400);
		window.getContentPane().setLayout(new GridBagLayout());
		
		// sets the layout that will be used to position items within
		// the dialog box
		GridBagConstraints gbcDialog = new GridBagConstraints();
		gbcDialog.fill = GridBagConstraints.HORIZONTAL;
		gbcDialog.anchor = GridBagConstraints.WEST;
		gbcDialog.ipady = 10;
		gbcDialog.weightx = .5;
		gbcDialog.insets = new Insets(1,2,0,0);
		gbcDialog.gridx = 0;
		gbcDialog.gridy = 0;
		
		// sets the layout that will be used to position items within
		// each individual panel on the dialog box
		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.anchor = GridBagConstraints.WEST;
		gbcPanel.weightx = .5;
		gbcPanel.insets = new Insets(1,2,0,0);
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		
		// creates a new panel located at the bottom of the dialog box
		// that will keep the status of the application
		JPanel Status = new JPanel();
		Status.setLayout(new GridBagLayout());
		gbcDialog.gridy = -4;
		lblStatus = new JLabel("Status: Bored.");
		Status.add(lblStatus, gbcPanel);
		
		// add the status panel to the dialog box
		window.add(Status, gbcDialog);
		gbcDialog.gridy = 0;
		
		// keeps track of the current panel being worked with
		// will be updated as new panel are added
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new GridBagLayout());
		
		// creates a new label to add within the current panel
		JLabel lblPrimesFile = new JLabel("Primes File");
		lblPrimesFile.setFont(new Font("Tahoma", Font.PLAIN, 24));
		gbcPanel.gridy = 1;
		// add the label to the current panel
		newPanel.add(lblPrimesFile, gbcPanel);
		
		// sets the default values for the text field containing
		// the name of the prime file from which to read/write
		tfPrimeFileName = new JTextField();
		lblPrimesFile.setLabelFor(tfPrimeFileName);
		tfPrimeFileName.setColumns(76);
		tfPrimeFileName.setText(Config.defaultPrimesFile);
		gbcPanel.gridy = 0;
		
		// adds the textfield to the current panel and the 
		// current panel to the dialog box
		newPanel.add(tfPrimeFileName, gbcPanel);
		window.add(newPanel, gbcDialog);
		
		// creates the label that will give the number of primes currently held
		// within the associated instance of primes.
		lblPrimeCount = new JLabel(String.valueOf(m_Primes.primeCount()));
		lblPrimeCount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gbcPanel.gridy = 0;
		gbcPanel.gridx = 1;
		gbcPanel.anchor = GridBagConstraints.EAST;
		
		// adds this label to the current panel
		newPanel.add(lblPrimeCount, gbcPanel);
		
		// creates the button used to load primes from the 
		// file in Config.DATAPATH with the file name in
		// the text field tfPrimeFileName
		JButton btnLoadPrimes = new JButton("Load");
		
		// adds an action to be performed when the button is pressed
		btnLoadPrimes.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	try
      	{
      		// gets the text from the text field
      		String FileName = tfPrimeFileName.getText();
      		
      		// loads the primes and throws an exception if there
      		// was an error
       		if(!FileAccess.loadPrimes(m_Primes, FileName))
       		{
       			throw new IOException();
       		}
       		
       		// update the status and stats
       		lblStatus.setText("Status: Excited. Primes have been loaded.");
       		updateStats();
      	}
      	catch(Exception IOException)
      	{
      		// update the status when there is an error
      		lblStatus.setText("Status: Error in loading primes. Ensure the file is in the " + Config.DATAPATH + " directory.");
      	}
      	
      }
    });
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 1;
		
		// adds the button to the current panel
		newPanel.add(btnLoadPrimes, gbcPanel);
		gbcDialog.gridx = 1;
		gbcDialog.gridy = 0;
		
		// creates the button used to save primes from the 
		// file in Config.DATAPATH with the file name in
		// the text field tfPrimeFileName
		JButton btnSavePrimes = new JButton("Save");
		
		// adds an action to be performed when the button is pressed
		btnSavePrimes.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	try
      	{
      		// gets the text from the associated text field
      		String FileName = tfPrimeFileName.getText();
      		
      		// saves the primes and throws an exception if there was 
      		// an error
       		if(!FileAccess.savePrimes(m_Primes, FileName))
       		{
       			throw new IOException();
       		}

       		// update the status and the stats
       		lblStatus.setText("Status: Excited. Primes have been saved.");
       		updateStats();
      	}
      	catch(Exception IOException)
      	{
      		// updates the status in the event of an error
      		lblStatus.setText("Status: There was an unidentified error in saving the primes to the given file.");
      	}
      	
      }
    });
		gbcPanel.gridx = 2;
		gbcPanel.gridy = 1;
		
		// adds the button to the current panel
		newPanel.add(btnSavePrimes, gbcPanel);
		
		// creates a new panel to add to the dialog box
		newPanel = new JPanel();
		newPanel.setLayout(new GridBagLayout());
		
		// reset panel layout
		gbcPanel.anchor = GridBagConstraints.WEST;
		gbcPanel.weightx = .5;
		gbcPanel.insets = new Insets(1,2,0,0);
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		
		// new label for the text field for the hexagon cross file name
		JLabel lblCrossFile = new JLabel("Hexagon Cross File");
		lblCrossFile.setFont(new Font("Tahoma", Font.PLAIN, 24));
		gbcPanel.gridy = 1;
		gbcPanel.anchor = GridBagConstraints.WEST;
		newPanel.add(lblCrossFile, gbcPanel);
		
		// Creates the text field for the hexagon cross file name
		tfCrossFileName = new JTextField();
		lblCrossFile.setLabelFor(tfCrossFileName);
		tfCrossFileName.setColumns(76);
		tfCrossFileName.setText(Config.defaultCrossesFile);
		gbcPanel.gridy = 0;
		newPanel.add(tfCrossFileName, gbcPanel);
		
		// add the new panel to the dialog box
		gbcDialog.gridx = 0;
		gbcDialog.gridy = 1;
		window.add(newPanel, gbcDialog);
		
		// label that gives the number of hexagon crosses in the Primes instance
		lblCrossCount = new JLabel(String.valueOf(m_Primes.crossesCount()));
		lblPrimeCount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gbcPanel.gridy = 0;
		gbcPanel.gridx = 1;
		gbcPanel.anchor = GridBagConstraints.EAST;
		newPanel.add(lblCrossCount, gbcPanel);
		
		
		// Creates new button to load hexagon crosses 
		// from a file indicated in the associated text field
		JButton btnLoadCrosses = new JButton("Load");
		
		// adds an action to be performed when the button is pressed
		btnLoadCrosses.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	try
      	{
      		// gets the string from the text field
      		String FileName = tfCrossFileName.getText();
      		
      		// attempts to open the file in Config.DATAPATH with the 
      		// filename taken from the text field
       		if(!FileAccess.loadCrosses(m_Primes, FileName))
       		{
       			throw new IOException();
       		}
       		
       		// updates the status and stats on a success
       		lblStatus.setText("Status: Excited. Hexagon crosses have been loaded.");
       		updateStats();
      	}
      	catch(Exception IOException)
      	{
      		lblStatus.setText("Status: Error in loading hexagon crosses. Ensure the file is in the " + Config.DATAPATH + " directory.");
      	}
      	
      }
    });
		// add the button to the panel
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 1;
		newPanel.add(btnLoadCrosses, gbcPanel);
		gbcDialog.gridx = 1;
		gbcDialog.gridy = 0;
		
		// creates button to save crosses to the indicated file
		// from the associated Primes instance
		JButton btnSaveCrosses = new JButton("Save");
		
		// adds an action to be performed when the button is pressed
		btnSaveCrosses.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	try
      	{
      		// get the file name from the associated text field
      		String FileName = tfCrossFileName.getText();
      		
      		// attempts to open the file and save the crosses
       		if(!FileAccess.saveCrosses(m_Primes, FileName))
       		{
       			throw new IOException();
       		}
       		
       		// updates status and stats on a success
       		lblStatus.setText("Status: Excited. Hexagon crosses have been saved.");
       		updateStats();
      	}
      	catch(Exception IOException)
      	{
      		// updates status in the event of a failure
      		lblStatus.setText("Status: There was an unidentified error in saving the crosses.");
      	}
      	
      }
    });
		// add the button to the panel
		gbcPanel.gridx = 2;
		gbcPanel.gridy = 1;
		newPanel.add(btnSaveCrosses, gbcPanel);
		
		// reset the panel layout
		gbcPanel.anchor = GridBagConstraints.WEST;
		gbcPanel.weightx = .5;
		gbcPanel.insets = new Insets(1,2,0,0);
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		
		// creates a new panel
		newPanel = new JPanel();
		newPanel.setLayout(new GridBagLayout());
		
		// add a button to bring up a popup to generate primes
		JButton btnGeneratePrimes = new JButton("Generate Primes");
		
		// adds an action to be performed when the button is pressed
		btnGeneratePrimes.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	try
      	{
      		// creates the popup to generate prime numbers
      		popupGeneratePrimes();
      	}
      	catch(Exception IOException)
      	{
      		// update the status in the event of a failure
      		lblStatus.setText("Status: Unidentified error in generating primes.");
      	}
      	
      }
    });
		// add the button to the panel
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		gbcPanel.anchor = GridBagConstraints.WEST;
		newPanel.add(btnGeneratePrimes, gbcPanel);
		gbcDialog.gridx = 1;
		gbcDialog.gridy = 0;
		
		// create the button to generate hexagon crosses
		JButton btnGenerateCrosses = new JButton("Generate Crosses");
		
		// adds an action to be performed when the button is pressed
		btnGenerateCrosses.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	try
      	{
      		// generate hexagon crosses from available primes
      		GenerateCrosses();
      	}
      	catch(Exception IOException)
      	{
      		// updates the status in the event of a failure
      		lblStatus.setText("Status: Unidentified error in generating hexagon crosses.");
      	}
      	
      }
    });
		// adds the button to the panel
		gbcPanel.gridx = 2;
		gbcPanel.gridy = 0;
		gbcPanel.anchor = GridBagConstraints.EAST;
		newPanel.add(btnGenerateCrosses, gbcPanel);
		
		// adds the panel to the dialog box
		gbcDialog.gridx = 0;
		gbcDialog.gridy = -1;
		window.add(newPanel,gbcDialog);
		
		// creates the label that gives information on the largest prime in the list
		lblLargestPrime = new JLabel("The largest prime has " + m_Primes.sizeofLastPrime() + " digits.");
		gbcPanel.gridx = 1;
		gbcPanel.gridy = 0;
		gbcPanel.anchor = GridBagConstraints.CENTER;
		newPanel.add(lblLargestPrime, gbcPanel);
		
		// creates the label that gives information on the largest hexagon cross
		lblLargestCross = new JLabel("The largest cross has " + m_Primes.sizeofLastCross().left() + " and " + m_Primes.sizeofLastCross().right() + " digits.");
		gbcPanel.gridx = 1;
		gbcPanel.gridy = -1;
		newPanel.add(lblLargestCross, gbcPanel);
		
		// sets the size of the window to fit the size of the items within the dialog box
		window.pack();
		
		// allows the user to see the dialog box
		window.setVisible(true);
	}

	protected void popupGeneratePrimes()
	{
		// creates the dialog box for the popup to generate primes
		JDialog dPrimes = new JDialog(this, "Prime Number Generation");
		GridBagLayout gridLayout = new GridBagLayout();
		dPrimes.getContentPane().setBackground(new Color(52, 0, 0));
		dPrimes.getContentPane().setLayout(gridLayout);
		
		// creates the layout for panels within the dialog box
		GridBagConstraints gbcDialog = new GridBagConstraints();
		gbcDialog.fill = GridBagConstraints.HORIZONTAL;
		gbcDialog.anchor = GridBagConstraints.WEST;
		gbcDialog.ipady = 10;
		gbcDialog.weightx = .5;
		gbcDialog.insets = new Insets(1,2,0,0);
		gbcDialog.gridx = 0;
		gbcDialog.gridy = 0;
		
		// creates the layout for items within the panels
		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.anchor = GridBagConstraints.WEST;
		gbcPanel.weightx = .5;
		gbcPanel.insets = new Insets(1,2,0,0);
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		
		// creates a new panel
		JPanel pnlGenerate = new JPanel();
		pnlGenerate.setLayout(new GridBagLayout());
		
		// creates a label specifying the number of primes to generate
		JLabel lblCount = new JLabel("Number of Primes to Generate");
		lblCount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlGenerate.add(lblCount, gbcPanel);
		
		// creates the text field to enter the number of primes to generate
		JTextField tfCount = new JTextField();
		lblCount.setLabelFor(tfCount);
		tfCount.setColumns(30);
		gbcPanel.gridx = 1;
		pnlGenerate.add(tfCount, gbcPanel);
		
		// creates a label specifying the number to start generating primes from
		JLabel lblStart = new JLabel("Starting Number (does not have to be prime)");
		lblStart.setFont(new Font("Tahoma", Font.PLAIN, 12));
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 1;
		pnlGenerate.add(lblStart, gbcPanel);
		
		// creates the text field in which to enter the starting number
		JTextField tfStart = new JTextField();
		lblStart.setLabelFor(tfStart);
		tfStart.setColumns(30);
		gbcPanel.gridx = 1;
		pnlGenerate.add(tfStart, gbcPanel);
		
		// adds the panel to the dialog box
		dPrimes.add(pnlGenerate, gbcDialog);
		
		// create a panel with buttons to generate primes
		JPanel pnlButtons = new JPanel();
		pnlButtons.setLayout(new GridBagLayout());
		
		// creates a button to generate primes
		JButton btnGeneratePrimes = new JButton("Generate Primes");
		btnGeneratePrimes.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	try
      	{
      		// generates prime numbers starting from the given integer
      		BigInteger start = new BigInteger(tfStart.getText());
      		int count = Integer.parseInt(tfCount.getText());
       		m_Primes.generatePrimes(start, count);
       		
       		// updates the status and stats on success
       		lblStatus.setText("Status: Excited. Primes have been generated.");
       		updateStats();
       		
       		// closes the dialog box to generate primes
      		dPrimes.dispose();
      	}
      	catch(NumberFormatException ex)
      	{
      		// indicates an error occurred
      		lblStatus.setText("You failed to type in an integer successfully. You are terrible at math. Shame.");
  
      		// closes the dialog box
      		dPrimes.dispose();
      	}
      	
      }
    });
		// adds the button to the panel
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 0;
		pnlButtons.add(btnGeneratePrimes, gbcPanel);
		
		// creates button to cancel prime generation
		JButton btnCancel = new JButton("Cancel Generation");
		btnCancel.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// closes the dialog box when the button is pressed
			dPrimes.dispose();
      }
    });
		// add the button to the panel
		gbcPanel.anchor = GridBagConstraints.EAST;
		gbcPanel.gridx = 1;
		pnlButtons.add(btnCancel, gbcPanel);		
		
		// add the panel to the dialog box
		gbcDialog.gridy = 1;
		dPrimes.add(pnlButtons, gbcDialog);
		
		// create a new panel to house a warning
		JPanel pnlStatus = new JPanel();
		pnlStatus.setLayout(new GridBagLayout());

		gbcPanel.anchor = GridBagConstraints.SOUTHWEST;
		gbcPanel.weightx = .5;
		gbcPanel.insets = new Insets(1,2,0,0);
		gbcPanel.gridx = 0;
		gbcPanel.gridy = 1;

		// create a label to indicate the application will freeze during generation
		JLabel lblNotice = new JLabel("Warning: This application is single threaded, and will freeze while generating primes.");
		lblNotice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pnlStatus.add(lblNotice, gbcPanel);
		
		gbcDialog.gridy = 2;
		dPrimes.add(pnlStatus, gbcDialog);
		
		// sets the dialog box size 
		dPrimes.setSize(200,200);
		
		// changes the size of the dialog box to fit the items within
		dPrimes.pack(); 
		
		// lets the user see the dialog box
		dPrimes.setVisible(true);		
	}
	
	protected void GenerateCrosses()
	{
		// generates twin primes from the available primes used to find hexagon crosses
		m_Primes.generateTwinPrimes();
		
		// generates hexagon crosses from the available twin primes
		m_Primes.generateHexPrimes();
		
		// updates the stats
		updateStats();
		if(m_Primes.primeCount() == 0)
		{
			// if there were no primes, tells the user that primes must be generated to find hexagon crosses
			lblStatus.setText("Status: You must generate or load primes before generating hexagon crosses.");
		}
	}

	// This function updates all the GUI statistics. (# of primes, # of crosses, etc)
	private void updateStats()
	{
		// updates the number of primes and crosses currently loaded
		lblPrimeCount.setText(String.valueOf(m_Primes.primeCount()));
		lblCrossCount.setText(String.valueOf(m_Primes.crossesCount()));
		
		// updates the size of the largest prime and the size of the largest hexagon cross
		lblLargestPrime.setText("The largest prime has " + String.valueOf(m_Primes.sizeofLastPrime()) + " digits.");
		lblLargestCross.setText("The largest cross has " + String.valueOf(m_Primes.sizeofLastCross().left()) + " and " + String.valueOf(m_Primes.sizeofLastCross().right()) + " digits.");
 	}

}
