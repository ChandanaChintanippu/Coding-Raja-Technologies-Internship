import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.sql.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class PrintingMembers extends JInternalFrame implements Printable{
    
    
    private Connection con = null;
    private Statement st = null;
    private ResultSet rs = null;
    
    private String url = "jdbc:odbc:LMS_DB";
    
    //Create text area
    
    private JTextArea txt_area = new JTextArea();
    
    //Create Vector line use in the print
    private Vector vec_line;
    
    private static final int TAB_SIZE = 10;
    
    public PrintingMembers(String query){
        
        super("Printing Members");
        
        Container c = getContentPane();
        
        txt_area.setFont(new Font("Tahoma",Font.PLAIN,10));
        
        c.add(txt_area);
        
        try{
            
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            
        }catch(ClassNotFoundException cnfe){
            
            System.out.println(cnfe.toString());
            
        }catch(Exception ex){
            
            System.out.println(ex.toString());
        }
        
        try{
            
            con = DriverManager.getConnection(url);
            st = con.createStatement();
            rs = st.executeQuery(query);
            
            txt_area.append("=============== Members Information ===============\n\n");
            while(rs.next()){
                
                txt_area.append("Member ID: " + rs.getString("ID") + "\n" + "Name: " + rs.getString("Name") + "\n" +
                        "Expired: " + rs.getString("Expired") + "\n\n");
                txt_area.append("=============== Members Information ===============");
            }
            con.close();
            st.close();
            rs.close();
            
        }catch(SQLException sqle){
            
            System.out.println(sqle.toString());
        }
        setVisible(true);
        pack();
    }
    public int print(Graphics g,PageFormat pf,int pageIndex) throws PrinterException{
        
        g.translate((int)pf.getImageableX(),(int) pf.getImageableY());
        
        int wpage = (int) pf.getImageableWidth();
        int hpage = (int) pf.getImageableHeight();
        g.setClip(0,0,wpage,hpage);
        
        g.setColor(txt_area.getBackground());
        g.fillRect(0, 0, wpage, hpage);
        g.setColor(txt_area.getBackground());
        
        Font font = txt_area.getFont();
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        
        int hline =   fm.getHeight();
        
        if(vec_line == null)
            vec_line = getLines(fm,wpage);
        
        int num_line = vec_line.size();
        int perpage_line = Math.max(hpage / hline,1);
        int numpage = (int)Math.ceil((double)num_line / (double) perpage_line);
        
        if(pageIndex >=numpage){
            
            vec_line = null;
            
            return NO_SUCH_PAGE;
        }
        int x=0;
        int y = fm.getAscent();
        int lineIndex = perpage_line * pageIndex;
        
        while(lineIndex < vec_line.size() && y < hpage){
            
            String str = (String) vec_line.get(lineIndex);  
            g.drawString(str, x, y);
            y += hline;
            lineIndex++;
            
        }
        return PAGE_EXISTS;
    }
    
    protected Vector getLines(FontMetrics fm,int wpage){
           
           Vector v = new Vector();
           
           String txt = txt_area.getText();
           String prevtoken = "";
           StringTokenizer st = new StringTokenizer(txt,"\n\r",true);
           
           while(st.hasMoreTokens()){
               String line = st.nextToken();
               
               if(line.equals("\r"))
                   continue;
               //String tokenrize ignore empty lines
               
               if(line.equals("\n") && prevtoken.equals("\n"))
                   v.add("");
               prevtoken = line;
               if(line.equals("\n"))
                   continue;
               
               StringTokenizer st2 = new StringTokenizer(line," \t",true);
               String line2="";
               
               while(st2.hasMoreElements()){
                   
                   String token = st2.nextToken();
                   
                   if(token.equals("\t")){
                       
                       int numspace = TAB_SIZE - line2.length() % TAB_SIZE;
                       token = "";
                       
                       for(int i=0;i<numspace;i++)
                            token +=" ";
                   }
                   int linelen = fm.stringWidth(line2 + token);
                   
                   if(linelen > wpage && line2.length() > 0){
                       
                       v.add(line2);
                       line2 = token.trim();
                       continue;
                   }
                   line2 +=token;
                   
               }
               v.add(line2);
           }
           return v;
       }

}
