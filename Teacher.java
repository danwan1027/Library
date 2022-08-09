public class Teacher extends Member {

    public Teacher(String name, String account, String password) {
        super(name, account, password, "Teacher");
        setFine(500);
        setLimit_book(3);
        setDue_date(21);
        
    }

    
}
