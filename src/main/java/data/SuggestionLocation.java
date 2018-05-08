package data;

import java.util.Date;

public class SuggestionLocation extends Location implements ISuggestion {

    private int suggestionID;

    private Date date;
    private String author;

    public SuggestionLocation(){
        super();
    }

    public SuggestionLocation(int suggestionID, Date date, String author){
        super();

        this.suggestionID = suggestionID;
        this.date = date;
        this.author = author;
    }

    public SuggestionLocation(Location loc, int suggestionID, Date date, String author){
        super(loc);

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getSuggestionID() {
        return suggestionID;
    }

    public void setSuggestionID(int id) {
        this.suggestionID = id;
    }

    @Override
    public String toString() {
        return "SuggestionLocation{" +
                super.toString()+
                "suggestionID=" + suggestionID +
                ", date=" + date +
                ", author='" + author + '\'' +
                '}';
    }

    public Location toLocation(){
        return this;
    }

}

