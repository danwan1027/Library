public class Staff extends Member{

    public Staff(String name, String account, String password) {
        super(name, account, password , "Staff");
        setFine(200);
        setLimit_book(2);
        setDue_date(7);
        
    }

}
