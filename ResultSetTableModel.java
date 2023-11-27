import javax.swing.table.AbstractTableModel;
import java.sql.*;

public class ResultSetTableModel extends AbstractTableModel{
    
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private ResultSetMetaData MetaData;
    private int numberOfRow;
    
    private boolean ConnectedToDatabase = false;
    
    //initialize resultset and rstd data object
    //number of rows
    
    public ResultSetTableModel(String db_driver,String db_url,String db_query) throws SQLException,ClassNotFoundException{
        
        Class.forName(db_driver);
        con = DriverManager.getConnection(db_url);
        st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        
        //Update database Connection
        
        ConnectedToDatabase = true;
        
        //set query 
        setQuery(db_query);  
    }
    
    //get class represent column type
    
    public Class getColumnClass(int column) throws IllegalStateException{
        
        //database connection available
        
       if (!ConnectedToDatabase) throw new IllegalStateException("Not Connected to Database");
        
        try{
            
            //Java class of column
            
           String className = MetaData.getColumnName(column + 1);
           
           //return class object that className
           
           return Class.forName(className);
            
        }catch(Exception ex){
            
            ex.printStackTrace();
        }
        
        //problm assume type object
        return Object.class;
    }
    
    //get numdber of column in Resultset
    
    public int getColumnCount() throws IllegalStateException{
        
        if (!ConnectedToDatabase) throw new IllegalStateException("Not Connected to Database");
        
        //number of column
       try {
			return MetaData.getColumnCount();
		}
		        // catch SQLExceptions and print error message
		catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	
        //problem occur, return 0 for number of column
        return 0;
        }
    //get name of particular column in Resultset
    
    public String getColumnName(int column) throws IllegalStateException{
        
        if (!ConnectedToDatabase) throw new IllegalStateException("Not Connected to Database");
        
        try{
            
            return MetaData.getColumnName(column + 1);
            
        }catch(SQLException sqle){
            
            sqle.printStackTrace();
        }
        
        //if problem is return string empty column name
        
        return "";
        
    }
    
   //number of row n result set
    
    public int getRowCount() throws IllegalStateException{
        
        if (!ConnectedToDatabase) throw new IllegalStateException("Not Connected to Database");
        
        return numberOfRow;
    }
    
    //value in particular row and column
    
    public Object getValueAt(int row,int column) throws IllegalStateException{
        
         if (!ConnectedToDatabase) throw new IllegalStateException("Not Connected to Database");
         
         try{
             
             rs.absolute(row + 1);
             return rs.getObject(column + 1);
             
             
         }catch(SQLException sqle){
             
             sqle.printStackTrace();
         }
         
         //if problem to return empty string object
         
         return "";
         
    }
    
    //set new database query in string
    
    public void setQuery(String query) throws SQLException,IllegalStateException{
        
        if (!ConnectedToDatabase) throw new IllegalStateException("Not Connected to Database");
        
        //query execute
        
        rs = st.executeQuery(query);
        
        //obtain metta data for resultset
        
        MetaData = rs.getMetaData();
        
        //number of row in resultset
        
        rs.last(); //move to last row
        
        //get row number
        numberOfRow = rs.getRow();
        
        //notify JTable Model has change
        
        fireTableStructureChanged();
        
    }
    
    //close statement and connection
    
    public void disconFormData(){
        
        try{
            
            st.close();
            con.close();
            
        }catch(SQLException sqle){
            
            sqle.printStackTrace();
        }
        
        //update databbase connection status
        
        finally{
            
            ConnectedToDatabase = false;
        }
    }
 }
