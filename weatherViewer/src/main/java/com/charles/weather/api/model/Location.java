package com.charles.weather.api.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location implements Serializable {

    @SerializedName("locationName")
    @Expose
    private String locationName;
    @SerializedName("weatherElement")
    @Expose
    private List<WeatherElement> weatherElement = null;
    private final static long serialVersionUID = 2456642026495791932L;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public List<WeatherElement> getWeatherElement() {
        return weatherElement;
    }

    public void setWeatherElement(List<WeatherElement> weatherElement) {
        this.weatherElement = weatherElement;
    }

}