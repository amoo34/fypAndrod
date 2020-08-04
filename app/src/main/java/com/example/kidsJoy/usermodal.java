package com.example.kidsJoy;

import java.io.StringReader;
import java.security.PublicKey;

public class usermodal {
    public String name;
    public String email;
    public String phoneNumber;
    public String password;
    public String homeLocLat;
    public String homeLocLon;
    public String workLocLat;
    public String workLocLon;
    public String homeLocation;
    public String workLocation;

    public usermodal()
    {

    }
    public usermodal(String name, String email, String phoneNumber, String password, String homeLocation, String workLocation, String homeLocLat, String homeLocLon,String workLocLat,String workLocLon)
    {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.workLocation=workLocation;
        this.homeLocation=homeLocation;
        this.homeLocLat=homeLocLat;
        this.homeLocLon=homeLocLon;
        this.workLocLat=workLocLat;
        this.workLocLon=workLocLon;

    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    public String getHomeLocLat() {
        return homeLocLat;
    }

    public void setHomeLocLat(String homeLocLat) {
        this.homeLocLat = homeLocLat;
    }

    public String getHomeLocLon() {
        return homeLocLon;
    }

    public void setHomeLocLon(String homeLocLon) {
        this.homeLocLon = homeLocLon;
    }

    public String getWorkLocLat() {
        return workLocLat;
    }

    public void setWorkLocLat(String workLocLat) {
        this.workLocLat = workLocLat;
    }

    public String getWorkLocLon() {
        return workLocLon;
    }

    public void setWorkLocLon(String workLocLon) {
        this.workLocLon = workLocLon;
    }
}
