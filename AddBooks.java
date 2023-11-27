import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class AddBooks extends JInternalFrame {

    
    private JPanel northPanel = new JPanel();
   
    private JLabel northLabel = new JLabel("BOOK INFORMATION");

    
    private JPanel centerPanel = new JPanel();
   
    private JPanel informationLabelPanel = new JPanel();

    
    private JLabel[] informationLabel = new JLabel[9];
    private JLabel lblShelfNo = new JLabel(" Shelf No");
    private JTextField txtShelfNo = new JTextField();
   
    private String[] str_info = {
        "Book Subject: ","Book Title: ","Book Author: ","Book Publisher: ","Copyright Book ",
        "Edition Number: ","Number Of Pages: ","ISBN Book: ","Number Of Copies: "
    };
    
    private JPanel informationTextFieldPanel = new JPanel();
    
    private JTextField[] informationTextField = new JTextField[9];

    
    private JPanel insertInformationButtonPanel = new JPanel();
    
    private JButton insertInformationButton = new JButton("Insert the Information");

    
    private JPanel southPanel = new JPanel();
   
    private JButton OKButton = new JButton("Exit");

    //create objects from another classes for using them in the ActionListener
    private Books book;
   
    private String[] data;
    
    private boolean availble = true;

    
    public boolean isCorrect() {
        data = new String[9];
        for (int i = 0; i < informationLabel.length; i++) {
            if (!informationTextField[i].getText().equals("")) {
                data[i] = informationTextField[i].getText();
            } else {
                return false;
            }
        }
        return true;
    }

    //for setting the array of JTextField to empty
    public void clearTextField() {
        for (int i = 0; i < informationTextField.length; i++) {
            informationTextField[i].setText(null);
        }
        txtShelfNo.setText(null);
    }

    
    public AddBooks() {
        
        super("Add Books", false, true, false, true);
        
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Add1.gif")));
        //for getting the graphical user interface components display area
        Container cp = getContentPane();

        
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        northLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        
        northPanel.add(northLabel);
        
        cp.add("North", northPanel);

        
        centerPanel.setLayout(new BorderLayout());
        
        centerPanel.setBorder(BorderFactory.createTitledBorder("Add a new book:"));
        
        informationLabelPanel.setLayout(new GridLayout(11, 1, 1, 1));
        
        for (int i = 0; i < informationLabel.length; i++) {
            informationLabelPanel.add(informationLabel[i] = new JLabel(str_info[i]));
            informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
        }
        centerPanel.add("West", informationLabelPanel);

        
        informationTextFieldPanel.setLayout(new GridLayout(11, 1, 1, 1));
       
         //add labels to the panel.
        //add panel to the container
        
        for (int i = 0; i < informationTextField.length; i++) {
            informationTextFieldPanel.add(informationTextField[i] = new JTextField(25));
            informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
        }
        lblShelfNo.setFont(new Font("Tahoma", Font.BOLD, 11));
        informationLabelPanel.add(lblShelfNo);
        txtShelfNo.setFont(new Font("Tahoma", Font.PLAIN, 11));
        informationTextFieldPanel.add(txtShelfNo);
        centerPanel.add("East", informationTextFieldPanel);

       //add labels to the panel.
        //add panel to the container
        
        insertInformationButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        insertInformationButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        insertInformationButtonPanel.add(insertInformationButton);
        centerPanel.add("South", insertInformationButtonPanel);
        cp.add("Center", centerPanel);

        //add labels to the panel.
        //add panel to the container
        
        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        OKButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        southPanel.add(OKButton);
        southPanel.setBorder(BorderFactory.createEtchedBorder());
        cp.add("South", southPanel);

        
        insertInformationButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {

                //for checking if there is a missing information
                if (isCorrect()) {
                    Thread runner = new Thread() {

                        public void run() {
                            book = new Books();
                            //Check Information in the Database
                            
                            book.connection("SELECT BookID FROM Books WHERE ISBN = '" + data[7] + "'");
                            String ISBN = book.getISBN();
                            if (!data[7].equalsIgnoreCase(ISBN)) {
                                try{
                                    String sql="INSERT INTO Books (Subject,Title,Author,Publisher,Copyright," +
                                        "Edition,Pages,ISBN,NumberOfBooks,NumberOfAvailableBooks,Available,ShelfNo) VALUES "+
                                        " (?,?,?,?,?,?,?,?,?,?,?,?)";
                                        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                                        Connection con=DriverManager.getConnection("jdbc:odbc:LMS_DB");
                                        PreparedStatement ps=con.prepareStatement(sql);
                                        ps.setString(1, data[0]);
                                   ps.setString(2, data[1]);
                                   ps.setString(3, data[2]);
                                   ps.setString(4, data[3]);
                                   ps.setInt(5,Integer.parseInt(data[4]));
                                   ps.setInt(6, Integer.parseInt(data[5]));
                                   ps.setInt(7, Integer.parseInt(data[6]));
                                   ps.setString(8, data[7]);
                                   ps.setInt(9, Integer.parseInt(data[8]));
                                   ps.setInt(10, Integer.parseInt(data[8]));
                                   ps.setBoolean(11, availble);
                                   ps.setInt(12, Integer.parseInt(txtShelfNo.getText()));
                                   ps.executeUpdate();
                                   
                                   JOptionPane.showMessageDialog(null, "Book Inserted Success !!!,Thank You", "Success", JOptionPane.INFORMATION_MESSAGE);
                                }catch(Exception ex){
                                    JOptionPane.showMessageDialog(null, ex.toString());
                                }
                                
                                
                                
                                //for setting the array of JTextField to empty
                                clearTextField();
                            } else {
                                JOptionPane.showMessageDialog(null, "Book Is Already Exists in Library", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    };
                    runner.start();
                } //if there is a missing data, then display Message Dialog
                else {
                    JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        //dispose the frame
        OKButton.addActionListener(new ActionListener() {

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