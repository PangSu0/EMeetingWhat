package com.example.emeetingwhat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

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

import static com.example.emeetingwhat.WaitingDialog.cancelWaitingDialog;
import static com.example.emeetingwhat.WaitingDialog.showWaitingDialog;

public class FriendsMainFragment extends Fragment implements View.OnClickListener, FriendsListAdapter.IFriendListCallback {

    public static final String EXTRA_KEY_SERVICE_TYPE = "KEY_FRIEND_TYPE";

    protected ListView list = null;
    private FriendsListAdapter adapter = null;
    private final List<FriendsRequest.FriendType> friendTypeList = new ArrayList<>();
    private FriendContext friendContext = null;
    private FriendsMainFragment.FriendsInfo friendsInfo = null;
    protected KakaoDialogSpinner msgType = null;
    public FriendsMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friends_main, container, false);
        list = view.findViewById(R.id.fragment_friends_list);

        if (friendTypeList.size() == 1) {
            friendsInfo = new FriendsMainFragment.FriendsInfo();
            requestFriends(friendTypeList.get(0));
            //requestFriends();
            KakaoToast.makeToast(getActivity(), friendsInfo.getTotalCount() + " ", Toast.LENGTH_SHORT).show();
        }
        // Inflate the layout for this fragment
        return view;
    }

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
    @Override
    public void onClick(View v) {
        FriendsRequest.FriendType type = FriendsRequest.FriendType.KAKAO_TALK;

        requestFriends(type);
    }

    private void requestFriends(FriendsRequest.FriendType type) {
        adapter = null;
        friendsInfo = new FriendsMainFragment.FriendsInfo();
        friendContext = FriendContext.createContext(type, FriendsRequest.FriendFilter.NONE, FriendsRequest.FriendOrder.NICKNAME, true, 0, 100, "asc");
        requestFriendsInner();
    }

    private void requestFriendsInner() {
        final FriendsListAdapter.IFriendListCallback callback = this;
        FriendsService.getInstance().requestFriends(new TalkResponseCallback<FriendsResponse>() {
            @Override
            public void onNotKakaoTalkUser() {
                KakaoToast.makeToast(getActivity(), "not a KakaoTalk user", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onNotSignedUp() {

            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                KakaoToast.makeToast(getActivity(), errorResult.toString(), Toast.LENGTH_SHORT).show();
                Logger.e("onFailure: " + errorResult.toString());
            }

            @Override
            public void onSuccess(FriendsResponse result) {
                if (result != null) {
                    friendsInfo.merge(result);

                    if (adapter == null) {
                        adapter = new FriendsListAdapter(friendsInfo.getFriendInfoList(), callback);
                        list.setAdapter(adapter);
                    } else {
                        adapter.setItem(friendsInfo.getFriendInfoList());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onDidStart() {
                showWaitingDialog(getActivity());
            }

            @Override
            public void onDidEnd() {
                cancelWaitingDialog();
            }
        }, friendContext);
    }

    private ListTemplate getListTemplate() {
        return ListTemplate.newBuilder("WEEKLY MAGAZINE",
                LinkObject.newBuilder()
                        .setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .build())
                .addContent(ContentObject.newBuilder("취미의 특징, 탁구",
                        "http://mud-kage.kakao.co.kr/dn/bDPMIb/btqgeoTRQvd/49BuF1gNo6UXkdbKecx600/kakaolink40_original.png",
                        LinkObject.newBuilder()
                                .setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com")
                                .build())
                        .setDescrption("스포츠")
                        .build())
                .addContent(ContentObject.newBuilder("크림으로 이해하는 커피이야기",
                        "http://mud-kage.kakao.co.kr/dn/QPeNt/btqgeSfSsCR/0QJIRuWTtkg4cYc57n8H80/kakaolink40_original.png",
                        LinkObject.newBuilder()
                                .setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com")
                                .build())
                        .setDescrption("음식")
                        .build())
                .addContent(ContentObject.newBuilder("신메뉴 출시❤️ 체리블라썸라떼",
                        "http://mud-kage.kakao.co.kr/dn/c7MBX4/btqgeRgWhBy/ZMLnndJFAqyUAnqu4sQHS0/kakaolink40_original.png",
                        LinkObject.newBuilder()
                                .setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com")
                                .build())
                        .setDescrption("사진").build())
                .addButton(new ButtonObject("웹으로 보기", LinkObject.newBuilder()
                        .setMobileWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .build()))
                .addButton(new ButtonObject("앱으로 보기", LinkObject.newBuilder()
                        .setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com")
                        .setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")
                        .build()))
                .build();
    }

    private TalkResponseCallback<Boolean> getTalkResponseCallback() {
        return new TalkResponseCallback<Boolean>() {
            @Override
            public void onNotKakaoTalkUser() {
                KakaoToast.makeToast(getActivity(), "not a KakaoTalk user", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                KakaoToast.makeToast(getActivity(), "failure : " + errorResult, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onNotSignedUp() {
                KakaoToast.makeToast(getActivity(), "onNotSignedUp : " + "User Not Registed App", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(Boolean result) {
                KakaoToast.makeToast(getActivity(), "Send message success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDidStart() {
                showWaitingDialog(getActivity());
            }

            @Override
            public void onDidEnd() {
                cancelWaitingDialog();
            }
        };
    }

    protected void requestDefaultMemo() {
        KakaoTalkService.getInstance().requestSendMemo(getTalkResponseCallback(), getListTemplate());
    }

    protected void requestDefaultMessage(final MessageSendable friendInfo) {
        KakaoTalkService.getInstance().requestSendMessage(getTalkResponseCallback(), friendInfo, getListTemplate());
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
