package CA2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.*;
/**
 * David Kelly c00193216
 * Voting application - menu file open CCnational_candidates.txt
 * user enters name and voter id 
 * selects candidates from list boxes in order of preference - top three
 * specifies output file
 * appends to specified output file
 */

public class Voters extends JFrame implements ActionListener{

	private MenuBar menuBar; //Menu bar item
	private Menu file; //File menu item
	//File menu items
	private MenuItem openFile; //Open option - **use to open the file and populate the listbox(s)
	private MenuItem saveFile; //Save option
	private MenuItem close; //Close option
	private File input, output;
	//List Box
	private JComboBox  nameList1, nameList2, nameList3, partyOut;
	//TextBox
	private JTextField nameOut, voteIdOut, fileOut, totalPoll, spoiledPoll; 
	//Panels
	private JPanel p1, p2, p3;
	private JLabel name, voteId, candVote;
	//Buttons
	private JButton edit, vote;
	private int poll = 0;
	private int spoiled = 0;


	public Voters(){

		this.setSize(300, 250); //Initial size of the window frame
		this.setTitle("Voters I/O"); //Set title
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
		p1.setLayout(new GridLayout(2,2));
		//P2 holds textFields and labels
		p2 = new JPanel();
		p2.setLayout(new GridLayout(4,1));
		//P3 holds output controls
		p3 = new JPanel();
		p3.setLayout(new GridLayout(4,1));

		//Candidate listBox
		nameList1 = new JComboBox();
		nameList2 = new JComboBox();
		nameList3 = new JComboBox();
		//Political Party ListBox
		String[] party= {"FineGael","FiannaFail","SinnFein","Independent"}; //String array - political parties
		partyOut = new JComboBox(party);
		partyOut.setSelectedIndex(0); //Set listbox to first element

		nameOut = new JTextField(10);
		voteIdOut = new JTextField(10);

		fileOut = new JTextField(10);
		
		totalPoll = new JTextField(10);
		totalPoll.setText("Total Votes : ");
		spoiledPoll = new JTextField(10);
		spoiledPoll.setText("Total Spoiled Votes : ");
		fileOut.setText("Output File Name"); //TextField to specify output file name
		name = new JLabel("Voter Name : ");
		//date = new JLabel("Date Declared : ");
		voteId = new JLabel("Voter Id : ");
		candVote = new JLabel("Select in order of preference");

		vote = new JButton("Submit Vote");

		//Add comboBox/buttons to action listener
		this.nameList1.addActionListener(this);
		this.nameList2.addActionListener(this);
		this.nameList3.addActionListener(this);
		this.vote.addActionListener(this);

		//Add components to first panel
		p1.add(name);
		p1.add(nameOut);
		p1.add(voteId);
		p1.add(voteIdOut);

		//Add components to second panel
		p2.add(candVote);
		p2.add(nameList1);
		p2.add(nameList2);
		p2.add(nameList3);

		//Add to third panel
		p3.add(fileOut);
		p3.add(vote);
		p3.add(totalPoll);
		p3.add(spoiledPoll);

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
						nameList1.addItem(line); //Add each line to comboBox
						nameList2.addItem(line); //Add each line to comboBox
						nameList3.addItem(line); //Add each line to comboBox
					}
				} 
				catch (Exception ex) { // catch any exceptions
					// write to the debug console
					System.out.println(ex.getMessage());
				}
			}
		}
		else if(e.getSource() == vote){
			try {
				output = new File(fileOut.getText());
				PrintWriter pw = new PrintWriter(new FileWriter(output, true));
				String c1 = (String)nameList1.getSelectedItem();
				String c2 = (String)nameList2.getSelectedItem();
				String c3 = (String)nameList3.getSelectedItem();
				pw.println(nameOut.getText() + " " + voteIdOut.getText() + " " + c1 + " "  + c2 + " "  + c3);
				nameOut.setText("");
				voteIdOut.setText("");		
				pw.close();
				
			}
			catch (IOException e1){
				System.out.println(e1.getMessage());

			}
			finally{
				poll++; //increment the total votes - and output the total to screen
				String v = String.valueOf(poll);
				totalPoll.setText("Total Votes : " + v);
				
				//check spoiled votes - update total and output to screen
				if((nameList1.getSelectedItem() == nameList2.getSelectedItem()) || (nameList1.getSelectedItem() == nameList3.getSelectedItem()) || (nameList2.getSelectedItem() == nameList3.getSelectedItem()) ){
					spoiled++;
					String s = String.valueOf(spoiled);
					spoiledPoll.setText("Total Spoiled Votes : " + s);
				}
			}
		}
	}
	private class WindowCloser extends WindowAdapter{
		public void windowClosing(WindowEvent event){ //window closer sub class
			System.exit(0); //close window

		}

	}
}



