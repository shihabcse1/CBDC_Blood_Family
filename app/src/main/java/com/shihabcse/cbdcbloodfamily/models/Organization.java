package com.shihabcse.cbdcbloodfamily.models;

import com.google.firebase.database.ServerValue;

public class Organization {

    private String organizationName;
    private String adminName;
    private String adminContactNumber;
    private String district;
    private Object timeStamp;

    public Organization(){

    }

    public Organization(String organizationName, String adminName, String adminContactNumber, String district) {
        this.organizationName = organizationName;
        this.adminName = adminName;
        this.adminContactNumber = adminContactNumber;
        this.district = district;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminContactNumber() {
        return adminContactNumber;
    }

    public void setAdminContactNumber(String adminContactNumber) {
        this.adminContactNumber = adminContactNumber;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
