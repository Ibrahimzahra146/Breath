package com.example.rabee.breath.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rabee.breath.Activities.CommentActivity;
import com.example.rabee.breath.Activities.ReactActivity;
import com.example.rabee.breath.Activities.YoutubeDialogActivity;
import com.example.rabee.breath.GeneralFunctions;
import com.example.rabee.breath.GeneralInfo;
import com.example.rabee.breath.Models.RequestModels.ReactRequestModel;
import com.example.rabee.breath.Models.ResponseModels.PostCommentResponseModel;
import com.example.rabee.breath.Models.ResponseModels.PostResponseModel;
import com.example.rabee.breath.R;
import com.example.rabee.breath.RequestInterface.PostInterface;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import static com.facebook.FacebookSdk.getApplicationContext;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.MyViewHolder> {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(GeneralInfo.SPRING_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    PostInterface postInterface = retrofit.create(PostInterface.class);
    boolean pressedLoveFlag = false, PressedLikeFlag = false, PressedUnlikeFlag = false;
    int likeCount = 0, disLikeCount = 0, loveCount = 0;
    private List<PostCommentResponseModel> postResponseModelsList;
    private Context context;

    public HomePostAdapter(Context context, List<PostCommentResponseModel> postResponseModelsList) {
        this.postResponseModelsList = postResponseModelsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.post_list_view_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PostResponseModel postResponseModel = postResponseModelsList.get(position).getPost();
        holder.posterUserName.setText(postResponseModel.getUserId().getFirst_name() + " " + postResponseModel.getUserId().getLast_name());
        holder.postBodyText.setText(postResponseModel.getText());
        String image;
        image = postResponseModel.getUserId().getImage();
        Gson gson = new Gson();
        String json = gson.toJson(postResponseModel);
        Log.d("PostActivity", json);
        String imageUrl = GeneralInfo.SPRING_URL + image;
        Picasso.with(context).load(imageUrl).into(holder.posterProfilePicture);
        if (postResponseModel.getImage() != null) {
            imageUrl = GeneralInfo.SPRING_URL + "/" + postResponseModel.getImage();
            Picasso.with(context).load(imageUrl).into(holder.postImage);

        }
        likeCount=postResponseModelsList.get(position).getReacts().getLikeList().getCount();
        disLikeCount=postResponseModelsList.get(position).getReacts().getDislikeList().getCount();
        loveCount=postResponseModelsList.get(position).getReacts().getLoveList().getCount();
        holder.postLoveCount.setText(loveCount > 0 ? (String.valueOf(loveCount) + (loveCount == 1 ? " Love" : " Loves")) : "");
        holder.postLikeCount.setText(likeCount > 0 ? (String.valueOf(likeCount) + (likeCount == 1 ? " Like" : " Likes")) : "");
        holder.postDislikeCount.setText(disLikeCount > 0 ? (String.valueOf(disLikeCount) + (disLikeCount > 1 ? " Unlikes" : " Unlike")) : "");
        //////////////////

        if (postResponseModelsList.get(position).getReacts().getLoveList().getMyAction()) {
            holder.postLoveIcon.setImageResource(R.drawable.filled_love_post);
        likeCount = postResponseModelsList.get(position).getReacts().getLikeList().getCount();
        disLikeCount = postResponseModelsList.get(position).getReacts().getDislikeList().getCount();
        loveCount = postResponseModelsList.get(position).getReacts().getLoveList().getCount();
        holder.postLoveCount.setText(loveCount > 0 ? String.valueOf(loveCount) : "");
        holder.postLikeCount.setText(likeCount > 0 ? String.valueOf(likeCount) : "");
        holder.postDislikeCount.setText(disLikeCount > 0 ? String.valueOf(disLikeCount) : "");
        //////////////////

        if (postResponseModelsList.get(position).getReacts().getLoveList().getMyAction()) {
            holder.postLoveIcon.setImageResource(R.drawable.love_icon_filled);
            pressedLoveFlag = true;
        }

        if (postResponseModelsList.get(position).getReacts().getLikeList().getMyAction()) {
            holder.postLikeIcon.setImageResource(R.drawable.filled_like3);
            PressedLikeFlag = true;

        }
        if (postResponseModelsList.get(position).getReacts().getDislikeList().getMyAction()) {
            holder.postUnlikeIcon.setImageResource(R.drawable.filled_unlike3);
            PressedUnlikeFlag = true;

        }
        /////////////////Listeners//////////////////////////////
        //React listener
        View.OnClickListener reactsClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ReactActivity.class);
                Bundle b = new Bundle();
                b.putInt("postId",postResponseModelsList.get(position).getPost().getPostId());

                i.putExtras(b);
                context.startActivity(i);
                // your stuff
            }
        };

        holder.postLoveCount.setOnClickListener(reactsClickListener);
        holder.postLikeCount.setOnClickListener(reactsClickListener);
        holder.postDislikeCount.setOnClickListener(reactsClickListener);
        //////////////react icon listener//////////////////////
        holder.postLoveIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!pressedLoveFlag) {
                            AddNewReact(position, 3);
                            Log.d("PostHolder", " press on love icon " + position);
                            holder.postLoveIcon.setImageResource(R.drawable.filled_love_post);
                            loveCount++;
                            holder.postLoveCount.setText(loveCount > 0 ? (String.valueOf(loveCount) + (loveCount == 1 ? " Love" : " Loves")) : "");
                        } else {
                            DeleteReact(position, 0);
                            holder.postLoveIcon.setImageResource(R.drawable.love_icon);
                            loveCount--;
                            holder.postLoveCount.setText(loveCount > 0 ? (String.valueOf(loveCount) + (loveCount == 1 ? " Love" : " Loves")) : "");
                        }
                        pressedLoveFlag = !pressedLoveFlag;
                    }
                });
            holder.postLikeIcon.setImageResource(R.drawable.filled_thumb_up);
            PressedLikeFlag = true;

        }

        if (postResponseModelsList.get(position).getReacts().getDislikeList().getMyAction()) {
            holder.postUnlikeIcon.setImageResource(R.drawable.dislike_filled_icon);
            PressedUnlikeFlag = true;

        }
        ///////////////Listeners//////////////////////////////
        // React listener
        View.OnClickListener reactsClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ReactActivity.class);
                Bundle b = new Bundle();
                b.putInt("postId", postResponseModelsList.get(position).getPost().getPostId());
                i.putExtras(b);
                context.startActivity(i);
            }
        };
        //  holder.postLoveCount.setOnClickListener(reactsClickListener);
        //holder.postLikeCount.setOnClickListener(reactsClickListener);
        //holder.postDislikeCount.setOnClickListener(reactsClickListener);
        //////////////react icon listener//////////////////////
        holder.postCommentCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(postResponseModelsList.get(position).getComments().size()==0){

                }else {
                    Intent i = new Intent(getApplicationContext(), CommentActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("postId",postResponseModelsList.get(position).getPost().getPostId());

                    i.putExtras(b);
                    context.startActivity(i);
                }

            }
        });
        holder.postLoveIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!pressedLoveFlag) {
                            AddNewReact(position, 3);
                            Log.d("PostHolder", " press on love icon " + position);
                            holder.postLoveIcon.setImageResource(R.drawable.love_icon_filled);
                            holder.postLikeIcon.setImageResource(R.drawable.like_icon);
                            holder.postUnlikeIcon.setImageResource(R.drawable.unlike_icon);
                            loveCount++;
                            likeCount=!PressedLikeFlag ? likeCount : likeCount--;
                            disLikeCount=!PressedUnlikeFlag ? disLikeCount : disLikeCount--;
                        } else {
                            DeleteReact(position, 0);
                            holder.postLoveIcon.setImageResource(R.drawable.love_icon);
                            loveCount--;
                        }
                        holder.postLikeCount.setText(likeCount > 0 ? String.valueOf(likeCount) : "");
                        holder.postDislikeCount.setText(disLikeCount > 0 ? String.valueOf(disLikeCount) : "");
                        holder.postLoveCount.setText(loveCount > 0 ? String.valueOf(loveCount) : "");
                        pressedLoveFlag = !pressedLoveFlag;
                    }
                });
        holder.postLikeIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!PressedLikeFlag) {
                            AddNewReact(position, 1);
                            holder.postLikeIcon.setImageResource(R.drawable.filled_thumb_up);
                            holder.postUnlikeIcon.setImageResource(R.drawable.unlike_icon);
                            holder.postLoveIcon.setImageResource(R.drawable.love_icon);
                            likeCount++;
                            loveCount=!pressedLoveFlag ? loveCount: loveCount--;
                            disLikeCount=!PressedUnlikeFlag? disLikeCount : disLikeCount--;
                        } else {
                            DeleteReact(position, 0);
                            holder.postLikeIcon.setImageResource(R.drawable.like_icon);
                            likeCount--;
                        }
                        holder.postLikeCount.setText(likeCount > 0 ? String.valueOf(likeCount) : "");
                        holder.postDislikeCount.setText(disLikeCount > 0 ? String.valueOf(disLikeCount) : "");
                        holder.postLoveCount.setText(loveCount > 0 ? String.valueOf(loveCount) : "");
                        PressedLikeFlag = !PressedLikeFlag;
                    }
                });

        holder.postUnlikeIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!PressedUnlikeFlag) {
                            AddNewReact(position, 2);
                            holder.postUnlikeIcon.setImageResource(R.drawable.dislike_filled_icon);
                            holder.postLoveIcon.setImageResource(R.drawable.love_icon);
                            holder.postLikeIcon.setImageResource(R.drawable.like_icon);
                            disLikeCount++;
                            loveCount=!pressedLoveFlag ? loveCount: loveCount--;
                            likeCount=!PressedLikeFlag? likeCount : likeCount--;
                        } else {
                            DeleteReact(position, 0);
                            holder.postUnlikeIcon.setImageResource(R.drawable.unlike_icon);
                            disLikeCount--;
                        }
                        holder.postLikeCount.setText(likeCount > 0 ? String.valueOf(likeCount) : "");
                        holder.postDislikeCount.setText(disLikeCount > 0 ? String.valueOf(disLikeCount) : "");
                        holder.postLoveCount.setText(loveCount > 0 ? String.valueOf(loveCount) : "");
                        PressedUnlikeFlag = !PressedUnlikeFlag;
                    }
                });

        if (postResponseModel.getYoutubelink().getLink() != "" && postResponseModel.getImage() == null) {
            holder.youtubeLinkTitle.setText(postResponseModel.getYoutubelink().getTitle());
            holder.youtubeLinkAuthor.setText("Channel: " + postResponseModel.getYoutubelink().getAuthor_name());
            imageUrl = postResponseModel.getYoutubelink().getImage();
            Picasso.with(getApplicationContext()).load(imageUrl).into(holder.youtubeLinkImage);
        } else {
            Log.d("PostActivity", "Photo with youtube");
            holder.youtubeLinkLayout.setVisibility(View.GONE);
        }

        View.OnClickListener youtubeListener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), YoutubeDialogActivity.class);
                Bundle b = new Bundle();
                b.putString("youtubeSongUrl", postResponseModel.getLink());
                i.putExtras(b);
                getApplicationContext().startActivity(i);
            }
        };

        holder.youtubeLinkTitle.setOnClickListener(youtubeListener);
        holder.youtubeLinkAuthor.setOnClickListener(youtubeListener);
        holder.youtubeLinkImage.setOnClickListener(youtubeListener);

    }

    @Override
    public int getItemCount() {
        return postResponseModelsList.size();
    }


    public void AddNewReact(int position, int type) {

        PostInterface addNewReact = retrofit.create(PostInterface.class);

        ReactRequestModel reactRequestModel = new ReactRequestModel();
        int postId = postResponseModelsList.get(position).getPost().getPostId();
        reactRequestModel.setPostId(postId);
        reactRequestModel.setType(type);
        reactRequestModel.setUserId(GeneralInfo.getGeneralUserInfo().getUser().getId());
        Call<Integer> addNewReactResponse = addNewReact.addNewReact(reactRequestModel);

        addNewReactResponse.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.d("PostHolder", " Post react addition " + response.code());
                if (response.code() == 404 || response.code() == 500 || response.code() == 502 || response.code() == 400) {
                    GeneralFunctions generalFunctions = new GeneralFunctions();
                    generalFunctions.showErrorMesaage(getApplicationContext());
                } else {


                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                GeneralFunctions generalFunctions = new GeneralFunctions();
                generalFunctions.showErrorMesaage(getApplicationContext());
                Log.d("PostHolder", t.getMessage());
            }

        });
    }

    public void DeleteReact(int position, int type) {

        PostInterface deleteReact = retrofit.create(PostInterface.class);

        ReactRequestModel reactRequestModel = new ReactRequestModel();
        int postId = postResponseModelsList.get(position).getPost().getPostId();
        reactRequestModel.setPostId(postId);
        reactRequestModel.setType(type);
        reactRequestModel.setUserId(GeneralInfo.getGeneralUserInfo().getUser().getId());
        Call<Integer> addNewReactResponse = deleteReact.deleteReact(reactRequestModel);

        addNewReactResponse.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.code() == 404 || response.code() == 500 || response.code() == 502 || response.code() == 400) {
                    GeneralFunctions generalFunctions = new GeneralFunctions();
                    generalFunctions.showErrorMesaage(getApplicationContext());
                } else {


                }



            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                GeneralFunctions generalFunctions = new GeneralFunctions();
                generalFunctions.showErrorMesaage(getApplicationContext());
                Log.d("PostHolder", t.getMessage());
            }

        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView posterProfilePicture;
        TextView posterUserName;
        TextView postTime;
        TextView postLoveCount, postLikeCount, postDislikeCount;
        ImageView postCommentCount;
        TextView postBodyText;
        ImageView postImage;
        ImageView youtubeLinkImage;
        TextView youtubeLinkTitle;
        TextView youtubeLinkAuthor;
        LinearLayout youtubeLinkLayout;
        ImageView postLoveIcon, postLikeIcon, postUnlikeIcon;

        public MyViewHolder(View view) {
            super(view);

            posterProfilePicture = (CircleImageView) itemView.findViewById(R.id.userProfilePicture);
            posterUserName = (TextView) itemView.findViewById(R.id.username);
            postBodyText = (TextView) view.findViewById(R.id.postText);
            postCommentCount=(ImageView)view.findViewById(R.id.comment_icon);
            postImage = (ImageView) view.findViewById(R.id.postImage);
            youtubeLinkImage = (ImageView) view.findViewById(R.id.youtubeLinkImage);
            youtubeLinkTitle = (TextView) view.findViewById(R.id.youtubeLinkTitle);
            youtubeLinkLayout = (LinearLayout) view.findViewById(R.id.youtubeLinkLayout);
            youtubeLinkAuthor = (TextView) view.findViewById(R.id.youtubeLinkAuthor);
            postLoveCount = (TextView) itemView.findViewById(R.id.loveCount);
            postLikeCount = (TextView) view.findViewById(R.id.likeCount);
            postDislikeCount = (TextView) view.findViewById(R.id.unlikeCount);
            postLoveIcon = (ImageView) view.findViewById(R.id.love_post);
            postLikeIcon = (ImageView) view.findViewById(R.id.like_post);
            postUnlikeIcon = (ImageView) view.findViewById(R.id.dislike_post);

        }
    }
}
