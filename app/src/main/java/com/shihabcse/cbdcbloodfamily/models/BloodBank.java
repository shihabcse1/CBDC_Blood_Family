package com.shihabcse.cbdcbloodfamily.models;

import com.google.firebase.database.ServerValue;

public class BloodBank {

    private String bloodBankName;
    private String bloodBankContactNumber;
    private String bloodBankAddress;
    private Object timeStamp;

    public BloodBank(){

    }

    public BloodBank(String bloodBankName, String bloodBankContactNumber, String bloodBankAddress) {
        this.bloodBankName = bloodBankName;
        this.bloodBankContactNumber = bloodBankContactNumber;
        this.bloodBankAddress = bloodBankAddress;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getBloodBankName() {
        return bloodBankName;
    }

    public void setBloodBankName(String bloodBankName) {
        this.bloodBankName = bloodBankName;
    }

    public String getBloodBankContactNumber() {
        return bloodBankContactNumber;
    }

    public void setBloodBankContactNumber(String bloodBankContactNumber) {
        this.bloodBankContactNumber = bloodBankContactNumber;
    }

    public String getBloodBankAddress() {
        return bloodBankAddress;
    }

    public void setBloodBankAddress(String bloodBankAddress) {
        this.bloodBankAddress = bloodBankAddress;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
