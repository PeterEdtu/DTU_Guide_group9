package controllers.tests;

import api.HTTPException;
import controllers.ChangedAppResources;
import data.SuggestionLocation;

import java.util.ArrayList;
import java.util.Date;

public class ApproveLocationTest {

    private static ChangedAppResources controls = null;

    public static void main(String[] args) {
        controls = ChangedAppResources.getInstance();

        try {
            ArrayList<String> tags = new ArrayList<>();

            tags.add("Blackboard");
            tags.add("CAE");

            SuggestionLocation location = new SuggestionLocation();
            location.setTags(tags);
            location.setAuthor("s165202");
            location.setDate(new Date());
            location.setDescription("");
            location.setLandmark("null");
            location.setLatitude(2232);
            location.setLongitude(332323);
            location.setFloor(0);
            location.setName("Mads Loc");

            controls.addLocation(location);


            controls.approveLocation(2);


            tags = new ArrayList<>();

            tags.add("Whiteboard");

            location = new SuggestionLocation();
            location.setTags(tags);
            location.setAuthor("s165202");
            location.setDate(new Date());
            location.setDescription("Mads description changed");
            location.setLandmark("fuckboi");
            location.setLatitude(77777777);
            location.setLongitude(777332323);
            location.setFloor(0);
            location.setName("Mads Loc");

            controls.addLocation(location);

            controls.approveLocation(3);


        } catch (HTTPException e) {

        }


    }
}
