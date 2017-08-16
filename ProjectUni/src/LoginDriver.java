//Import all of the needed elements for the functionality of the window.
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

//This is the LoginDriver class which extends JFrame and implements an action listener which has
//the auto-implemented action performed method at the bottom of the page.
public class LoginDriver extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Application mainApp;
	String username1;
	String password1;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	String c;

	// This is the constructor of the class which contains a super class.
	// It sets the parameters of the frame and links to the main application.
	// It contains a link to the setup method that will be placed into the
	// frame.
	public LoginDriver(Application app) {

		mainApp = app;
		this.setSize(425, 400);
		this.setTitle("Login");
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		setVisible(true);
		this.getContentPane().setLayout(new GridLayout(0, 3, 0, 0));
		setupDriver();
	}

	// This is the main method of the class.
	// It defines, creates and adds the elements to the frame that has been
	// created by the constructor.
	private void setupDriver() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		this.getContentPane().add(panel);

		JPanel panel_2 = new JPanel();
		this.getContentPane().add(panel_2);
		panel_2.setLayout(new GridLayout(2, 0, 0, 0));

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(192, 192, 192));
		panel_2.add(panel_3);
		panel_3.setLayout(new GridLayout(6, 0, 0, 0));

		JLabel emptyLabel = new JLabel("");
		panel_3.add(emptyLabel);

		JLabel userLabel = new JLabel("Username");
		userLabel.setFont(new Font("Century Gothic", Font.BOLD, 12));
		userLabel.setForeground(new Color(255, 255, 255));
		userLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(userLabel);

		JTextField userField = new JTextField();
		userField.setForeground(new Color(0, 0, 0));
		userField.setBackground(new Color(211, 211, 211));
		panel_3.add(userField);
		userField.setColumns(10);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Century Gothic", Font.BOLD, 12));
		passwordLabel.setForeground(new Color(255, 255, 255));
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(passwordLabel);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBackground(new Color(211, 211, 211));
		panel_3.add(passwordField);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(192, 192, 192));
		panel_2.add(panel_4);
		panel_4.setLayout(new GridLayout(5, 0, 0, 0));

		JButton loginButton = new JButton("Login");
		panel_4.add(loginButton);
		loginButton.setFont(new Font("Century Gothic", Font.BOLD, 11));
		loginButton.setForeground(new Color(255, 255, 255));
		loginButton.setBackground(new Color(107, 142, 35));

		JButton registerButton = new JButton("Register");
		panel_4.add(registerButton);
		registerButton.setFont(new Font("Century Gothic", Font.BOLD, 11));
		registerButton.setBackground(new Color(107, 142, 35));
		registerButton.setForeground(new Color(255, 255, 255));

		JButton backButton = new JButton("Back");
		panel_4.add(backButton);
		backButton.setFont(new Font("Century Gothic", Font.BOLD, 11));
		backButton.setForeground(new Color(255, 255, 255));
		backButton.setBackground(new Color(139, 69, 19));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(192, 192, 192));
		this.getContentPane().add(panel_1);

		// The backButton just disposes of the active window and goes back to the
		// main window.
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		// The registerButton disposes of the active window and opens a new
		// RegisterCustomer3 window.
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new RegisterCustomer3(null);
			}
		});

		// The loginButton has been set as hardcoded for each type of user.
		// The hardcoded bit of the button is just the role that is compared
		// to the role column in the database, in this case the string "driver".
		// Other than this difference, the button works almost the same as the
		// other SQL buttons.
		// It first makes the connection to the database, it prepares a statement,
		// it specifies what the query is, it executes the query and finally it 
		// displays a login successful message which contains the entered user.
		// After the message is displayed, a new LoggedDriver() window is opened
		// with the value of userId.
		// The user displayed in the message is pulled from the second column of
		// the users table in the database.
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// These are variables that get values from the text field and the password field.
				// Uncrypted is the string value of the password field.
				String username1 = userField.getText();
				String uncrypted = String.valueOf(passwordField.getPassword());
				String role = "driver";
				Connection connection;
				PreparedStatement pst;
				try {
					connection = DriverManager.getConnection("jdbc:mysql://localhost/group28", "root", "");
					pst = connection.prepareStatement("select * from users where username=? and password=? and role=?");
					pst.setString(1, username1);
					pst.setString(2, uncrypted);
					pst.setString(3, role);
					rs = pst.executeQuery();
					if (rs.next()) {
						String userId = rs.getString(1);
						String displayId = rs.getString(2);
						JOptionPane.showMessageDialog(null, "Welcome " + displayId + "!");
						dispose();
						new LoggedDriver(mainApp, userId);
					} else {
						JOptionPane.showMessageDialog(null, "Login failed!");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});

	}

	//This is an auto-generated method by eclipse.
	//It can be used to implement the actions performed by buttons.
	//Some of the classes use this method to perform certain actions with the help of if statements.
	//This is an efficient and cleaner way of writing code as all of the
	//buttons can have listeners assigned and the method can just perform the actions.
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}

