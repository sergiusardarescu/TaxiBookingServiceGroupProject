//The following lines represent the imports needed for the functionality of the application.
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//As the code states, the next line is the public class containing the frame of the window that uses action listeners.
public class Application extends JFrame implements ActionListener {

	//Deserialization uses this line to make sure that the loaded class corresponds exactly to a serialized object.
	//This is an auto generated line which helps the program keep its objects and bounds.
	private static final long serialVersionUID = 1L;

	//This is the constructor that specifies the size, title, layout and various parameters needed for the container frame.
	public Application() {
		super();
		this.setSize(370, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Taxi Booking System");
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.getContentPane().setLayout(new GridLayout(0, 3, 0, 0));
		setupComponents();
	}

	//
	/**
	 * This method sets up all the components that go into the main frame.
	 */
	private void setupComponents() {
		//The following lines represent an empty panel used mainly for the arrangement of the middle panel.
		//It sets the colour of the panel with the set.Background command.
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(new Color(192, 192, 192));
		this.getContentPane().add(leftPanel);

		//The following lines create a JPanel with all of its parameters.
		//This is the middle panel which contains all of the elements.
		//It sets the colour of the panel and the grid used to position all of the elements.
		//It adds the panel to the content pane.
		JPanel midPanel = new JPanel();
		midPanel.setBackground(new Color(192, 192, 192));
		this.getContentPane().add(midPanel);
		midPanel.setLayout(new GridLayout(6, 0, 0, 5));

		//The following lines represent the creation of the different elements that will be positioned in the frame.
		//The elements used are JLabels, JTextFields and JButtons.
		//The JLabel is centered horizontally with the help of SwingConstants which have imported.
		//Certain colours are put in to place in order to make the interface more pleasant.
		JLabel lblNewLabel = new JLabel("Welcome!");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		midPanel.add(lblNewLabel);

		JLabel emptyLabel = new JLabel("");
		midPanel.add(emptyLabel);

		JButton customerButton = new JButton("Customer");
		customerButton.setBackground(new Color(107, 142, 35));
		midPanel.add(customerButton);

		JButton driverButton = new JButton("Driver");
		driverButton.setBackground(new Color(107, 142, 35));
		midPanel.add(driverButton);

		JButton adminButton = new JButton("Admin");
		adminButton.setBackground(new Color(107, 142, 35));
		midPanel.add(adminButton);

		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(new Color(192, 192, 192));
		this.getContentPane().add(rightPanel);
		
		
		//The next elements represent the buttons used in the main frame.
		//Each button opens a new class without disposing of the main class
		//in order to navigate easier through the initial steps of the application.
		//Each button contains an action listener and a method that performs a certain
		//action.
		customerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LoginCustomer(null);
			}
		});

		driverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LoginDriver(null);
			}
		});

		adminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new LoginAdmin(null);
			}
		});

	}

	//This is an auto-generated method by eclipse.
	//It can be used to implement the actions performed by buttons.
	//Some of the classes use this method to perform certain actions with the help of if statements.
	//This is an efficient and cleaner way of writing code as all of the
	//buttons can have listeners assigned and the method can just perform the actions.
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
