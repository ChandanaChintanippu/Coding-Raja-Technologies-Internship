
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SearchBooksAndMembers extends JInternalFrame {
	
	private JPanel northPanel = new JPanel();
	
	private JLabel title = new JLabel("Search for Books and Members");

	
	private JPanel center = new JPanel();

	
	private JPanel centerBooksPanel = new JPanel();
	
	private JPanel searchBooksPanel = new JPanel();
	
	private JPanel searchBooksButtonPanel = new JPanel();

	//for creating the table
	private JLabel searchBooksLabel = new JLabel(" Search by: ");
	//for creating JComboBox
	private JComboBox searchBooksTypes;
	//for creating String[]
	private String[] booksTypes = {"BookID", "Subject", "Title", "Author", "Publisher", "ISBN"};
	
	private JLabel booksKey = new JLabel("Enter Keyword: ");
	
	private JTextField booksKeyTextField = new JTextField();
	
	private JButton searchBooksButton = new JButton("Search");

	
	private JPanel centerMembersPanel = new JPanel();
	
	private JPanel searchMembersPanel = new JPanel();
	
	private JPanel searchMembersButtonPanel = new JPanel();

	//for creating the table
	private JLabel searchMembersLabel = new JLabel(" Search by: ");
	//for creating JComboBox
	private JComboBox searchMembersTypes;
	//for creating String[]
	private String[] membersTypes = {"MemberID", "Name", "E-Mail"};
	
	private JLabel membersKey = new JLabel("Enter Keyword: ");
	
	private JTextField membersKeyTextField = new JTextField();
	
	private JButton searchMembersButton = new JButton("Search");

	
	private JPanel southPanel = new JPanel();
	
	private JButton cancelButton = new JButton("Cancel");

	//for creating an array of string to store the data
	private String[] booksData;
	private String[] membersData;
	//create objects from another classes for using them in the ActionListener
	private ListSearchBooks listBooks;
	private ListSearchMembers listMembers;
	private Books book;
	private Members member;

	//for check information from the text field
	public boolean isBooksDataCorrect() {
		booksData = new String[2];
		booksData[0] = searchBooksTypes.getSelectedItem().toString();
		for (int i = 1; i < booksData.length; i++) {
			if (!booksKeyTextField.getText().equals("")) {
				if (searchBooksTypes.getSelectedItem().toString().equals("BookID")) {
					booksData[i] = booksKeyTextField.getText();
				}
				else
					booksData[i] = "'%" + booksKeyTextField.getText() + "%'";
			}
			else
				return false;
		}
		return true;
	}

	//for check information from the text field
	public boolean isMembersDataCorrect() {
		membersData = new String[2];
		membersData[0] = searchMembersTypes.getSelectedItem().toString();
		for (int i = 1; i < membersData.length; i++) {
			if (!membersKeyTextField.getText().equals("")) {
				if (searchMembersTypes.getSelectedItem().toString().equals("MemberID")) {
					membersData[i] = membersKeyTextField.getText();
				}
				else
					membersData[i] = "'%" + membersKeyTextField.getText() + "%'";
			}
			else
				return false;
		}
		return true;
	}

	
	public SearchBooksAndMembers() {
		
		super("Search", false, true, false, true);
		//for setting the icon
		setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Find1.gif")));
		//for getting the graphical user interface components display area
		Container cp = getContentPane();

		
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		title.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		northPanel.add(title);
		
		cp.add("North", northPanel);

		
		center.setLayout(new BorderLayout());

		
		centerBooksPanel.setLayout(new BorderLayout());
		
		searchBooksPanel.setLayout(new GridLayout(2, 2, 1, 1));
		
		searchBooksPanel.add(searchBooksLabel);
		//for adding the JComboBos[]
		searchBooksPanel.add(searchBooksTypes = new JComboBox(booksTypes));
		
		searchBooksPanel.add(booksKey);
		
		searchBooksPanel.add(booksKeyTextField);
		
		centerBooksPanel.add("North", searchBooksPanel);

		
		searchBooksButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		searchBooksButtonPanel.add(searchBooksButton);
		
		centerBooksPanel.add("South", searchBooksButtonPanel);
		
		centerBooksPanel.setBorder(BorderFactory.createTitledBorder("Search for a books:"));
		
		center.add("West", centerBooksPanel);

		
		centerMembersPanel.setLayout(new BorderLayout());
		
		searchMembersPanel.setLayout(new GridLayout(2, 2, 1, 1));
		
		searchMembersPanel.add(searchMembersLabel);
		//for adding the JComboBos[]
		searchMembersPanel.add(searchMembersTypes = new JComboBox(membersTypes));
		
		searchMembersPanel.add(membersKey);
		
		searchMembersPanel.add(membersKeyTextField);
		
		centerMembersPanel.add("North", searchMembersPanel);

		
		searchMembersButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		searchMembersButtonPanel.add(searchMembersButton);
		
		centerMembersPanel.add("South", searchMembersButtonPanel);
		
		centerMembersPanel.setBorder(BorderFactory.createTitledBorder("Search for a members:"));
		
		center.add("East", centerMembersPanel);

		
		cp.add("Center", center);

		searchBooksLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		searchBooksTypes.setFont(new Font("Tahoma", Font.BOLD, 11));
		booksKey.setFont(new Font("Tahoma", Font.BOLD, 11));
		booksKeyTextField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchBooksButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		searchMembersLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		searchMembersTypes.setFont(new Font("Tahoma", Font.BOLD, 11));
		membersKey.setFont(new Font("Tahoma", Font.BOLD, 11));
		membersKeyTextField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		searchMembersButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));

		
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		southPanel.add(cancelButton);
		
		southPanel.setBorder(BorderFactory.createEtchedBorder());
		
		cp.add("South", southPanel);

		searchBooksButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//for check  missing information
				if (isBooksDataCorrect()) {
					book = new Books();
					String bookQuery = "SELECT BookID, Subject, Title, Author, Publisher," +
					        "Copyright, Edition, Pages, NumberOfBooks,ISBN,Available,ShelfNo FROM Books" +
					        " WHERE " + booksData[0] + " LIKE " + booksData[1];
					book.connection(bookQuery);
					int bookID = book.getBookID();
					if (bookID != 0) {
						listBooks = new ListSearchBooks(bookQuery);
						getParent().add(listBooks);
						try {
							listBooks.setSelected(true);
						}
						catch (java.beans.PropertyVetoException e) {
						}
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(null, "No Match(es)", "Error", JOptionPane.ERROR_MESSAGE);
						booksKeyTextField.setText(null);
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		searchMembersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (isMembersDataCorrect()) {
					member = new Members();
					String memberQuery = "SELECT MemberID, ID, Name, Email,Expired" +
					        " FROM Members WHERE " + membersData[0] + " LIKE " + membersData[1];
					member.Connection(memberQuery);
					int memberID = member.getMemberID();
					if (memberID != 0) {
						listMembers = new ListSearchMembers(memberQuery);
						getParent().add(listMembers);
						try {
							listMembers.setSelected(true);
						}
						catch (java.beans.PropertyVetoException e) {
						}
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(null, "No Match(es)", "Error", JOptionPane.ERROR_MESSAGE);
						membersKeyTextField.setText(null);
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		});
		//for adding the action listener for the button to dispose the frame
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
		//for setting the visible to true
		setVisible(true);
		//show the internal frame
		pack();
	}
}