// Import all the needed elements for the creation and functionality of the window.
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

//This is the RegisterCustomer3 class which extends JFrame and implements an action listener which has
//The auto-implemented action performed method at the bottom of the page.
public class RegisterCustomer3 extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Customer mainApp;

	// This is the constructor of the class that sets the parameters of the
	// frame and creates it.
	// It links to the main method.
	public RegisterCustomer3(Customer app) {

		super();
		mainApp = app;
		this.setSize(450, 350);
		this.setTitle("Register Customer");
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		setVisible(true);
		this.getContentPane();
		this.setLayout(new GridLayout(2, 1, 0, 0));
		setupRegister();
	}

	// This is the main method of the class.
	// It defines, creates and adds the elements to the frame that has been
	// created by the constructor.
	// It contains all of the panels, buttons, labels and text fields needed.
	private void setupRegister() {
		JPanel detailsPanel = new JPanel();
		this.add(detailsPanel);
		detailsPanel.setLayout(new GridLayout(6, 4, 5, 2));

		JLabel userLabel = new JLabel("Username");
		detailsPanel.add(userLabel);

		JTextField userField = new JTextField();
		detailsPanel.add(userField);
		userField.setColumns(10);

		JLabel cardLabel = new JLabel("Card Number");
		detailsPanel.add(cardLabel);

		JTextField cardField = new JTextField();
		detailsPanel.add(cardField);
		cardField.setColumns(10);

		JLabel passLabel = new JLabel("Password");
		detailsPanel.add(passLabel);

		JTextField passField = new JTextField();
		detailsPanel.add(passField);
		passField.setColumns(10);

		JLabel expDateLabel = new JLabel("Expiry Date");
		detailsPanel.add(expDateLabel);

		JTextField expDateField = new JTextField();
		detailsPanel.add(expDateField);
		expDateField.setColumns(10);

		JLabel nameLabel = new JLabel("Full Name");
		detailsPanel.add(nameLabel);

		JTextField nameField = new JTextField();
		detailsPanel.add(nameField);
		nameField.setColumns(10);

		JLabel cvvLabel = new JLabel("CVV");
		detailsPanel.add(cvvLabel);

		JTextField cvvField = new JTextField();
		detailsPanel.add(cvvField);
		cvvField.setColumns(10);

		JLabel emailLabel = new JLabel("Email");
		detailsPanel.add(emailLabel);

		JTextField emailField = new JTextField();
		detailsPanel.add(emailField);
		emailField.setColumns(10);

		JLabel licenceLabel = new JLabel("Licence Plate");
		detailsPanel.add(licenceLabel);
		licenceLabel.setEnabled(false);

		JTextField licenceField = new JTextField();
		detailsPanel.add(licenceField);
		licenceField.setColumns(10);
		licenceField.setEnabled(false);

		JLabel addressLabel = new JLabel("Address");
		detailsPanel.add(addressLabel);

		JTextField addressField = new JTextField();
		detailsPanel.add(addressField);
		addressField.setColumns(10);

		JRadioButton custBtn = new JRadioButton("Customer");
		detailsPanel.add(custBtn);
		custBtn.setHorizontalAlignment(SwingConstants.CENTER);

		JRadioButton driverBtn = new JRadioButton("Driver");
		detailsPanel.add(driverBtn);
		driverBtn.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel phoneLabel = new JLabel("Telephone Number");
		detailsPanel.add(phoneLabel);

		JTextField phoneField = new JTextField();
		detailsPanel.add(phoneField);
		phoneField.setColumns(10);

		JPanel btnPanel = new JPanel();
		this.add(btnPanel);
		btnPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		btnPanel.add(panel, BorderLayout.NORTH);

		JButton backBtn = new JButton("Back");
		panel.add(backBtn);

		JButton regBtn = new JButton("Register");
		panel.add(regBtn);

		JPanel panel_1 = new JPanel();
		btnPanel.add(panel_1, BorderLayout.SOUTH);

		JButton driverRegButton = new JButton("Register Driver");
		panel_1.add(driverRegButton);
		driverRegButton.setEnabled(false);

		ButtonGroup group = new ButtonGroup();
		group.add(custBtn);
		group.add(driverBtn);

		// The driverBtn radio button, makes the components dedicated to the registration
		// of the driver editable and the ones that are not driver related are disabled.
		// This is the only functionality of this radio button.
		driverBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				regBtn.setEnabled(false);
				driverRegButton.setEnabled(true);
				licenceField.setEnabled(true);
				licenceLabel.setEnabled(true);
				cardLabel.setEnabled(false);
				cvvLabel.setEnabled(false);
				expDateLabel.setEnabled(false);
				addressLabel.setEnabled(false);
				cardField.setEnabled(false);
				cvvField.setEnabled(false);
				addressField.setEnabled(false);
				expDateField.setEnabled(false);
				emailField.setEnabled(false);
				phoneField.setEnabled(false);
				emailLabel.setEnabled(false);
				phoneLabel.setEnabled(false);


			}
		});

		// The custBtn radio button, makes the components dedicated to the registration
		// of the customer editable and the ones that are not customer related are disabled.
		// This is the only functionality of this radio button.
		custBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				regBtn.setEnabled(true);
				driverRegButton.setEnabled(false);
				licenceField.setEnabled(false);
				licenceLabel.setEnabled(false);
				cardLabel.setEnabled(true);
				cvvLabel.setEnabled(true);
				expDateLabel.setEnabled(true);
				addressLabel.setEnabled(true);
				cardField.setEnabled(true);
				cvvField.setEnabled(true);
				addressField.setEnabled(true);
				expDateField.setEnabled(true);
				emailField.setEnabled(true);
				phoneField.setEnabled(true);
				emailLabel.setEnabled(true);
				phoneLabel.setEnabled(true);
			}
		});

		// The back button disposes of the current window.
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

		// This is the register button which contains a method that defines a statement that will be used 
		// in the addClient() method.
		// It basically states all of the database columns that will be affected and it links each column
		// to its correspondent from the text fields.
		regBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					addClient(
							"insert into customers (username,password,name,phoneNumber,address,email,ccnum,ccexp,cvv) values('"
									+ userField.getText() + "','" + passField.getText() + "','" + nameField.getText()
									+ "','" + phoneField.getText() + "','" + addressField.getText() + "','"
									+ emailField.getText() + "','" + cardField.getText() + "','"
									+ expDateField.getText() + "','" + cvvField.getText() + "')",
									userField.getText(), passField.getText());
				} catch (Exception ex) {
				}
			}
		});

		// The driver register button has the same functionality as the register button with much less
		// columns selected for the database insert.
		driverRegButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					addDriver(
							"insert into drivers (name,licenseplate,available) values('" + nameField.getText() + "','"
									+ licenceField.getText() + "','" + "0" + "')",
									userField.getText(), passField.getText());
				} catch (Exception ex) {
				}
			}
		});
	}

	// This method adds the data to the customers table taking into consideration the query, the user string and the
	// password string.
	// It also contains an if statement that also adds data to the user table without allowing duplicates.
	public void addClient(String query, String user, String pass) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		java.sql.PreparedStatement pst = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/group28", "root", "");
			st = con.createStatement();
			pst = con.prepareStatement("select * from users where username = '" + user + "'");
			rs = pst.executeQuery();
			if (rs.next()) {
				user = rs.getString(2);
				JOptionPane.showMessageDialog(null, "Username already taken!");
			} else {
				st.executeUpdate(query);
				st.executeUpdate("INSERT into users (username, password,role) VALUES('" + user + "', '" + pass + "','"
						+ "customer" + "' )");
				setVisible(false);
				mainApp.setVisible(true);
				dispose();
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());

		}

	}

	// This method adds the data to the customers table taking into consideration the query, the user string and the
	// password string.
	// It also contains an if statement that also adds data to the user table without allowing duplicates.
	public void addDriver(String query, String user, String pass) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		java.sql.PreparedStatement pst = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/group28", "root", "");
			st = con.createStatement();
			pst = con.prepareStatement("select * from users where username = '" + user + "'");
			rs = pst.executeQuery();
			if (rs.next()) {
				user = rs.getString(1);
				JOptionPane.showMessageDialog(null, "Username already taken!");
			} else {
				st.executeUpdate(query);
				st.executeUpdate("INSERT into users (username, password,role) VALUES('" + user + "', '" + pass + "','"
						+ "driver" + "' )");
				setVisible(false);
				mainApp.setVisible(true);
				dispose();
			}

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());

		}

	}

	public void actionPerformed(ActionEvent e) {
	}

}
