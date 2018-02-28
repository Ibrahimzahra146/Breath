package com.example.rabee.breath.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
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

import static com.example.rabee.breath.Activities.UserProfileActivity.verifyStoragePermissions;


public class AddPostActivity extends Activity {
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int SELECTED_PICTURE = 100;

    TextView toolbarText,username;
    Switch showComments;
    CircleImageView userProfilePicture;
    ImageView PostImage, AddImage, sendPost;//, deletePostYoutube;
    ImageButton deletePostImage ,deletePostYoutube;
    static ProgressBar progressBar;
    EditText PostText;
    static boolean isThereIsImage = false;






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
        //////////////Listeners///////////////////
        sendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isThereIsImage == true || (!PostText.getText().toString().trim().equals("") /*|| !youtubeLinkString.equals("")*/)){
                    progressBar.setVisibility(View.VISIBLE);}
               // AddNewPost();

            }
        });
        AddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyStoragePermissions(AddPostActivity.this);

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECTED_PICTURE);
            }
        });
        deletePostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostImage.setImageBitmap(null);
                deletePostImage.setVisibility(View.INVISIBLE);
                isThereIsImage = false;
            }
        });
        toolbarText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;

                if (event.getX() <= (toolbarText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width() + 30)) {
                    //finish();
                    onBackPressed();
                    return true;
                }
                return false;
            }
        });

    }
}
