package com.example.emeetingwhat;

import android.app.Activity;
import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.emeetingwhat.Data.AccountDetailData;
import com.kakao.friends.response.model.FriendInfo;

import java.util.ArrayList;
import java.util.List;

public class MyFriendsListAdapter extends RecyclerView.Adapter<MyFriendsListAdapter.CustomViewHolder> {
    private ArrayList<MyFriendsInfo> mList = null;
    private Activity context = null;

    public MyFriendsListAdapter(Activity context, ArrayList<MyFriendsInfo> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected  NetworkImageView profileView;
        protected TextView nickName;


        public CustomViewHolder(View view) {
            super(view);
            this.profileView = (NetworkImageView) view.findViewById(R.id.pager_profile_image);
            this.nickName = (TextView) view.findViewById(R.id.pager_nickname);
        }
    }

    @Override
    public MyFriendsListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pager_item, null);
        MyFriendsListAdapter.CustomViewHolder viewHolder = new MyFriendsListAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyFriendsListAdapter.CustomViewHolder viewholder, int position) {
        viewholder.profileView.setDefaultImageResId(R.drawable.thumb_story);
        viewholder.profileView.setErrorImageResId(R.drawable.thumb_story);
        final MyFriendsInfo info = mList.get(position);
        Application app  = GlobalApplication.getGlobalApplicationContext();
        String profileUrl = info.getThumbnailImagePath();
        if (profileUrl != null && profileUrl.length() > 0) {
            viewholder.profileView.setImageUrl(profileUrl, ((GlobalApplication) app).getImageLoader());
        } else {
            viewholder.profileView.setImageResource(R.drawable.thumb_story);
        }

        String nickName = String.valueOf(position) + " " + info.getNickName();
        if (nickName.length() > 0) {
            viewholder.nickName.setVisibility(View.VISIBLE);
            viewholder.nickName.setText(nickName);
        } else {
            viewholder.nickName.setVisibility(View.INVISIBLE);
        }
        viewholder.nickName.setText(" " + mList.get(position).getNickName());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}