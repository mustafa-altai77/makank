package com.example.makank.ui.home;

public class Home {
    int id;
    String name;
    int image;

    public Home(String name, int image) {
        this.name = name;
        this.image = image;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
