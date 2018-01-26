package com.example.rabee.breath.Adapters;

/**
 * Created by Rabee on 1/16/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rabee on 1/16/2018.
 */


import com.example.rabee.breath.GeneralInfo;
import com.example.rabee.breath.Models.ResponseModels.PostCommentResponseModel;
import com.example.rabee.breath.Models.ResponseModels.PostResponseModel;
import com.example.rabee.breath.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Rabee on 1/3/2018.
 */

public class HomePostAdapter extends RecyclerView.Adapter<HomePostAdapter.MyViewHolder>{
    private List<PostCommentResponseModel> postResponseModelsList;
    private Context context;
    public HomePostAdapter(Context context, List<PostCommentResponseModel> postResponseModelsList){
        this.postResponseModelsList=postResponseModelsList;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.reacts_list_view_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PostResponseModel postResponseModel =postResponseModelsList.get(position).getPost();
        holder.posterUserName.setText(postResponseModel.getUserId().getFirst_name() + " " + postResponseModel.getUserId().getLast_name());
        holder.postBodyText.setText(postResponseModel.getText());
        String image;
        image = postResponseModel.getUserId().getImage();
        String imageUrl = GeneralInfo.SPRING_URL + image;
        Picasso.with(context).load(imageUrl).into(holder.posterProfilePicture);


    }

    @Override
    public int getItemCount() {
        return postResponseModelsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView posterProfilePicture;
        TextView posterUserName;
        TextView postTime;
        TextView    postLoveCount, postLikeCount, postDislikePost;
        TextView postCommentCount;
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
            postImage = (ImageView) view.findViewById(R.id.postImage);
            youtubeLinkImage = (ImageView) view.findViewById(R.id.youtubeLinkImage);
            youtubeLinkTitle = (TextView) view.findViewById(R.id.youtubeLinkTitle);
            youtubeLinkLayout = (LinearLayout) view.findViewById(R.id.youtubeLinkLayout);
            youtubeLinkAuthor = (TextView) view.findViewById(R.id.youtubeLinkAuthor);
            postLikeCount = (TextView) view.findViewById(R.id.likeCount);
            postDislikePost = (TextView) view.findViewById(R.id.unlikeCount);
            postLoveIcon = (ImageView) view.findViewById(R.id.love_post);
            postLikeIcon = (ImageView) view.findViewById(R.id.like_post);
            postUnlikeIcon = (ImageView) view.findViewById(R.id.dislike_post);

        }
    }
}
