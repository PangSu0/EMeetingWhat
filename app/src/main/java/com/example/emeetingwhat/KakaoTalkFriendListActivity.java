package com.example.emeetingwhat;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;

public class KakaoTalkFriendListActivity extends FriendsMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button confirm = findViewById(R.id.button_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                ArrayList<Integer> arrayList = adapter.getChecked();
                for( int i = 0 ; i <  arrayList.size() ; i++ ){
                    FriendInfo groupFInfo = adapter.getItem(arrayList.get(i));
                    /*
                      groupFInfo.getProfileThumbnailImage();
                      selectedFriends.add(groupFInfo.getId());
                      GroupFriendsDetailFragment.InsertData task = new GroupFriendsDetailFragment.InsertData();
                      KakaoToast.makeToast(getActivity(), userProfile.getNickname(), Toast.LENGTH_SHORT).show();
                      task.execute("http://" + IP_ADDRESS + "/insertGroupFriends.php"
                         , Long.toString(groupFInfo.getId())
                         , groupId
                         , groupFInfo.getProfileNickname()
                         , groupFInfo.getProfileThumbnailImage()
                         , groupFInfo.getProfileThumbnailImage()
                    );
                    */

                    Intent intent = new Intent(getApplicationContext(), Step4_2_InviteMemberActivity.class);
                    startActivity(intent);
                }
            }
        });
        findViewById(R.id.title_back).setOnClickListener(v -> finish());
        final UserProfile userProfile = UserProfile.loadFromCache();
    }

    @Override
    public void onItemSelected(final int position, final FriendInfo friendInfo) {
        if (!friendInfo.isAllowedMsg()) {
            return;
        }
        adapter.setChecked(position);
        adapter.notifyDataSetChanged();
    }


}
