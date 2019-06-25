package com.example.emeetingwhat;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.toolbox.NetworkImageView;
import com.kakao.friends.response.FriendsResponse;
import com.kakao.friends.response.model.FriendInfo;

import java.util.ArrayList;
import java.util.List;

public class FriendsPager extends PagerAdapter {

    private Context context;
    public FriendsPager(Context context) {
        this.context = context;
    }

    private List<MyFriendsInfo> items = null;

    public void setItem(List<MyFriendsInfo> items) {
        this.items = items;
    }

    private class ViewHolder {
        NetworkImageView profileView;
        TextView nickName;
    }
    @Override
    public int getCount() {
        return items.size();
    }
    /*
    This callback is responsible for creating a page. We inflate the layout and set the drawable
    to the ImageView based on the position. In the end we add the inflated layout to the parent
    container .This method returns an object key to identify the page view, but in this example page view
    itself acts as the object key
    */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_item, null);
        if (position == getCount() - 5) {
//            if (listener != null) {
//                listener.onPreloadNext();
//            }
        }

        FriendsPager.ViewHolder holder;
        holder = new FriendsPager.ViewHolder();
        holder.profileView = view.findViewById(R.id.profile_image);
        holder.profileView.setDefaultImageResId(R.drawable.thumb_story);
        holder.profileView.setErrorImageResId(R.drawable.thumb_story);
        holder.nickName = view.findViewById(R.id.nickname);
        view.setTag(holder);


        final MyFriendsInfo info = getItem(position);
        Application app  = GlobalApplication.getGlobalApplicationContext();
        String profileUrl = info.getProfileImagePath();
        if (profileUrl != null && profileUrl.length() > 0) {
            holder.profileView.setImageUrl(profileUrl, ((GlobalApplication) app).getImageLoader());
        } else {
            holder.profileView.setImageResource(R.drawable.thumb_story);
        }

        String nickName = String.valueOf(position) + " " + info.getNickName();
        if (nickName.length() > 0) {
            holder.nickName.setVisibility(View.VISIBLE);
            holder.nickName.setText(nickName);
        } else {
            holder.nickName.setVisibility(View.INVISIBLE);
        }

        view.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onItemSelected(position, info);
//            }
        });

        return view;
    }

    /*
    This callback is responsible for destroying a page. Since we are using view only as the
    object key we just directly remove the view from parent container
    */
    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    public MyFriendsInfo getItem(int position) {
        return items.get(position);
    }

    /*
    Used to determine whether the page view is associated with object key returned by instantiateItem.
    Since here view only is the key we return view==object
    */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    private int getImageAt(int position) {
        return position;
    }

}