package com.shihabcse.cbdcbloodfamily.models;

import com.google.firebase.database.ServerValue;

public class HelpLine {
    private String AmbulanceName;
    private String AmbulanceContactNumber;
    private String AmbulanceUpazilaOrUniversity;
    private String AmbulanceDistrict;
    private String AmbulanceStatus;
    private Object timeStamp;

    public HelpLine(){

    }

    public HelpLine(String ambulanceName, String ambulanceContactNumber, String ambulanceUpazilaOrUniversity, String ambulanceDistrict, String ambulanceStatus) {
        AmbulanceName = ambulanceName;
        AmbulanceContactNumber = ambulanceContactNumber;
        AmbulanceUpazilaOrUniversity = ambulanceUpazilaOrUniversity;
        AmbulanceDistrict = ambulanceDistrict;
        AmbulanceStatus = ambulanceStatus;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getAmbulanceName() {
        return AmbulanceName;
    }

    public void setAmbulanceName(String ambulanceName) {
        AmbulanceName = ambulanceName;
    }

    public String getAmbulanceContactNumber() {
        return AmbulanceContactNumber;
    }

    public void setAmbulanceContactNumber(String ambulanceContactNumber) {
        AmbulanceContactNumber = ambulanceContactNumber;
    }

    public String getAmbulanceUpazilaOrUniversity() {
        return AmbulanceUpazilaOrUniversity;
    }

    public void setAmbulanceUpazilaOrUniversity(String ambulanceUpazilaOrUniversity) {
        AmbulanceUpazilaOrUniversity = ambulanceUpazilaOrUniversity;
    }

    public String getAmbulanceDistrict() {
        return AmbulanceDistrict;
    }

    public void setAmbulanceDistrict(String ambulanceDistrict) {
        AmbulanceDistrict = ambulanceDistrict;
    }

    public String getAmbulanceStatus() {
        return AmbulanceStatus;
    }

    public void setAmbulanceStatus(String ambulanceStatus) {
        AmbulanceStatus = ambulanceStatus;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

}
