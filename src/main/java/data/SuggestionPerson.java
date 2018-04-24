package data;

import java.util.Date;

public class SuggestionPerson extends Person implements ISuggestion {

    private int suggestionID;
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

    public SuggestionPerson(Date date, String author){
        super();

        this.date = date;
        this.author = author;


    }
}

