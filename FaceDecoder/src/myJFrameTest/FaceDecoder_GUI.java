package myJFrameTest;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;
import java.awt.Panel;

import javax.swing.*;

import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;

public class FaceDecoder_GUI {

	private static final long serialVersionUID = 1L;
	private JPanel basePane;
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
	private WelcomePanel panel1;
	private NewUserPanel panel2;
	private OldUserPanel panel3;
	private NewUserAdded panel4;
	private LoggedIn panel5;
	private Connection myconn;
	private int currentID;
	private String currentIDstr;
	private String currentFirstName;
	private String currentLastName;
/*	private String [] choices = {"Welcome Panel", "New User Panel", "Returning User Panel"};*/


	public static void main(String[] args) throws SQLException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new FaceDecoder_GUI().initializeGUI();
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
	
	class WelcomePanel extends JPanel{

		private static final long serialVersionUID = 3051031197269645226L;
		private JButton jcomp4;
		private JPanel basePane;
		private JComboBox choiceBox;

		public WelcomePanel(JPanel panel, FaceDecoder_GUI fdg) {

			//JPanel card1_welcomepanel = new JPanel();
			setLayout(null);
			//CARD 1: WELCOMEPANEL
/*			fdg.getChoiceBox();*/
			basePane = panel;
			setOpaque(true);
	        
			JButton btnIAmA = new JButton("I am a new user");
			btnIAmA.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout)(basePane.getLayout());
					cl.show(basePane,NEWUSERPANEL);
				}
			});

			btnIAmA.setBounds(134, 98, 153, 25);
			add(btnIAmA);

			JButton btnIAlreadyHave = new JButton("I already have an account");
			btnIAlreadyHave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CardLayout cl = (CardLayout)(basePane.getLayout());
					cl.show(basePane,OLDUSERPANEL);
				}
			});
			btnIAlreadyHave.setBounds(121, 136, 179, 25);
			add(btnIAlreadyHave);
			
			JLabel lblWelcomeToThe = new JLabel("Welcome to the facial expression decoder system");
			lblWelcomeToThe.setBounds(71, 43, 291, 25);
			add(lblWelcomeToThe);
		}
		
	    @Override
	    public Dimension getPreferredSize()
	    {
	        return (new Dimension(500, 500));
	    }
	}
	
	public class ConnectionToDatabase {
		public Connection conn;
		
		public ConnectionToDatabase() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			conn =  database_access2.openConnection();
		}
		
		public Connection getConnection(){
			return conn;
		}
		
		public void closeConnection(){
			if (conn!=null){
				try {
					conn.close();
				} catch (SQLException ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
	class NewUserPanel extends JPanel{

		private static final long serialVersionUID = -6992458216831520029L;

		public NewUserPanel(JPanel panel, FaceDecoder_GUI fdg){
			basePane = panel;
			Connection conn = myconn;
/*			JPanel card2_newuserpanel = new JPanel();*/
			setLayout(null);
			
			// CARD 2: NEWUSERPANEL
			JButton btnSubmit = new JButton("Submit"); // New user submits name
			btnSubmit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					currentFirstName = tf_firstname_newuser.getText();
					currentLastName = tf_lastname_newuser.getText();
					if (currentFirstName.length()==0 || currentLastName.length()==0){//If the user has not filled in a first or last name
						//Error Handling
						JOptionPane.showMessageDialog(basePane, "One or more name fields has been left blank.  Please fill in both first and last name fields.");
						CardLayout cl = (CardLayout)(basePane.getLayout());
						cl.show(basePane,NEWUSERPANEL);
					}
					else{
						try {
							int maxID = database_access2.getMaxCustID(conn);
							int newID = maxID + 1;
							database_access2.newEntry(conn,newID,currentFirstName,currentLastName);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						CardLayout cl = (CardLayout)(basePane.getLayout());
						cl.show(basePane,NEWUSERADDED);
					};
				}
			});
			btnSubmit.setBounds(151, 187, 97, 25);
			add(btnSubmit);
			
			tf_firstname_newuser = new JTextField(); //First Name new user input text field
			tf_firstname_newuser.setBounds(185, 95, 116, 22);
			add(tf_firstname_newuser);
			tf_firstname_newuser.setColumns(10);
			
			tf_lastname_newuser = new JTextField(); //Last Name new user input text field
			tf_lastname_newuser.setBounds(185, 130, 116, 22);
			add(tf_lastname_newuser);
			tf_lastname_newuser.setColumns(10);
			
			JLabel lblFirstName = new JLabel("First Name:");
			lblFirstName.setBounds(98, 98, 75, 16);
			add(lblFirstName);
			
			JLabel lblLastName_1 = new JLabel("Last Name:");
			lblLastName_1.setBounds(98, 133, 75, 16);
			add(lblLastName_1);
			
			JLabel lblPleaseEnterYour = new JLabel("Please enter your information:");
			lblPleaseEnterYour.setBounds(98, 49, 203, 16);
			add(lblPleaseEnterYour);

		}
		
	    @Override
	    public Dimension getPreferredSize()
	    {
	        return (new Dimension(500, 500));
	    }
	}
	
	class OldUserPanel extends JPanel {

		private static final long serialVersionUID = -256834256845627875L;
		public String loggedInUser = null;
		public int loggedInUserInt = 0;
		
		public OldUserPanel(JPanel panel, FaceDecoder_GUI fdg){
			basePane = panel;
			setOpaque(true);
			Connection conn = myconn;
			//JPanel card3_olduserpanel = new JPanel();
			setLayout(null);
			
			//CARD 3: OLDUSERPANEL
			JLabel lblIfYouKnow = new JLabel("If you know your ID number, enter it below:");
			lblIfYouKnow.setBounds(12, 30, 271, 16);
			add(lblIfYouKnow);
			
			tf_idnumber_olduser = new JTextField(); //Old User has entered his or her ID number in this text box
			tf_idnumber_olduser.setBounds(125, 56, 116, 22);
			add(tf_idnumber_olduser);
			tf_idnumber_olduser.setColumns(10);
			currentIDstr = tf_idnumber_olduser.getText();
			if (currentIDstr.length() != 0){
				currentID = Integer.parseInt(loggedInUser);
			}
			
			JLabel lblIdNumber = new JLabel("ID Number:");
			lblIdNumber.setBounds(22, 59, 91, 16);
			add(lblIdNumber);
			
			JLabel lblOtherwiseEnterYour = new JLabel("Otherwise, enter your first and last name below:");
			lblOtherwiseEnterYour.setBounds(12, 102, 298, 16);
			add(lblOtherwiseEnterYour);
			
			JButton btnSubmitIdNumber = new JButton("Submit ID Number");
			btnSubmitIdNumber.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {//Old user has submitted his or her ID number
					String loggedInUser2 = tf_idnumber_olduser.getText();
					int loggedInUserInt2 = Integer.parseInt(loggedInUser2);
					if (loggedInUser2.length() == 0){
						//Error Handling
						JOptionPane.showMessageDialog(basePane, "ID field has been left blank.  Please fill in your ID.");
						CardLayout cl = (CardLayout)(basePane.getLayout());
						cl.show(basePane,OLDUSERPANEL);
					}
					else{
						try {
							int item = database_access2.checkID(conn, loggedInUserInt2);
							System.out.println(item);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						CardLayout cl = (CardLayout)(basePane.getLayout());
						cl.show(basePane,LOGGEDIN);
					}
				}
			});
			btnSubmitIdNumber.setBounds(273, 53, 137, 25);
			add(btnSubmitIdNumber);
			
			tf_firstname_olduser = new JTextField();
			tf_firstname_olduser.setBounds(125, 133, 116, 22);
			add(tf_firstname_olduser);
			tf_firstname_olduser.setColumns(10);
			
			tf_lastname_olduser = new JTextField();
			tf_lastname_olduser.setBounds(125, 171, 116, 22);
			add(tf_lastname_olduser);
			tf_lastname_olduser.setColumns(10);
			
			JLabel lblFirstName_1 = new JLabel("First Name:");
			lblFirstName_1.setBounds(22, 136, 91, 16);
			add(lblFirstName_1);
			
			JLabel lblLastName = new JLabel("Last Name:");
			lblLastName.setBounds(22, 174, 75, 16);
			add(lblLastName);
			
			JButton btnSubmitName = new JButton("Submit Name");
			btnSubmitName.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { // Old user has submitted his or her name
					String firstName = tf_firstname_olduser.getText();
					String lastName = tf_lastname_olduser.getText();
					if (firstName.length()==0 || lastName.length()==0){//If the user has not filled in a first or last name
						//Error Handling
						JOptionPane.showMessageDialog(basePane, "One or more name fields has been left blank.  Please fill in both first and last name fields.");
						CardLayout cl = (CardLayout)(basePane.getLayout());
						cl.show(basePane,OLDUSERPANEL);
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
							CardLayout cl = (CardLayout)(basePane.getLayout());
							cl.show(basePane,LOGGEDIN);
						}
						else{
							JOptionPane.showMessageDialog(basePane, "Your name could not be found in the database, please try entering your informaiton again");
							CardLayout cl = (CardLayout)(basePane.getLayout());
							cl.show(basePane,OLDUSERPANEL);
						}
					}
				}
			});
			btnSubmitName.setBounds(273, 170, 137, 25);
			add(btnSubmitName);
		}
		
	    @Override
	    public Dimension getPreferredSize()
	    {
	        return (new Dimension(500, 500));
	    }
		
	}
	
	class NewUserAdded extends JPanel {

		private static final long serialVersionUID = 3498911370386853907L;

		public NewUserAdded (JPanel panel, FaceDecoder_GUI fdg) throws SQLException{
			basePane = panel;
			Connection conn = myconn;
			setOpaque(true);
			setLayout(null);
			
			//CARD 4: NEWUSERADDED
			int id = database_access2.getMaxCustID(conn)+1;
			String s1 = "You, " + currentFirstName + " " + currentLastName + ", have been successfully added to the database.";
			String s2 = "Your user ID is: " + id;
			
			JLabel lblYouHaveBeen = new JLabel(s1);
			lblYouHaveBeen.setBounds(106, 68, 232, 16);
			add(lblYouHaveBeen);
			
			JButton btnContinue = new JButton("Continue");
			btnContinue.setBounds(173, 173, 97, 25);
			add(btnContinue);
			
			JLabel lblNewLabel = new JLabel(s2);
			lblNewLabel.setBounds(162, 121, 119, 16);
			add(lblNewLabel);
		}
	}
	
	class LoggedIn extends JPanel {

		private static final long serialVersionUID = 4631572650799688037L;
		
		public LoggedIn(JPanel panel, FaceDecoder_GUI fdg) throws SQLException{
			basePane = panel;
			Connection conn = myconn;
			setOpaque(true);
			/*JPanel card5_loggedin = new JPanel();*/
			setLayout(null);
			
			// CARD 5: LOGGEDIN
			JLabel lblYouHaveBeen_1 = new JLabel("You have been logged in as: ");
			lblYouHaveBeen_1.setBounds(123, 45, 175, 16);
			add(lblYouHaveBeen_1);
			
			String firstname_loggedin = database_access2.getFirstName(conn, currentID);
			String lastname_loggedin = database_access2.getLastName(conn, currentID);
			JLabel lblOldUsersName = new JLabel(firstname_loggedin + " " + lastname_loggedin);//(firstName + " " + lastName);
			lblOldUsersName.setBounds(143, 84, 135, 16);
			add(lblOldUsersName);
			
			JLabel lblUserId = new JLabel("User ID: " + "another HOLDER");//OldUserPanel.loggedInUser);
			lblUserId.setBounds(153, 113, 114, 16);
			add(lblUserId);
			
			JButton btnContinue_1 = new JButton("Continue");
			btnContinue_1.setBounds(154, 155, 113, 37);
			add(btnContinue_1);
			
			JButton btnThisIsntMe = new JButton("This isn't me");
			btnThisIsntMe.setBounds(158, 205, 105, 25);
			add(btnThisIsntMe);
		}
		
	}
	
/*    public JComboBox getChoiceBox()
    {
        return choiceBox;
    }
	*/
	public void initializeGUI() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{

		JFrame frame = new JFrame("FaceDecoder_GUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel basePane = new JPanel();
		basePane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		basePane.setLayout(new CardLayout());
		
/*		choiceBox = new JComboBox(choices);*/
		ConnectionToDatabase aconn = new ConnectionToDatabase();
		myconn = aconn.conn;
		
		panel1 = new WelcomePanel(basePane,this);
		panel2 = new NewUserPanel(basePane,this);
		panel3 = new OldUserPanel(basePane,this);
		panel4 = new NewUserAdded(basePane,this);
		panel5 = new LoggedIn(basePane,this);
		
		basePane.add(panel1,WELCOMEPANEL);
		basePane.add(panel2,NEWUSERPANEL);
		basePane.add(panel3,OLDUSERPANEL);
		basePane.add(panel4,NEWUSERADDED);
		basePane.add(panel5,LOGGEDIN);

/*		frame.getContentPane().add(choiceBox);*/
		frame.getContentPane().add(basePane);
        frame.pack();
        frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}
}
