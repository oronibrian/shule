package com.example.user.shule.Models;

import android.support.annotation.Keep;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
@Keep

public class School  implements Serializable {
    private String Name, Desc;
    private Double Longi, Lat;

    public School() {
    }

    public School(String name, String desc, Double longi, Double lat) {
        Name = name;
        Desc = desc;
        Longi = longi;
        Lat = lat;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public Double getLongi() {
        return Longi;
    }

    public void setLongi(Double longi) {
        Longi = longi;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }
}
