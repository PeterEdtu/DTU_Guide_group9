package data;

import java.util.ArrayList;

public class Location extends Searchable {

    private String name;

    private String description;

    private int floor;

    private String landmark;

    private double latitude;

    private double longitude;

    private ArrayList<String> tags;

    public Location() {

    }

    @Override
    public String getSearchName() {
        return name;
    }

    public Location(String name, String description, int floor, String landmark, double latitude, double longitude, ArrayList<String> tags) {
        this.name = name;
        this.description = description;
        this.floor = floor;
        this.landmark = landmark;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tags = tags;
    }

    public Location(Location loc) {
        this(loc.getName(),
                loc.getDescription(),
                loc.getFloor(),
                loc.getLandmark(),
                loc.getLatitude(),
                loc.getLongitude(),
                loc.getTags());
    }



    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", floor=" + floor +
                ", landmark='" + landmark + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", tags=" + tags +
                '}';
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
