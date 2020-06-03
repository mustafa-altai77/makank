package com.example.makank.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statistc {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("new_sure_cases")
    @Expose
    String new_sure_cases;
    @SerializedName("recovery_cases")
    @Expose
    String recovery_cases;
    @SerializedName("new_Deaths")
    @Expose
    String new_Deaths;
    @SerializedName("suspected_cases")
    @Expose
    String suspected_cases;
    @SerializedName("sum_cases")
    @Expose
    String sum_cases;
    @SerializedName("sum_recovery_cases")
    @Expose
    String sum_recovery_cases;
    @SerializedName("sum_Deaths")
    @Expose
    String sum_Deaths;
    @SerializedName("updated_at")
    @Expose
    String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNew_sure_cases() {
        return new_sure_cases;
    }

    public void setNew_sure_cases(String new_sure_cases) {
        this.new_sure_cases = new_sure_cases;
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

    public String getSuspected_cases() {
        return suspected_cases;
    }

    public void setSuspected_cases(String suspected_cases) {
        this.suspected_cases = suspected_cases;
    }

    public String getSum_cases() {
        return sum_cases;
    }

    public void setSum_cases(String sum_cases) {
        this.sum_cases = sum_cases;
    }

    public String getSum_recovery_cases() {
        return sum_recovery_cases;
    }

    public void setSum_recovery_cases(String sum_recovery_cases) {
        this.sum_recovery_cases = sum_recovery_cases;
    }

    public String getSum_Deaths() {
        return sum_Deaths;
    }

    public void setSum_Deaths(String sum_Deaths) {
        this.sum_Deaths = sum_Deaths;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
