package CA2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.*;

/**
 * David Kelly c00193216
 * Candidate entry application - menu file (file chooser) open CCnational_candidates.txt
 * user enters name, date and address of each candidate
 * selects candidate party from list boxes
 * specifies output file
 * appends candidate information to specified output file
 */

public class Candidates extends JFrame implements ActionListener{
	
	private MenuBar menuBar; //Menu bar item
	private Menu file; //File menu item
	//File menu items
	private MenuItem openFile; //Open option - **use to open the file and populate the listbox
	private MenuItem saveFile; //Save option
	private MenuItem close; //Close option
	private File input, output;
	//List Box
	JComboBox  nameList, partyOut;
	//TextBox
	JTextField nameOut, dateOut, addressOut, fileOut; 
	//Panels
	JPanel p1, p2, p3;
	JLabel name, date, address, setOutput;
	//Buttons
	JButton edit, append;


	public Candidates(){
		
		this.setSize(300, 250); //Initial size of the window frame
		this.setTitle("Candidates I/O"); //Set title
		WindowCloser listener = new WindowCloser();
		addWindowListener(listener);

		this.getContentPane().setLayout(new BorderLayout()); 

		//MENU BAR********************************************
		// add menu bar
		menuBar = new MenuBar();
		file = new Menu();
		openFile = new MenuItem();
		saveFile = new MenuItem();
		close = new MenuItem();
		this.setMenuBar(menuBar);
		this.menuBar.add(file); 
		file.setLabel("File");
		// OPEN option
		this.openFile.setLabel("Open"); // set  label of the menu item
		this.openFile.addActionListener(this); // add  action listener 
		this.file.add(this.openFile); // add to the "File" menu
		// SAVE option
		this.saveFile.setLabel("Save");
		this.saveFile.addActionListener(this);
		this.file.add(this.saveFile);
		// CLOSE option
		this.close.setLabel("Close");
		this.close.addActionListener(this);
		this.file.add(this.close);
		//Panels
		//P1 holds comboBox and edit button
		p1 = new JPanel();
		p1.setLayout(new GridLayout(2,1));
		//P2 holds textFields and labels
		p2 = new JPanel();
		p2.setLayout(new GridLayout(3,2));
		//P3 holds output controls
		p3 = new JPanel();
		p3.setLayout(new GridLayout(3,1));
		
		//Candidate listBox
		nameList = new JComboBox();
		//Political Party ListBox
		String[] party= {"FineGael","FiannaFail","SinnFein","Independent"}; //String array - political parties
		partyOut = new JComboBox(party);
		partyOut.setSelectedIndex(0); //Set listbox to first element
		
		nameOut = new JTextField(10);
		dateOut = new JTextField(10);
		addressOut = new JTextField(10);
		fileOut = new JTextField(5);
		fileOut.setText("Output File Name"); //TextField to specify output file name
		name = new JLabel("Candidate Name : ");
		date = new JLabel("Date Declared : ");
		address = new JLabel("Address : ");
		edit = new JButton("Edit Profile");
		append = new JButton("Add to File");
		
		//Add comboBox/buttons to action listener
		this.nameList.addActionListener(this);
		this.partyOut.addActionListener(this);
		this.edit.addActionListener(this);
		this.append.addActionListener(this);
		
		//Add components to first panel
		p1.add(nameList);
		p1.add(edit);
		
		//Add components to second panel
		p2.add(name);
		p2.add(nameOut);
		p2.add(date);
		p2.add(dateOut);
		p2.add(address);
		p2.add(addressOut);
		
		//Add to third panel
		p3.add(partyOut);
		p3.add(fileOut);
		p3.add(append);
		
		//Add panels to Frame
		this.add(p1, "North");
		this.add(p2, "Center");
		this.add(p3, "South");
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.openFile) {
			JFileChooser open = new JFileChooser(); // open file chooser
			int option = open.showOpenDialog(this); // get the option user selected
				if (option == JFileChooser.APPROVE_OPTION) {
					try {
						// create scanner instance to read chosen file
						Scanner input = new Scanner(new FileReader(open.getSelectedFile().getPath()));
						
						while (input.hasNextLine()){ //while there's something to read
							String line = input.nextLine(); //Assign line to String variable "line"
							nameList.addItem(line); //Add each line to comboBox
						}
					} 
					catch (Exception ex) { // catch any exceptions
							// write to the debug console
							System.out.println(ex.getMessage());
						}
				}
			}
		else if(e.getSource() == edit){
			String n = (String)nameList.getSelectedItem();
			StringTokenizer tokenizer = new StringTokenizer(n); //to specify delimiter: (String n, String delim) in constructor
			nameOut.setText(n);
			while(tokenizer.hasMoreTokens()){
				nameOut.setText(tokenizer.nextToken());
				dateOut.setText(tokenizer.nextToken());
				addressOut.setText(tokenizer.nextToken());
				tokenizer.nextToken();
				//partyOut.setSelectedIndex(tokenizer.nextToken()); //Set listbox to first element
				
			}
		}
		else if(e.getSource() == append){
			try {
				output = new File(fileOut.getText());
				PrintWriter pw = new PrintWriter(new FileWriter(output, true));
				String p = (String)partyOut.getSelectedItem();
				pw.println(nameOut.getText() + " " + dateOut.getText() + " " + addressOut.getText() + " "  + p );
				nameOut.setText("");
				dateOut.setText("");
				addressOut.setText("");
				
				pw.close();
			}
			catch (IOException e1){
				System.out.println(e1.getMessage());

			}
		}
	}
	private class WindowCloser extends WindowAdapter{
		public void windowClosing(WindowEvent event){ //window closer sub class
			System.exit(0); //close window
			
		}

	}
}



