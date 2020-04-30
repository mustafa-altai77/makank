package com.example.makank.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

public class PdfClass {
    @SerializedName("image")
    @Expose
    private File image;

    @SerializedName("user_id")
    @Expose
    private String userID;

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
