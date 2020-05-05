package com.example.makank.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filresponse {
    @SerializedName("success")
    boolean success;
    @SerializedName("document")
    String document;
    @SerializedName("person_id")
    String person_id;

    @SerializedName("message")

    String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

}
