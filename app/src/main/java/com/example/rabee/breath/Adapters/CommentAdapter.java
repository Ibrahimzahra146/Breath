package com.example.rabee.breath.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rabee.breath.Models.CommentModel;
import com.example.rabee.breath.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Rabee on 2/10/2018.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.UserViewHolder>  {
    Context context;
    List<CommentModel> commentModelsList;
    TextView commentText;
    View view;

    public CommentAdapter(Context context, List<CommentModel> commentModelsList){
        this.context=context;
        this.commentModelsList=commentModelsList;
    }
    @Override
    public CommentAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.comment_list_view_item, null);
        return new CommentAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        Log.d("commentModelsList.get(position).getText()",commentModelsList.get(position).getText());
        holder.commentText.setText(commentModelsList.get(position).getText());
        if(commentModelsList.get(position).getReplies().size()==0){
            holder.repliesNumber.setText("No replies");
            holder.repliesNumber.setVisibility(View.INVISIBLE);

        }else holder.repliesNumber.setText(commentModelsList.get(position).getReplies().size()+" replies");

    }



    @Override
    public int getItemCount() {
        return commentModelsList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView commentText;
        TextView repliesNumber;
        public UserViewHolder(View itemView) {
            super(itemView);
            commentText=(TextView)itemView.findViewById(R.id.commentText);
            repliesNumber=(TextView)itemView.findViewById(R.id.repliesNumber);

        }
    }
}