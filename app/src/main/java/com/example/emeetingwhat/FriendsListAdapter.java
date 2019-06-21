package com.example.emeetingwhat;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.kakao.friends.response.model.FriendInfo;

import java.util.List;

public class FriendsListAdapter extends BaseAdapter {
    private final IFriendListCallback listener;

    public interface IFriendListCallback {
        void onItemSelected(int position, FriendInfo friendInfo);
        void onPreloadNext();
    }
    private class ViewHolder {
        NetworkImageView profileView;
        TextView nickName;
    }
    private List<FriendInfo> items = null;

    FriendsListAdapter(List<FriendInfo> items, IFriendListCallback listener) {
        this.items = items;
        this.listener = listener;
    }

    public void setItem(List<FriendInfo> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public FriendInfo getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (position == getCount() - 100) {
            if (listener != null) {
                listener.onPreloadNext();
            }
        }

        View layout = convertView;
        ViewHolder holder;
        if (layout == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            layout = inflater.inflate(R.layout.view_friend_item, parent, false);
            holder = new ViewHolder();
            holder.profileView = layout.findViewById(R.id.profile_image);
            holder.profileView.setDefaultImageResId(R.drawable.thumb_story);
            holder.profileView.setErrorImageResId(R.drawable.thumb_story);
            holder.nickName = layout.findViewById(R.id.nickname);

            layout.setTag(holder);
        } else {
            holder = (ViewHolder)layout.getTag();
        }

        final FriendInfo info = getItem(position);
        Application app  = GlobalApplication.getGlobalApplicationContext();
        String profileUrl = info.getProfileThumbnailImage();
        if (profileUrl != null && profileUrl.length() > 0) {
            holder.profileView.setImageUrl(profileUrl, ((GlobalApplication) app).getImageLoader());
        } else {
            holder.profileView.setImageResource(R.drawable.thumb_story);
        }

        String nickName = String.valueOf(position) + " " + info.getProfileNickname();
        if (nickName.length() > 0) {
            holder.nickName.setVisibility(View.VISIBLE);
            holder.nickName.setText(nickName);
        } else {
            holder.nickName.setVisibility(View.INVISIBLE);
        }

        layout.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemSelected(position, info);
            }
        });
        return layout;
    }
}