package com.example.rabee.recyclerviewspeedtest.Models.ResponseModels;

import com.example.rabee.recyclerviewspeedtest.Models.DeviceTokenModel;
import com.example.rabee.recyclerviewspeedtest.Models.UserModel;

/**
 * Created by Rabee on 1/22/2018.
 */
 public class UserIdDeviceIdResponseModel {
    UserModel userModel;
    DeviceTokenModel deviceTokenModel;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public DeviceTokenModel getDeviceTokenModel() {
        return deviceTokenModel;
    }

    public void setDeviceTokenModel(DeviceTokenModel deviceTokenModel) {
        this.deviceTokenModel = deviceTokenModel;
    }
}
