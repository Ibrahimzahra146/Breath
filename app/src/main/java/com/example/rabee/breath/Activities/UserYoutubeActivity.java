package com.example.rabee.breath.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.rabee.breath.GeneralInfo;
import com.example.rabee.breath.Models.RequestModels.AboutUserRequestModel;
import com.example.rabee.breath.Models.ResponseModels.AboutUserResponseModel;
import com.example.rabee.breath.R;
import com.example.rabee.breath.RequestInterface.AboutUserInterface;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserYoutubeActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {
    private static YouTubePlayerView youTubePlayerFragment;
    int youtubeFlag = 0;
    String video_id, backUp_video_id = "",backUp_Url="", youtubeBundleSong;
    String API_KEY = "AIzaSyCHH7wadSVxjRklA5ebZHLIofaiYj_pgeE";
    EditText youtubeEdit;
    Button saveBtn;

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_user_youtube);
        youTubePlayerFragment = (YouTubePlayerView) findViewById(R.id.youtubeplayerfragment);
        youtubeEdit = (EditText) findViewById(R.id.youtubeText);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        youTubePlayerFragment = new YouTubePlayerView(this);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            youtubeBundleSong = b.getString("youtubeSongUrl");
            youtubeEdit.setText(youtubeBundleSong);
        }

        if (youtubeEdit.getText().toString() != null) {
            String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(youtubeEdit.getText());
            if (matcher.find()) {
                video_id = matcher.group();
            }
        }
        try {
            setNewVideo(video_id);
        } catch (Exception e) {

        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUserSongRequest(backUp_Url);
            }
        });

        youtubeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                String pattern = "https://m.youtube.com/watch?v=";
                String pattern1 = "https://www.youtube.com/watch?v=";
                String s = String.valueOf(youtubeEdit.getText());
                int i = s.indexOf(pattern);
                int j = s.indexOf(pattern1);
                //Log.d("youtubeFlag", "i " + i + "j " + j + "Falg " + youtubeFlag);


                if ((i >= 0) || (j >= 0)) {
                    backUp_Url = youtubeBundleSong;
                    backUp_video_id = video_id;
                    Log.d("youtubeFlag", "i " + i + "j " + j + "Falg " + youtubeFlag);
                    youTubePlayerFragment = null;


                    String[] split = s.split("v=");
                    try {
                        if (isValidVideoId((s))) {
                            video_id = split[1];
                            setNewVideo(video_id);
                            backUp_video_id = video_id;
                            backUp_Url = s;
                        }


                    } catch (Exception e) {
                        video_id = backUp_video_id;
                        setNewVideo(video_id);

                    }

                    //  youTubePlayerFragment.cut
                }


            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
    public void setNewVideo(String video_id) {


        youTubePlayerFragment = new YouTubePlayerView(this);

        youTubePlayerFragment.initialize(API_KEY, this);
        addContentView(youTubePlayerFragment, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        youTubePlayerFragment.setVisibility(View.VISIBLE);
        youtubeFlag = 1;

    }
    public boolean isValidVideoId(String youtubeUrl) {
        int flag = 0;
        String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";
        Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(youtubeUrl);
        return matcher.find();
    }
    public void editUserSongRequest(String songUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        AboutUserInterface aboutUserApi = retrofit.create(AboutUserInterface.class);
        AboutUserRequestModel aboutUserRequestModel = new AboutUserRequestModel();
        aboutUserRequestModel.setUserId(GeneralInfo.getUserID());
        aboutUserRequestModel.setUserBio("");
        aboutUserRequestModel.setUserStatus("");
        aboutUserRequestModel.setUserSong(songUrl);


        Call<AboutUserResponseModel> call = aboutUserApi.editUserSong(aboutUserRequestModel);
        call.enqueue(new Callback<AboutUserResponseModel>() {
            @Override
            public void onResponse(Call<AboutUserResponseModel> call, Response<AboutUserResponseModel> response) {
                if (response.code() == 404 || response.code() == 500 || response.code() == 502 || response.code() == 400) {

                   // GeneralFunctions generalFunctions = new GeneralFunctions();
                   // generalFunctions.showErrorMesaage(getApplicationContext());
                }
                Log.d("AboutProfileUpdate", "Done successfully " + response.code() + " " + response.body());
                finish();
            }

            @Override
            public void onFailure(Call<AboutUserResponseModel> call, Throwable t) {
//                GeneralFunctions generalFunctions = new GeneralFunctions();
//                generalFunctions.showErrorMesaage(getApplicationContext());
            }
        });
    }

}
