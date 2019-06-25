package com.example.emeetingwhat;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.toolbox.NetworkImageView;
import com.kakao.auth.Session;
import com.kakao.friends.response.model.FriendInfo;
import com.kakao.usermgmt.response.model.UserProfile;

public class MyFriendsListFragment extends FriendsMainFragment {
    public  final UserProfile userProfile = UserProfile.loadFromCache();
    public MyFriendsListFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_friends_fragment, container, false);
        final UserProfile userProfile = UserProfile.loadFromCache();
        if (userProfile != null) {
            View headerView = getLayoutInflater().inflate(R.layout.view_friend_item, list, false);

            NetworkImageView profileView = headerView.findViewById(R.id.profile_image);
            profileView.setDefaultImageResId(R.drawable.thumb_story);
            profileView.setErrorImageResId(R.drawable.thumb_story);
            TextView nickNameView = headerView.findViewById(R.id.nickname);

            String profileUrl = userProfile.getThumbnailImagePath();
            Application app  = GlobalApplication.getGlobalApplicationContext();
            if (profileUrl != null && profileUrl.length() > 0) {
                profileView.setImageUrl(profileUrl, ((GlobalApplication) app).getImageLoader());
            } else {
                profileView.setImageResource(R.drawable.thumb_story);
            }

            String nickName = " " + userProfile.getNickname();
            nickNameView.setText(nickName);

        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
