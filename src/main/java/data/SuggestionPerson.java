package data;

import java.util.Date;

public class SuggestionPerson extends Person implements ISuggestion {

    private int suggestionID;

    private Date date;
    private String author;

    public SuggestionPerson() {

    }

    public SuggestionPerson(Person p, int suggestionID, Date date, String author) {
        super(p);

        this.suggestionID = suggestionID;
        this.date = date;
        this.author = author;


    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = new Date(date.getTime());
    }

    public int getSuggestionID() {
        return suggestionID;
    }

    public void setSuggestionID(int suggestionID) {
        this.suggestionID = suggestionID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public SuggestionPerson(int suggestionID, Date date, String author) {
        super();

        this.suggestionID = suggestionID;
        this.date = date;
        this.author = author;
    }

    @Override
    public String toString() {
        return "SuggestionPerson{" +
                super.toString() +
                "suggestionID=" + suggestionID +
                ", date=" + date +
                ", author='" + author + '\'' +
                '}';
    }

    public Person toPerson() {
        return this;
    }
}

