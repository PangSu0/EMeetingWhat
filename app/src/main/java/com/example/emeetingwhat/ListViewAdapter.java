package com.example.emeetingwhat;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.emeetingwhat.Data.AccountDetailData;
import com.kakao.friends.response.model.FriendInfo;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private final ListViewAdapter.IFriendListCallback listener;

    public interface IFriendListCallback {
        void onItemSelected(int position, MyFriendsInfo friendInfo);
        void onPreloadNext();
    }
    private class ViewHolder {
        NetworkImageView profileView;
        TextView nickName;
    }
    private ArrayList<MyFriendsInfo> list;
    private Activity activity;
    ListViewAdapter(Activity activity ,ArrayList<MyFriendsInfo> list,  ListViewAdapter.IFriendListCallback listener){
        this.activity = activity;
        list = new ArrayList<MyFriendsInfo>();
        this.listener = listener;
    }

    public void setList(MyFriendsInfo info){
        list.add(info);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
