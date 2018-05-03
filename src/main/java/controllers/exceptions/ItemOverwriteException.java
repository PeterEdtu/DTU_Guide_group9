package controllers.exceptions;

import api.HTTPException;
import data.Location;
import data.Person;
import data.SuggestionLocation;

import javax.ws.rs.core.Response;

public class ItemOverwriteException extends HTTPException {

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

    public ItemOverwriteException(){}

    public ItemOverwriteException(Location overwriteLoc){
        this.overwriteLoc = overwriteLoc;
    }

    public ItemOverwriteException(Person overwritePer){
        this.overwritePer = overwritePer;
    }

    public ItemOverwriteException(SuggestionLocation overwriteSug){
        this.overwriteSug = overwriteSug;
    }

    @Override
    public Response getHttpResponse() {
        return Response.status(409).build();
    }
}
