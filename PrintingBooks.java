import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.sql.*;
import java.util.*;

public class PrintingBooks extends JInternalFrame implements Printable{
    
    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    
    private String url = "jdbc:odbc:LMS_DB";
    
    private JTextArea txt_area  = new JTextArea();
    
    //create vector to use the print
    
    private Vector vector_line;
    private static final int TAB_SIZE = 5;
    
    public PrintingBooks(String query){
        
        super("Printing Books", false, true, false, true);
        Container c = getContentPane();
       
        txt_area.setFont(new Font("Tahoma", Font.PLAIN, 9));
        c.add(txt_area);
        
        try {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	}
	catch (ClassNotFoundException ea) {
		System.out.println(ea.toString());
	}
	catch (Exception e) {
		System.out.println(e.toString());
	}
                
       try{
                
                con = DriverManager.getConnection(url);
                st = con.createStatement();
                rs = st.executeQuery(query);
                
                txt_area.append("====================== Book Information ====================\n\n");
                
                while(rs.next()){
                    
                    txt_area.append("Subject: "+rs.getString("Subject") + "\n" + "Title: "+rs.getString("Title") + "\n" + 
                            "Author: "+rs.getString("Author") + "\n" + "Copyright: "+ rs.getString("Copyright") + "\n" +
                            "Edition: "+rs.getString("Edition") + "\n" + "ISBN: "+rs.getString("ISBN") + "\n\n");
                            
                }
                txt_area.append("====================== Book Information ====================\n\n");
                
                st.close();
                con.close();
                rs.close();
                
            }catch(SQLException sqle){
                
                System.out.println(sqle.toString());
        }
            setVisible(true);
            pack(); //show Frame
    
    }
    
    public int print(Graphics g,PageFormat page_format,int pageIndex) throws PrinterException{
        
        g.translate((int)page_format.getImageableX(), (int)page_format.getImageableY());
        int wpage = (int) page_format.getImageableWidth();
        int hpage = (int) page_format.getImageableHeight();
        g.setClip(0,0,wpage,hpage);
        
        Font font = txt_area.getFont();
        g.setFont(font);
        
        FontMetrics fm = g.getFontMetrics();
        int hline = fm.getHeight();
        
        if(vector_line == null)
            
          vector_line = getLines(fm,wpage);
            
        int num_lines = vector_line.size();
        int vlines_PerPage = Math.max(hpage / hline,1);
        int num_pages = (int)Math.ceil((double) num_lines / (double) vlines_PerPage);
        
        if(pageIndex >= num_pages){
            
            vector_line = null;
            return NO_SUCH_PAGE;
            
        }
        int x = 0;
        int y = fm.getAscent();
        int line_Index = vlines_PerPage * pageIndex;
        
        while(line_Index < vector_line.size() && y < hpage){
            
            String str = (String) vector_line.get(line_Index);
            g.drawString(str, x, y);
            y +=hline;
            line_Index++;
            
        }
        return PAGE_EXISTS;
    }
    
    protected Vector getLines(FontMetrics fm,int wpage){
        
        Vector vctr = new Vector();
        
        String txt = txt_area.getText();
        String prevToken = "";
            
        StringTokenizer stokn = new StringTokenizer(txt,"\n\r",true);
        
        while(stokn.hasMoreTokens()){
                
                String line = stokn.nextToken();
                
                if(line.equals("\r"))
                    continue;
                //Stringtokenizer will be ignore empty lines
                
                if(line.equals("\n") && prevToken.equals("\n"))
                    vctr.add("");
                
                prevToken = line;
                
                if(line.equals("\n"))
                    continue;
                
                StringTokenizer stokn2 = new StringTokenizer(line," \t",true);
                        
                String line2 = "";
                
                while(stokn2.hasMoreTokens()){
                    
                    String token = stokn2.nextToken();
                    
                    if(token.equals("\t")){
                        
                        int num_space = TAB_SIZE - line2.length() % TAB_SIZE;
                        
                        token = "";
                        
                        for(int i=0;i<num_space;i++)
                            token +=" ";
                        
                    }
                    int line_len = fm.stringWidth(line2 + token);
                    if(line_len > wpage && line2.length() > 0){
                        
                        vctr.add(line2);
                        line2 = token.trim();
                        continue;
                    }
                    
                    line2 += token;
                    
                }
                vctr.add(line2);
                
            }
            return vctr;
    }
}
