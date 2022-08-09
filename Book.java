public class Book extends Date{
    private String title;
    private String author;
    private String publisher;
    private String status;// available & lend




    //Constructor
    public Book(String title, String author, String publisher, String status){
        setTitle(title);
        setAuthor(author);
        setPublisher(publisher);
        setStatus(status);
    }



    // 查看書集資料(information)
    public String toString(){
        String output =  "Title: "+ title +"\nAuthor: " + author + "\nPublisher: " + publisher + "\nStatus: " + status;
        return output ;
    } // End toString

    // 模糊搜尋時用
    public String search_toString(){
        String output =  title +" " + author + " " + publisher + " " + status;
        return output;
    }


    //Getter and Setter
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    // Table的格式
    public Object[] toObjectArray(){
        Object [] string_array = {this.getTitle(),this.getAuthor(),this.getPublisher(),this.getStatus()};
        return string_array;
    }
}
