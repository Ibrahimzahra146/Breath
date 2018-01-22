package com.example.rabee.recyclerviewspeedtest.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rabee.recyclerviewspeedtest.GeneralInfo;
import com.example.rabee.recyclerviewspeedtest.Models.UserModel;
import com.example.rabee.recyclerviewspeedtest.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rabee on 1/20/2018.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    public View view;
    private Context mContext;
    public static List<UserModel> userModelList;


    public UserListAdapter(Context mContext, List<UserModel> userModelList) {
        this.mContext = mContext;
        this.userModelList = userModelList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.user_list_view_item, null);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        String fullName=userModelList.get(position).getFirst_name() +""+userModelList.get(position).getLast_name();
        holder.tvName.setText(fullName);
        String imageUrl = GeneralInfo.SPRING_URL + "/" +userModelList.get(position).getImage() ;
        Picasso.with(mContext).load(imageUrl).into(holder.ivProfile);
    }


    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        CircleImageView ivProfile;
        TextView tvName;


        public UserViewHolder(View itemView) {

            super(itemView);

            ivProfile = (CircleImageView) itemView.findViewById(R.id.profile_pic);
            tvName = (TextView) itemView.findViewById(R.id.Name);
        }
    }
}
