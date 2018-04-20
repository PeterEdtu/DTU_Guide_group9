package data;

import java.util.Date;

public class Suggestion extends Location {

    private Date date;
    private String author;

    public Suggestion(Date date, String author){
        super();

        this.date = date;
        this.author = author;


    }
}

