package com.shihabcse.cbdcbloodfamily.models;

import com.google.firebase.database.ServerValue;

public class BloodRequestPatient {
    private String registerUserId;
    private String patientName;
    private String quantityOfBlood;
    private String bloodGroupPatient;
    private String patientType;
    private String patientPhoneNumber;
    private String districtPatient;
    private String cityPatient;
    private String bloodNeededDate;
    private Object timeStamp;

    public BloodRequestPatient(){

    }

    public BloodRequestPatient(String registerUserId, String patientName, String quantityOfBlood, String bloodGroupPatient, String patientType, String patientPhoneNumber, String districtPatient,String cityPatient, String bloodNeededDate) {
        this.registerUserId = registerUserId;
        this.patientName = patientName;
        this.quantityOfBlood = quantityOfBlood;
        this.bloodGroupPatient = bloodGroupPatient;
        this.patientType = patientType;
        this.patientPhoneNumber = patientPhoneNumber;
        this.districtPatient = districtPatient;
        this.cityPatient = cityPatient;
        this.bloodNeededDate = bloodNeededDate;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getCityPatient() {
        return cityPatient;
    }

    public void setCityPatient(String cityPatient) {
        this.cityPatient = cityPatient;
    }

    public String getPatientPhoneNumber() {
        return patientPhoneNumber;
    }

    public void setPatientPhoneNumber(String patientPhoneNumber) {
        this.patientPhoneNumber = patientPhoneNumber;
    }

    public String getRegisterUserId() {
        return registerUserId;
    }

    public void setRegisterUserId(String registerUserId) {
        this.registerUserId = registerUserId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getQuantityOfBlood() {
        return quantityOfBlood;
    }

    public void setQuantityOfBlood(String quantityOfBlood) {
        this.quantityOfBlood = quantityOfBlood;
    }

    public String getBloodGroupPatient() {
        return bloodGroupPatient;
    }

    public void setBloodGroupPatient(String bloodGroupPatient) {
        this.bloodGroupPatient = bloodGroupPatient;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public String getDistrictPatient() {
        return districtPatient;
    }

    public void setDistrictPatient(String districtPatient) {
        this.districtPatient = districtPatient;
    }

    public String getBloodNeededDate() {
        return bloodNeededDate;
    }

    public void setBloodNeededDate(String bloodNeededDate) {
        this.bloodNeededDate = bloodNeededDate;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
