package com.example.jnufood;

public class Get_Food_List {
    private String  amount;
    private String name;
    private String photo;
    private String price;
    private String restaurant;
    private String status;

    public Get_Food_List(){

    }

    public Get_Food_List(String amount, String name, String photo, String price, String restaurant,String status) {
        this.amount = amount;
        this.name = name;
        this.photo = photo;
        this.price = price;
        this.restaurant = restaurant;
        this.status=status;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
