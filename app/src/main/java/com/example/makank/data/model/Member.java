package com.example.makank.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Member {
    String id;
    String status;
    String first_name;
    String second_name;
    String last_name;
    @SerializedName("pivot")
    @Expose
    private Pivot pivots;
    @SerializedName("user")
    @Expose
    private User user;
    private String groupID;
    public Member(String id, String my_id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getstatus() {
        return status;
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



    public Pivot getPivots() {
        return pivots;
    }

    public User getUser() {
        return user;
    }

    public void setPivots(Pivot pivots) {
        this.pivots = pivots;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getGroupID() {
        return groupID;

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

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

}
