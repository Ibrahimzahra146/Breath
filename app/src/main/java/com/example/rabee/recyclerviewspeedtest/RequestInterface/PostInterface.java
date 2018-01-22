package com.example.rabee.recyclerviewspeedtest.RequestInterface;

import com.example.rabee.recyclerviewspeedtest.Models.PostCommentModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Rabee on 1/20/2018.
 */

public interface PostInterface {
    @Headers("Cache-Control: max-age=64000")
    @GET("/api/v1/post/getUserHomePost/{id}")
    Call<List<PostCommentModel>> getUserHomePost(@Path("id") int id);
}
