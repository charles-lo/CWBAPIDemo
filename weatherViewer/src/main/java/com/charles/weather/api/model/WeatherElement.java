package com.charles.weather.api.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class WeatherElement implements Serializable
{
    @Id(assignable = true)
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("elementName")
    @Expose
    private String elementName;
    @SerializedName("time")
    @Expose
    private List<Time> time;
    private final static long serialVersionUID = -1580645210794646367L;

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public List<Time> getTime() {
        return time;
    }

    public void setTime(List<Time> time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}