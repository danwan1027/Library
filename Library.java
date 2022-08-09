import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Font;
import java.time.LocalDate;


public class Library implements ActionListener {


    public static ArrayList<Book> books = new ArrayList<Book>();
    public static ArrayList <User> users = new ArrayList<User>();

    //Main
    public static void main(String args[]) throws IOException{
        db_import();
        
        new Library();
    }// End Main

    //模糊搜尋
    public static ArrayList<Book> fuzzy_search_book(String search_key){
        ArrayList<Book> search_book_list = new ArrayList<Book>();
        for(Book b: Library.books){
            if(b.search_toString().contains(search_key)){
                search_book_list.add(b);
            }

        }
        return search_book_list;
    }

    // 用書名查
    public static Book getBookbyTitle(String book_title){
        for(Book b: Library.books){
            if(b.getTitle().equals(book_title)){
                return b;
            }
        }
        return null;
    }

    // 更新db_books.txt
    public static void updat_book_db(){

            try{
                FileWriter bookfile = new FileWriter("db_book.txt");
                for(Book book : Library.books){
                    String bookstring =book.getTitle()+","+book.getAuthor()+","+book.getPublisher()+"," + book.getStatus();
                    bookfile.write(bookstring+"\n");
                }
                bookfile.close();
           }catch(IOException e){
               System.out.println("An error occurred.");
           }

    }

    // 更新 db_user.txt
    public static void update_user_db(){
        try{
            FileWriter userfile = new FileWriter("db_user.txt");
            // true使之維持原檔案並繼續書寫
            for(User user : users){
                String userstring = "";
                userstring += user.getIdentity() + "," + user.getName() + "," + user.getAccount() + "," + user.getPassword();
                if(!user.getIdentity().equals("Administrator")){
                    Member m_user = (Member)(user);
                    for (Book current : m_user.borrowing){
                        userstring +=  "," + current.getTitle();
                    }
                }
                userfile.write(userstring+"\n");
            }
            userfile.close();
       }catch(IOException e){
            
           System.out.println("An error occurred.");
       }
    }

    //Constructor
    public Library(){
        //展示用
        Book temp = new Book("test","test","test","test");
        temp.setStatus("lend");
        temp.setBorrow_date(LocalDate.now().minusDays(21));
        books.add(temp);
        Student temp_user = (Student) users.get(1);
        temp_user.borrowing.add(temp);
        temp_user.history.add(temp);
        updat_book_db();
        update_user_db();
        Login_GUI();
    }// End Constructor



    // 一開始抓db建立books
    public static void db_import()throws IOException{
        // import book
        File db_book = new File("db_book.txt");
        Scanner scanner_book = new Scanner (db_book);
        while( scanner_book.hasNextLine() == true){
            String temp = scanner_book.nextLine();
            // 逗點分割
            String temps[] = temp.split(",");
            books.add( new Book(temps[0], temps[1], temps[2], temps[3]));
        }
        // End import book

    // 一開始抓db建立user
        //import user
        File db_user = new File("db_user.txt");
        Scanner scanner_user = new Scanner (db_user);
        while (scanner_user.hasNextLine() == true){
            String temp = scanner_user.nextLine();
            String temps[] = temp.split(",");

            if(temps[0].equals("Administrator")){
                users.add( new Administrator(temps[1], temps[2], temps[3]));
                continue;
            }

            else if (temps[0].equals("Student")){
                Student cons = new Student(temps[1], temps[2], temps[3]);
                users.add(cons);
            }
            else if (temps[0].equals("Teacher")){
                Teacher cons = new Teacher(temps[1], temps[2], temps[3]);
                users.add(cons);
            }
            else if (temps[0].equals("Staff")){
                Staff cons = new Staff(temps[1], temps[2], temps[3]);
                users.add(cons);
            }

            if(temps.length >= 5){
                for (int i = 4 ; i < temps.length ; i++){ // 借過的書存入borrowing
                    Book b_book= Library.getBookbyTitle(temps[i]);
                    Member m_user=(Member)users.get(users.size()-1);
                    m_user.borrowing.add(b_book);
                    m_user.history.add(b_book);
                }
            }
        }
        // End import user

    }



    //驗證身份
    public static User checkIdentity(String account, String password){

        for (User recentUser : users ){
            if( recentUser.getAccount().equals(account) && recentUser.getPassword().equals(password)){
                return recentUser;
            }
        }
        return null;

    } // End 驗證身份

    

    // Login_GUI
    public static void Login_GUI(){
        JFrame frame = new JFrame();
        JPanel panel_right = new JPanel();
        JPanel panel_left = new JPanel();
        Color left_panel = new Color(0,32,63);
        Color right_panel = new Color(173,239,209);


        frame.setSize( 700, 400);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);


        panel_left.setSize(350,400);
        panel_left.setLayout(null);
        panel_left.setBackground(left_panel);
        frame.getContentPane().add(panel_left);


        panel_right.setSize(350, 400);
        panel_right.setLayout(null);
        panel_right.setBackground(right_panel);
        frame.getContentPane().add(panel_right);

        JLabel imagelabel = new JLabel( new ImageIcon("icon.png"));
        imagelabel.setBounds(70, 100, 215, 210);
        panel_left.add(imagelabel);


        JLabel account = new JLabel("Account");
        account.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        account.setBounds(382,160,80,25);
        account.setForeground(Color.white);
        panel_right.add(account);

        JTextField acc = new JTextField();
        acc.setBounds(470,160,165,25);
        acc.setBackground(Color.white);
        acc.setBorder(BorderFactory.createEmptyBorder());
        panel_right.add(acc);

        JLabel password = new JLabel("Password");
        password.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
        password.setBounds(382,195,80,25);
        password.setForeground(Color.white);
        panel_right.add(password);

        JPasswordField pw = new JPasswordField();
        pw.setBounds(470,197,165,25);
        pw.setBorder(BorderFactory.createEmptyBorder());
        pw.setBackground(Color.white);
        panel_right.add(pw);

    // 按exit botton時做的事
        JButton exit = new JButton("X");
        exit.setBounds(682,0,18,25);
        exit.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white, 1),
        BorderFactory.createEmptyBorder(0, 3, 0, 3)));
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exit.setBounds(677,0,23,30);
            }
            public void mouseExited(MouseEvent e) {
                exit.setBounds(682,0,18,25);
            }
            
        });
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel_right.add(exit);

    // 按log_in botton時做的事
        JButton log_in = new JButton("Login");
        log_in.setBounds(650,275,46,25);
        log_in.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white, 1),
        BorderFactory.createEmptyBorder(0, 3, 0, 3)));
        log_in.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                log_in.setBounds(645,270,56,35);
            }
            public void mouseExited(MouseEvent e) {
                log_in.setBounds(650,275,46,25);
            }
            
        });
        log_in.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

               User a = checkIdentity( acc.getText(), pw.getText());

                if(a == null){
                        frame.dispose();
                        JOptionPane.showMessageDialog(null, "Wrong password or account");
                        Login_GUI();
                        return;
                        // JOptionPane.showMessageDialog(null, "Erroe\nYou are at Login_GUI-0");
                }
                else if (a.getIdentity().equals("Administrator")){ //admin
                        Administrator  temp = (Administrator) a;
                        frame.dispose();
                        temp.mainPage_administrator();
                        return;
                        // JOptionPane.showMessageDialog(null, "Erroe\nYou are at Login_GUI-1");
                    }
                else if(a.getIdentity().equals("Student")){ //Student
                        Student temp = (Student)a;
                        frame.dispose();
                        new Table(temp);
                        return;
                        // JOptionPane.showMessageDialog(null, "Erroe\nYou are at Login_GUI-2");
                    }
                else if (a.getIdentity().equals("Teacher")){ //teacher
                        Teacher temp = (Teacher) a;
                        frame.dispose();
                        new Table(temp);
                        return;
                        // JOptionPane.showMessageDialog(null, "Erroe\nYou are at Login_GUI-3");
                    }
                else if(a.getIdentity().equals("Staff")){ // staff
                        Staff temp = (Staff) a;
                        frame.dispose();
                        new Table(temp);
                        return;
                        // JOptionPane.showMessageDialog(null, "Erroe\nYou are at Login_GUI-4");
                    }

                }

        });
        panel_right.add(log_in);

    // 按sign_up botton時做的事
        JButton sign_up = new JButton("Sign up");
        sign_up.setBounds(575,275,60,25);
        sign_up.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white, 1),
        BorderFactory.createEmptyBorder(0, 3, 0, 3)));
        sign_up.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sign_up.setBounds(570,270,70,30);
            }
            public void mouseExited(MouseEvent e) {
                sign_up.setBounds(575,275,60,25);
            }
            
        });
        sign_up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    SignUp_GUI();
                    return;
                    // JOptionPane.showMessageDialog(null, "error\nYou are at Login_GUI-5");
            }
        });
        panel_right.add(sign_up);

        JLabel text = new JLabel("Don't have an account yet?");
        text.setBounds(370,275,170,25);
        text.setBorder(BorderFactory.createEmptyBorder());
        text.setForeground(Color.blue);
        panel_right.add(text);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 45));
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setBounds(461, 77, 140, 60);
        panel_right.add(lblNewLabel);

        frame.setVisible(true);
        } // End Login_GUI


        // SignUp_GUI
    public static void SignUp_GUI(){
            JFrame frame = new JFrame();
            JPanel panel_right = new JPanel();
            JPanel panel_left = new JPanel();
            Color left_panel = new Color(0,32,63);
            Color right_panel = new Color(173,239,209);
            String identity[] = {"Student", "Teacher" , "Staff", "Administrator"};

            frame.setSize( 700, 400);
            frame.setLocationRelativeTo(null);
            frame.setUndecorated(true);

            panel_left.setSize(350,400);
            panel_left.setLayout(null);
            panel_left.setBackground(left_panel);
            frame.getContentPane().add(panel_left);

            panel_right.setSize(350, 400);
            panel_right.setLayout(null);
            panel_right.setBackground(right_panel);
            frame.getContentPane().add(panel_right);

            JLabel imagelabel = new JLabel( new ImageIcon("icon.png"));
            imagelabel.setBounds(70, 100, 215, 210);
            panel_left.add(imagelabel);

            JLabel account = new JLabel("Account");
            account.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
            account.setBounds(382,219,80,25);
            account.setForeground(Color.white);
            panel_right.add(account);

            JTextField acc = new JTextField();
            acc.setBounds(470,221,165,25);
            acc.setBackground(Color.white);
            acc.setBorder(BorderFactory.createEmptyBorder());
            panel_right.add(acc);

            JLabel password = new JLabel("Password");
            password.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
            password.setBounds(382,256,80,25);
            password.setForeground(Color.white);
            panel_right.add(password);

            JPasswordField pw = new JPasswordField();
            pw.setBounds(470,258,165,25);
            pw.setBorder(BorderFactory.createEmptyBorder());
            pw.setBackground(Color.white);
            panel_right.add(pw);

            // 按exit botton時做的事
            JButton exit = new JButton("X");
            exit.setBounds(682,0,18,25);
            exit.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white, 1),
            BorderFactory.createEmptyBorder(0, 3, 0, 3)));
            exit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    exit.setBounds(677,0,23,30);
                }
                public void mouseExited(MouseEvent e) {
                    exit.setBounds(682,0,18,25);
                }
                
            });
            exit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            panel_right.add(exit);


            JComboBox comboBox = new JComboBox(identity);
            comboBox.setBounds(470, 142, 165, 27);
            panel_right.add(comboBox);

            JTextField tf_name = new JTextField();
            tf_name.setBorder(BorderFactory.createEmptyBorder());
            tf_name.setBackground(Color.WHITE);
            tf_name.setBounds(470, 181, 165, 25);
            panel_right.add(tf_name);

            JLabel lb_name = new JLabel("Name");
            lb_name.setForeground(Color.WHITE);
            lb_name.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
            lb_name.setBounds(396, 182, 80, 25);
            panel_right.add(lb_name);

        // 按sign_up botton時做的事
            JButton sign_up = new JButton("Sign up");
            sign_up.setBounds(575,317,60,25);
            sign_up.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white, 1),
            BorderFactory.createEmptyBorder(0, 3, 0, 3)));
            sign_up.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    sign_up.setBounds(570,312,70,35);
                }
                public void mouseExited(MouseEvent e) {
                    sign_up.setBounds(575,317,60,25);
                }
                
            });
            sign_up.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                String id = (String) comboBox.getSelectedItem();
                boolean CAcc = CheckAccount((String)acc.getText());
                boolean CPw = CheckPassword(new String(pw.getPassword()));
                if(tf_name.getText().equals("") ){
                    JOptionPane.showMessageDialog(null, "Name can not be blank");
                }
                if(CAcc==true && CPw==true){
                    AddAccount(id,tf_name.getText(),acc.getText(),new String(pw.getPassword()));
                    JOptionPane.showMessageDialog(null, "Sign up successfully");
                    frame.dispose();
                    Login_GUI();
                    return;
                }


                }
            });
            panel_right.add(sign_up);

            JLabel lblNewLabel = new JLabel("Sign Up");
            lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 45));
            lblNewLabel.setForeground(Color.WHITE);
            lblNewLabel.setBounds(426, 54, 209, 60);
            panel_right.add(lblNewLabel);

            frame.setVisible(true);
            } // End SignUp_GUI


// 檢查帳號不可為空白或已存在
    public static boolean CheckAccount(String acc) {
                if(acc.equals("")){
                    JOptionPane.showMessageDialog(null, "Accout can not be blank");
                    return false;
                }
                for (User user : users){
                    if(acc.equals(user.getAccount())){
                        JOptionPane.showMessageDialog(null, "Account already exist");
                        return false;
                    }
                }
                return true;
            }


// 檢查密碼(大於八個字且一定要有數字且一定要有大寫)
    public static boolean CheckPassword(String pw) {
                if(pw.length()<8) {
                    JOptionPane.showMessageDialog(null, "Password should have over 8 character");
                    return false;
                }
                else if( ! pw.matches(".*[0-9]+.*")) {
                    JOptionPane.showMessageDialog(null, "Password should contain  0 ~ 9");
                    return false;
                }
                else if( ! pw.matches(".*[A-Z]+.*")) {
                    JOptionPane.showMessageDialog(null, "Password should contain at least a capital letter");
                    return false;
                }
                else {
                    return true;
                }
            }


// 創建user
    public static void AddAccount(String id, String user_name , String account, String password){
                switch(id){
                    case "Student" :
                    users.add(new Student(user_name,account,password));
                    break;
                    case "Teacher":
                    users.add(new Teacher(user_name,account,password));
                    break;
                    case "Staff" :
                    users.add(new Staff(user_name,account,password));
                    break;
                    case "Administrator" :
                    users.add(new Administrator(user_name,account,password));
                    break;
                }
                // 寫進db
                try{
                    FileWriter userfile = new FileWriter("db_user.txt",true);
                    // true使之維持原檔案並繼續書寫
                    String userstring = id+","+ user_name+","+account+"," + password;
                    userfile.write(userstring+"\n");
                    userfile.close();
               }catch(IOException e){
                   System.out.println("An error occurred.");
               }

            }


        @Override
    public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub

        }


}
