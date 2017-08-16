
// Import all the needed elements for the creation and functionality of the window.
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import com.mysql.jdbc.PreparedStatement;
import net.proteanit.sql.DbUtils;

// This is the LoggedCust class which extends JFrame and implements an action listener which has
// The auto-implemented action performed method at the bottom of the page.
// The majority of the components are being defined in the public class as private.
// They can be defined and created in the main method that actually adds everything to the frame.
public class LoggedDriver extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton logOutButton;
	private JButton cancelOrder;
	private JButton refreshBtn;
	private JButton completeOrder;
	private Application mainApp;
	private JTable tripsTable;
	private JScrollPane scrollPane;
	private String user;
	private String lastOrderId;
	private JLabel lastOrder;
	private JTextArea lastOrderInfo;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;

	// This is the constructor of the class that sets the parameters of the
	// frame and creates it.
	// It contains a default close operation on exit.
	// It links to the main method.
	public LoggedDriver(Application app, String userId) {

		super();
		mainApp = app;
		user = userId;
		this.setSize(900, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Logged Driver");
		this.setLocationRelativeTo(null);
		// this.setLayout(null);
		this.setResizable(false);
		setVisible(true);
		setLayout(new BorderLayout());
		setupDriver();
	}

	// The following lines define the text area that contains the details of the
	// active trip.
	// The text area is not editable and is basically a canvas for the active
	// trip.
	private JTextArea textAreaProperties(JTextArea textArea) {
		textArea.setEditable(false);
		textArea.setCursor(null);
		textArea.setOpaque(false);
		textArea.setFocusable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		return textArea;
	}

	// This is the main method of the class.
	// It defines, creates and adds the elements to the frame that has been
	// created by the constructor.
	// The elements have been set with absolute bounds which is not the best way
	// to arrange the elements as long as the window is resizable.
	// It has been set with absolute bounds because of the size of the elements.
	// In this case, the window is not resizable and it does not represent an
	// issue
	// when interacting with it.
	private void setupDriver() {

		// A table is being created, which will contain all of the information
		// taken from the database.
		// A scrollPane is created as well so that you can see all of the data,
		// even if it does not fit
		// in the pane.
		// The setViewportView method basically turns the scrollPane into a
		// container that can
		// accomodate the table. In other words, the scrollPane already has a
		// single component
		// attached to it, so setViewportView can actually add an element.
		tripsTable = new JTable();
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 400, 880, 200);
		getContentPane().add(scrollPane);
		retrieveOrders();
		scrollPane.setViewportView(tripsTable);

		cancelOrder = new JButton("Cancel Order");
		cancelOrder.setBounds(10, 150, 136, 40);
		cancelOrder.addActionListener(this);
		cancelOrder.setActionCommand("cancel");
		getContentPane().add(cancelOrder);

		completeOrder = new JButton("Complete Order");
		completeOrder.setBounds(150, 150, 136, 40);
		completeOrder.addActionListener(this);
		completeOrder.setActionCommand("complete");
		getContentPane().add(completeOrder);

		lastOrder = new JLabel("Last Order:");
		lastOrder.setBounds(10, 10, 91, 14);
		getContentPane().add(lastOrder);

		lastOrderInfo = new JTextArea("Who: \nFrom: \nTo: \nWhen: \nPhone: \nStatus: ");
		textAreaProperties(lastOrderInfo);
		lastOrderInfo.setBounds(10, 25, 200, 100);
		getContentPane().add(lastOrderInfo);
		retrieveLastOrder();

		logOutButton = new JButton("Logout");
		logOutButton.setBounds(10, 700, 136, 40);
		logOutButton.addActionListener(this);
		logOutButton.setActionCommand("logout");
		getContentPane().add(logOutButton);

		refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(300, 700, 136, 40);
		refreshBtn.addActionListener(this);
		refreshBtn.setActionCommand("refresh");
		getContentPane().add(refreshBtn);

		JPanel background = new JPanel();
		add(background);
		background.setLayout(new FlowLayout());
		this.add(background);

	}

	// This is a method that connects to the database and gets all the data for
	// a highlighted
	// user from the users table.
	// This.user is being collected from the login page and passed into this
	// application so that
	// the correct information is displayed for a specific user.
	public void retrieveUserInfo() {
		Connection connection;
		java.sql.PreparedStatement pst;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/group28", "root", "");
			pst = connection.prepareStatement("select * from users where id = " + this.user);
			rs = pst.executeQuery();
			if (rs.next())
				;

		} catch (Exception e1) {
			e1.printStackTrace();

		}

	}

	// This is a method that connects to the database and gets all the data for
	// a highlighted
	// user from the booking table.
	// This.user is being collected from the login page and passed into this
	// application so that
	// the correct information is displayed for a specific user.
	// The information in displayed in the scrollPane table created previously.
	// The model is being set to DbUtils.resultSetToTableModel, which is
	// included in the libraries
	// used. This basically converts the rs to a table model and it sets it to
	// the table.
	public void retrieveOrders() {
		Connection connection;
		java.sql.PreparedStatement pst;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/group28", "root", "");
			pst = connection.prepareStatement("select * from booking where driver_assigned = " + this.user);
			rs = pst.executeQuery();
			tripsTable.setModel(DbUtils.resultSetToTableModel(rs));
			if (rs.next())
				;

		} catch (Exception e1) {
			e1.printStackTrace();

		}

	}

	// This method selects all the data from the booking table with some
	// conditions.
	// It selects all of the orders which are pending or taken, it matches the
	// user_id from
	// the database with the user stored in the application momentarily and it
	// orders all
	// the data based on created_at from the oldest first.
	// Next, it grabs most of the columns from the Booking table and it sets the
	// values in the
	// textArea created previously.
	// After performing the query, it makes the cancelOrder button available.
	// lastOrderId = rs.getString(1); gets the information from the first column
	// of the Booking
	// table.
	// lastOrderDriver = rs.getString(9); gets the information from the 9th
	// column of the Booking
	// table.
	// Else, the data in the textArea is null, which means that it did not get
	// anything from the
	// Booking table.
	public void retrieveLastOrder() {
		Connection connection;
		java.sql.PreparedStatement pst;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/group28", "root", "");
			pst = connection.prepareStatement("select * from booking where status = 'taken' AND driver_assigned = "
					+ this.user + " ORDER BY created_at DESC");
			rs = pst.executeQuery();
			if (rs.next()) {
				lastOrderId = rs.getString(1);
				String order = "Who: " + rs.getString(3) + "\nFrom: " + rs.getString(4) + "\nTo: " + rs.getString(5)
						+ "\nWhen: " + rs.getString(7) + " " + rs.getString(6) + "\nPhone: " + rs.getString(8)
						+ "\nStatus: " + rs.getString(10);
				lastOrderInfo.setText(order);
				this.cancelOrder.setEnabled(true);
				this.completeOrder.setEnabled(true);

			} else {
				lastOrderInfo.setText("Who: \nFrom: \nTo: \nWhen: \nPhone: \nStatus: ");
				lastOrderId = null;
				this.cancelOrder.setEnabled(false);
				this.completeOrder.setEnabled(false);

			}

		} catch (Exception e1) {
			e1.printStackTrace();

		}

	}

	// This method is used to update the database with specific parameters.
	// It updates the available column in the Drivers table where the ID
	// matches the 9th column in the booking table.
	// Secondly, it updates the status column in the Booking table where the ID
	// matches the first column of the booking table.
	// Both of the statements are joined in a batch and executed as a whole.
	public void cancelLastOrder() {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/group28", "root", "");
			Statement stmt = connection.createStatement();
			String sql1 = "UPDATE drivers SET available = 0 WHERE id = " + this.user;
			String sql2 = "UPDATE booking SET driver_assigned = " + this.user + ", status = 'canceled' where id = "
					+ this.lastOrderId;
			stmt.addBatch(sql1);
			stmt.addBatch(sql2);
			stmt.executeBatch();

		} catch (Exception e1) {
			e1.printStackTrace();

		}

	}

	// This method updates the database with specific parameters.
	// It updates the available column to 0 where the id matches
	// the 9th column in the booking table.
	// Secondly, it updates the driver_assigned column to this user,
	// the status column to completed where the id column is equal to
	// the value of the lastOrderId.
	// Finally, both queries are joined in a batch and executed as a whole.
	public void completeOrder() {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/group28", "root", "");
			Statement stmt = connection.createStatement();
			String sql1 = "UPDATE drivers SET available = 0 WHERE id = " + this.user;
			String sql2 = "UPDATE booking SET driver_assigned = " + this.user + ", status = 'completed' where id = "
					+ this.lastOrderId;
			stmt.addBatch(sql1);
			stmt.addBatch(sql2);
			stmt.executeBatch();

		} catch (Exception e1) {
			e1.printStackTrace();

		}

	}

	// This is the action performed method which answers to the listeners
	// that have been set on the buttons.
	// It contains an if statement which takes into account the actions set
	// on each button.
	// If the action equals to "logout" it makes the current window invisible
	// and makes the mainApp window visible.
	// If the action equals to "cancel", it runs the retrieveLastOrder(),
	// cancelLastOrder(), retrieveOrders(), retrieveLastOrder() methods in order
	// to cancel the active order.
	// If the action equals to "refresh" it runs just the get methods in order
	// to update the data in the table and the text area.
	// If the action equals to "complete", it runs the completeOrder() set
	// method
	// and the two get methods, retrieveOrders() and retrieveLastOrder().
	public void actionPerformed(ActionEvent e) {

		String action = e.getActionCommand();
		if (action.equals("logout")) {
			setVisible(false);
			mainApp.setVisible(true);
			dispose();
		} else if (action.equals("cancel")) {
			cancelLastOrder();
			retrieveOrders();
			retrieveLastOrder();
		} else if (action.equals("refresh")) {
			retrieveOrders();
			retrieveLastOrder();
		} else if (action.equals("complete")) {
			completeOrder();
			retrieveOrders();
			retrieveLastOrder();
		}

	}
}
