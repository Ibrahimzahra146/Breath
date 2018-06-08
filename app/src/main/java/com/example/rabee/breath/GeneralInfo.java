package com.example.rabee.breath;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.rabee.breath.Models.ResponseModels.UserProfileResponseModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class GeneralInfo {

    public static String SPRING_URL = "http://b433ffd5.ngrok.io";
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
    public static OkHttpClient getClient(Context context){

        SharedPreferences sharedPreferences =  context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        final String session  = sharedPreferences.getString("session","");
         Log.d("Session",session);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override public Response intercept(Chain chain) throws IOException {
                        return chain.proceed(chain.request()
                                .newBuilder()
                                .addHeader("Cookie", session)
                                .build());
                    }})
                .build();
        return client;
    }

    public static String getLoginType() {
        return loginType;
    }

    public static void setLoginType(String loginType) {
        GeneralInfo.loginType = loginType;
    }

    public  static String loginType;
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


