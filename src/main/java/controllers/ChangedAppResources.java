package controllers;

import controllers.interfaces.IChangedAppResources;
import controllers.exceptions.DataAccessException;
import controllers.exceptions.ItemOverwriteException;
import controllers.exceptions.NotFoundException;
import data.Person;
import data.Location;
import data.SuggestionLocation;
import data.SuggestionPerson;
import database.connector.DummyConnector;

import java.util.ArrayList;

public class ChangedAppResources implements IChangedAppResources {

    DummyConnector connector = new DummyConnector();

    private static ChangedAppResources controller = null;

    private ChangedAppResources() {
    }


    public synchronized ChangedAppResources getInstance() {
        if (controller == null) {
            controller = new ChangedAppResources();
        }

        return controller;
    }


    @Override
    public void updateLocation(SuggestionLocation newLoc, SuggestionLocation previousLocation) throws ItemOverwriteException, DataAccessException {
        //Compare the previousLocation with the
        //actual (instance) location before update it
        //with the variable "newLoc" wich is the new location

        SuggestionLocation actualLocation = connector.getLocationSuggestion(previousLocation.getSuggestionID());

        SuggestionLocation overwrite = new SuggestionLocation(actualLocation.getSuggestionID(),
                actualLocation.getDate(),
                actualLocation.getAuthor());

        if (!actualLocation.equals(previousLocation)) {

            if (!actualLocation.getName().equals(previousLocation.getName())) {
                overwrite.setName(actualLocation.getName());
            }

            if (!actualLocation.getDescription().equals(previousLocation.getDescription())) {
                overwrite.setDescription(actualLocation.getDescription());
            }
            if (actualLocation.getFloor() != previousLocation.getFloor()) {
                overwrite.setFloor(actualLocation.getFloor());
            }
            if (!actualLocation.getLandmark().equals(previousLocation.getLandmark())) {
                overwrite.setLandmark(actualLocation.getLandmark());
            }
            if (actualLocation.getLatitude() != previousLocation.getLatitude()) {
                overwrite.setLatitude(actualLocation.getLatitude());
            }
            if (actualLocation.getLongitude() != previousLocation.getLongitude()) {
                overwrite.setLongitude(actualLocation.getLongitude());
            }
            if (!actualLocation.getName().equals(previousLocation.getName())) {
                overwrite.setName(actualLocation.getName());
            }

            if(!actualLocation.getTags().containsAll(previousLocation.getTags())){
                overwrite.setTags(actualLocation.getTags());
            }

            ItemOverwriteException exception = new ItemOverwriteException(overwrite);

            throw exception;

        } else {
            connector.updateLocation(newLoc);
        }

    }

    @Override
    public void addLocation(SuggestionLocation location) throws DataAccessException {
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

        if(suggestionLocation == null){
            throw new NotFoundException("Suggestion ID "+id+" cannot be found");
        }else{
            connector.createLocation(suggestionLocation.toLocation());
        }
    }

    @Override
    public void addPerson(SuggestionPerson person) throws DataAccessException {
        connector.createPeopleSuggestion(person);
    }

    @Override
    public void updatePerson(SuggestionPerson person, SuggestionPerson previousPerson) throws DataAccessException, ItemOverwriteException {
        //TODO ...

        SuggestionPerson actualPerson = connector.getPeopleSuggestion(previousPerson.getSuggestionID());

        SuggestionPerson overwrite = new SuggestionPerson(actualPerson.getSuggestionID(),
                actualPerson.getDate(),
                actualPerson.getAuthor());

        if (!actualPerson.equals(previousPerson)) {

            if (!actualPerson.getName().equals(previousPerson.getName())) {
                overwrite.setName(actualPerson.getName());
            }

            if (!actualPerson.getDescription().equals(previousPerson.getDescription())) {
                overwrite.setDescription(actualPerson.getDescription());
            }

            if (!actualPerson.getMail().equals(previousPerson.getMail())) {
                overwrite.setMail(actualPerson.getMail());
            }

            if (!actualPerson.getPicture().equals(previousPerson.getPicture())) {
                overwrite.setPicture(actualPerson.getPicture());
            }

            if (!actualPerson.getRole().equals(previousPerson.getRole())) {
                overwrite.setRole(actualPerson.getRole());
            }

            if (!actualPerson.getRoom().equals(previousPerson.getRoom())) {
                overwrite.setRoom(actualPerson.getRoom());
            }


            ItemOverwriteException exception = new ItemOverwriteException(overwrite);

            throw exception;

        } else {
            connector.updatePerson(person);
        }
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
        SuggestionPerson suggestionPerson = connector.getPeopleSuggestion(id);

        if(suggestionPerson == null){
            throw new NotFoundException("No People suggestion with ID "+id+" found");
        }else{
            connector.createPerson(suggestionPerson.toPerson());
        }
    }


}