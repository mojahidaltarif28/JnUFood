package com.example.jnufood;

public class Get_Menu_Item {
    private String name;
    private String photo;

    public Get_Menu_Item() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public Get_Menu_Item(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
