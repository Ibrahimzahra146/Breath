package com.example.rabee.breath.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.rabee.breath.Adapters.CommentAdapter;
import com.example.rabee.breath.Adapters.UserListAdapter;
import com.example.rabee.breath.GeneralInfo;
import com.example.rabee.breath.Models.CommentModel;
import com.example.rabee.breath.Models.ResponseModels.PostCommentResponseModel;
import com.example.rabee.breath.Models.UserModel;
import com.example.rabee.breath.R;
import com.example.rabee.breath.RequestInterface.PostInterface;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentActivity extends AppCompatActivity {
    public RecyclerView recyclerView;
    TextView back_icon,toolbarText;
    PostInterface postInterface;
    public List<CommentModel> commentModelsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Bundle b = getIntent().getExtras();
        int postId = 0;
        if (b != null) {
            postId = b.getInt("postId");
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        back_icon = (TextView) findViewById(R.id.back_icon);
        toolbarText=(TextView) findViewById(R.id.toolBarText);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GeneralInfo.SPRING_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        postInterface = retrofit.create(PostInterface.class);
        final Call<PostCommentResponseModel> postResponse=postInterface.getPost(postId);
        postResponse.enqueue(new Callback<PostCommentResponseModel>() {
            @Override
            public void onResponse(Call<PostCommentResponseModel> call, Response<PostCommentResponseModel> response) {
                Log.d("response",response.code()+"res");
                Log.d("response",response.body().getComments().get(0).getText()+"res");

                commentModelsList=  response.body().getComments();
                toolbarText.setText(commentModelsList.size()+" comments");

                recyclerView.setAdapter(new CommentAdapter(getApplicationContext(), commentModelsList));


            }


            @Override
            public void onFailure(Call<PostCommentResponseModel> call, Throwable t) {

            }
        });




    }
}