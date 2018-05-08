package controllers.tests;

import controllers.ChangedAppResources;
import controllers.exceptions.ItemOverwriteException;
import data.SuggestionLocation;

import java.util.ArrayList;
import java.util.Date;

public class SpecificTest {
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
            location.setSuggestionID(2);
            location.setDescription("");
            location.setLandmark("null");
            location.setLatitude(122.332);
            location.setLongitude(33.55);
            location.setFloor(0);
            location.setName("Test1");

            controls.addLocation(location);


            SuggestionLocation oldlocation = new SuggestionLocation();
            oldlocation.setTags(tags);
            oldlocation.setAuthor("s165202");
            oldlocation.setDate(new Date());
            oldlocation.setSuggestionID(2);
            oldlocation.setDescription(""); //changed params
            oldlocation.setLandmark("null");
            oldlocation.setLatitude(122.332);
            oldlocation.setLongitude(33.55);
            oldlocation.setFloor(0);
            oldlocation.setName("Test1");

            SuggestionLocation updatelocation = new SuggestionLocation();
            updatelocation.setTags(tags);
            updatelocation.setAuthor("s165202");
            updatelocation.setDate(new Date());
            updatelocation.setSuggestionID(2);
            updatelocation.setDescription("Changed description");
            updatelocation.setLandmark("null");
            updatelocation.setLatitude(666); //changed params
            updatelocation.setLongitude(33.55);
            updatelocation.setFloor(0);
            updatelocation.setName("Test1");

            controls.updateLocation(updatelocation, oldlocation);

            //controls.approveLocation(2);
            //controls.approvePerson(2);

        }catch(ItemOverwriteException error){
            error.printStackTrace();
            System.out.println(error.getOverwriteSug());


        }catch(Exception e){
            e.printStackTrace();
            e.getMessage();

        }
        }
}
