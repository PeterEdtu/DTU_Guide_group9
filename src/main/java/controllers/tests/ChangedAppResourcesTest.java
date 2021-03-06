package controllers.tests;

import api.HTTPException;
import controllers.ChangedAppResources;
import controllers.exceptions.DataAccessException;
import controllers.exceptions.NotFoundException;
import data.SuggestionLocation;
import data.SuggestionPerson;

import java.util.ArrayList;
import java.util.Date;

public class ChangedAppResourcesTest {

    private static ChangedAppResources controls = null;

    public static void main(String[] args) {
        controls = ChangedAppResources.getInstance();

        try {
            System.out.println(controls.getAllChangedLocations());

            System.out.println(controls.getAllChangedPeople());

            System.out.println(controls.getLocation(1));

            System.out.println(controls.getPerson(1));

            ArrayList<String> tags = new ArrayList<>();

            tags.add("Blackboard");
            tags.add("CAE");
            //tags.add("depression");

            SuggestionLocation location = new SuggestionLocation();
            location.setTags(tags);
            location.setAuthor("s165202");
            location.setDate(new Date());
            location.setSuggestionID(2);
            location.setDescription("");
            location.setLandmark("null");
            location.setLatitude(122.332);
            location.setLongitude(33.55);
            location.setFloor(0);
            location.setName("No U");

            controls.addLocation(location);

            System.out.println("NO U LOCATION :"+controls.getAllChangedLocations());

            System.out.println("Approuve location : No U");
            //controls.approveLocation(3);

            SuggestionPerson person = new SuggestionPerson();
            person.setPicture("https://pics.me.me/no-u-spell-card-no-u-deflects-all-roasts-29807977.png");
            person.setAuthor("s165202");
            person.setDate(new Date());
            person.setSuggestionID(3);
            person.setId(999);
            person.setMail("no_u@gmail.com");
            person.setName("M No U");
            person.setRoom("No U");

            controls.addPerson(person);

            System.out.println(controls.getAllChangedLocations());

            controls.approvePerson(3);

            System.out.println(controls.getAllChangedPeople());

        } catch (HTTPException e) {
            e.printStackTrace();
        }
    }
}
