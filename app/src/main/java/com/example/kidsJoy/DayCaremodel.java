package com.example.kidsJoy;

import android.content.Intent;

public class DayCaremodel {
    public String dname;
    public String demail;
    public String imageUrl;
    public String dphoneNumber;
    public String latitude;
    public String longitude;
    public String dpassword;
    public  String dCtime;
    public String dOtime;
    public String dRates;
    public String dRestrections;
    public String availability;
    public String overAllRating;


    public DayCaremodel(){

    }


    public DayCaremodel(String dname,String demail,String dphoneNumber,String latitude,String longitude,String dpassword,String dOtime,String dCtime,String dRates,String dRestrections,String availability,String overAllRating)
    {
        this.dname = dname;
        this.demail = demail;
        this.dphoneNumber = dphoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dpassword = dpassword;
        this.dOtime=dOtime;
        this.dCtime=dCtime;
        this.dRates=dRates;
        this.dRestrections=dRestrections;
        this.availability=availability;
        this.overAllRating=overAllRating;

    }

    public String getPhoneNumber() {
        return dphoneNumber;
    }

    public String getPassword() {
        return dpassword;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDemail() {
        return demail;
    }

    public String getDname() {
        return dname;
    }

    public String getdOtime() {return dOtime;}

    public String getdCtime() {return dCtime;}

    public String getdRates() {return dRates;}

    public String getdRestrections(){return dRestrections;}

    public String getimageUrl(){return imageUrl;}

    public String getAvailability() { return availability; }

    public String getOverAllRating() {return overAllRating;}






}
