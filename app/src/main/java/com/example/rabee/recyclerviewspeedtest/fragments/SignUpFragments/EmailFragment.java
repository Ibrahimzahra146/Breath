package com.example.rabee.recyclerviewspeedtest.Fragments.SignUpFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.example.rabee.recyclerviewspeedtest.Activities.SignUpActivity;
import com.example.rabee.recyclerviewspeedtest.GeneralFunctions;
import com.example.rabee.recyclerviewspeedtest.GeneralInfo;
import com.example.rabee.recyclerviewspeedtest.Models.ResponseModels.UserProfileResponseModel;
import com.example.rabee.recyclerviewspeedtest.Models.SignInRequestModel;
import com.example.rabee.recyclerviewspeedtest.R;
import com.example.rabee.recyclerviewspeedtest.RequestInterface.AuthInterface;
import com.example.rabee.recyclerviewspeedtest.Services.ValidationService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EmailFragment extends android.app.Fragment {

    Button nextbtn;
    EditText email;
    AuthInterface service;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(email, InputMethodManager.SHOW_IMPLICIT);
        View view = inflater.inflate(R.layout.signup_email_fragment, container, false);
        email = (EditText) view.findViewById(R.id.userEmail);
        email.setFocusable(true);
        nextbtn = (Button) view.findViewById(R.id.nextBtn);
        final ProgressBar checkEmailProgress = (ProgressBar) view.findViewById(R.id.checkEmailProgress);

        nextbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString();
                if ((userEmail.equals(""))) {
                    email.setError("Email is required");
                } else if (!(ValidationService.isEmailValid(email.getText().toString()))) {
                    email.setError("Email is not valid");
                } else {
                    checkEmailProgress.setVisibility(View.VISIBLE);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(GeneralInfo.SPRING_URL)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    service = retrofit.create(AuthInterface.class);
                    final SignInRequestModel signInModel = new SignInRequestModel();
                    signInModel.setEmail(email.getText().toString());
                    signInModel.setPassword("@(-_-)@");
                    final Call<UserProfileResponseModel> userModelCall = service.signIn(signInModel);
                    userModelCall.enqueue(new Callback<UserProfileResponseModel>() {
                        @Override
                        public void onResponse(Call<UserProfileResponseModel> call, Response<UserProfileResponseModel> response) {
                            checkEmailProgress.setVisibility(View.INVISIBLE);
                            if (response.code() == 204) {
                                ((SignUpActivity) getActivity()).setUserEmail(email.getText().toString());
                                android.app.Fragment f = new MobileFragment();
                                ((SignUpActivity) getActivity()).replaceFragmnets(f);
                            } else if (response.code() == 409 || response.code() == 200) {
                                email.setError("Email is already used!");
                            } else if (response.code() == 404 || response.code() == 500 || response.code() == 502 || response.code() == 400) {
                                GeneralFunctions generalFunctions = new GeneralFunctions();
                                generalFunctions.showErrorMesaage(getActivity().getApplicationContext());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserProfileResponseModel> call, Throwable t) {
                            GeneralFunctions generalFunctions = new GeneralFunctions();
                            generalFunctions.showErrorMesaage(getActivity().getApplicationContext());
                            checkEmailProgress.setVisibility(View.INVISIBLE);
                        }
                    });
                }


            }
        });
        return view;
    }

}
