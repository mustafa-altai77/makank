package com.example.makank.data.model;

import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {
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
    @SerializedName("blood_type")
    @Expose
    private String blood_type;
    @SerializedName("id")
    @Expose
    private int id;

    public Person(String first_name, String second_name, String last_name, String gender, String age, String local_id,String blood_type) {
        this.first_name = first_name;
        this.second_name = second_name;
        this.last_name = last_name;
        this.gender = gender;
        this.age = age;
        this.local_id = local_id;
        this.blood_type =blood_type;
        this.id = id;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }
}
