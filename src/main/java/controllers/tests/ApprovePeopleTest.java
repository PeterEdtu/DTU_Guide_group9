package controllers.tests;

import api.HTTPException;
import controllers.ChangedAppResources;
import data.SuggestionPerson;

import java.util.Date;

public class ApprovePeopleTest {
    private static ChangedAppResources controls = null;

    public static void main(String[] args) {
        controls = ChangedAppResources.getInstance();

        try {

            //controls.approveLocation(6);

            SuggestionPerson person = new SuggestionPerson();
            //person.setPicture("https://pics.me.me/no-u-spell-card-no-u-deflects-all-roasts-29807977.png");
            person.setAuthor("s165202");
            person.setDate(new Date());
            person.setDescription("Easter Egg");
            person.setMail("no@u.meme");
            person.setName("M No U");
            person.setRoom("X1.13");
            person.setRole("null");


            SuggestionPerson oldperson = new SuggestionPerson();
            //oldperson.setPicture("https://pics.me.me/no-u-spell-card-no-u-deflects-all-roasts-29807977.png");
            oldperson.setAuthor("s165202");
            oldperson.setDate(new Date());
            oldperson.setSuggestionID(2);
            oldperson.setDescription("Easter Egg");
            oldperson.setMail("no@u.meme");
            oldperson.setName("M No U");
            oldperson.setRoom("X1.13");
            oldperson.setRole("null");

            controls.addPerson(person);

            person = new SuggestionPerson();
            //person.setPicture("https://pics.me.me/no-u-spell-card-no-u-deflects-all-roasts-29807977.png");
            person.setAuthor("s165202");
            person.setDate(new Date());
            person.setSuggestionID(2);
            person.setDescription("Easter Egg CHANGED");
            person.setMail("no@u.meme");
            person.setName("M No U");
            person.setRoom("X1.13");
            person.setRole("null");

            controls.updatePerson(person, oldperson);

            System.out.println(controls.getAllChangedLocations());

            controls.approvePerson(3);

            System.out.println(controls.getAllChangedPeople());
        } catch (HTTPException e) {
            e.printStackTrace();
        }

    }

}

