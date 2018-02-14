package com.example.rabee.breath.Activities;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.rabee.breath.GeneralInfo;
import com.example.rabee.breath.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class AddPostActivity extends Activity {
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    TextView toolbarText,username;
    Switch showComments;
    CircleImageView userProfilePicture;
    ImageView PostImage, AddImage, sendPost;//, deletePostYoutube;
    ImageButton deletePostImage ,deletePostYoutube;
    static ProgressBar progressBar;
    EditText PostText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        PostText = (EditText) findViewById(R.id.postText);
        sendPost = (ImageView) findViewById(R.id.sendPost);
        PostImage = (ImageView) findViewById(R.id.showPhoto);
        AddImage = (ImageView) findViewById(R.id.addPhoto);
        showComments = (Switch) findViewById(R.id.showComments);
        deletePostImage = (ImageButton) findViewById(R.id.btn_close);
        deletePostYoutube = (ImageButton) findViewById(R.id.closeYoutubeIcon);
        progressBar= (ProgressBar) findViewById(R.id.postProgressBar);
        toolbarText = (TextView) findViewById(R.id.toolBarText);
        username=(TextView) findViewById(R.id.username);
        deletePostYoutube.setVisibility(View.INVISIBLE);
        userProfilePicture = ( CircleImageView) findViewById(R.id.userProfilePicture);

        ///////////////////////////
        username.setText(GeneralInfo.getGeneralUserInfo().getUser().getFirst_name()+ " "+GeneralInfo.getGeneralUserInfo().getUser().getLast_name());
        String imageUrl = GeneralInfo.SPRING_URL + "/" + GeneralInfo.getGeneralUserInfo().getUser().getImage();
        Picasso.with(getApplicationContext()).load(imageUrl).into(userProfilePicture);

    }
}
