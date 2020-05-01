package com.example.makank.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class Disease implements Serializable {
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    @SerializedName("id")
    int id;
    @SerializedName("name")
     String name;

    public Disease() {
        this.id = id;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
