
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.GregorianCalendar;


public class ReturnBooks extends JInternalFrame implements ActionListener {

    
    
    private JPanel northPanel = new JPanel();
   
    private JLabel title = new JLabel("BOOK INFORMATION");

   
    private JPanel centerPanel = new JPanel();
   
    private JPanel informationPanel = new JPanel();
    
    private JLabel[] informationLabel = new JLabel[2];
   
    private String[] informationString = {"Enter Book ID:","Enter Member ID:"};
   
    private JTextField[] informationTextField = new JTextField[2];
    //for creating an array of string to store the data
    private String[] data;
    private JLabel lblFinePerDay = new JLabel("Fine/Day");
    private JTextField txtFinePerDay = new JTextField();
    private JLabel lblTotalFineAmt = new JLabel("Total fine amount");
    private JTextField txtTotalFineAmt = new JTextField();
    //for creating an Internal Panel in the center panel
    private JPanel returnButtonPanel = new JPanel();
    
    private JButton returnButton = new JButton("Return");

   
    private JPanel southPanel = new JPanel();
   
    private JButton cancelButton = new JButton("Cancel");

    //for creating an object
    private Books book;
    private Members member;
    private Borrow borrow;


    //for checking the information from the text field
    public boolean isCorrect() {
        data = new String[2];
        for (int i = 0; i < informationLabel.length; i++) {
            if (!informationTextField[i].getText().equals("")) {
                data[i] = informationTextField[i].getText();
            } else {
                return false;
            }
        }
        return true;
    }

    //for setting the array of JTextField to null
    public void clearTextField() {
        for (int i = 0; i < informationTextField.length; i++) {
            if (i != 2) {
                informationTextField[i].setText(null);
            }
            txtFinePerDay.setText(null);
            txtTotalFineAmt.setText(null);
        }
    }

    //constructor of returnBooks
    public ReturnBooks() {
        //for setting the title for the internal frame
        super("Return books", false, true, false, true);
        //for setting the icon
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Import1.gif")));
        //for getting the graphical user interface components display area
        Container cp = getContentPane();

        //for setting the layout
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
       
        title.setFont(new Font("Tahoma", Font.BOLD, 14));
       
        northPanel.add(title);
       
        cp.add("North", northPanel);

        
        centerPanel.setLayout(new BorderLayout());
       
        informationPanel.setLayout(new GridLayout(4, 2, 1, 1));

        
        for (int i = 0; i < informationLabel.length; i++) {
            informationPanel.add(informationLabel[i] = new JLabel(informationString[i]));
            informationLabel[i].setFont(new Font("Tahoma", Font.BOLD, 11));
            informationPanel.add(informationTextField[i] = new JTextField());
            informationTextField[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
        }
        informationPanel.add(lblFinePerDay);
        informationPanel.add(txtFinePerDay);
        informationPanel.add(lblTotalFineAmt);
        informationPanel.add(txtTotalFineAmt);
        txtTotalFineAmt.setEditable(false);
        txtFinePerDay.addKeyListener(new keyListener());
        centerPanel.add("Center", informationPanel);
       
        returnButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        returnButtonPanel.add(returnButton);
        
        returnButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        
        centerPanel.add("South", returnButtonPanel);
        
        centerPanel.setBorder(BorderFactory.createTitledBorder("Return a book:"));
      
        cp.add("Center", centerPanel);

      
        southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        

        southPanel.add(cancelButton);
       
        cancelButton.setFont(new Font("Tahoma", Font.BOLD, 11));
     
        southPanel.setBorder(BorderFactory.createEtchedBorder());
       
        cp.add("South", southPanel);

       
        returnButton.addActionListener(this);
      
        cancelButton.addActionListener(this);
        
        setVisible(true);
        
        pack();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == returnButton) {
            //for checking if there is a missing information
            if (isCorrect()) {
                Thread runner = new Thread() {

                    public void run() {
                        book = new Books();
                        member = new Members();
                        borrow = new Borrow();
                        book.connection("SELECT * FROM Books WHERE BookID = " + data[0]);
                        member.Connection("SELECT * FROM Members WHERE MemberID = " + data[1]);
                        int numberOfAvailbleBooks = book.getNumberOfAvailbleBooks();
                        int numberOfBorrowedBooks = book.getNumberOfBorrowedBooks() - 1;
                        int numberOfBooks = member.getNumberOfBooks();
                        //for checking if there is no same information in the database
                        if (numberOfAvailbleBooks == 0 && numberOfBooks > 0) {
                            numberOfAvailbleBooks += 1;
                            numberOfBooks -= 1;
                            book.update("UPDATE Books SET NumberOfAvailbleBooks =" + numberOfAvailbleBooks +
                                    ",NumberOfBorrowedBooks =" + numberOfBorrowedBooks + ",Availble = true WHERE BookID =" + data[0]);
                            member.update("UPDATE Members SET NumberOfBooks =" + numberOfBooks + " WHERE MemberID =" + data[1]);
                            borrow.update("DELETE FROM Borrow WHERE BookID =" + data[0] + " AND MemberID =" + data[1]);
                            //for setting the array of JTextField to null
                            JOptionPane.showMessageDialog(null, "The book is Successfully returned", "Success", JOptionPane.INFORMATION_MESSAGE);
                            clearTextField();
                        } else if (numberOfAvailbleBooks > 0 && numberOfBooks > 0) {
                            numberOfAvailbleBooks += 1;
                            numberOfBooks -= 1;
                            book.update("UPDATE Books SET NumberOfAvailbleBooks =" + numberOfAvailbleBooks +
                                    ",NumberOfBorrowedBooks =" + numberOfBorrowedBooks + " WHERE BookID =" + data[0]);
                            member.update("UPDATE Members SET NumberOfBooks =" + numberOfBooks + " WHERE MemberID =" + data[1]);
                            borrow.update("DELETE FROM Borrow WHERE BookID =" + data[0] + " AND MemberID =" + data[1]);
                            //for setting the array of JTextField to null
                            JOptionPane.showMessageDialog(null, "The book is Successfully Returned", "Success", JOptionPane.INFORMATION_MESSAGE);
                            clearTextField();
                        } else {
                            JOptionPane.showMessageDialog(null, "The book is not borrowed", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                };
                runner.start();
            } //if there is a missing data, then display Message Dialog
            else {
                JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
        if (ae.getSource() == cancelButton) {
            dispose();
        }
    }

    class keyListener extends KeyAdapter {

        public void keyPressed(KeyEvent k) {
            java.sql.Date da = null;
            if (k.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    int fineamt = Integer.parseInt(txtFinePerDay.getText());
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                    Connection con = DriverManager.getConnection("jdbc:odbc:LMS_DB");
                    Statement st = con.createStatement();
                    int bookid = Integer.parseInt(informationTextField[0].getText());
                    int memid = Integer.parseInt(informationTextField[1].getText());
                    try {
                        String sql = "SELECT DayOfReturn from Borrow where MemberID=" + memid + " and BookID=" + bookid;
                        ResultSet rs = st.executeQuery(sql);
                        if (rs.next()) {
                            
                            da = rs.getDate(1);                            
                            java.util.Date today = new java.util.Date();                            
                            /*java.util.Date retdate=new java.util.Date(da.getYear(),da.getMonth(),da.getDate());
                            JOptionPane.showMessageDialog(null, "today=" + today + "\nRet date=" + retdate);*/
                            
                            System.out.println(today.after(da));
                            
                            if (today.after(da)) {
                                long finedays = today.getTime() - da.getTime();
                                int days = (int) (finedays / (1000 * 60 * 60 * 24));
                                System.out.println(days);
                                txtTotalFineAmt.setText(String.valueOf(fineamt * days));
                            } else {
                                txtTotalFineAmt.setText("0");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Member ID entered not found on databse");
                        }
                        
                    } catch (Exception ex1) {
                        JOptionPane.showMessageDialog(null, "Error, Cannot retrieve date value from table" + ex1.toString());
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error, cannot connect to database" + ex.toString());
                }
            }
        }
    }//inner class closed
}//class closed
