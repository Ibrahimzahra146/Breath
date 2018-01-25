package com.example.rabee.recyclerviewspeedtest.Fragments.SignUpFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.example.rabee.recyclerviewspeedtest.Activities.SignUpActivity;
import com.example.rabee.recyclerviewspeedtest.GeneralFunctions;
import com.example.rabee.recyclerviewspeedtest.R;
import com.example.rabee.recyclerviewspeedtest.Services.ValidationService;
import com.hbb20.CountryCodePicker;


public class MobileFragment extends android.app.Fragment {
    EditText userMobile;
    Button Nextbtn;
    CountryCodePicker ccp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.signup_mobile_fragment, container, false);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(userMobile, InputMethodManager.SHOW_IMPLICIT);

        userMobile = (EditText) view.findViewById(R.id.userMobile);
        Nextbtn = (Button) view.findViewById(R.id.nextBtn);
        ccp = (CountryCodePicker) view.findViewById(R.id.countryCode);

        Nextbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (userMobile.getText().toString().trim().equals("")) {
                    userMobile.setError("Mobile number is required");
                } else {
                    if (!ValidationService.isValidMobile(userMobile.getText().toString())) {
                        userMobile.setError("Mobile number is not valid");
                    } else {
                        String phoneCode = ccp.getSelectedCountryCode();
                        String phoneNumber = userMobile.getText().toString();
                        int phoneNumberInt = Integer.valueOf(phoneNumber);
                        String fullPhoneNumber = phoneCode + String.valueOf(phoneNumberInt);

                        try {
                            ((SignUpActivity) getActivity()).setMobileNumber(fullPhoneNumber);
                            android.app.Fragment f = new BirthDateFragment();
                            ((SignUpActivity) getActivity()).replaceFragmnets(f);
                        } catch (NumberFormatException e) {
                            GeneralFunctions generalFunctions = new GeneralFunctions();
                            generalFunctions.showErrorMesaage(getActivity().getApplicationContext());
                        }
                    }
                }
            }
        });
        return view;
    }
}
