package com.example.rabee.recyclerviewspeedtest.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rabee.recyclerviewspeedtest.Constants;
import com.example.rabee.recyclerviewspeedtest.R;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity{
        private static String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    private static final int SELECTED_PICTURE = 100;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    ImageView img,coverImage;
    TextView userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        img = (ImageView) findViewById(R.id.user_profile_photo);
        coverImage = (ImageView) findViewById(R.id.coverImage);
        userName = (TextView) findViewById(R.id.user_profile_name);

        userName.setText(Constants.USER_NAME);
        String imageUrl= Constants.SPRING_URL+"/"+Constants.PROFILE_PIC;
        Picasso.with(getApplicationContext()).load(imageUrl).into(img);
        Picasso.with(getApplicationContext()).load(imageUrl).into(coverImage);

    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
