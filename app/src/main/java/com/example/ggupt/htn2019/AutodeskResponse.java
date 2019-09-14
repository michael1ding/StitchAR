package com.example.ggupt.htn2019;

import com.google.gson.annotations.SerializedName;

public class AutodeskResponse {
    @SerializedName("info")
    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

