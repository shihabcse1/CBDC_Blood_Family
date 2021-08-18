package com.shihabcse.cbdcbloodfamily.models;

import com.google.firebase.database.ServerValue;

public class BloodDonor {
    private String bloodDonorName;
    private String bloodDonorContactNumber;
    private String bloodDonorAddress;
    private String bloodGroupPatient;
    private String lastBloodDonationDate;
    private String bloodDonorOrganization;
    private String bloodDonationStatus;
    private Object timeStamp;

    public BloodDonor(){

    }

    public BloodDonor(String bloodDonorName, String bloodDonorContactNumber, String bloodDonorAddress, String bloodGroupPatient, String lastBloodDonationDate, String bloodDonorOrganization, String bloodDonationStatus) {
        this.bloodDonorName = bloodDonorName;
        this.bloodDonorContactNumber = bloodDonorContactNumber;
        this.bloodDonorAddress = bloodDonorAddress;
        this.bloodGroupPatient = bloodGroupPatient;
        this.lastBloodDonationDate = lastBloodDonationDate;
        this.bloodDonorOrganization = bloodDonorOrganization;
        this.bloodDonationStatus = bloodDonationStatus;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getBloodDonationStatus() {
        return bloodDonationStatus;
    }

    public String getLastBloodDonationDate() {
        return lastBloodDonationDate;
    }

    public void setLastBloodDonationDate(String lastBloodDonationDate) {
        this.lastBloodDonationDate = lastBloodDonationDate;
    }

    public void setBloodDonationStatus(String bloodDonationStatus) {
        this.bloodDonationStatus = bloodDonationStatus;
    }

    public String getBloodDonorName() {
        return bloodDonorName;
    }

    public void setBloodDonorName(String bloodDonorName) {
        this.bloodDonorName = bloodDonorName;
    }

    public String getBloodDonorContactNumber() {
        return bloodDonorContactNumber;
    }

    public void setBloodDonorContactNumber(String bloodDonorContactNumber) {
        this.bloodDonorContactNumber = bloodDonorContactNumber;
    }

    public String getBloodDonorAddress() {
        return bloodDonorAddress;
    }

    public void setBloodDonorAddress(String bloodDonorAddress) {
        this.bloodDonorAddress = bloodDonorAddress;
    }

    public String getBloodGroupPatient() {
        return bloodGroupPatient;
    }

    public void setBloodGroupPatient(String bloodGroupPatient) {
        this.bloodGroupPatient = bloodGroupPatient;
    }

    public String getBloodDonorOrganization() {
        return bloodDonorOrganization;
    }

    public void setBloodDonorOrganization(String bloodDonorOrganization) {
        this.bloodDonorOrganization = bloodDonorOrganization;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
