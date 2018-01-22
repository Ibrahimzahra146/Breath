package com.example.rabee.recyclerviewspeedtest.RequestInterface;

import com.example.rabee.recyclerviewspeedtest.Models.RequestModels.LoginWithFacebookRequestsModel;
import com.example.rabee.recyclerviewspeedtest.Models.RequestModels.LoginWithGoogleRequestModel;
import com.example.rabee.recyclerviewspeedtest.Models.RequestModels.SignOutRequestModel;
import com.example.rabee.recyclerviewspeedtest.Models.RequestModels.SignUpRequestModel;
import com.example.rabee.recyclerviewspeedtest.Models.ResponseModels.UserProfileResponseModel;
import com.example.rabee.recyclerviewspeedtest.Models.SignInRequestModel;
import com.example.rabee.recyclerviewspeedtest.Models.UserModel;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Rabee on 6/26/2017.
 */

public interface AuthInterface {
    @Headers("Cache-Control: max-age=64000")
    @POST("/api/v1/user/signIn")
    Call<UserProfileResponseModel> signIn(@Body SignInRequestModel signInModel);

    @Headers("Cache-Control: max-age=64000")
    @POST("/api/v1/user/update-password")
    Call<UserModel> updatePassword(@Body SignInRequestModel signInModel);

    @Headers("Cache-Control: max-age=64000")
    @POST("/api/v1/user/signUp")
    Call<UserProfileResponseModel> signUp(@Body SignUpRequestModel signUpModel);

    @POST("/api/v1/user/login-with-google")
    Call<UserProfileResponseModel> loginWithGoogle(@Body LoginWithGoogleRequestModel loginWIthGoogleModel);

    @POST("/api/v1/user/login-with-facebook")
    Call<UserProfileResponseModel> loginWithFacebook(@Body LoginWithFacebookRequestsModel loginWithFacebookModel);

    @POST("/api/v1/user/signOut")
    Call<Integer> signOut(@Body SignOutRequestModel signOutModel);


}
