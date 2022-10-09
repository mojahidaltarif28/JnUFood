package com.example.jnufood;

public class Get_Menu_Item_Recycle_view {
    String name,photo;

    public Get_Menu_Item_Recycle_view(){

    }
    public Get_Menu_Item_Recycle_view(String name, String photo) {
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
