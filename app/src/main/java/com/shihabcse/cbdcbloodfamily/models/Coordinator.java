package com.shihabcse.cbdcbloodfamily.models;

import com.google.firebase.database.ServerValue;

public class Coordinator {
    private String coordinatorName;
    private String coordinatorContactNumber;
    private Object timeStamp;

    public Coordinator(){

    }

    public Coordinator(String coordinatorName, String coordinatorContactNumber) {
        this.coordinatorName = coordinatorName;
        this.coordinatorContactNumber = coordinatorContactNumber;
        this.timeStamp = ServerValue.TIMESTAMP;
    }

    public String getCoordinatorName() {
        return coordinatorName;
    }

    public void setCoordinatorName(String coordinatorName) {
        this.coordinatorName = coordinatorName;
    }

    public String getCoordinatorContactNumber() {
        return coordinatorContactNumber;
    }

    public void setCoordinatorContactNumber(String coordinatorContactNumber) {
        this.coordinatorContactNumber = coordinatorContactNumber;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}
