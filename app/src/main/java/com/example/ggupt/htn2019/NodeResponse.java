package com.example.ggupt.htn2019;

import com.google.gson.annotations.SerializedName;

public class NodeResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("result")
    private boolean result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
