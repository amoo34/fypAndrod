package com.example.kidsJoy;

public class BookingRequestModel {

    private String username;
    private String userEmail;
    private String centerEmail;
    private String centerName;
    private String bookingDate;
    private String startTime;
    private String endTime;
    private String childName;
    private String childAge;
    private String childGender;
    private String price;
    private String bookingID;


    public BookingRequestModel() {
    }


    public BookingRequestModel(String username, String userEmail, String centerEmail,String centerName, String bookingDate, String startTime, String endTime, String childName, String childAge, String childGender, String price,String bookingID) {
        this.username = username;
        this.userEmail = userEmail;
        this.centerEmail = centerEmail;
        this.centerName= centerName;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.childName = childName;
        this.childAge = childAge;
        this.childGender = childGender;
        this.price = price;
        this.bookingID=bookingID;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCenterEmail() {
        return centerEmail;
    }

    public void setCenterEmail(String centerEmail) {
        this.centerEmail = centerEmail;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildAge() {
        return childAge;
    }

    public void setChildAge(String childAge) {
        this.childAge = childAge;
    }

    public String getChildGender() {
        return childGender;
    }

    public void setChildGender(String childGender) {
        this.childGender = childGender;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }
}

