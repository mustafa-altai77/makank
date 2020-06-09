package com.example.makank.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastCase {
  @SerializedName("new_sure_cases")
  @Expose
    String new_sure_cases;
    @SerializedName("recovery_cases")
    @Expose
    String recovery_cases;
    @SerializedName("new_Deaths")
    @Expose
    String new_Deaths;
    @SerializedName("created_at")
    @Expose
    String created_at;

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
