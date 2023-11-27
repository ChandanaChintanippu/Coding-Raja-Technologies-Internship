
import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;


public class ListSearchMembers extends JInternalFrame {
	
	
	private JPanel northPanel = new JPanel();
	
	private JPanel centerPanel = new JPanel();
	
	private JLabel label = new JLabel("LIST FOR SEARCHED MEMBERS");
	
	private JButton printButton;
	
	private JTable table;
	//for creating the TableColumn
	private TableColumn column = null;
	//for creating the JScrollPane
	private JScrollPane scrollPane;

	//for creating an object for the ResultSetTableModel class
	private ResultSetTableModel tableModel;

	
	public ListSearchMembers(String query) {
		
		super("Searched Members", false, true, false, true);
		
		setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/List1.gif")));
		
		Container cp = getContentPane();

		
		final String JDBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
		final String DATABASE_URL = "jdbc:odbc:LMS_DB";
		final String DEFAULT_QUERY = query;

		
		try {
			tableModel = new ResultSetTableModel(JDBC_DRIVER, DATABASE_URL, DEFAULT_QUERY);
			//for setting the Query
			try {
				tableModel.setQuery(DEFAULT_QUERY);
			}
			catch (SQLException sqlException) {
			}
		}
		catch (ClassNotFoundException classNotFound) {
			System.out.println("ListSearchMembers.java\n" + classNotFound.toString());
		}
		catch (SQLException sqlException) {
		}
		//for setting the table with the information
		table = new JTable(tableModel);
		//for setting the size for the table
		table.setPreferredScrollableViewportSize(new Dimension(700, 200));
		
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		//for setting the scrollpane to the table
		scrollPane = new JScrollPane(table);

		//for setting the size for the table columns
		for (int i = 0; i < 5; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) //MemberID
				column.setPreferredWidth(30);
			if (i == 1) //ID
				column.setPreferredWidth(20);
			if (i == 2) //Name
				column.setPreferredWidth(150);
			if (i == 3) //E-MAIL
				column.setPreferredWidth(120);
			if (i == 4) //Expired
				column.setPreferredWidth(40);
			
		}
		
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		northPanel.add(label);
		
		cp.add("North", northPanel);

		
		centerPanel.setLayout(new BorderLayout());
		//for creating an image for the button
		ImageIcon printIcon = new ImageIcon(ClassLoader.getSystemResource("images/Print1.gif"));
		
		printButton = new JButton("print the members", printIcon);
		//for setting the tip text
		printButton.setToolTipText("Print");
		
		printButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		centerPanel.add(printButton, BorderLayout.NORTH);
		
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		
		centerPanel.setBorder(BorderFactory.createTitledBorder("Members:"));
		
		cp.add("Center", centerPanel);

		
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Thread runner = new Thread() {
					public void run() {
						try {
							PrinterJob prnJob = PrinterJob.getPrinterJob();
							prnJob.setPrintable(new PrintingMembers(DEFAULT_QUERY));
							if (!prnJob.printDialog())
								return;
							setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
							prnJob.print();
							setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						}
						catch (PrinterException ex) {
							System.out.println("Printing error: " + ex.toString());
						}
					}
				};
				runner.start();
			}
		});
		//for setting the visible to true
		setVisible(true);
		//to show the frame
		pack();
	}
}