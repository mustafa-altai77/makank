package com.example.makank.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filresponse {
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

}
