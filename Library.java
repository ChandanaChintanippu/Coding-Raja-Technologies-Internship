import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Library extends JFrame implements ActionListener {

   
   
    private JPanel searchPanel = new JPanel();
   
    private JToolBar searchToolBar = new JToolBar();
   
    private JLabel searchLabel = new JLabel("Book title: ");
   
    private JTextField searchTextField = new JTextField(15);
    
    private JButton goButton = new JButton("Go");

    private JDesktopPane desktop = new JDesktopPane();
    private JSplitPane splitPane;
    private JScrollPane desktopScrollPane;
    private JScrollPane treeScrollPane;
    
    private MenuBar menu;
    private Toolbar toolbar;
    private Statusbar statusbar = new Statusbar();
    private ListBooks listBooks;
    private AddBooks addBooks;
    private BorrowBooks borrowBooks;
    private ReturnBooks returnBooks;

    private AddMembers addMembers;
    private ListMembers listMembers;
    private SearchBooksAndMembers search;


    
    public Library() {
        
        super("Library System");
        //for setting the size
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        //setSize(screenSize.width, screenSize.height - 30);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        
        
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.getImage(ClassLoader.getSystemResource("images/Host1.gif"));
        setIconImage(image);

        menu = new MenuBar();
        toolbar = new Toolbar();
        
        
        
        // menu bar Action.........
        
        setJMenuBar(menu);
        menu.exit.addActionListener(this);
        menu.addBook.addActionListener(this);
        menu.listBook.addActionListener(this);
        menu.addMember.addActionListener(this);
        menu.listMember.addActionListener(this);
        menu.searchBooksAndMembers.addActionListener(this);
        menu.borrowBook.addActionListener(this);
        menu.returnBook.addActionListener(this);
        menu.about.addActionListener(this);
        
        //get the graphical user interface components display the desktop
        Container cp = getContentPane();
        desktop.setBackground(Color.GRAY);
        cp.add("Center", desktop);
        
        searchLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        
        searchTextField.setFont(new Font("Tahoma", Font.PLAIN, 12));
        goButton.setFont(new Font("Tahoma", Font.BOLD, 9));
        
        searchToolBar.add(searchLabel);
       
        searchToolBar.add(searchTextField);
        
        searchToolBar.add(goButton);
        
        goButton.addActionListener(this);
        
        searchPanel.setLayout(new BorderLayout());
        
        searchPanel.add("Center", toolbar);
       
        cp.add("North", searchPanel);
        
        cp.add("South", statusbar);

        for (int i = 0; i < toolbar.imgName5.length; i++) {
            //for adding the action to the button
            toolbar.btn[i].addActionListener(this);
        }

        //for adding WindowListener to the program
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        //show the program
        show();
    }

    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu.addBook || e.getSource() == toolbar.btn[0]) {
            Thread runner = new Thread() {

                public void run() {
                    addBooks = new AddBooks();
                    desktop.add(addBooks);
                    try {
                        addBooks.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        if (e.getSource() == menu.listBook || e.getSource() == toolbar.btn[1]) {
            Thread runner = new Thread() {

                public void run() {
                    listBooks = new ListBooks();
                    desktop.add(listBooks);
                    try {
                        listBooks.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
      if (e.getSource() == menu.addMember || e.getSource() == toolbar.btn[2]) {
            Thread runner = new Thread() {

                public void run() {
                    addMembers = new AddMembers();
                    desktop.add(addMembers);
                    try {
                        addMembers.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        if (e.getSource() == menu.listMember || e.getSource() == toolbar.btn[3]) {
            Thread runner = new Thread() {

                public void run() {
                    listMembers = new ListMembers();
                    desktop.add(listMembers);
                    try {
                        listMembers.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        if (e.getSource() == menu.searchBooksAndMembers || e.getSource() == toolbar.btn[4]) {
            Thread runner = new Thread() {

                public void run() {
                    search = new SearchBooksAndMembers();
                    desktop.add(search);
                    try {
                        search.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        if (e.getSource() == menu.borrowBook || e.getSource() == toolbar.btn[5]) {
            Thread runner = new Thread() {

                public void run() {
                    borrowBooks = new BorrowBooks();
                    desktop.add(borrowBooks);
                    try {
                        borrowBooks.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        if (e.getSource() == menu.returnBook || e.getSource() == toolbar.btn[6]) {
            Thread runner = new Thread() {

                public void run() {
                    returnBooks = new ReturnBooks();
                    desktop.add(returnBooks);
                    try {
                        returnBooks.setSelected(true);
                    } catch (java.beans.PropertyVetoException e) {
                    }
                }
            };
            runner.start();
        }
        
        if (e.getSource() == menu.exit || e.getSource() == toolbar.btn[7]) {
            dispose();
            System.exit(0);
        }
        
        if(e.getSource() == menu.about || e.getSource() == toolbar.btn[8]){
            
            Thread runner = new Thread(){
          
              public void run(){
                  
                  JOptionPane.showMessageDialog(null, new About(),"About Library",JOptionPane.PLAIN_MESSAGE);
              }
          };
          runner.start();
        }
    }
}
