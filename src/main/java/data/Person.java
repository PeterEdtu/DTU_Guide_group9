package data;

public class Person extends Searchable {

    private int id;
    private String name;
    private String mail;
    private String description;
    private String picture;
    private String role;
    private String room;
    private Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Person() {
        _init_();
    }

    public void _init_(){
        id = 0;
        name = null;
        mail = "";
        description = "";
        picture = "https://cdn.discordapp.com/attachments/390440892009414657/443416059761000448/unknown.png";
        role = "None";
        room = "¤";
        location = new Location();
    }

    public Person(int id, String name, String mail, String description, String picture, String role, String room) {
        _init_();

        this.id = id;
        this.name = name;
        if(mail!=null) {
            this.mail = mail;
        }
        if(description!=null) {
            this.description = description;
        }
        if(picture!=null) {
            this.picture = picture;
        }
        if(role!=null){
            this.role = role;
        }
        if(room!=null){
            this.room = room;
        }

    }

    public Person(Person pers) {
        this(pers.id,
                pers.name,
                pers.mail,
                pers.description,
                pers.picture,
                pers.role,
                pers.room
        );
        this.location=pers.location;
    }


    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", role='" + role + '\'' +
                ", room='" + room + '\'' +
                ", location=" + location +
                '}';
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
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

    @Override
    public String searchName() {
        return name;
    }

}
