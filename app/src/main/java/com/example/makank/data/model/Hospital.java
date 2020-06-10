package com.example.makank.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hospital {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lan")
    @Expose
    private String lan;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("desc_address")
    @Expose
    private String desc_address;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("bed_count")
    @Expose
    private String bed_count;
    @SerializedName("is_corona")
    @Expose
    private String is_corona;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getDesc_address() {
        return desc_address;
    }

    public void setDesc_address(String desc_address) {
        this.desc_address = desc_address;
    }

    public String getBed_count() {
        return bed_count;
    }

    public void setBed_count(String bed_count) {
        this.bed_count = bed_count;
    }

    public String getIs_corona() {
        return is_corona;
    }

    public void setIs_corona(String is_corona) {
        this.is_corona = is_corona;
    }

    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }
}
