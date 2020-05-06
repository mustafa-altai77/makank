package com.example.makank.ui.home;

public class Home {
    int id;
    String name;
    int image;

    public Home(int id,String name, int image) {
        this.name = name;
        this.image = image;
        this.id = id;

    }



    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
