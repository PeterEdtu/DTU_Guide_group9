package controllers.exceptions;

import data.Location;
import data.Person;
import data.SuggestionLocation;

public class ItemOverwriteException extends Exception {

    private Location overwriteLoc;
    private Person overwritePer;
    private SuggestionLocation overwriteSug;

    public Location getOverwriteLoc() {
        return overwriteLoc;
    }

    public void setOverwriteLoc(Location overwriteLoc) {
        this.overwriteLoc = overwriteLoc;
    }

    public Person getOverwritePer() {
        return overwritePer;
    }

    public void setOverwritePer(Person overwritePer) {
        this.overwritePer = overwritePer;
    }

    public SuggestionLocation getOverwriteSug() {
        return overwriteSug;
    }

    public void setOverwriteSug(SuggestionLocation overwriteSug) {
        this.overwriteSug = overwriteSug;
    }

    public ItemOverwriteException(){};

    public ItemOverwriteException(Location overwriteLoc){
        this.overwriteLoc = overwriteLoc;
    }

    public ItemOverwriteException(Person overwritePer){
        this.overwritePer = overwritePer;
    }

    public ItemOverwriteException(SuggestionLocation overwriteSug){
        this.overwriteSug = overwriteSug;
    }
}
