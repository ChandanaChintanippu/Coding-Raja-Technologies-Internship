import java.sql.*;

public class Members {
    
    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    
    private int memberID;
    private int id;
    private String password;
    private String name;
    private String email;
    private int numberofbooks;
    private int money;
    private Date expired;
    private String url = "jdbc:odbc:LMS_DB";
    
    public Members(){
        
        
    }
    /*public Members(int memberID, int ID, String password, String name, String email, String major, int numberOfBooks, int mony, Date expired) {
		this.memberID = memberID;
		this.ID = ID;
		this.password = password;
		this.name = name;
		this.email = email;
		this.major = major;
		this.numberOfBooks = numberOfBooks;
		this.mony = mony;
		this.expired = expired;
	}*/
    
    public int getMemberID(){
        
        return memberID;
    }
    public int getID(){
        
        return id;
    }
    public String getPassword(){
        
        return password;
    }
    public String getName(){
        
        return name;
        
    }
    public String getEmail(){
        
        return email;
        
    }
    public int getNumberOfBooks(){
        
        return numberofbooks;
        
    }
    public int getMoney(){
        
        return money;
        
    }
    public Date getExpired(){
        
        return expired;
        
    }
    public void Connection(String query){
        
        try{
            
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        }catch(ClassNotFoundException cnfe){
            
            System.out.println("Members.java\n" + cnfe.toString());
        }catch(Exception ex){
            
            System.out.println("Members.java\n" + ex.toString());
        }
        try{
            con = DriverManager.getConnection(url);
            st = con.createStatement();
            rs = st.executeQuery(query);
            
            while(rs.next()){
                
                memberID = rs.getInt(1);
                id = rs.getInt(2);
                password = rs.getString(3);
                name = rs.getString(4);
                email = rs.getString(5);
                numberofbooks = rs.getInt(6);
                money = rs.getInt(7);
                expired = rs.getDate(8);
            }
            con.close();
            rs.close();
            st.close();
            
        }catch(SQLException sqle){
            
            System.out.println("Members.java\n" + sqle.toString());
        }
    }
    public void update(String query){
        
        try{
            
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        }catch(ClassNotFoundException cnfe){
            
            System.out.println("Members.java\n" + cnfe.toString());
        }catch(Exception ex){
            
            System.out.println("Members.java\n" + ex.toString());
        }
        //Close statement and connection
        try{
            
            con = DriverManager.getConnection(url);
            st = con.createStatement();
            st.executeUpdate(query);
            st.close();
            con.close();
        }catch(SQLException sqle){
            
            System.out.println("Members.java\n" + sqle.toString());
        }
    }
}
