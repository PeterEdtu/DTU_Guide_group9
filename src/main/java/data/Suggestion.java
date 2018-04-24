package data;

import java.util.Date;

public class Suggestion extends Location {

    private Date date;
    private String author;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Suggestion(Date date, String author){
        super();

        this.date = date;
        this.author = author;


    }
}

