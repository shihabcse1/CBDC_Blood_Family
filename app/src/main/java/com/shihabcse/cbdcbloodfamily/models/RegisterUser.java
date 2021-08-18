package com.shihabcse.cbdcbloodfamily.models;

import com.google.firebase.database.ServerValue;

public class RegisterUser {
    private String registerUserName;
    private String registerUserEmail;
    private String registerUserPhoneNumber;
    private String registerUserBloodGroup;
    private String registerUserDistrict;
    private String registerUserCity;
    private String registerUserOrganization;
    private String registerUserDonationStatus;
    private Object timeStamp;

    public RegisterUser(String registerUserName, String registerUserEmail, String registerUserPhoneNumber, String registerUserBloodGroup, String registerUserDistrict, String registerUserCity, String registerUserOrganization, String registerUserDonationStatus) {
        this.registerUserName = registerUserName;
        this.registerUserEmail = registerUserEmail;
        this.registerUserPhoneNumber = registerUserPhoneNumber;
        this.registerUserBloodGroup = registerUserBloodGroup;
        this.registerUserDistrict = registerUserDistrict;
        this.registerUserCity = registerUserCity;
        this.registerUserOrganization = registerUserOrganization;
        this.registerUserDonationStatus = registerUserDonationStatus;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public RegisterUser(){

    }


    public String getRegisterUserName() {
        return registerUserName;
    }

    public String getRegisterUserEmail() {
        return registerUserEmail;
    }

    public String getRegisterUserPhoneNumber() {
        return registerUserPhoneNumber;
    }

    public String getRegisterUserBloodGroup() {
        return registerUserBloodGroup;
    }

    public String getRegisterUserDistrict() {
        return registerUserDistrict;
    }

    public String getRegisterUserCity() {
        return registerUserCity;
    }

    public String getRegisterUserOrganization() {
        return registerUserOrganization;
    }

    public String getRegisterUserDonationStatus() {
        return registerUserDonationStatus;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setRegisterUserName(String registerUserName) {
        this.registerUserName = registerUserName;
    }

    public void setRegisterUserEmail(String registerUserEmail) {
        this.registerUserEmail = registerUserEmail;
    }

    public void setRegisterUserPhoneNumber(String registerUserPhoneNumber) {
        this.registerUserPhoneNumber = registerUserPhoneNumber;
    }

    public void setRegisterUserBloodGroup(String registerUserBloodGroup) {
        this.registerUserBloodGroup = registerUserBloodGroup;
    }

    public void setRegisterUserDistrict(String registerUserDistrict) {
        this.registerUserDistrict = registerUserDistrict;
    }

    public void setRegisterUserCity(String registerUserCity) {
        this.registerUserCity = registerUserCity;
    }

    public void setRegisterUserOrganization(String registerUserOrganization) {
        this.registerUserOrganization = registerUserOrganization;
    }

    public void setRegisterUserDonationStatus(String registerUserDonationStatus) {
        this.registerUserDonationStatus = registerUserDonationStatus;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
