import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
public class Administrator extends User {

    private ArrayList <Book> books;


    // Constructor
    public Administrator(String name, String account, String password) {
        super(name, account, password, "Administrator");
        books = Library.books;
    }// Constructor




    // mainPage_administrator
    public void mainPage_administrator(){


            JFrame frame = new JFrame();
	        JPanel jp = new JPanel();
	        JLabel welcome_lb = new JLabel();
	        JLabel icon_lb = new JLabel();
	        JPanel input_panel = new JPanel();
	        ImageIcon imageIcon = new ImageIcon("Gary.jpg");
	        JButton bt_delete = new JButton();
	        JButton bt_information  =new JButton();

	        icon_lb.setIcon(imageIcon);
	        welcome_lb.setText("Welcome to Center Library");
	        welcome_lb.setFont(new Font("Serif", Font.PLAIN, 24));
	        welcome_lb.setSize(278,50);
	        welcome_lb.setLocation(242, 18);

	        input_panel.setLayout(null);
	        input_panel.setSize(297,214);
	        input_panel.setLocation(223,58);

	        icon_lb.setSize(200,200);
	        icon_lb.setLocation(30,25);

	        bt_delete.setText("Delete");
	        bt_information.setText("Information");
	        bt_delete.setSize(100,30);
	        bt_information.setSize(100,30);
	        bt_delete.setLocation(60,88);
	        bt_information.setLocation(162,88);

	        jp.setLayout(null);
	        frame.getContentPane().setLayout(null);


	        frame.setSize(520, 300);
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);

	        frame.setContentPane(jp);
	        input_panel.add(bt_delete);
	        input_panel.add(bt_information);
	        jp.add(icon_lb);
	        jp.add(input_panel);

	        JButton bt_add = new JButton();
	        bt_add.setText("Add");
	        bt_add.setBounds(60, 29, 100, 30);
	        input_panel.add(bt_add);

	        JButton bt_exit = new JButton();
	        bt_exit.setText("Exit");
	        bt_exit.setBounds(109, 144, 100, 30);
	        input_panel.add(bt_exit);

	        JButton bt_modify = new JButton();
	        bt_modify.setText("Modify");
	        bt_modify.setBounds(162, 29, 100, 30);
	        input_panel.add(bt_modify);
	        jp.add(welcome_lb);

            bt_add.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    addBook();
                    frame.dispose();
                }
            });
            bt_delete.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    deleteBook();
                    frame.dispose();
                }
            });
            bt_modify.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    modifyBook();
                    frame.dispose();
                }
            });
            bt_exit.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    Library.Login_GUI();
                    frame.dispose();
                }
            });
            bt_information.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    show_information();
                    frame.dispose();
                }
            });

            frame.setVisible(true);


    } // End mainPage_administrator




     // addBook
    public void addBook(){
        JTextField title = new JTextField();
        JTextField author = new JTextField();
        JTextField publisher = new JTextField();
        int x;
        boolean exist = false;
        String options [] = {"Add", "Cancel"};
        Object information[] = {"Please enter the information of the book.\n",
                                "title",title,
                                "author", author,
                                "publisher", publisher};

        x = JOptionPane.showOptionDialog (null,information,
        "Add Book",JOptionPane.DEFAULT_OPTION,1,null, options, options[0]) ;

        if(x == -1){
            mainPage_administrator();
            return;
        }

        for (Book recent: books){
            if(title.getText().equals(recent.getTitle())){
                exist = true;
            }
        }

        if(exist == false){ // 書不存在
            if(x == 0){ // add
                books.add( new Book( title.getText() , author.getText(), publisher.getText() , "available") );
                JOptionPane.showMessageDialog(null, "Add successfully\nGoing back to Main Page");
                mainPage_administrator();
                // JOptionPane.showMessageDialog(null, "Error\nYou are at addBook-1");
               try{
                    FileWriter bookfile = new FileWriter("db_book.txt",true);
                    String bookstring =title.getText()+","+author.getText()+","+publisher.getText()+"," + "available";
                    bookfile.write(bookstring+"\n");
                    bookfile.close();
               }catch(IOException e){
                   System.out.println("An error occurred.");
               }
                return;
            }
            else if(x == 1){ // cancel add
                mainPage_administrator();
                return;
                // JOptionPane.showMessageDialog(null, "Error\nYou are at addBook-2");
            }
        }
        else{  // 書已經存在
            x = JOptionPane.showOptionDialog (null,"The book are already exist.\nKeep adding or Cancel.",
            "登入",JOptionPane.DEFAULT_OPTION,1,null, options, options[0]) ;

            if(x == 0){ // add
                addBook();
                return;
                // JOptionPane.showMessageDialog(null, "Error\nYou are at addBook-3");
            }
            else if (x == 1){ //cancel
                mainPage_administrator();
                return;
                // JOptionPane.showMessageDialog(null, "Error\nYou are at addBook-4");
            }
            else{
                mainPage_administrator();
                return;
            }
        }


    }// End addBook



    // modifyBook
    public void modifyBook(){

        String input;
        Book temp = null;
        String options[] = {"Modify", "Cancel"};
        int x;
        JTextField title = new JTextField();
        JTextField author = new JTextField();
        JTextField publisher = new JTextField();
        Object information[] = {"Enter book's information.",
                                "Title",title,
                                "Author", author,
                                "Publisher",publisher};

        input = JOptionPane.showInputDialog(null, "What book do you want to make change");
        for (Book recent : books){
            if(input.equals(recent.getTitle())){
                temp = recent;
                break;
            }
        }
        if(temp == null){ // 沒找到書

            x = JOptionPane.showOptionDialog(null,
                                        "The book doesn't exist.\ndo you still want to make change of the book or go back to main page",
                                            "Modify",
                                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if(x == 0 ){ // modify
                modifyBook();
                return;
                // JOptionPane.showMessageDialog(null, "Erroe\nYou are at modifyBook-1");
            }
            else if ( x == 1){ // back to main page
                mainPage_administrator();
                return;
                // JOptionPane.showMessageDialog(null, "Erroe\nYou are at modifyBook-2");
            }
            else{
                mainPage_administrator();
                return;
            }
        }
        else{ // 有找到書
            title.setText( temp.getTitle() );
            author.setText( temp.getAuthor() );
            publisher.setText( temp.getPublisher());

            x = JOptionPane.showOptionDialog (null,information, // 輸入帳號密碼
            "登入",JOptionPane.DEFAULT_OPTION,1,null, options, options[0]) ;

            if(x == 0){ //確認更改
                temp.setTitle(title.getText());
                temp.setAuthor(author.getText());
                temp.setPublisher(publisher.getText());
                Library.updat_book_db();
                JOptionPane.showMessageDialog(null, "Modify successfully\nGo back to Main Page");
                mainPage_administrator();
                return;
                // JOptionPane.showMessageDialog(null, "Error\nYou are at modifyBook-3");
            }
            else{ //取消更改
                mainPage_administrator();
                return;
                // JOptionPane.showMessageDialog(null, "Error\nYou are at modifyBook-4");
                }
            }

    } // End modyfyBook



    //deleteBook
    public void deleteBook(){
        int input; // JOptionPane.showoptioinDialog
        String options[] = {"Delete", "Cancel"};
        JTextField title = new JTextField();
        Object information[] = {"Enter the book you want to delete","Book Title", title};
        Book temp = null;
        int x;



        input = JOptionPane.showOptionDialog(null,
        information,
            "Delete",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if(input == -1){
            mainPage_administrator();
            return;
        }


        if(input == 1 ){ // Cancel
            mainPage_administrator();
            return;
            // JOptionPane.showMessageDialog(null, "Error\nYou are at deleteBook-0");
        }

        for (Book recent : books){
            if(title.getText().equals(recent.getTitle())){
                temp = recent;
                break;
            }
        }

        if(temp != null){ // 書存在
            books.remove( temp );
            JOptionPane.showMessageDialog(null, "Delete successfully\nGoing back to Main Page.");
            mainPage_administrator();
            return;
            // JOptionPane.showMessageDialog(null, "Error\nYou are at deleteBook-1");
        }
        else{ // 書不存在
            x = JOptionPane.showOptionDialog(null,
                                        "The book doesn't exist.\ndo you still want to delete book or go back to main page",
                                            "Delete",
                                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if(x == 0){ // keep deleting
                deleteBook();
                return;
                // JOptionPane.showMessageDialog(null, "Error\nYou are at deleteBook-2");
            }
            else if(x == 1){ // cancel
                mainPage_administrator();
                return;
                // JOptionPane.showMessageDialog(null, "Error\nYou are at deleteBook-3");
            }
            else{
                mainPage_administrator();
                return;
            }
        }

    } // deleteBook



    public void show_information(){
        String output = "";
        output += "Name: " + getName();
        output += "\nAccount: " + getAccount();
        output += "\nPassword: " + getPassword();
        JOptionPane.showMessageDialog(null, output);
        mainPage_administrator();
        return;
        // JOptionPane.showMessageDialog(null, "Error\nYou are at show_information");
    }



}
