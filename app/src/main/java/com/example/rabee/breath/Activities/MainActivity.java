package com.example.rabee.breath.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rabee.breath.GeneralInfo;
import com.example.rabee.breath.Models.RequestModels.LoginWithFacebookRequestsModel;
import com.example.rabee.breath.Models.RequestModels.LoginWithGoogleRequestModel;
import com.example.rabee.breath.Models.ResponseModels.UserProfileResponseModel;
import com.example.rabee.breath.Models.SignInRequestModel;
import com.example.rabee.breath.R;
import com.example.rabee.breath.ReactsRecyclerViewModel;
import com.example.rabee.breath.RequestInterface.AuthInterface;
import com.example.rabee.breath.Services.FieldValidationService;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    static Dialog LoggingInDialog;
    LoginButton loginButton;
    AuthInterface service;
    CallbackManager callbackManager;
    SignInButton signInButton;
    CircleImageView fb, google;
    GoogleApiClient googleApiClient;
    Dialog progressDialog;
    TextView AppTitle;
    private EditText emailEditText;
    private EditText passEditText;
    SharedPreferences sharedPreferences;

    private List<ReactsRecyclerViewModel> reactsList = new ArrayList<>();
    RecyclerView recyclerView;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(GeneralInfo.SPRING_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);


        signInButton = (SignInButton) findViewById(R.id.loginWithGoogleBtn);
        // signInButton.setOnClickListener(this);
        //signInButton.setSize(SignInButton.SIZE_STANDARD);
        //setGooglePlusButtonText(signInButton, "Log in with google ");
        emailEditText = (EditText) findViewById(R.id.username);
        passEditText = (EditText) findViewById(R.id.password);
        fb = (CircleImageView) findViewById(R.id.fb);
        google = (CircleImageView) findViewById(R.id.google);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        LoggingInDialog = new Dialog(this);
        LoggingInDialog.setContentView(R.layout.logging_in_dialog);
        progressDialog = new Dialog(MainActivity.this);
        progressDialog.setContentView(R.layout.facebook_progress_dialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        service = retrofit.create(AuthInterface.class);
        //check the user if he already logined
        int id = sharedPreferences.getInt("id", -1);
        boolean isLogined = sharedPreferences.getBoolean("isLogined", false);
        GeneralInfo.setUserID(id);

        if ((isLogined == true)) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString("generalUserInfo", "");
            GeneralInfo.setGeneralUserInfo(gson.fromJson(json, UserProfileResponseModel.class));
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        }
        //Login with facebook
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(final LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        LoginWithFacebookRequestsModel loginWithFacebookModel = new LoginWithFacebookRequestsModel();
                        try {
                            loginWithFacebookModel.setFirstName(object.getString("first_name"));
                            loginWithFacebookModel.setLastName(object.getString("last_name"));
                            loginWithFacebookModel.setId(loginResult.getAccessToken().getUserId());
                            loginWithFacebookModel.setAccessToken(loginResult.getAccessToken().getToken());
                            loginWithFacebookModel.setImage("");
                            loginWithFacebookModel.setGender(object.getString("gender"));
                            loginWithFacebookModel.setEmail(object.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.d("Facebook login", "");


            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Facebook login", "");


            }
        });
        //Sign in with google
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .requestIdToken("283243462011-63sts0savm58dj146d2d8vur45i00bif.apps.googleusercontent.co").
                        requestServerAuthCode("283243462011-63sts0savm58dj146d2d8vur45i00bif.apps.googleusercontent.co").requestScopes(new Scope(Scopes.PLUS_LOGIN)).build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this/* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).addApi(Plus.API)
                .build();
    }
    public void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, 9001);
    }

    public void onClick(View v) {
        Log.d("Facebook login clicked", "");

        switch (v.getId()) {
            case R.id.loginWithGoogleBtn:
                signIn();
                break;
        }
        if (v == fb) {
            progressDialog.show();
            loginButton.performClick();
        }
        if (v == google) {
            signInButton.performClick();
            signIn();
        }


    }

    public void checkLogin(View arg0) {
        final SignInRequestModel signInRequestModel = new SignInRequestModel();
        signInRequestModel.setEmail(emailEditText.getText().toString());
        signInRequestModel.setPassword(passEditText.getText().toString());
        final GeneralInfo generalFunctions = new GeneralInfo();
        //final boolean isOnline = generalFunctions.isOnline(getApplicationContext());
        if (true == false) {
            Toast.makeText(this, "no internet connection!",
                    Toast.LENGTH_LONG).show();
        } else {
            final SignInRequestModel signInModel = new SignInRequestModel();
            signInModel.setEmail(emailEditText.getText().toString());
            signInModel.setPassword(passEditText.getText().toString());
            if (FieldValidationService.valid(signInModel.getEmail(), signInModel.getPassword(), emailEditText, passEditText)) {
                LoggingInDialog.show();

                final Call<UserProfileResponseModel> userModelCall = service.signIn(signInRequestModel);
                userModelCall.enqueue(new Callback<UserProfileResponseModel>() {

                    @Override
                    public void onResponse(Call<UserProfileResponseModel> call, Response<UserProfileResponseModel> response) {
                        if (response.code() == 200) {
                            UserProfileResponseModel userProfileResponseModel = response.body();
                            GeneralInfo.setUserID(Integer.valueOf(userProfileResponseModel.getUser().getId()));
                            GeneralInfo.setGeneralUserInfo(userProfileResponseModel);
                            saveLoginedUserInfo(userProfileResponseModel);


                        }
                    }

                    @Override
                    public void onFailure(Call<UserProfileResponseModel> call, Throwable t) {

                    }
                });
            }
        }

    }

    public void loginWithFacebookRequest(LoginWithFacebookRequestsModel loginWithFacebookRequestsModel) {
        final Call<UserProfileResponseModel> userModelCall = service.loginWithFacebook(loginWithFacebookRequestsModel);
        userModelCall.enqueue(new Callback<UserProfileResponseModel>() {

            @Override
            public void onResponse(Call<UserProfileResponseModel> call, Response<UserProfileResponseModel> response) {
                UserProfileResponseModel userProfileResponseModel = response.body();
                if (response.code() == 200 || response.code() == 202) {
                    GeneralInfo.setUserID(Integer.valueOf(userProfileResponseModel.getUser().getId()));
                    GeneralInfo.setGeneralUserInfo(userProfileResponseModel);
                    saveLoginedUserInfo(userProfileResponseModel);

                }
            }

            @Override
            public void onFailure(Call<UserProfileResponseModel> call, Throwable t) {

            }
        });
    }

    public void saveLoginedUserInfo(UserProfileResponseModel userProfileResponseModel) {
        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userProfileResponseModel);

        editor.putString("generalUserInfo", json);
        editor.putString("email", emailEditText.getText().toString());
        editor.putString("password", passEditText.getText().toString());
        editor.putString("profileImage", userProfileResponseModel.getUser().getImage());
        editor.putString("coverImage", userProfileResponseModel.getUser().getCover_image());

        editor.putInt("id", GeneralInfo.getUserID());
        editor.putBoolean("isLogined", true);
        editor.apply();
        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
        LoggingInDialog.dismiss();
        startActivity(i);
        finish();

    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data) {

            Log.i("899999999999999999999","");
            // data.getStringExtra("")

            LoggingInDialog.show();
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleResult(result);
            if (googleApiClient.hasConnectedApi(Plus.API)) {
                LoggingInDialog.show();

                com.google.android.gms.plus.model.people.Person person = Plus.PeopleApi.getCurrentPerson(googleApiClient);

                if (requestCode == 9001) {
                Log.i("", "Gender: " + person.getGender());
            }

        } else {
            progressDialog.dismiss();
            callbackManager.onActivityResult(requestCode, responseCode, data);
        }

    }

    public void handleGoogleResult(GoogleSignInResult googleSignInResult) {
        Log.d("Handle google result","");

        if (googleSignInResult.isSuccess()) {
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();
            String email = account.getEmail();
            String userId = account.getId();
            LoginWithGoogleRequestModel loginWIthGoogleModel = new LoginWithGoogleRequestModel();
            loginWIthGoogleModel.setEmail(email);
            loginWIthGoogleModel.setAccessToken(account.getIdToken());
            loginWIthGoogleModel.setFirstName(account.getGivenName());
            loginWIthGoogleModel.setLastName(account.getFamilyName());
            loginWIthGoogleModel.setGender("male");
            loginWIthGoogleModel.setId(userId);
            loginWIthGoogleModel.setImage(account.getPhotoUrl().toString());
            loginWithGoogleRequest(loginWIthGoogleModel);
            Log.d("account.photo", account.getPhotoUrl().toString());
            Log.d("Google email", email);
            Log.d("account.getIdToken();", account.getIdToken());
            Log.d("account.getIdToken();", account.getServerAuthCode());


        }

    }

    public void loginWithGoogleRequest(LoginWithGoogleRequestModel loginWithGoogleRequestModel) {
        final Call<UserProfileResponseModel> userModelCall = service.loginWithGoogle(loginWithGoogleRequestModel);
        userModelCall.enqueue(new Callback<UserProfileResponseModel>() {

            @Override
            public void onResponse(Call<UserProfileResponseModel> call, Response<UserProfileResponseModel> response) {

            }

            @Override
            public void onFailure(Call<UserProfileResponseModel> call, Throwable t) {

            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
