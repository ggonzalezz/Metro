package model;


public class User {
    String id;
    String name;
    String lastName;

    String gmail;

    public User(String id, String name, String lastName, String gmail) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.gmail = gmail;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
