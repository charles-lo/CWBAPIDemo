package com.charles.weather.api.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Id;

public class Records implements Serializable {
    @SerializedName("datasetDescription")
    @Expose
    private String datasetDescription;
    @SerializedName("location")
    @Expose
    private List<Location> location = null;
    private final static long serialVersionUID = 6527174977135861012L;

    public String getDatasetDescription() {
        return datasetDescription;
    }

    public void setDatasetDescription(String datasetDescription) {
        this.datasetDescription = datasetDescription;
    }

    public List<Location> getLocation() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }

}