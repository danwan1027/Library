import java.time.LocalDate;

// 拿到電腦上顯示的時間
public class Date{
    private LocalDate borrow_date;
    private LocalDate return_date;





    public LocalDate getBorrow_date() {
        return borrow_date;
    }
    public void setBorrow_date(LocalDate borrow_date) {
        this.borrow_date = borrow_date;
    }
    public LocalDate getReturn_date() {
        return return_date;
    }
    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }



}