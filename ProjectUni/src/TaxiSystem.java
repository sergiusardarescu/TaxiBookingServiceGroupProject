
//Imports all of the elements needed for the window
import java.awt.EventQueue;
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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.mysql.jdbc.PreparedStatement;
import net.proteanit.sql.DbUtils;

//This is the TaxiSystem class which extends JFrame.
//The auto-implemented action performed method at the bottom of the page.
//The majority of the components are being defined in the public class as private.
//They can be defined and created in the main method that actually adds everything to the frame.
public class TaxiSystem extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String idDriver;
	private String idOrder;
	Connection conn = null;
	ResultSet rs = null;
	PreparedStatement pst = null;
	String s;
	private JTable table;
	private JLabel lblOrders;
	private JTable table2;
	private JLabel lblDrivers;
	private JButton btnLoadDrivers;
	private JButton btnNewButton_1;
	private JTable table_1;
	private JLabel lblInProgress;
	private JButton btnNewButton_2;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;

	/**
	 * This is the main method that launches the TaxiSystem Application. It sets
	 * the frame in which the elements rest. It sets the main frame visible.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TaxiSystem frame = new TaxiSystem();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * This is the constructor of the class, which contains all of the elements
	 * needed for the window. The elements are: a JPanel, JLabels, JTables which
	 * are stored in JScrollPanes and finally, the buttons that make all the
	 * magic happen.
	 */

	public TaxiSystem() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 800);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JButton btnNewButton = new JButton("Load Orders");

		btnNewButton.setBounds(668, 58, 135, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retrievePendingOrders();
				retrieveAllOrders();
			}
		});

		contentPane.setLayout(null);
		btnLoadDrivers = new JButton("Load Drivers");
		btnLoadDrivers.setBounds(668, 92, 135, 23);
		contentPane.add(btnLoadDrivers);

		btnLoadDrivers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				retrieveDrivers();

			}
		});

		contentPane.add(btnNewButton);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 31, 543, 149);
		contentPane.add(scrollPane);
		table = new JTable();
		retrievePendingOrders();

		// The following lines contain the action listener of the list that is
		// nested inside of the
		// JTable that has been created previously.
		table.setRowSelectionAllowed(true);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (table.getSelectedRow() > -1) {
					idOrder = table.getValueAt(table.getSelectedRow(), 0).toString();
					System.out.println(idOrder);
					if (idOrder != null && idDriver != null) {
						btnNewButton_1.setEnabled(true);
					} else {
						btnNewButton_1.setEnabled(false);
					}
				}
			}
		});

		scrollPane.setViewportView(table);

		lblOrders = new JLabel("New Orders");
		lblOrders.setBounds(10, 11, 91, 14);
		contentPane.add(lblOrders);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 210, 543, 149);
		contentPane.add(scrollPane_1);

		table2 = new JTable();
		retrieveDrivers();
		table2.setRowSelectionAllowed(true);

		table2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (table2.getSelectedRow() > -1) {
					idDriver = table2.getValueAt(table2.getSelectedRow(), 0).toString();
					System.out.println(idDriver);
					if (idOrder != null && idDriver != null) {
						btnNewButton_1.setEnabled(true);
					} else {
						btnNewButton_1.setEnabled(false);
					}
				}
			}
		});

		scrollPane_1.setViewportView(table2);
		lblDrivers = new JLabel("Drivers");
		lblDrivers.setBounds(10, 185, 46, 14);
		contentPane.add(lblDrivers);
		btnNewButton_1 = new JButton("Assign Driver");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(668, 126, 135, 23);

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				assignDriver(idOrder, idDriver);
				retrieveDrivers();
				retrievePendingOrders();
				retrieveAllOrders();
			}
		});

		contentPane.add(btnNewButton_1);
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 391, 543, 149);
		contentPane.add(scrollPane_2);

		table_1 = new JTable();
		retrieveAllOrders();
		scrollPane_2.setViewportView(table_1);

		lblInProgress = new JLabel("All Orders");
		lblInProgress.setBounds(10, 366, 91, 14);
		contentPane.add(lblInProgress);

		btnNewButton_2 = new JButton("Get All Data");
		btnNewButton_2.setBounds(668, 160, 135, 23);
		btnNewButton_2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				retrieveAllOrders();
			}
		});
		contentPane.add(btnNewButton_2);

	}

	// This method retrieves all of the pending orders from the SQL database.
	// It contains a statement that selects everything from the Booking table
	// where the status column is equal to "pending"
	// After it executes the statement, a console message is being displayed.
	public void retrievePendingOrders() {
		Connection connection;
		java.sql.PreparedStatement pst;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/group28", "root", "");
			pst = connection.prepareStatement("select * from booking where status = 'pending'");
			rs = pst.executeQuery();
			System.out.println("Successfully Load Orders!");
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e1) {
			e1.printStackTrace();

		}
	}
	
	// This method retrieves all of the active orders that can be assigned to the drivers.
	// It contains a statement that extracts all of the data needed from the database and
	// reformats it in order to be positioned in the table.
	// After the statement is executed, a console message is displayed and the data is 
	// set to the table.
	public void retrieveAllOrders() {
		Connection connection;
		java.sql.PreparedStatement pst;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/group28", "root", "");
			pst = connection.prepareStatement(
					"select firstname as 'FirstName', pickupaddress as 'From',concat(pickupdate, ' ', pickuptime) as 'When', pickoffaddress as 'To', phone as 'Phone', name as 'Driver', status as 'Status' from booking inner join drivers on booking.driver_assigned = drivers.id");
			rs = pst.executeQuery();
			System.out.println("Successfully Load Orders!");
			table_1.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e1) {
			e1.printStackTrace();

		}

	}

	// This method makes the connection to the database and it executes a statement on it.
	// The statement selects all the drivers which have the available field set to "0".
	// After the statement is executed, a console message is displayed and the data
	// is set to the table.
	public void retrieveDrivers() {
		Connection connection;
		java.sql.PreparedStatement pst;

		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/group28", "root", "");
			pst = connection.prepareStatement("select * from drivers where available = 0");
			rs = pst.executeQuery();
			System.out.println("Successfully Load Drivers!");
			table2.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e1) {
			e1.printStackTrace();

		}

	}

	// This method makes the connection to the database and executes a batch of statements.
	// The first statement updates the drivers table to change the available column to "1"
	// where the id equals to the selected driver from the table.
	// The second statement updates the booking table to change the driver_assigned column
	// to the driver selected from the table and the status column to taken where the id 
	// column is equal to the id of the order taken from the table.
	// After the statements are created, they are joined in a batch and they are executed.
	public void assignDriver(String idOrder, String idDriver) {
		Connection connection;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/group28", "root", "");
			Statement stmt = connection.createStatement();
			String sql1 = "UPDATE drivers SET available = 1 WHERE id = " + idDriver;
			String sql2 = "UPDATE booking SET driver_assigned = " + idDriver + ", status = 'taken' where id = "
					+ idOrder;
			stmt.addBatch(sql1);
			stmt.addBatch(sql2);
			stmt.executeBatch();
			this.idDriver = null;
			this.idOrder = null;
			btnNewButton_1.setEnabled(false);
			System.out.println("Successfully changed driver status!");

		} catch (Exception e1) {
			e1.printStackTrace();

		}

	}
}
