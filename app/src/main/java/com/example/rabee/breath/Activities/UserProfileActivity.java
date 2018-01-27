package com.example.rabee.breath.Activities;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rabee.breath.GeneralInfo;
import com.example.rabee.breath.Models.RequestModels.AboutUserRequestModel;
import com.example.rabee.breath.Models.ResponseModels.AboutUserResponseModel;
import com.example.rabee.breath.R;
import com.example.rabee.breath.RequestInterface.AboutUserInterface;
import com.example.rabee.breath.RequestInterface.ImageInterface;
import com.example.rabee.breath.Services.ImageService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.rabee.breath.Services.ImageService.RotateBitmap;
import static com.example.rabee.breath.Services.ImageService.scaleDown;

public class UserProfileActivity extends AppCompatActivity{
        private static String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    private static final int SELECTED_PICTURE = 100;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static ObjectAnimator anim;
    SharedPreferences sharedPreferences;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(GeneralInfo.SPRING_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    ImageView img,coverImage,imageView;
    TextView followingTxt, newPostTxt , followerCount , followingCount,userName,profileBio;
    TextView changePic, viewPic, RemovePic, toolBarText,editBio;
    CircleImageView editProfile, editSong;
    Button saveAbout, saveSong;
    EditText bioTxt, statusTxt, songTxt;
    Dialog imgClick,ViewImgDialog,editMyBio, editMySong;
    ProgressBar coverProgressBar, progressBar;
    Uri imageuri;




    String songUrl;
    int youtubeFlag = 0;
    //    TextView followerTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        img = (ImageView) findViewById(R.id.user_profile_photo);
        coverImage = (ImageView) findViewById(R.id.coverImage);
        userName = (TextView) findViewById(R.id.user_profile_name);
        followingTxt = (TextView) findViewById(R.id.followingTxt);
        newPostTxt = (TextView) findViewById(R.id.newPostTxt);
        editProfile = (CircleImageView) findViewById(R.id.editProfile);
        editSong = (CircleImageView) findViewById(R.id.editSong);
        coverImage = (ImageView) findViewById(R.id.coverImage);
        profileBio = (TextView) findViewById(R.id.profileBio);
        coverProgressBar = (ProgressBar) findViewById(R.id.coverProgressBar);
        img = (ImageView) findViewById(R.id.user_profile_photo);
       // songTxt = (EditText) editMySong.findViewById(R.id.songTxt);
       // saveSong = (Button) editMySong.findViewById(R.id.saveSong);
        editBio = (TextView) findViewById(R.id.editBio);
        toolBarText = (TextView) findViewById(R.id.toolBarText);
        progressBar = (ProgressBar) findViewById(R.id.profilePictureProgressBar);
        followingCount= (TextView) findViewById(R.id.followingCount);
        followerCount= (TextView) findViewById(R.id.followerCount);


        ////////////////////////////////////////
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        userName.setText(GeneralInfo.getGeneralUserInfo().getUser().getFirst_name() + " " + GeneralInfo.getGeneralUserInfo().getUser().getLast_name());
        String imageUrl = GeneralInfo.SPRING_URL + "/" + GeneralInfo.getGeneralUserInfo().getUser().getImage();
        String coverUrl = GeneralInfo.SPRING_URL + "/" + GeneralInfo.getGeneralUserInfo().getUser().getCover_image();
        Picasso.with(getApplicationContext()).load(imageUrl).into(img);
        Picasso.with(getApplicationContext()).load(coverUrl).into(coverImage);
        profileBio.setText(GeneralInfo.getGeneralUserInfo().getAboutUser().getUserBio());
        followingCount.setText(String.valueOf(GeneralInfo.getGeneralUserInfo().getNumberOfFollowing()));
        followerCount.setText(String.valueOf(GeneralInfo.getGeneralUserInfo().getNumberOfFollower()));
        //animation
        progressBar.setProgress(0);
        progressBar.setMax(100);
        anim = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        anim.setDuration(2000);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();
        ///////////////
        imgClick = new Dialog(this);
        ViewImgDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        editMyBio = new Dialog(this);
        editMySong = new Dialog(this);


        imgClick.setContentView(R.layout.profile_picture_dialog);
        ViewImgDialog.setContentView(R.layout.view_profilepic_dialog);
        editMyBio.setContentView(R.layout.edit_my_bio_dialog);
        editMyBio.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editMySong.setContentView(R.layout.edit_song_profile_dialog);
        editMySong.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       /////
        imageView = (ImageView) ViewImgDialog.findViewById(R.id.ImageView);
        saveAbout = (Button) editMyBio.findViewById(R.id.saveAbout);
        bioTxt = (EditText) editMyBio.findViewById(R.id.bioTxt);
        statusTxt = (EditText) editMyBio.findViewById(R.id.statusTxt);
        songTxt = (EditText) editMySong.findViewById(R.id.songTxt);
        saveSong = (Button) editMySong.findViewById(R.id.saveSong);
        editBio = (TextView) findViewById(R.id.editBio);
        toolBarText = (TextView) findViewById(R.id.toolBarText);
        fillAbout();
        //Listens
        //Listener for profile picture and it's dialog
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imgClick.getWindow().getAttributes().y = -90;
                imgClick.getWindow().getAttributes().x = 130;
                imgClick.show();
                changePic = (TextView) imgClick.findViewById(R.id.EditPic);
                changePic.setText("Change Profile Picture");
                viewPic = (TextView) imgClick.findViewById(R.id.ViewPic);
                viewPic.setText("View Profile Picture");
                RemovePic = (TextView) imgClick.findViewById(R.id.RemovePic);
                RemovePic.setText("Remove Profile Picture");
                changePic.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        verifyStoragePermissions(UserProfileActivity.this);

                        imgClick.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 100);
                    }
                });

                viewPic.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        imgClick.dismiss();
                        imageView.setImageDrawable(img.getDrawable());
                        ImageView coverImageDialog = (ImageView) ViewImgDialog.findViewById(R.id.ImageView);
                        coverImageDialog.setImageDrawable(img.getDrawable());
                        ViewImgDialog.show();

                    }
                });

                RemovePic.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        imgClick.dismiss();
                        removeImage(0);/////////


                    }
                });
            }
        });
        //Listener for cover image
        coverImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                imgClick.getWindow().getAttributes().y = -280;
                imgClick.getWindow().getAttributes().x = 30;
                imgClick.show();
                changePic = (TextView) imgClick.findViewById(R.id.EditPic);
                changePic.setText("Change Cover Picture");
                viewPic = (TextView) imgClick.findViewById(R.id.ViewPic);
                viewPic.setText("View Cover Picture");
                RemovePic = (TextView) imgClick.findViewById(R.id.RemovePic);
                RemovePic.setText("Remove Cover Picture");

                changePic.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        verifyStoragePermissions(UserProfileActivity.this);

                        imgClick.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 200);
                    }
                });

                viewPic.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        imgClick.dismiss();
                        coverImage.setImageDrawable(coverImage.getDrawable());
                        ImageView coverImageDialog = (ImageView) ViewImgDialog.findViewById(R.id.ImageView);
                        coverImageDialog.setImageDrawable(coverImage.getDrawable());
                        ViewImgDialog.show();
                    }
                });

                RemovePic.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        imgClick.dismiss();
                        removeImage(1);

                    }
                });
            }
        });
        toolBarText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;

                if (event.getX() <= (toolBarText.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                    finish();
                    return true;
                }

                if (event.getX() >= 900) {
                    return true;
                }


                return false;
            }
        });
        followingTxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), FollowingActivity.class);
                startActivity(i);
            }
        });
        editBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editMyBio.show();

            }
        });
        editSong.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UserYoutubeActivity.class);
                Bundle b = new Bundle();
                if (songUrl != null) {
                    b.putString("youtubeSongUrl", songUrl);
                }
                i.putExtras(b);
                startActivity(i);

            }
        });
        saveAbout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String bioText, statusText, songText;
                bioText = bioTxt.getText().toString();
                songText = songTxt.getText().toString();
                statusText = statusTxt.getText().toString();
                updateAbout(bioText, statusText, songText);
                editMyBio.dismiss();

            }
        });











    }
    public void getUserInfo() {
        userName.setText(GeneralInfo.getGeneralUserInfo().getUser().getFirst_name() + " " + GeneralInfo.getGeneralUserInfo().getUser().getLast_name());
        String imageUrl = GeneralInfo.SPRING_URL + "/" + GeneralInfo.getGeneralUserInfo().getUser().getImage();
        progressBar.setVisibility(View.INVISIBLE);
        Picasso.with(getApplicationContext()).load(imageUrl).into(img);
        String coverUrl = GeneralInfo.SPRING_URL + "/" + GeneralInfo.getGeneralUserInfo().getUser().getCover_image();
        Picasso.with(getApplicationContext()).load(coverUrl).into(coverImage);

    }
    public void fillAbout() {

        profileBio.setText(GeneralInfo.getGeneralUserInfo().getAboutUser().getUserBio());
        bioTxt.setText(GeneralInfo.getGeneralUserInfo().getAboutUser().getUserBio());
        statusTxt.setText(GeneralInfo.getGeneralUserInfo().getAboutUser().getUserStatus());
        songTxt.setText(GeneralInfo.getGeneralUserInfo().getAboutUser().getUserSong());
        songUrl = GeneralInfo.getGeneralUserInfo().getAboutUser().getUserSong();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == 100 || requestCode == 200)) {
            imageuri = data.getData();
            try {
                ImageService imageService = new ImageService();
                String path = imageService.getRealPathFromURI(this, imageuri);
                Log.d("Path", path);
                int rotate = imageService.getPhotoOrientation(path);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);
                bitmap = scaleDown(bitmap, 1000, true);
                bitmap = RotateBitmap(bitmap, rotate);
                if (requestCode == 100) {
                    img.setImageBitmap(bitmap);
                }
                if (requestCode == 200) {
                    Log.d("requestCode","Uploading cover image...");

                    coverImage.setImageBitmap(bitmap);
                }
                byte[] image = imageService.getBytes(bitmap);
                String encodedImage = Base64.encodeToString(image, Base64.DEFAULT);
                imageService.uploadImagetoDB(GeneralInfo.getUserID(),  path, bitmap, requestCode, coverProgressBar);

            } catch (Exception e) {
                Log.d("XX", "Image cannot be uploaded");

            }
        }
    }
    //Update user info
    public void updateAbout(final String bioText, final String statusText, final String songText) {
        AboutUserRequestModel aboutUserModel = new AboutUserRequestModel(GeneralInfo.getUserID(), bioText, statusText, songText);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AboutUserInterface aboutUserApi = retrofit.create(AboutUserInterface.class);

        Call<AboutUserResponseModel> call = aboutUserApi.addNewAboutUser(aboutUserModel);
        call.enqueue(new Callback<AboutUserResponseModel>() {
            @Override
            public void onResponse(Call<AboutUserResponseModel> call, Response<AboutUserResponseModel> response) {
                if (response.code() == 404 || response.code() == 500 || response.code() == 502 || response.code() == 400) {
                    GeneralInfo generalFunctions = new GeneralInfo();
                   // generalFunctions.showErrorMesaage(getApplicationContext());
                } else {
                    GeneralInfo.generalUserInfo.setAboutUser(response.body());
                    sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(GeneralInfo.generalUserInfo);
                    editor.putString("generalUserInfo", json);
                    editor.apply();
                    fillAbout();
                }
            }

            @Override
            public void onFailure(Call<AboutUserResponseModel> call, Throwable t) {
               // GeneralInfo generalFunctions = new GeneralFunctions();
               // generalFunctions.showErrorMesaage(getApplicationContext());
                Log.d("AboutUserUpdate", "Failure " + t.getMessage());
            }
        });

    }

    public void removeImage(final int profileOrCover) {


        ImageInterface imageInterface = retrofit.create(ImageInterface.class);
        Call<Integer> removeImageResponse = imageInterface.removeImage(GeneralInfo.userID, profileOrCover);
        removeImageResponse.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("ImagesCode ", " " + response.code());
                if(profileOrCover == 0)
                {
                    GeneralInfo.generalUserInfo.getUser().setImage("");
                }
                else
                {
                    GeneralInfo.generalUserInfo.getUser().setCover_image("");
                }
                sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(GeneralInfo.generalUserInfo);
                editor.putString("generalUserInfo", json);
                editor.apply();
                getUserInfo();

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("ImagesCode ", " Error " + t.getMessage());
            }
        });

    }
}