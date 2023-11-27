import javax.swing.*;
import java.awt.*;

public class About extends JPanel{
    
    public About(){
        
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/java.jpg"));
        
        //Create Lable and ImageIcon setting
        
        JLabel lbl = new JLabel(icon);
        //add Label to the Panel
        this.add(lbl);
        
        JLabel lbl2 = new JLabel("<html><b>Library Management System Ver 2.0<br><br><b><p>Developed By:Bhaumik Mevada</p><br>"
                + "Copyright<font size=\"3\">&copy;</font>2015 - 2016</li></html>"
             );
        
        lbl2.setFont(new Font("Tahoma",Font.PLAIN,12));
        this.add(lbl2);
                
    }
}
