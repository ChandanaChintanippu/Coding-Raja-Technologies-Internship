import javax.swing.*;
import java.awt.*;
import javax.swing.border.SoftBevelBorder;

public class Statusbar extends JPanel{
    
   private JLabel lbl_status = new JLabel(" ");
   
   public Statusbar(){
       
       lbl_status.setFont(new Font("Tahoma", Font.BOLD, 10));
       this.add(lbl_status);
       this.setBorder(new SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
   }
}
