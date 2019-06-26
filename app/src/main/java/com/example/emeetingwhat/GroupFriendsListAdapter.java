package com.example.emeetingwhat;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.kakao.friends.response.model.FriendInfo;

import java.util.ArrayList;
import java.util.List;

public class GroupFriendsListAdapter extends BaseAdapter {
    private final IFriendListCallback listener;
    private boolean[] isCheckedConfirm;

    public interface IFriendListCallback {
        void onItemSelected(int position, FriendInfo friendInfo);
        void onPreloadNext();
    }
    private class ViewHolder {
        NetworkImageView profileView;
        TextView nickName;
        CheckBox checkBox;
    }
    private List<FriendInfo> items = null;

    GroupFriendsListAdapter(List<FriendInfo> items, IFriendListCallback listener) {
        this.items = items;
        this.listener = listener;
        this.isCheckedConfirm = new boolean[items.size()];
    }
    public void setChecked(int position) {
        isCheckedConfirm[position] = !isCheckedConfirm[position];
    }

    public ArrayList<Integer> getChecked(){
        int tempSize = isCheckedConfirm.length;
        ArrayList<Integer> mArrayList = new ArrayList<Integer>();
        for(int i=0 ; i<tempSize ; i++){
            if(isCheckedConfirm[i]){
                mArrayList.add(i);
            }
        }
        return mArrayList;
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

        if (position == getCount() - 5) {
            if (listener != null) {
                listener.onPreloadNext();
            }
        }

        View layout = convertView;
        ViewHolder holder;
        if (layout == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            layout = inflater.inflate(R.layout.view_group_friend_item, parent, false);
            holder = new ViewHolder();
            holder.profileView = layout.findViewById(R.id.profile_image);
            holder.profileView.setDefaultImageResId(R.drawable.thumb_story);
            holder.profileView.setErrorImageResId(R.drawable.thumb_story);
            holder.nickName = layout.findViewById(R.id.nickname);
            holder.checkBox = layout.findViewById(R.id.checkBox);
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

        String nickName = " " + info.getProfileNickname();
        if (nickName.length() > 0) {
            holder.nickName.setVisibility(View.VISIBLE);
            holder.nickName.setText(nickName);
        } else {
            holder.nickName.setVisibility(View.INVISIBLE);
        }
        holder.checkBox.setClickable(false);
        holder.checkBox.setFocusable(false);
        // isCheckedConfrim 배열은 초기화시 모두 false로 초기화 되기때문에
        // 기본적으로 false로 초기화 시킬 수 있다.
        holder.checkBox.setChecked(isCheckedConfirm[position]);

        layout.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemSelected(position, info);
            }
        });
        return layout;
    }
}
