public class Student extends Member {

    public Student(String name, String account, String password) {
        super(name, account, password, "Student");
        setFine(100);
        setLimit_book(1);
        setDue_date(14);
        
    }


}
