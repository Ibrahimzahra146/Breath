package com.example.rabee.recyclerviewspeedtest.Models.RequestModels;

/**
 * Created by Rabee on 1/22/2018.
 */

public class SignOutRequestModel {

    int userId;
    String deviceId;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}