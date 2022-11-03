package com.example.jnufood;

public class Get_Menu_Item {
    private String name;
    private String photo;
    private String restaurant;



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
    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }
}
