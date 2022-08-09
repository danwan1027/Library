import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Font;
import java.time.temporal.ChronoUnit;



public class Member extends User implements Cloneable {
    private int limit_book;
    private int due_date;
    private int fine;
    protected  ArrayList<Book> borrowing; // 正在借的書 （reference 跟總書庫是同一個）
    protected ArrayList <Book> books; // 總書庫
    protected ArrayList <Book> history = new ArrayList<Book>(); // 借書紀錄 （clone 出來的書籍）


    //Constructor
    public Member(String name, String account, String password, String identity) {
        super(name, account, password, identity);
        books = Library.books;
        borrowing = new ArrayList <Book>();
    }// End Constructo

    //mainPage_member
    public void mainPage_member(){


        JFrame frame = new JFrame();
        JPanel jp = new JPanel();
        JLabel welcome_lb = new JLabel();
        JLabel icon_lb = new JLabel();
        JPanel input_panel = new JPanel();
        ImageIcon imageIcon = new ImageIcon("Gary.jpg");

        JButton bt_return = new JButton();
        JButton bt_history  =new JButton();

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

        bt_return.setText("Return");
        bt_history.setText("History");
        bt_return.setSize(100,30);
        bt_history.setSize(100,30);
        bt_return.setLocation(60,88);
        bt_history.setLocation(162,88);

        jp.setLayout(null);
        frame.getContentPane().setLayout(null);


        frame.setSize(520, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setContentPane(jp);
        input_panel.add(bt_return);
        input_panel.add(bt_history);
        jp.add(icon_lb);
        jp.add(input_panel);

        JButton bt_information = new JButton();
        bt_information.setText("Information");
        bt_information.setBounds(60, 142, 100, 30);
        input_panel.add(bt_information);

        JButton bt_borrow = new JButton();
        bt_borrow.setText("Borrow");
        bt_borrow.setBounds(60, 29, 100, 30);
        input_panel.add(bt_borrow);

        JButton bt_Exit = new JButton();
        bt_Exit.setText("Exit");
        bt_Exit.setBounds(162, 142, 100, 30);
        input_panel.add(bt_Exit);

        JButton bt_search = new JButton();
        bt_search.setText("Search");
        bt_search.setBounds(162, 29, 100, 30);
        input_panel.add(bt_search);
        jp.add(welcome_lb);

        bt_Exit.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Library.Login_GUI();
                frame.dispose();
            }

        });
        bt_borrow.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                borrowBook();
                frame.dispose();
            }

        });
        bt_history.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                show_history();
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
        bt_return.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // returnBook();
                frame.dispose();
            }

        });
        bt_search.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                searchBook();
                frame.dispose();
            }

        });




        frame.setVisible(true);









        // String options [] = {"Borrow", "Return", "Search", "Information" , "History", "Exit"};

        // int input = JOptionPane.showOptionDialog(null,
        //                                 "Welcome " + getName(),
        //                                 "Main Page",
        //                                 JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]
        //                                 );
        // if(input == -1){System.exit(0);} // 左上角的叉叉
        // switch (input){ // 進入功能
        //     case 0:
        //         borrowBook();
        //         return;
        //     case 1:
        //         returnBook();
        //         return;
        //     case 2:
        //         searchBook();
        //         return;
        //     case 3:
        //         show_information();
        //         return;
        //     case 4:
        //         show_history();
        //         return;
        //     case 5:
        //         Library.Login_GUI();
        //         break;
        // }

    } // End mainPage_member



    // BorrowBook
    public void borrowBook(){
        searchBook();
        return;
    } // End BorrowBook


    // BorrowBook_parameter
    public void borrowBook_parameter(Book found){


        if(check_limit_book() == false){ // 若借書數量已滿，回到 Main Page
            JOptionPane.showMessageDialog(null, "You reach your borrowing limit:" + limit_book+"\nGo back to main page.");
            //mainPage_member();
            return;
            // JOptionPane.showMessageDialog(null, "Error\nYour are at borrowBook_parameter-1");
        }
        if (found.getStatus().equals("lend")){
            JOptionPane.showMessageDialog(null, "The book is not available right now." + limit_book+"\nGo back to main page.");
            //mainPage_member();
            return;
        }
        // System.out.print("Yuan0103");
        Book temp = new Book (found.getTitle(),found.getAuthor(), found.getPublisher(),"lend");  // temp 是要放進借書紀錄的書
        history.add(temp);
        borrowing.add(found);
        found.setBorrow_date(LocalDate.now());
        found.setReturn_date(null);
        temp.setBorrow_date(LocalDate.now());
        found.setStatus("lend");
        temp.setReturn_date(null);
        JOptionPane.showMessageDialog(null, "Borrow success\nYou should return the book by " + found.getBorrow_date().plusDays(getDue_date()) + "\nGo back to Main Page");
        Library.updat_book_db();
        Library.update_user_db();
        //mainPage_member();
        return;
        // JOptionPane.showMessageDialog(null, "Error\nYour are at borrowBook_parameter-2");
    } // End BorrowBook_parameter



    // ReturnBook
    public void returnBook(String return_book_name){
        boolean did_borrow = false;
        Book temp_borrowing = null;
        Book temp_history = null;
        String options[] = {"return", "Main Page"};
        int x;
        Boolean expired = false;
        long diff;

        for (Book recent: borrowing){ // from 現在借的書
            if(return_book_name.equals(recent.getTitle())){
                temp_borrowing = recent;
                did_borrow = true;
                break;
            }
        }



        for (Book recent : history){ // from 借書紀錄
            if(return_book_name.equals(recent.getTitle())){
                temp_history = recent;
                break;
            }
        }

        if(did_borrow == true){

            temp_borrowing.setStatus("available"); //總書庫 available
            borrowing.remove(temp_borrowing);
            temp_borrowing.setReturn_date(LocalDate.now());
            temp_history.setReturn_date(LocalDate.now()); // 借書紀錄填進還書時間
            temp_history.setStatus("available");  // 借書紀錄改成已歸還
            Library.updat_book_db();
            Library.update_user_db();
            if(temp_history.getBorrow_date() != null && temp_history.getReturn_date() != null){ // 程式重開時，借書與還書時間沒有輸入，所以以為瑕疵設計
                diff = ChronoUnit.DAYS.between(temp_history.getBorrow_date() , temp_history.getReturn_date());
            }
            else {
                diff = 0;
            }
            
            if( diff > getDue_date() ){ // 超過時間，要繳罰金
                JOptionPane.showMessageDialog(null,"Return successfully\nOverdue: " + ( diff - getDue_date() ) +" days"+ "\nOverdue fine: " + getFine() + "\nGoing back to Main Page");
            }
            else{
                JOptionPane.showMessageDialog(null,"Return successfully\nReturn on time\nGoing back to Main Page");
            }
            return;
            // JOptionPane.showMessageDialog(null,"Error\nYou are at returnBook-1");
        }
        else if (did_borrow == false){
            JOptionPane.showMessageDialog(null,"You didn't borrow the book");
            // x = JOptionPane.showOptionDialog(null,
            //                             "You didn't borrow the book\nDo you still want to return the book or go back to main page.",
            //                                 "Return book",
            //                                     JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            // if(x == -1){System.exit(0);} //左上角的叉叉

            // if(x == 0){ // keep returning
            //     returnBook();
            //     return;
            //     // JOptionPane.showMessageDialog(null,"Error\nYou are at returnBook-2");
            // }
            // else if(x == 1){ // go back to main page
            //     mainPage_member();
            //     return;
            //     // JOptionPane.showMessageDialog(null,"Error\nYou are at returnBook-3");
            // }


        }

    }   // End ReturnBook



    // Search
    public void searchBook(){
        String target;
        String[] options_search = {"Yes","No"};
        String [] options_not_found = {"Search", "Main Page"};
        int x;
        Book found = null; // 找到的書
        while(true){


            target = JOptionPane.showInputDialog(null, "Enter book's title" );
            System.out.println(target);
            if (target == null){
                mainPage_member();
                return;
                // JOptionPane.showMessageDialog(null, "Error\nYou are at searchBook-4");
            }// 左上角的叉叉

            found = search(target);

            if(found == null){ //沒找到書

                x = JOptionPane.showOptionDialog(null,
                                            "The book doesn't exist.\nKeeping look?",
                                                "Search",
                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options_not_found, options_not_found[0]);
                if(x == 0){
                    continue;
                }
                else if( x == 1){
                    mainPage_member();
                    return;
                    // JOptionPane.showMessageDialog(null, "Error\nYou are at searchbook-5");
                }
                else{ // 左上角的叉叉
                    System.exit(0);
                }
            }


            if ( found != null ){ //有找到書

                if(found.getStatus().equals("lend")){ // 判斷書是否已借出
                    JOptionPane.showMessageDialog(null, "The book is not available now.\nGoing back to Main Page");
                    mainPage_member();
                    return;
                    // JOptionPane.showMessageDialog(null, "Error\nYou are at searchbook-3");
                }

                    x = JOptionPane.showOptionDialog(null,
                                            "Do you want to borrow the book?"+"\nTitle: " + found.getTitle()+"\nAuthor: " + found.getAuthor()+"\nPublisher: " + found.getPublisher(),
                                                "Search",
                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options_search, options_search[0]);
                    if( x == 0){ // 要借書


                        borrowBook_parameter(found);
                        return;
                        // JOptionPane.showMessageDialog(null, "Error\nYou are at searchbook-1");
                    }
                    else if (x == 1){ // 不借書
                        mainPage_member();
                        return;
                        // JOptionPane.showMessageDialog(null, "Error\nYou are at searchbook-2");
                    }
                    else {System.exit(0);} //左上角的叉叉

                    break;
                }



        }
    } // End SearchBook


    // check_limit_book
    public boolean check_limit_book(){
        if(  borrowing.size() < limit_book ){
            System.out.println(borrowing.size());
            return true;
        }
        else {
            return false;
        }

    } // End check_limit_book




    //show_information
    public void show_information(){
        String output = "";
        output += "Name:" + getName() + "\n";
        output += "Account:" + getAccount() + "\n";
        output += "Password:" + getPassword() + "\n";

        if(borrowing.size() == 0 ){
            output += "\nYou have not borrow any book yet";
        }
        else {
            output += "\nThe books you borrowed           Return date\n";
            for(Book recent : borrowing){
                output += recent.getTitle() + "\t\t\t\t\t\t\t\t\t                               " + recent.getBorrow_date().plusDays(getDue_date()) + "\n";

            }
        }

        JOptionPane.showMessageDialog(null, output);
        // mainPage_member();
        return;
        // JOptionPane.showMessageDialog(null, "Error\nYou are at Member's toString");

        } // End show_information


        // show history
    public void show_history(){

            String output = "";
            output+= "Borrowing history\n";
            for (Book recent : history){

                if(recent.getBorrow_date() == null ){ // 如果書是匯入的，此為瑕疵，因為匯出時沒有匯出借書時間
                    recent.setBorrow_date(LocalDate.now());  
                }
                output += "Title:" + recent.getTitle() ;
                output += "   Borrow date:" + recent.getBorrow_date() ; // 借出日期
                output += ( recent.getReturn_date() == null? "   Haven't return yet" : " Return date: "+recent.getReturn_date() ); //歸還日期
                output += ( ChronoUnit.DAYS.between( LocalDate.now(),  recent.getBorrow_date().plusDays(getDue_date()))<0 ? "   Book expired: " +  ChronoUnit.DAYS.between(recent.getBorrow_date().plusDays(getDue_date())  , LocalDate.now()) + " days": ""); // 逾期
                output += ( ChronoUnit.DAYS.between( LocalDate.now(),  recent.getBorrow_date().plusDays(getDue_date()) )<0 ? "   Fine: " + getFine() : "");
                output += "\n";
            }
            if(history.size() == 0){
                output += "You haven't borrow any book";
            }
            JOptionPane.showMessageDialog(null, output);
            // mainPage_member();
            return;
            // JOptionPane.showMessageDialog(null, "You are at show_history");
        } // End show_history





    //Getter and Setter
    public int getLimit_book() {
        return limit_book;
    }
    public void setLimit_book(int limit) {
        this.limit_book = limit;
    }
    public int getFine() {
        return fine;
    }
    public void setFine(int fine) {
        this.fine = fine;
    }
    public void setDue_date(int due_date){
        this.due_date = due_date;
    }
    public int getDue_date(){
        return due_date;
    }// End Getter and Setter

}
