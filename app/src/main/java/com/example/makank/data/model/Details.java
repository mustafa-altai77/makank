package com.example.makank.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("first_name")
    @Expose
    private String first_name;
    @SerializedName("second_name")
    @Expose
    private String second_name;
    @SerializedName("last_name")
    @Expose
    private String last_name;

    @SerializedName("qr_code")
    @Expose
    private String qr_code;

    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("local_id")
    @Expose
    private String local_id;
    @SerializedName("user_id")
    @Expose
    private int user_id;

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getQr_code() {
        return qr_code;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {
        return status;
    }

    public String getAge() {
        return age;
    }

    public String getLocal_id() {
        return local_id;
    }

    public int getUser_id() {
        return user_id;
    }
}
