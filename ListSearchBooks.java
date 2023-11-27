
import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;


public class ListSearchBooks extends JInternalFrame {
	
	
	private JPanel northPanel = new JPanel();
	
	private JPanel centerPanel = new JPanel();
	
	private JLabel label = new JLabel(" LIST FOR  SEARCHED BOOKS");
	
	private JButton printButton;
	
	private JTable table;
	
	private TableColumn column = null;
	
	private JScrollPane scrollPane;

	//for creating an object for the ResultSetTableModel class
	private ResultSetTableModel tableModel;

	
	public ListSearchBooks(String query) {
		
		super("Searched Books", false, true, false, true);
		
		setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/List1.gif")));
		
		Container cp = getContentPane();

		
		final String JDBC_DRIVER = "sun.jdbc.odbc.JdbcOdbcDriver";
		final String DATABASE_URL = "jdbc:odbc:LMS_DB";
		final String DEFAULT_QUERY = query;

		//for bassing the required information to the ResultSetTableModel object
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
		}
		catch (SQLException sqlException) {
		}

		//for setting the table with the information
		table = new JTable(tableModel);
		//for setting the size for the table
		table.setPreferredScrollableViewportSize(new Dimension(990, 200));
		
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		//for setting the scrollpane to the table
		scrollPane = new JScrollPane(table);

		//for setting the size for the table columns
		for (int i = 0; i < 12; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0) //BookID
				column.setPreferredWidth(20);
			if (i == 1) //Subject
				column.setPreferredWidth(100);
			if (i == 2) //Title
				column.setPreferredWidth(150);
			if (i == 3) //Auther
				column.setPreferredWidth(50);
			if (i == 4) //Publisher
				column.setPreferredWidth(70);
			if (i == 5) //Copyright
				column.setPreferredWidth(40);
			if (i == 6) //Edition
				column.setPreferredWidth(40);
			if (i == 7) //Pages
				column.setPreferredWidth(40);
			if (i == 8) //NumberOfBooks
				column.setPreferredWidth(80);
			if (i == 9) //ISBN
				column.setPreferredWidth(70);
			if (i == 10) //Availble
				column.setPreferredWidth(30);
			if (i == 11) //ShelfNo
				column.setPreferredWidth(30);
                      
		}
		
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		northPanel.add(label);
		
		cp.add("North", northPanel);

		
		centerPanel.setLayout(new BorderLayout());
		
		ImageIcon printIcon = new ImageIcon(ClassLoader.getSystemResource("images/Print1.gif"));
		
		printButton = new JButton("print the books", printIcon);
		
		printButton.setToolTipText("Print");
		
		printButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		centerPanel.add(printButton, BorderLayout.NORTH);
		
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		
		centerPanel.setBorder(BorderFactory.createTitledBorder("Books:"));
		
		cp.add("Center", centerPanel);

		
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Thread runner = new Thread() {
					public void run() {
						try {
							PrinterJob prnJob = PrinterJob.getPrinterJob();
							prnJob.setPrintable(new PrintingBooks(DEFAULT_QUERY));
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