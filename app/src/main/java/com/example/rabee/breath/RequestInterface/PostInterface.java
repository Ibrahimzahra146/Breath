package com.example.rabee.breath.RequestInterface;


import com.example.rabee.breath.Models.ReactSingleModel;
import com.example.rabee.breath.Models.ReplyModel;
import com.example.rabee.breath.Models.RequestModels.PostRequestModel;
import com.example.rabee.breath.Models.RequestModels.ReactRequestModel;
import com.example.rabee.breath.Models.RequestModels.SavePostRequestModel;
import com.example.rabee.breath.Models.ResponseModels.PostCommentResponseModel;
import com.example.rabee.breath.Models.ResponseModels.PostResponseModel;
import com.example.rabee.breath.Models.ResponseModels.ReplyResponseModel;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rabee on 1/20/2018.
 */

public interface PostInterface {
    @Headers("Cache-Control: max-age=64000")
    @GET("/api/v1/post/getUserHomePost/{id}")
    Call<List<PostCommentResponseModel>> getUserHomePost(@Path("id") int id);
    @Multipart
    @POST("/api/v1/post/addNewImagePost")
    Call<PostResponseModel> addNewPost(@Part MultipartBody.Part file , @Query("id") int id, @Query("text") String text, @Query("is_public_comment") boolean is_public_comment);

    @Headers("Cache-Control: max-age=64000")
    @POST("/api/v1/post/addNewPost")
    Call<PostResponseModel> addNewPost(@Body PostRequestModel postRequestModel);

    @Headers("Cache-Control: max-age=64000")
    @POST("/api/v1/post/addNewReact")
    Call<Integer> addNewReact(@Body ReactRequestModel reactRequestModel);

    @Headers("Cache-Control: max-age=64000")
    @POST("/api/v1/post/deleteReact")
    Call<Integer> deleteReact(@Body ReactRequestModel reactRequestModel);
    @Headers("Cache-Control: max-age=64000")
    @GET("/api/v1/post/getPostReact/{postId}/{type}")
    Call<ReactSingleModel> getPostReact(@Path("postId") int postId, @Path("type") int type);
    @Headers("Cache-Control: max-age=64000")
    @GET("/api/v1/post/getPost/{postId}")
    Call<PostCommentResponseModel> getPost(@Path("postId") int postId);
    @Headers("Cache-Control: max-age=64000")
    @GET("/api/v1/comment/getCommentReplies/{commentId}")
    Call<ReplyResponseModel> getCommentReplies(@Path("commentId") int commentId);
    @Headers("Cache-Control: max-age=64000")
    @POST("/api/v1/post/save-post")
    Call<SavePostRequestModel> savePost(@Body SavePostRequestModel savePostRequestModel);
}
