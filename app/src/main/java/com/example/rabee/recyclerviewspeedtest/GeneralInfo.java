package com.example.rabee.recyclerviewspeedtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.rabee.recyclerviewspeedtest.Models.ResponseModels.UserProfileResponseModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Rabee on 1/20/2018.
 */

public class GeneralInfo {
    public static String SPRING_URL = "http://17caa777.ngrok.io";
    public static String USER_NAME="Ibrahim zahra";
    public static String PROFILE_PIC="_437.022828770496916832227_1341475782539973_288842465026282085_n.jpg";
    public static int notifications_counter = 0;
    public static int home_tab_position = 0;
    public static int notifications_tab_position = 1;
    public static int setting_tab_position = 2;
    public static int friendMode = -1;
    public static int userID;
    public static UserProfileResponseModel generalUserInfo;
    public static Bitmap userProfilePicture;

    public static Bitmap getUserProfilePicture() {
        return userProfilePicture;
    }

    public static void setUserProfilePicture(Bitmap userProfilePicture) {
        GeneralInfo.userProfilePicture = userProfilePicture;
    }

    public static UserProfileResponseModel getGeneralUserInfo() {
        return generalUserInfo;
    }

    public static void setGeneralUserInfo(UserProfileResponseModel generalUserInfo) {
        GeneralInfo.generalUserInfo = generalUserInfo;
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        GeneralInfo.userID = userID;
    }

    public static Bitmap StringToBitMap(String encodedString) {

        URL url = null;
        try {
            url = new URL(encodedString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

}

