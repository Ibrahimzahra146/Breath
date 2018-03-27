package com.example.rabee.breath.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rabee.breath.Adapters.HomePostAdapter;
import com.example.rabee.breath.GeneralInfo;
import com.example.rabee.breath.Models.ResponseModels.PostCommentResponseModel;
import com.example.rabee.breath.R;
import com.example.rabee.breath.RequestInterface.PostInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OneTimeLogInActivity extends Activity{

    RecyclerView recyclerView ;
    public static List<PostCommentResponseModel> postResponseModelsList;
    LinearLayout searchLayout;

    // Add the search bar ---------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_time_login_activity);

        searchLayout = (LinearLayout) findViewById(R.id.SearchLayout);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(i);
            }
        });

        PostInterface postInterface;
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.hasFixedSize();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        postInterface = retrofit.create(PostInterface.class);


        //Change the API
        final Call<List<PostCommentResponseModel>> postResponse = postInterface.getRandomPosts(GeneralInfo.getUserID(),0 );
        postResponse.enqueue(new Callback<List<PostCommentResponseModel>>() {

            @Override
            public void onResponse(Call<List<PostCommentResponseModel>> call, Response<List<PostCommentResponseModel>> response) {
                postResponseModelsList = response.body();
                recyclerView.setAdapter(new HomePostAdapter(getApplicationContext(),postResponseModelsList));
            }

            @Override
            public void onFailure(Call<List<PostCommentResponseModel>> call, Throwable t) {

            }
        });

    }

}
