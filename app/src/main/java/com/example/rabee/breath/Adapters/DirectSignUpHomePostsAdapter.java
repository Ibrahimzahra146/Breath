package com.example.rabee.breath.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rabee.breath.GeneralInfo;
import com.example.rabee.breath.Models.ResponseModels.PostCommentResponseModel;
import com.example.rabee.breath.Models.ResponseModels.PostResponseModel;
import com.example.rabee.breath.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rabee on 3/27/2018.
 */

public class DirectSignUpHomePostsAdapter extends RecyclerView.Adapter<DirectSignUpHomePostsAdapter.MyViewHolder> {
    Context context;
    private List<PostCommentResponseModel> postResponseModelsList;
    EditText commentTextDialog;
    TextView cancelBtnDialog;
    ImageView sendBtnDialog;
    int addCommentDialogPostId;
    Dialog addCommentDialog;

    public DirectSignUpHomePostsAdapter(Context context, List<PostCommentResponseModel> postResponseModelsList){
        this.postResponseModelsList = postResponseModelsList;
        this.context=context;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.direct_sign_up_post_list_view_item, parent, false);
        return new MyViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final PostResponseModel postResponseModel = postResponseModelsList.get(position).getPost();
        holder.posterUserName.setText(postResponseModel.getUserId().getFirst_name() + " " + postResponseModel.getUserId().getLast_name());
        holder.postBodyText.setText(postResponseModel.getText());
        long now = System.currentTimeMillis();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date convertedDate = dateFormat.parse(postResponseModelsList.get(position).getPost().getTimestamp());
            CharSequence relativeTIme = DateUtils.getRelativeTimeSpanString(
                    convertedDate.getTime(),
                    now,
                    DateUtils.SECOND_IN_MILLIS);
            holder.postTime.setText(relativeTIme);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String image;
        image = postResponseModel.getUserId().getImage();
        Gson gson = new Gson();
        String json = gson.toJson(postResponseModel);
        String imageUrl = GeneralInfo.SPRING_URL + image;
        Picasso.with(context).load(imageUrl).into(holder.posterProfilePicture);
        if (postResponseModel.getImage() != null) {
            imageUrl = GeneralInfo.SPRING_URL + "/" + postResponseModel.getImage();
            Picasso.with(context).load(imageUrl).into(holder.postImage);

        }
        //// postStatusIcon

        holder.postStatusIcon.setImageResource(postResponseModelsList.get(position).getPost().is_public_comment() ? R.drawable.unlocked_icon : R.drawable.locked_icon);
        // holder.postTime.setText(postResponseModelsList.get(position).getPost());
        int commentSize = postResponseModelsList.get(position).getComments().size();
        if (commentSize > 0) {
            if (commentSize == 1) {
                holder.postCommentCount.setText(String.valueOf(commentSize) + " comment");
            } else {
                holder.postCommentCount.setText(String.valueOf(commentSize) + " comments");
            }
        } else {
            holder.postCommentCount.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return postResponseModelsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView posterProfilePicture;
        TextView posterUserName;
        TextView postTime;
        TextView postCommentCount;
        TextView postBodyText;
        ImageView postImage;
        ImageView youtubeLinkImage;
        TextView youtubeLinkTitle;
        TextView youtubeLinkAuthor;
        LinearLayout youtubeLinkLayout;
        ImageView postCommentIcon,  postStatusIcon, addComnent;
        public MyViewHolder(View view) {
            super(view);
            posterProfilePicture = (CircleImageView) itemView.findViewById(R.id.userProfilePicture);
            posterUserName = (TextView) itemView.findViewById(R.id.username);
            postBodyText = (TextView) view.findViewById(R.id.postText);
            postCommentIcon = (ImageView) view.findViewById(R.id.comment_icon);
            postCommentCount = (TextView) view.findViewById(R.id.commentText);
            postImage = (ImageView) view.findViewById(R.id.postImage);
            youtubeLinkImage = (ImageView) view.findViewById(R.id.youtubeLinkImage);
            youtubeLinkTitle = (TextView) view.findViewById(R.id.youtubeLinkTitle);
            youtubeLinkLayout = (LinearLayout) view.findViewById(R.id.youtubeLinkLayout);
            youtubeLinkAuthor = (TextView) view.findViewById(R.id.youtubeLinkAuthor);

            postStatusIcon = (ImageView) view.findViewById(R.id.postStatus);
            addComnent = (ImageView) view.findViewById(R.id.add_comment);
            postTime = (TextView) view.findViewById(R.id.postTime);
        }
    }
}
