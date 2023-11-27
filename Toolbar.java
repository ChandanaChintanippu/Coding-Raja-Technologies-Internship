
//import the packages for using the classes in them into the program

import javax.swing.*;

public class Toolbar extends JToolBar {
    
	//for creating the buttons to use them in ToolBar
	public JButton[] btn;
	//for creating the name of the image file 24*24
	public String[] imgName5 = {"images/Add5.gif", "images/List5.gif",	                                                        
	                           "images/Add5.gif",
	                               "images/List5.gif", 
	                            
	                               "images/Find5.gif", "images/Export5.gif",
	                               "images/Import5.gif",
	                          
	                               "images/Exit5.gif"};
	//for creating the tipText for the toolbar
	public String[] tipText = {"Add Books", "List All Books",	                           
	                           "Add Members", "List Members", 
	                           "Search", "Borrow Books", "Return Books","Exit","About"};
	public Toolbar() {
		btn = new JButton[19];
		for (int i = 0; i < imgName5.length; i++) {
			if (i == 2|| i ==4 || i == 5|| i == 7)
			//for adding separator to the toolBar
				addSeparator();
			//for adding the buttons to toolBar
			add(btn[i] = new JButton(new ImageIcon(ClassLoader.getSystemResource(imgName5[i]))));
			//for setting the ToolTipText to the button
			btn[i].setToolTipText(tipText[i]);
		}
	}
}