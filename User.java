import java.util.ArrayList;

public class User {
    private String name;
    private String account;
    private String password;
    private String identity;
    private ArrayList <Book> books;
  
    //Constructor
    public User(String name, String account, String password, String identity) {
        setName(name);
        setAccount(account);
        setPassword(password);
        setIdentity(identity);
        books = Library.books;
    }// End Constructor



    // Search
    public Book search(String search_title){
        for(Book recent : books){
            if (recent.getTitle().equals(search_title)){
                return recent;
            }
        }
        
        return null;
    } // End Search



    // Getter and Setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getIdentity() {
        return identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
        
}
