package com.example.rabee.breath.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.rabee.breath.Adapters.CommentAdapter;
import com.example.rabee.breath.Adapters.ReplyAdapter;
import com.example.rabee.breath.GeneralInfo;
import com.example.rabee.breath.Models.CommentModel;
import com.example.rabee.breath.Models.ReplyModel;
import com.example.rabee.breath.Models.ResponseModels.PostCommentResponseModel;
import com.example.rabee.breath.Models.ResponseModels.ReplyResponseModel;
import com.example.rabee.breath.Models.UserModel;
import com.example.rabee.breath.R;
import com.example.rabee.breath.RequestInterface.PostInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReplyActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    TextView back_icon,toolbarText;
    PostInterface postInterface;
    public List<ReplyModel> replytModelsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        Bundle b = getIntent().getExtras();
        int commentId = 0;
        if (b != null) {
            commentId = b.getInt("commentId");
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        back_icon = (TextView) findViewById(R.id.back_icon);
        toolbarText=(TextView) findViewById(R.id.toolBarText);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        postInterface = retrofit.create(PostInterface.class);
        final Call<ReplyResponseModel> postResponse=postInterface.getCommentReplies(commentId);
        postResponse.enqueue(new Callback<ReplyResponseModel>() {
            @Override
            public void onResponse(Call<ReplyResponseModel> call, Response<ReplyResponseModel> response) {

                replytModelsList=  response.body().getReplies();
                UserModel userModel=response.body().getUser();
                toolbarText.setText(replytModelsList.size()+" replies");
                Log.d("response.body().getUser()",response.body().getUser().getId()+"");

               recyclerView.setAdapter(new ReplyAdapter(getApplicationContext(), replytModelsList,userModel));


            }


            @Override
            public void onFailure(Call<ReplyResponseModel> call, Throwable t) {

            }
        });

    }
}
