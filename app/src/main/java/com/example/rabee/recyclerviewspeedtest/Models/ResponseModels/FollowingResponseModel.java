package com.example.rabee.recyclerviewspeedtest.Models.ResponseModels;

import com.example.rabee.recyclerviewspeedtest.Models.UserModel;

/**
 * Created by Rabee on 1/22/2018.
 */

public class FollowingResponseModel {
    UserModel user;
    int state;
    public UserModel getUser() {
        return user;
    }
    public void setUser(UserModel user) {
        this.user = user;
    }

    public int getState() {
        return state;
        }

    public void setState(int state) {
        this.state = state;
    }
}