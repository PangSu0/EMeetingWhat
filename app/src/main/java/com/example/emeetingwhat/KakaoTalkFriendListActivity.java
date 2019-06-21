package com.example.emeetingwhat;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.emeetingwhat.common.log.Logger;
import com.example.emeetingwhat.common.widget.KakaoToast;
import com.kakao.auth.common.MessageSendable;
import com.kakao.friends.response.model.FriendInfo;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.response.model.UserProfile;

public class KakaoTalkFriendListActivity extends FriendsMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

            list.addHeaderView(headerView);


        }
    }

    @Override
    public void onItemSelected(final int position, final FriendInfo friendInfo) {
        if (!friendInfo.isAllowedMsg()) {
            return;
        }

    }


}
