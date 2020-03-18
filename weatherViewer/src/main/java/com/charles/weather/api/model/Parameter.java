package com.charles.weather.api.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Parameter implements Serializable {

    @Id(assignable = true)
    @SerializedName("id")
    @Expose
    Long id;
    @SerializedName("parameterName")
    @Expose
    private String parameterName;
    @SerializedName("parameterValue")
    @Expose
    private String parameterValue;
    @SerializedName("parameterUnit")
    @Expose
    private String parameterUnit;
    private final static long serialVersionUID = -8632159517708970835L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getParameterUnit() {
        return parameterUnit;
    }

    public void setParameterUnit(String parameterUnit) {
        this.parameterUnit = parameterUnit;
    }

}