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

    public void setId(int id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

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
