package com.example.makank.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statistc {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("updated_at")
    @Expose
    String updated_at;
    @SerializedName("cases_count")
    @Expose
    String cases_count;
    @SerializedName("recovery_cases")
    @Expose
    String recovery_cases;
    @SerializedName("new_Deaths")
    @Expose
    String new_Deaths;
    @SerializedName("latest_cases")
    @Expose
    private LastCase latest_cases;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCases_count() {
        return cases_count;
    }

    public void setCases_count(String cases_count) {
        this.cases_count = cases_count;
    }

    public String getRecovery_cases() {
        return recovery_cases;
    }

    public void setRecovery_cases(String recovery_cases) {
        this.recovery_cases = recovery_cases;
    }

    public String getNew_Deaths() {
        return new_Deaths;
    }

    public void setNew_Deaths(String new_Deaths) {
        this.new_Deaths = new_Deaths;
    }

    public LastCase getLatest_cases() {
        return latest_cases;
    }

    public void setLatest_cases(LastCase latest_cases) {
        this.latest_cases = latest_cases;
    }
}
