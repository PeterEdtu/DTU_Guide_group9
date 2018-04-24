package data;

public class Location extends Searchable {

    private String name;

    private String description;

    private int floor;

    private String landmark;

    private double latitude;

    private double longitude;

    public Location(){

    }

    public Location(String name, String description, int floor, String landmark, double latitude, double longitude) {
        this.name = name;
        this.description = description;
        this.floor = floor;
        this.landmark = landmark;
        this.latitude = latitude;
        this.longitude = longitude;
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
