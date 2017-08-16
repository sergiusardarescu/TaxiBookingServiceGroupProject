
//The imports needed for this window.
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

//This is the Customer class which uses JFrame and implements an action listener that is actually used.
//Action listeners are set into each button and the action performed at the bottom of the page
//performs an if statement to decide what each button does when pressed.
//Each button has an action command which is set to a string which, when pressed, is passed into
//the if statement at the bottom of the page.
public class Customer extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton RegisterCustomer;
	private JButton LoginCustomer;
	private Application mainApp;
	private JButton backButton;

	// This is the constructor of the class which contains a super class.
	// It sets the parameters of the frame, the default close operation and it
	// links to the main application.
	// It contains a link to the setup method that will be placed into the
	// frame.
	public Customer(Application app) {

		super();
		mainApp = app;
		this.setSize(900, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Customer");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		setVisible(true);
		setLayout(new BorderLayout());
		setupCustomer();
	}

	// This is the method that will define and add all of the elements to the
	// frame.
	// As it has been stated before, each button has an action listener and an
	// action command.
	// When each button is pressed, it passes the action command to the action
	// performed method.
	private void setupCustomer() {

		RegisterCustomer = new JButton("Register");
		RegisterCustomer.setBounds(320, 200, 190, 40);
		RegisterCustomer.addActionListener(this);
		RegisterCustomer.setActionCommand("a");
		this.add(RegisterCustomer);

		LoginCustomer = new JButton("Login");
		LoginCustomer.setBounds(350, 300, 125, 40);
		LoginCustomer.addActionListener(this);
		LoginCustomer.setActionCommand("b");
		this.add(LoginCustomer);

		backButton = new JButton("Back");
		backButton.setBounds(350, 400, 125, 40);
		backButton.addActionListener(this);
		backButton.setActionCommand("back");
		this.add(backButton);
	}

	// This is the action performed method that contains the if statement that
	// has been specified previously.
	// If the action equals "a", the window closes and a new register customer
	// window is opened.
	// Else, if the action equals "b", the window closes and a new login
	// customer window is opened.
	// Finally, if the action equals to "back", the window turns invisible and
	// the mainApp is opened.
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("a")) {
			dispose();
			new RegisterCustomer3(this);
		} else if (action.equals("b")) {
			dispose();
			new LoginCustomer(this);
		} else if (action.equals("back")) {
			setVisible(false);
			mainApp.setVisible(true);
			dispose();
		}

	}

}
