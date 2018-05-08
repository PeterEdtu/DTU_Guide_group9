package controllers;

import api.HTTPException;
import controllers.exceptions.AlreadyExistException;
import controllers.exceptions.DataAccessException;
import controllers.exceptions.ItemOverwriteException;
import controllers.exceptions.NotFoundException;
import controllers.interfaces.IChangedAppResources;
import data.SuggestionLocation;
import data.SuggestionPerson;
import database.connector.Connector;

import java.util.ArrayList;

public class ChangedAppResources implements IChangedAppResources {

    Connector connector = new Connector();

    private static ChangedAppResources controller = null;

    private ChangedAppResources() {
    }


    public static synchronized ChangedAppResources getInstance() {
        if (controller == null) {
            controller = new ChangedAppResources();
        }

        return controller;
    }


    @Override
    public void updateLocation(SuggestionLocation newLoc, SuggestionLocation previousLocation) throws ItemOverwriteException, DataAccessException, NotFoundException {
        //Compare the previousLocation with the
        //actual (instance) location before update it
        //with the variable "newLoc" wich is the new location

        boolean isDifferent = false;

        SuggestionLocation actualLocation = connector.getLocationSuggestion(previousLocation.getSuggestionID());

        if(actualLocation.getName() == null){
            throw new NotFoundException("No Location suggestion with name "+previousLocation.getName()+" found");
        }

        SuggestionLocation overwrite = new SuggestionLocation(actualLocation.getSuggestionID(),
                actualLocation.getDate(),
                actualLocation.getAuthor());

        if (!actualLocation.equals(previousLocation)) {

            if (!actualLocation.getDescription().equals(previousLocation.getDescription())) {
                overwrite.setDescription(actualLocation.getDescription());
                isDifferent = true;
            }
            if (actualLocation.getFloor() != previousLocation.getFloor()) {
                overwrite.setFloor(actualLocation.getFloor());
                isDifferent = true;
            }
            if (!actualLocation.getLandmark().equals(previousLocation.getLandmark())) {
                overwrite.setLandmark(actualLocation.getLandmark());
                isDifferent = true;
            }
            if (actualLocation.getLatitude() != previousLocation.getLatitude()) {
                overwrite.setLatitude(actualLocation.getLatitude());
                isDifferent = true;
            }
            if (actualLocation.getLongitude() != previousLocation.getLongitude()) {
                overwrite.setLongitude(actualLocation.getLongitude());
                isDifferent = true;
            }
            if (!actualLocation.getName().equals(previousLocation.getName())) {
                overwrite.setName(actualLocation.getName());
                isDifferent = true;
            }

            //if(!actualLocation.getTags().containsAll(previousLocation.getTags())){
            //    overwrite.setTags(actualLocation.getTags());
            //    isDifferent = true;
            //}

            System.out.println("OVERWRITE : " + overwrite);

            ItemOverwriteException exception = new ItemOverwriteException(overwrite);

            if(isDifferent)
                throw exception;

        }

            if(previousLocation.getName().equals(newLoc.getName())) {
                connector.deleteLocationSuggestion(newLoc.getSuggestionID());
                connector.createLocationSuggestion(newLoc);
            }else
                throw new DataAccessException("The name of the location "+previousLocation.getName()+" cannot be changed");

    }

    @Override
    public void addLocation(SuggestionLocation location) throws HTTPException {
        if(connector.getLocationSuggestion(location.getSuggestionID()).getName() != null)
            throw new AlreadyExistException("Item "+location+" already exists !");

        connector.createLocationSuggestion(location);

    }


    @Override
    public ArrayList<SuggestionLocation> getAllChangedLocations() throws DataAccessException, NotFoundException {

        ArrayList<SuggestionLocation> suggestions = connector.getLocationSuggestions();

        if(suggestions == null){
            throw new DataAccessException("Null pointer at LocationSuggestions list");
        }else if(suggestions.isEmpty()){
            throw new NotFoundException("No LocationSuggestions found");
        }else{
            return suggestions;
        }
    }

    @Override
    public SuggestionLocation getLocation(int id) throws DataAccessException, NotFoundException {
        SuggestionLocation suggestionLocation = connector.getLocationSuggestion(id);

        if(suggestionLocation == null){
            throw new NotFoundException("No Location suggestion with ID "+id+" found");
        }else{
            return suggestionLocation;
        }
    }


    @Override
    public void deleteLocationChange(int id) throws DataAccessException, NotFoundException {
        SuggestionLocation suggestionLocation = connector.getLocationSuggestion(id);

        if(suggestionLocation == null){
            throw new NotFoundException("No Location suggestion with ID "+id+" found");
        }else{
            connector.deleteLocationSuggestion(id);
        }
    }

    @Override
    public void approveLocation(int id) throws DataAccessException, NotFoundException {
        SuggestionLocation suggestionLocation = connector.getLocationSuggestion(id);

        if(suggestionLocation.getName() == null){
            throw new NotFoundException("Suggestion ID "+id+" cannot be found");
        }else{
            if(connector.getLocations(suggestionLocation.getName()).isEmpty())
                connector.createLocation(suggestionLocation.toLocation());
            else {
                System.out.println("Try to update location : "+suggestionLocation.getName());
                connector.updateLocation(suggestionLocation.toLocation());

            }

            connector.deleteLocationSuggestion(id);
        }
    }

    @Override
    public void addPerson(SuggestionPerson person) throws HTTPException {
        connector.createPeopleSuggestion(person);
    }

    @Override
    public void updatePerson(SuggestionPerson person, SuggestionPerson previousPerson) throws DataAccessException, ItemOverwriteException, NotFoundException {
        boolean isDifferent = false;

        SuggestionPerson actualPerson = connector.getPeopleSuggestion(previousPerson.getSuggestionID());

        if(actualPerson.getName() == null){
            throw new NotFoundException("No People suggestion with ID "+person.getSuggestionID()+" found");
        }

        SuggestionPerson overwrite = new SuggestionPerson(actualPerson.getSuggestionID(),
                actualPerson.getDate(),
                actualPerson.getAuthor());


            if (!actualPerson.getName().equals(previousPerson.getName())) {
                overwrite.setName(actualPerson.getName());
                isDifferent = true;
            }

            if (!actualPerson.getDescription().equals(previousPerson.getDescription())) {
                overwrite.setDescription(actualPerson.getDescription());
                isDifferent = true;
            }

            if (!actualPerson.getMail().equals(previousPerson.getMail())) {
                overwrite.setMail(actualPerson.getMail());
                isDifferent = true;
            }

            if (!actualPerson.getPicture().equals(previousPerson.getPicture())) {
                overwrite.setPicture(actualPerson.getPicture());
                isDifferent = true;
            }

            if (!actualPerson.getRole().equals(previousPerson.getRole())) {
                overwrite.setRole(actualPerson.getRole());
                isDifferent = true;
            }

            if (!actualPerson.getRoom().equals(previousPerson.getRoom())) {
                overwrite.setRoom(actualPerson.getRoom());
                isDifferent = true;

            }


            ItemOverwriteException exception = new ItemOverwriteException(overwrite);

            if(isDifferent)
                throw exception;

            connector.deletePeopleSuggestion(person.getSuggestionID());
            connector.createPeopleSuggestion(person);


    }

    @Override
    public ArrayList<SuggestionPerson> getAllChangedPeople() throws DataAccessException, NotFoundException {
        ArrayList<SuggestionPerson> suggestions = connector.getPeopleSuggestions();

        if(suggestions == null){
            throw new DataAccessException("Null pointer at PeopleSuggestions list");
        }else if(suggestions.isEmpty()){
            throw new NotFoundException("No PeopleSuggestions found");
        }else{
            return suggestions;
        }
    }

    @Override
    public SuggestionPerson getPerson(int id) throws DataAccessException, NotFoundException {
        SuggestionPerson suggestionPerson = connector.getPeopleSuggestion(id);

        if(suggestionPerson == null){
            throw new NotFoundException("No People suggestion with ID "+id+" found");
        }else{
            return suggestionPerson;
        }

    }

    @Override
    public void deletePersonChange(int id) throws DataAccessException, NotFoundException {
        SuggestionPerson suggestionPerson = connector.getPeopleSuggestion(id);

        if(suggestionPerson == null){
            throw new NotFoundException("No People suggestion with ID "+id+" found");
        }else{
            connector.deletePeopleSuggestion(id);
        }
    }

    @Override
    public void approvePerson(int id) throws DataAccessException, NotFoundException {
        SuggestionPerson suggestionPerson = getPerson(id);

        System.out.println("Approve: "+suggestionPerson);

        if(suggestionPerson.getName() == null){
            throw new NotFoundException("No People suggestion with ID "+id+" found");
        }else{
            connector.createPerson(suggestionPerson.toPerson());
            connector.deletePeopleSuggestion(id);
        }
    }


}