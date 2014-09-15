package myJFrameTest;

//import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;
//import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JOptionPane;
//import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.SwingConstants;

public class CardLayoutTest2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_firstname_newuser;
	private JTextField tf_lastname_newuser;
	final static String WELCOMEPANEL = "Welcome Panel";
	final static String NEWUSERPANEL = "New User Panel";
	final static String OLDUSERPANEL = "Returning User Panel";
	final static String NEWUSERADDED = "New User Added Panel";
	final static String LOGGEDIN = "Old User Logged In Panel";
	private JTextField tf_idnumber_olduser;
	private JTextField tf_firstname_olduser;
	private JTextField tf_lastname_olduser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardLayoutTest2 frame = new CardLayoutTest2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String findWhoIsLoggedIn(Connection conn, int index) throws SQLException{
		String namecomponent = null;
		if (index==1){
			String firstname = database_access2.getFirstName(conn, index);
			namecomponent = firstname; 
		}
		if (index==2){
			String lastname;
			lastname = database_access2.getLastName(conn, index);
			namecomponent = lastname;
		}
		return namecomponent;
	}
	

	/**
	 * Create the frame.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public CardLayoutTest2() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		Connection conn =  database_access2.openConnection();
		String loggedInUser = null;
		int loggedInUserInt = 0;
		
		JPanel card1 = new JPanel();
		JPanel card2 = new JPanel();
		JPanel card3 = new JPanel();
		JPanel card4 = new JPanel();
		JPanel card5 = new JPanel();
		
		contentPane.add(card1, WELCOMEPANEL);
		contentPane.add(card2, NEWUSERPANEL);
		contentPane.add(card3, OLDUSERPANEL);
		contentPane.add(card4, NEWUSERADDED);
		contentPane.add(card5, LOGGEDIN);
		card1.setLayout(null);
		card2.setLayout(null);
		card3.setLayout(null);
		card4.setLayout(null);
		card5.setLayout(null);
		
		// CARD 5: LOGGEDIN
		JLabel lblYouHaveBeen_1 = new JLabel("You have been logged in as: ");
		lblYouHaveBeen_1.setBounds(123, 45, 175, 16);
		card5.add(lblYouHaveBeen_1);
		
		String firstname_loggedin = database_access2.getFirstName(conn, loggedInUserInt);
		String lastname_loggedin = database_access2.getLastName(conn, loggedInUserInt);
		JLabel lblOldUsersName = new JLabel(firstname_loggedin + " " + lastname_loggedin);//(firstName + " " + lastName);
		lblOldUsersName.setHorizontalAlignment(SwingConstants.CENTER);
		lblOldUsersName.setBounds(143, 84, 135, 16);
		card5.add(lblOldUsersName);
		
		JLabel lblUserId = new JLabel("User ID: " + loggedInUser);
		lblUserId.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserId.setBounds(154, 113, 114, 16);
		card5.add(lblUserId);
		
		JButton btnContinue_1 = new JButton("Continue");
		btnContinue_1.setBounds(154, 155, 113, 37);
		card5.add(btnContinue_1);
		
		JButton btnThisIsntMe = new JButton("This isn't me");
		btnThisIsntMe.setBounds(158, 205, 105, 25);
		card5.add(btnThisIsntMe);
		
		//CARD 4: NEWUSERADDED
		int id = database_access2.getMaxCustID(conn)+1;
		//String s1 = "You, " + database_access2.getFirstName(conn, id) + " " + database_access2.getLastName(conn, id) + ", have been successfully added to the database.";
		String s2 = "Your user ID is: " + id;
		
		JLabel lblYouHaveBeen = new JLabel("You have been added to the database");
		lblYouHaveBeen.setHorizontalAlignment(SwingConstants.CENTER);
		lblYouHaveBeen.setBounds(35, 68, 351, 16);
		card4.add(lblYouHaveBeen);
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.setBounds(162, 173, 97, 25);
		card4.add(btnContinue);
		
		JLabel lblNewLabel = new JLabel(s2);
		lblNewLabel.setBounds(151, 121, 119, 16);
		card4.add(lblNewLabel);
		
		//CARD 3: OLDUSERPANEL
		JLabel lblIfYouKnow = new JLabel("If you know your ID number, enter it below:");
		lblIfYouKnow.setBounds(12, 30, 271, 16);
		card3.add(lblIfYouKnow);
		
		tf_idnumber_olduser = new JTextField(); //Old User has entered his or her ID number in this text box
		tf_idnumber_olduser.setBounds(125, 56, 116, 22);
		card3.add(tf_idnumber_olduser);
		tf_idnumber_olduser.setColumns(10);
		loggedInUser = tf_idnumber_olduser.getText();
		if (loggedInUser.length() != 0){
			loggedInUserInt = Integer.parseInt(loggedInUser);
		}
		
		JLabel lblIdNumber = new JLabel("ID Number:");
		lblIdNumber.setBounds(22, 59, 91, 16);
		card3.add(lblIdNumber);
		
		JLabel lblOtherwiseEnterYour = new JLabel("Otherwise, enter your first and last name below:");
		lblOtherwiseEnterYour.setBounds(12, 102, 298, 16);
		card3.add(lblOtherwiseEnterYour);
		
		JButton btnSubmitIdNumber = new JButton("Submit ID Number");
		btnSubmitIdNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {//Old user has submitted his or her ID number
				String loggedInUser2 = tf_idnumber_olduser.getText();
				int loggedInUserInt2 = Integer.parseInt(loggedInUser2);
				if (loggedInUser2.length() == 0){
					//Error Handling
					JOptionPane.showMessageDialog(card2, "ID field has been left blank.  Please fill in your ID.");
					CardLayout cl = (CardLayout)(contentPane.getLayout());
					cl.show(contentPane,OLDUSERPANEL);
				}
				else{
					try {
						int item = database_access2.checkID(conn, loggedInUserInt2);
						System.out.println(item);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					CardLayout cl = (CardLayout)(contentPane.getLayout());
					cl.show(contentPane,LOGGEDIN);
				}
			}
		});
		btnSubmitIdNumber.setBounds(273, 53, 137, 25);
		card3.add(btnSubmitIdNumber);
		
		tf_firstname_olduser = new JTextField();
		tf_firstname_olduser.setBounds(125, 133, 116, 22);
		card3.add(tf_firstname_olduser);
		tf_firstname_olduser.setColumns(10);
		
		tf_lastname_olduser = new JTextField();
		tf_lastname_olduser.setBounds(125, 171, 116, 22);
		card3.add(tf_lastname_olduser);
		tf_lastname_olduser.setColumns(10);
		
		JLabel lblFirstName_1 = new JLabel("First Name:");
		lblFirstName_1.setBounds(22, 136, 91, 16);
		card3.add(lblFirstName_1);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(22, 174, 75, 16);
		card3.add(lblLastName);
		
		JButton btnSubmitName = new JButton("Submit Name");
		btnSubmitName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // Old user has submitted his or her name
				String firstName = tf_firstname_olduser.getText();
				String lastName = tf_lastname_olduser.getText();
				if (firstName.length()==0 || lastName.length()==0){//If the user has not filled in a first or last name
					//Error Handling
					JOptionPane.showMessageDialog(card2, "One or more name fields has been left blank.  Please fill in both first and last name fields.");
					CardLayout cl = (CardLayout)(contentPane.getLayout());
					cl.show(contentPane,OLDUSERPANEL);
				}
				else{
					int temporarycustID = 0;
					try {
						temporarycustID = database_access2.getID(conn, firstName, lastName);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if (temporarycustID!=0){
						System.out.println(temporarycustID);
						CardLayout cl = (CardLayout)(contentPane.getLayout());
						cl.show(contentPane,LOGGEDIN);
					}
					else{
						JOptionPane.showMessageDialog(card2, "Your name could not be found in the database, please try entering your informaiton again");
						CardLayout cl = (CardLayout)(contentPane.getLayout());
						cl.show(contentPane,OLDUSERPANEL);
					}
				}
			}
		});
		btnSubmitName.setBounds(273, 170, 137, 25);
		card3.add(btnSubmitName);
		
		// CARD 2: NEWUSERPANEL
		JButton btnSubmit = new JButton("Submit"); // New user submits name
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstName = tf_firstname_newuser.getText();
				String lastName = tf_lastname_newuser.getText();
				if (firstName.length()==0 || lastName.length()==0){//If the user has not filled in a first or last name
					//Error Handling
					JOptionPane.showMessageDialog(card2, "One or more name fields has been left blank.  Please fill in both first and last name fields.");
					CardLayout cl = (CardLayout)(contentPane.getLayout());
					cl.show(contentPane,NEWUSERPANEL);
				}
				else{
					try {
						int maxID = database_access2.getMaxCustID(conn);
						int newID = maxID + 1;
						database_access2.newEntry(conn,newID,firstName,lastName);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					CardLayout cl = (CardLayout)(contentPane.getLayout());
					cl.show(contentPane,NEWUSERADDED);
				};
			}
		});
		btnSubmit.setBounds(151, 187, 97, 25);
		card2.add(btnSubmit);
		
		tf_firstname_newuser = new JTextField(); //First Name new user input text field
		tf_firstname_newuser.setBounds(185, 95, 116, 22);
		card2.add(tf_firstname_newuser);
		tf_firstname_newuser.setColumns(10);
		
		tf_lastname_newuser = new JTextField(); //Last Name new user input text field
		tf_lastname_newuser.setBounds(185, 130, 116, 22);
		card2.add(tf_lastname_newuser);
		tf_lastname_newuser.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(98, 98, 75, 16);
		card2.add(lblFirstName);
		
		JLabel lblLastName_1 = new JLabel("Last Name:");
		lblLastName_1.setBounds(98, 133, 75, 16);
		card2.add(lblLastName_1);
		
		JLabel lblPleaseEnterYour = new JLabel("Please enter your information:");
		lblPleaseEnterYour.setBounds(98, 49, 203, 16);
		card2.add(lblPleaseEnterYour);
		
		//CARD 1: WELCOMEPANEL
		JButton btnIAmA = new JButton("I am a new user");
		btnIAmA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(contentPane.getLayout());
				cl.show(contentPane,NEWUSERPANEL);
			}
		});
		btnIAmA.setBounds(134, 98, 153, 25);
		card1.add(btnIAmA);
		
		JButton btnIAlreadyHave = new JButton("I already have an account");
		btnIAlreadyHave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(contentPane.getLayout());
				cl.show(contentPane,OLDUSERPANEL);
			}
		});
		btnIAlreadyHave.setBounds(121, 136, 179, 25);
		card1.add(btnIAlreadyHave);
		
		JLabel lblWelcomeToThe = new JLabel("Welcome to the facial expression decoder system");
		lblWelcomeToThe.setBounds(71, 43, 291, 25);
		card1.add(lblWelcomeToThe);
	}
}
