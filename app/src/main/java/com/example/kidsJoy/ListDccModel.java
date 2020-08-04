package com.example.kidsJoy;

public class ListDccModel {
    private String dCtime;
    private String dOtime;
    private String dRates;
    private String dRestrections;
    private String demail;
    private String dname;
    private String dpassword;
    private String dphoneNumber;
    private String imageUrl;
    private String latitude;
    private String longitude;
    private boolean permission;
    private String availability;
    public String overAllRating;

    public ListDccModel() {
    }

    public ListDccModel(String dCtime, String dOtime,String dRates, String dRestrections, String demail, String dname, String dpassword, String dphoneNumber, String imageUrl, String latitude, String longitude, boolean permission,String availability,String overAllRating) {
        this.dCtime = dCtime;
        this.dOtime = dOtime;
        this.dRates=dRates;
        this.dRestrections = dRestrections;
        this.demail = demail;
        this.dname = dname;
        this.dpassword = dpassword;
        this.dphoneNumber = dphoneNumber;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.permission = permission;
        this.availability=availability;
        this.overAllRating=overAllRating;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getdRates() {
        return dRates;
    }

    public void setdRates(String dRates) {
        this.dRates = dRates;
    }

    public String getdCtime() {
        return dCtime;
    }

    public void setdCtime(String dCtime) {
        this.dCtime = dCtime;
    }

    public String getdOtime() {
        return dOtime;
    }

    public void setdOtime(String dOtime) {
        this.dOtime = dOtime;
    }

    public String getdRestrections() {
        return dRestrections;
    }

    public void setdRestrections(String dRestrections) {
        this.dRestrections = dRestrections;
    }

    public String getDemail() {
        return demail;
    }

    public void setDemail(String demail) {
        this.demail = demail;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDpassword() {
        return dpassword;
    }

    public void setDpassword(String dpassword) {
        this.dpassword = dpassword;
    }

    public String getDphoneNumber() {
        return dphoneNumber;
    }

    public void setDphoneNumber(String dphoneNumber) {
        this.dphoneNumber = dphoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isPermission() {
        return permission;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public String getOverAllRating() {
        return overAllRating;
    }

    public void setOverAllRating(String overAllRating) {
        this.overAllRating = overAllRating;
    }
}
