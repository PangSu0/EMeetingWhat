package com.example.emeetingwhat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.emeetingwhat.FriendsListAdapter.IFriendListCallback;
import com.example.emeetingwhat.common.log.Logger;
import com.example.emeetingwhat.common.widget.KakaoDialogSpinner;
import com.example.emeetingwhat.common.widget.KakaoToast;
import com.kakao.auth.common.MessageSendable;
import com.kakao.friends.FriendContext;
import com.kakao.friends.FriendsService;
import com.kakao.friends.request.FriendsRequest;
import com.kakao.friends.response.FriendsResponse;
import com.kakao.friends.response.model.FriendInfo;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.kakaotalk.v2.KakaoTalkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.ListTemplate;
import com.kakao.network.ErrorResult;

import java.util.ArrayList;
import java.util.List;

public class IndividualFriendsDetailActivity extends BaseActivity implements View.OnClickListener, GroupFriendsListAdapter.IFriendListCallback {

    private static class FriendsInfo {
        private final List<FriendInfo> friendInfoList = new ArrayList<>();
        private int totalCount;
        private String id;

        public FriendsInfo() {
        }

        public List<FriendInfo> getFriendInfoList() {
            return friendInfoList;
        }

        public void merge(FriendsResponse response) {
            this.id = response.getId();
            this.totalCount = response.getTotalCount();
            this.friendInfoList.addAll(response.getFriendInfoList());
        }

        public String getId() {
            return id;
        }

        public int getTotalCount() {
            return totalCount;
        }
    }

    public static final String EXTRA_KEY_SERVICE_TYPE = "KEY_FRIEND_TYPE";

    protected ListView list = null;
    private GroupFriendsListAdapter adapter = null;
    private final List<FriendsRequest.FriendType> friendTypeList = new ArrayList<>();
    private FriendContext friendContext = null;
    private FriendsInfo friendsInfo = null;
    protected KakaoDialogSpinner msgType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_friends_main);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_KEY_SERVICE_TYPE)) {
            String[] serviceTypes = intent.getStringArrayExtra(EXTRA_KEY_SERVICE_TYPE);
            for (String serviceType : serviceTypes) {
                friendTypeList.add(FriendsRequest.FriendType.valueOf(serviceType));
            }
        } else {
            friendTypeList.add(FriendsRequest.FriendType.KAKAO_TALK);
            friendTypeList.add(FriendsRequest.FriendType.KAKAO_STORY);
            friendTypeList.add(FriendsRequest.FriendType.KAKAO_TALK_AND_STORY);
        }

        list = findViewById(R.id.friend_list);


        if (friendTypeList.size() == 1) {
            friendsInfo = new FriendsInfo();
            requestFriends(friendTypeList.get(0));
            //requestFriends();
            KakaoToast.makeToast(getApplicationContext(), friendsInfo.getTotalCount() + " ", Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.title_back).setOnClickListener(v -> finish());
    }

    @Override
    public void onClick(View v) {
        FriendsRequest.FriendType type = FriendsRequest.FriendType.KAKAO_TALK;

        requestFriends(type);
    }

    private void requestFriends(FriendsRequest.FriendType type) {
        adapter = null;
        friendsInfo = new FriendsInfo();
        friendContext = FriendContext.createContext(type, FriendsRequest.FriendFilter.NONE, FriendsRequest.FriendOrder.NICKNAME, true, 0, 100, "asc");
        requestFriendsInner();
    }

    private void requestFriendsInner() {
        final GroupFriendsListAdapter.IFriendListCallback callback = this;
        FriendsService.getInstance().requestFriends(new TalkResponseCallback<FriendsResponse>() {
            @Override
            public void onNotKakaoTalkUser() {
                KakaoToast.makeToast(getApplicationContext(), "not a KakaoTalk user", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {
                redirectSignupActivity();
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                KakaoToast.makeToast(getApplicationContext(), errorResult.toString(), Toast.LENGTH_SHORT).show();
                Logger.e("onFailure: " + errorResult.toString());
            }

            @Override
            public void onSuccess(FriendsResponse result) {
                if (result != null) {
                    friendsInfo.merge(result);

                    if (adapter == null) {
                        adapter = new GroupFriendsListAdapter(friendsInfo.getFriendInfoList(), callback);
                        list.setAdapter(adapter);
                    } else {
                        adapter.setItem(friendsInfo.getFriendInfoList());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onDidStart() {
                showWaitingDialog();
            }

            @Override
            public void onDidEnd() {
                cancelWaitingDialog();
            }
        }, friendContext);
    }

    @Override
    public void onItemSelected(int position, FriendInfo friendInfo) {

    }

    @Override
    public void onPreloadNext() {
        if (friendContext.hasNext()) {
            requestFriendsInner();
        }
    }


}
