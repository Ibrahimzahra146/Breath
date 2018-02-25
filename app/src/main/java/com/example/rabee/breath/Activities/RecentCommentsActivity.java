package com.example.rabee.breath.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rabee.breath.GeneralInfo;
import com.example.rabee.breath.Models.ResponseModels.PostCommentResponseModel;
import com.example.rabee.breath.R;
import com.example.rabee.breath.RequestInterface.PostInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecentCommentsActivity extends AppCompatActivity {
    public static ArrayList<PostCommentResponseModel> postCommentResponseModel = new ArrayList<>();

    public RecyclerView recyclerView;
    ProgressBar progressBar;
    PostInterface postInterface;
    TextView headerLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_comments);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        headerLabel = (TextView) findViewById(R.id.header_label);
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        postInterface = retrofit.create(PostInterface.class);
        final Call<List<PostCommentResponseModel>> reactResponse = postInterface.getRecentComments(GeneralInfo.getUserID(), 1);
        reactResponse.enqueue(new Callback<List<PostCommentResponseModel>>() {
            @Override
            public void onResponse(Call<List<PostCommentResponseModel>> call, Response<List<PostCommentResponseModel>> response) {
                postCommentResponseModel = (ArrayList<PostCommentResponseModel>) response.body();

                //recyclerView.setAdapter(new UserListAdapter(getApplicationContext(), userModelList));
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<List<PostCommentResponseModel>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
    }
}
