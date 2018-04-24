package data;

public class Person extends Searchable {

    private int id;
    private String name;
    private String mail;
    private String desc;
    private String picture;
    private String role;
    private String room;

    public Person(){

    }

    public Person(int id, String name, String mail, String desc, String picture, String role, String room) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.desc = desc;
        this.picture = picture;
        this.role = role;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
