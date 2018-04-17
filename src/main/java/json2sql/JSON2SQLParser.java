package json2sql;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class JSON2SQLParser {

    public static void main(String[] args) throws Exception {

        //Parsing the source *.json file. Switch to either dist_persons, or dist_locations for best results."
        JSONObject objPersons = (JSONObject) ((JSONObject) new JSONParser().parse(new FileReader("src/main/resources/json_backup/dist_persons.json"))).get("Persons");

        HashMap<String, HashMap> persons = new HashMap<>();
        persons.putAll(objPersons);

        for (Map.Entry<String, HashMap> entryPersons : persons.entrySet()) {
            String pplName = (String) entryPersons.getKey();
            String pplRawName = (String) entryPersons.getValue().get("name");
            String pplMail = (String) entryPersons.getValue().get("email");
            String pplDesc = (String) entryPersons.getValue().get("description");
            String pplPictureURL = (String) entryPersons.getValue().get("pictureURL");
            String pplRole = (String) entryPersons.getValue().get("role");
            String pplRoomName = (String) entryPersons.getValue().get("roomName");

            System.out.println("(\"" + pplName + "\", \"" + pplRawName + "\", \"" + pplMail + "\", \"" + pplDesc + "\", \"" + pplPictureURL + "\", \"" + pplRole + "\", \"" + pplRoomName + "\"),");
        }

        JSONObject objLocations = (JSONObject) ((JSONObject) new JSONParser().parse(new FileReader("src/main/resources/json_backup/dist_locations.json"))).get("Locations");

        HashMap<String, HashMap<String, Object>> locations = new HashMap<String, HashMap<String, Object>>();
        locations.putAll(objLocations);

        for (HashMap<String, Object> location : locations.values()) {
            String locName = (String) location.get("name");
            String locDesc = (String) location.get("description");
            String locFloor = (String) location.get("floor");
            String locLandmark = (String) location.get("landmark");

            double locLatitude = (Double) ((HashMap) location.get("position")).get("latitude");
            double locLongitude = (Double) ((HashMap) location.get("position")).get("longitude");

            System.out.println("(\"" + locName + "\", \"" + locDesc + "\", \"" + locFloor + "\", \"" + locLandmark + "\", \"" + locLatitude + "\", \"" + locLongitude + "\"),");
        }
    }
}

