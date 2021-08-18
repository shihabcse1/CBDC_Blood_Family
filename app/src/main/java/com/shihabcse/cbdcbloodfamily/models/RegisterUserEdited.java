package com.shihabcse.cbdcbloodfamily.models;

import com.google.firebase.database.ServerValue;

public class RegisterUserEdited {

    private String RegisterUserEditedId;
    private String RegisterUserEditedName;
    private String RegisterUserEditedEmail;
    private String RegisterUserEditedPhoneNumber;
    private String RegisterUserEditedBloodGroup;
    private String RegisterUserEditedCountry;
    private String RegisterUserEditedDistrict;
    private String RegisterUserEditedCity;
    private String registerUserOrganization;
    private String registerUserDonationStatus;
    private Object timeStamp;

    public RegisterUserEdited(){

    }

    public RegisterUserEdited(String registerUserEditedId, String registerUserEditedName, String registerUserEditedEmail, String registerUserEditedPhoneNumber, String registerUserEditedBloodGroup, String registerUserEditedCountry, String registerUserEditedDistrict, String registerUserEditedCity, String registerUserOrganization, String registerUserDonationStatus) {
        RegisterUserEditedId = registerUserEditedId;
        RegisterUserEditedName = registerUserEditedName;
        RegisterUserEditedEmail = registerUserEditedEmail;
        RegisterUserEditedPhoneNumber = registerUserEditedPhoneNumber;
        RegisterUserEditedBloodGroup = registerUserEditedBloodGroup;
        RegisterUserEditedCountry = registerUserEditedCountry;
        RegisterUserEditedDistrict = registerUserEditedDistrict;
        RegisterUserEditedCity = registerUserEditedCity;
        this.registerUserOrganization = registerUserOrganization;
        this.registerUserDonationStatus = registerUserDonationStatus;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getRegisterUserEditedCountry() {
        return RegisterUserEditedCountry;
    }

    public void setRegisterUserEditedCountry(String registerUserEditedCountry) {
        RegisterUserEditedCountry = registerUserEditedCountry;
    }

    public String getRegisterUserEditedId() {
        return RegisterUserEditedId;
    }

    public void setRegisterUserEditedId(String registerUserEditedId) {
        RegisterUserEditedId = registerUserEditedId;
    }

    public String getRegisterUserEditedName() {
        return RegisterUserEditedName;
    }

    public void setRegisterUserEditedName(String registerUserEditedName) {
        RegisterUserEditedName = registerUserEditedName;
    }

    public String getRegisterUserEditedEmail() {
        return RegisterUserEditedEmail;
    }

    public void setRegisterUserEditedEmail(String registerUserEditedEmail) {
        RegisterUserEditedEmail = registerUserEditedEmail;
    }

    public String getRegisterUserEditedPhoneNumber() {
        return RegisterUserEditedPhoneNumber;
    }

    public void setRegisterUserEditedPhoneNumber(String registerUserEditedPhoneNumber) {
        RegisterUserEditedPhoneNumber = registerUserEditedPhoneNumber;
    }

    public String getRegisterUserEditedBloodGroup() {
        return RegisterUserEditedBloodGroup;
    }

    public void setRegisterUserEditedBloodGroup(String registerUserEditedBloodGroup) {
        RegisterUserEditedBloodGroup = registerUserEditedBloodGroup;
    }

    public String getRegisterUserEditedDistrict() {
        return RegisterUserEditedDistrict;
    }

    public void setRegisterUserEditedDistrict(String registerUserEditedDistrict) {
        RegisterUserEditedDistrict = registerUserEditedDistrict;
    }

    public String getRegisterUserOrganization() {
        return registerUserOrganization;
    }

    public void setRegisterUserOrganization(String registerUserOrganization) {
        this.registerUserOrganization = registerUserOrganization;
    }

    public String getRegisterUserDonationStatus() {
        return registerUserDonationStatus;
    }

    public void setRegisterUserDonationStatus(String registerUserDonationStatus) {
        this.registerUserDonationStatus = registerUserDonationStatus;
    }

    public String getRegisterUserEditedCity() {
        return RegisterUserEditedCity;
    }

    public void setRegisterUserEditedCity(String registerUserEditedCity) {
        RegisterUserEditedCity = registerUserEditedCity;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
