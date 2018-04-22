package controllers.exceptions;

import data.Location;
import data.Person;
import data.Suggestion;

public class ItemOverwriteException extends Exception {

    private Location overwriteLoc;
    private Person overwritePer;
    private Suggestion overwriteSug;

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

    public Suggestion getOverwriteSug() {
        return overwriteSug;
    }

    public void setOverwriteSug(Suggestion overwriteSug) {
        this.overwriteSug = overwriteSug;
    }

    public ItemOverwriteException(){};

    public ItemOverwriteException(Location overwriteLoc){
        this.overwriteLoc = overwriteLoc;
    }

    public ItemOverwriteException(Person overwritePer){
        this.overwritePer = overwritePer;
    }

    public ItemOverwriteException(Suggestion overwriteSug){
        this.overwriteSug = overwriteSug;
    }
}
