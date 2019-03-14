package com.example.acer.sampleproject;

public class MyModelClass {

    String title,location,howApply;

    public MyModelClass(String title, String location, String howApply) {
        this.title = title;
        this.location = location;
        this.howApply = howApply;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHowApply() {
        return howApply;
    }

    public void setHowApply(String howApply) {
        this.howApply = howApply;
    }
}
