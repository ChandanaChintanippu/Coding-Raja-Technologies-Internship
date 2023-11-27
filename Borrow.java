
import java.sql.*;

public class Borrow {
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	private int bookID;
	private int memberID;
	private Date dayOfBorrowed;
	private Date dayOfReturn;
	private String URL = "jdbc:odbc:LMS_DB";

	public Borrow() {
	}

	public int getBookID() {
		return bookID;
	}

	public int getMemberID() {
		return memberID;
	}

	public Date getDayOfBorrowed() {
		return dayOfBorrowed;
	}

	public Date getDayOfReturn() {
		return dayOfReturn;
	}

	public void connection() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("Borrow.java\n" + cnfe.toString());
		}
		catch (Exception e) {
			System.out.println("Borrow.java\n" + e.toString());
		}
		
		try {
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM Borrow");
			while (resultSet.next()) {
				bookID = resultSet.getInt(1);
				memberID = resultSet.getInt(2);
				dayOfBorrowed = resultSet.getDate(3);
				dayOfReturn = resultSet.getDate(4);
			}
			resultSet.close();
			statement.close();
			connection.close();
		}
		catch (SQLException SQLe) {
			System.out.println("Borrow.java\n" + SQLe.toString());
		}
	}

	public void update(String Query) {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		catch (ClassNotFoundException cnfe) {
			System.out.println("Borrow.java\n" + cnfe.toString());
		}
		catch (Exception e) {
			System.out.println("Borrow.java\n" + e.toString());
		}
		
		try {
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();
			statement.executeUpdate(Query);
			statement.close();
			connection.close();
		}
		catch (SQLException SQLe) {
			System.out.println("Borrow.java\n" + SQLe.toString());
		}
	}
}