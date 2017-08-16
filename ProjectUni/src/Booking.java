//The following lines represent all of the imports needed.
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

//This is the Booking class which uses JFrame as a container and implements an action listener.
public class Booking extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private String userId;
	private LoggedCust mainApp;
	private Booking thisPage;

	//This is the constructor which inherits the LoggedCust app and the user string.
	public Booking(LoggedCust app, String user) {
		super();
		userId = user;
		mainApp = app;
		System.out.println(user);
		thisPage = this;
		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Booking");
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new GridLayout(7, 2, 0, 0));
		this.setResizable(true);
		//this.setBackground(new Color(192, 192, 192));
		setVisible(true);
		setupRegister();
	}

	//This method adds all of the elements used to the frame.
	private void setupRegister() {
		
		//The following lines represent all of the elements needed for the main interface window.
		//It contains JLabels, JTextFields and JButtons which rest in a (7,2) GridLayout which is
		//specified in the constructor.
		//The labels have their fonts changed and their background colour.
		//There is no need for aditional panels because a GridLayout can be set directly in the JFrame.
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setFont(new Font("Century Gothic", Font.BOLD, 12));
		nameLabel.setBackground(new Color(128, 128, 128));
		this.add(nameLabel);
		
		JTextField firstName = new JTextField();
		firstName.setBackground(new Color(211, 211, 211));
		this.add(firstName);
		firstName.setColumns(10);
		
		JLabel pickupLabel = new JLabel("Pick-up Address");
		pickupLabel.setFont(new Font("Century Gothic", Font.BOLD, 12));
		pickupLabel.setBackground(new Color(128, 128, 128));
		this.add(pickupLabel);
		
		JTextField pickupAddress = new JTextField();
		pickupAddress.setBackground(new Color(211, 211, 211));
		this.add(pickupAddress);
		pickupAddress.setColumns(10);
		
		JLabel dropoffLabel = new JLabel("Drop-off Address");
		dropoffLabel.setFont(new Font("Century Gothic", Font.BOLD, 12));
		dropoffLabel.setBackground(new Color(128, 128, 128));
		this.add(dropoffLabel);
		
		JTextField pickoffAddress = new JTextField();
		pickoffAddress.setBackground(new Color(211, 211, 211));
		this.add(pickoffAddress);
		pickoffAddress.setColumns(10);
		
		JLabel picupTLabel = new JLabel("Pick-up Time");
		picupTLabel.setFont(new Font("Century Gothic", Font.BOLD, 12));
		picupTLabel.setBackground(new Color(128, 128, 128));
		this.add(picupTLabel);
		
		JTextField pickupTime = new JTextField();
		pickupTime.setBackground(new Color(211, 211, 211));
		this.add(pickupTime);
		pickupTime.setColumns(10);
		
		JLabel pickupDLabel = new JLabel("Pick-up Date");
		pickupDLabel.setFont(new Font("Century Gothic", Font.BOLD, 12));
		pickupDLabel.setBackground(new Color(128, 128, 128));
		this.add(pickupDLabel);
		
		JTextField pickupDate = new JTextField();
		pickupDate.setBackground(new Color(211, 211, 211));
		this.add(pickupDate);
		pickupDate.setColumns(10);
		
		JLabel phoneLabel = new JLabel("Telephone Number");
		phoneLabel.setFont(new Font("Century Gothic", Font.BOLD, 12));
		phoneLabel.setBackground(new Color(128, 128, 128));
		this.add(phoneLabel);
		
		JTextField phone = new JTextField();
		phone.setBackground(new Color(211, 211, 211));
		this.add(phone);
		phone.setColumns(10);
		
		JButton backButton = new JButton("Back");
		backButton.setBackground(new Color(139, 69, 19));
		this.add(backButton);
		
		JButton confirmBtn = new JButton("Confirm");
		confirmBtn.setBackground(new Color(107, 142, 35));
		this.add(confirmBtn);
		
		
		//This button disposes of the window, which shows us the previous customer window.
		//backButton.addActionListener(new ActionListener() {
			//public void actionPerformed(ActionEvent e) {
				//dispose();
			//}
		//});

		
		//The confirm button represents the link to the SQL database.
		//It contains a method that inserts the information from the text boxes
		//into the database.
		//A new date variable is being created in order to memorise the time at which the button has been pressed
		//which is then converted to an SQL timestamp and is inserted into the database.
		//The order of the insert statement must be the same as the layout of the table in which the data is being
		//inserted.
		//thisPage.userId is used to store the id of the client that made the order.
		//When the method tries to make the statement readable, it is set to catch exceptions.
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					java.util.Date date = new java.util.Date();
					long t = date.getTime();
					java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(t);
					theQuery(
							"insert into booking (user_id,firstname,pickupaddress,pickoffaddress,pickuptime,pickupdate,phone,created_at) values("
									+ thisPage.userId + ",'" + firstName.getText() + "','" + pickupAddress.getText()
									+ "','" + pickoffAddress.getText() + "','" + pickupTime.getText() + "','"
									+ pickupDate.getText() + "','" + phone.getText() + "', '" + sqlTimestamp + "')");
				} catch (Exception ex) {
				}
			}
		});
		
		//This button sets the active window invisible, sets LoggedCust as visible and
		//disposes of the active window using the dispose() method.
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				mainApp.setVisible(true);
				dispose();
			}
		});

	}

	//This method establishes the connection to the database, performs a query and uses two embedded methods to
	//retrieve some data from the database.
	//To be more specific, after the update query is executed, JOptionPane message is displayed, and the
	//mainApp(LoggedCust) is being displayed with the information regarding the active trip, as well as
	//information from the last trips made by this specific user.
	//After all of the information has been retrieved, the window is closed with the dispose() method.
	public void theQuery(String query) {
		Connection con = null;
		Statement st = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/group28", "root", "");
			st = con.createStatement();
			st.executeUpdate(query);
			JOptionPane.showMessageDialog(null, "Booking complete!");
			setVisible(false);
			mainApp.setVisible(true);
			mainApp.retrieveOrders();
			mainApp.retrieveLastOrder();
			dispose();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
