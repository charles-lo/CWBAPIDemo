package com.charles.weather.api.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response implements Serializable {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("records")
    @Expose
    private Records records;
    private final static long serialVersionUID = -2931594941390320105L;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Records getRecords() {
        return records;
    }

    public void setRecords(Records records) {
        this.records = records;
    }

}