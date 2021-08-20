package com.shihabcse.cbdcbloodfamily.models;

import com.google.firebase.database.ServerValue;

public class HelpLine {
    private String ambulanceName;
    private String ambulanceContactNumber;
    private String ambulanceUpazilaOrUniversity;
    private String ambulanceDistrict;
    private String ambulanceStatus;
    private Object timeStamp;

    public HelpLine(){

    }

    public HelpLine(String ambulanceName, String ambulanceContactNumber, String ambulanceUpazilaOrUniversity, String ambulanceDistrict, String ambulanceStatus) {
        this.ambulanceName = ambulanceName;
        this.ambulanceContactNumber = ambulanceContactNumber;
        this.ambulanceUpazilaOrUniversity = ambulanceUpazilaOrUniversity;
        this.ambulanceDistrict = ambulanceDistrict;
        this.ambulanceStatus = ambulanceStatus;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getAmbulanceName() {
        return ambulanceName;
    }

    public void setAmbulanceName(String ambulanceName) {
        this.ambulanceName = ambulanceName;
    }

    public String getAmbulanceContactNumber() {
        return ambulanceContactNumber;
    }

    public void setAmbulanceContactNumber(String ambulanceContactNumber) {
        this.ambulanceContactNumber = ambulanceContactNumber;
    }

    public String getAmbulanceUpazilaOrUniversity() {
        return ambulanceUpazilaOrUniversity;
    }

    public void setAmbulanceUpazilaOrUniversity(String ambulanceUpazilaOrUniversity) {
        this.ambulanceUpazilaOrUniversity = ambulanceUpazilaOrUniversity;
    }

    public String getAmbulanceDistrict() {
        return ambulanceDistrict;
    }

    public void setAmbulanceDistrict(String ambulanceDistrict) {
        this.ambulanceDistrict = ambulanceDistrict;
    }

    public String getAmbulanceStatus() {
        return ambulanceStatus;
    }

    public void setAmbulanceStatus(String ambulanceStatus) {
        this.ambulanceStatus = ambulanceStatus;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
