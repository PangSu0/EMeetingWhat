package com.example.emeetingwhat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.common.log.Logger;
import com.example.emeetingwhat.common.widget.KakaoToast;
import com.kakao.friends.FriendContext;
import com.kakao.friends.FriendsService;
import com.kakao.friends.request.FriendsRequest;
import com.kakao.friends.response.FriendsResponse;
import com.kakao.friends.response.model.FriendInfo;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.network.ErrorResult;

import java.util.ArrayList;
import java.util.List;

import static com.example.emeetingwhat.WaitingDialog.cancelWaitingDialog;

public class Step4_2_InviteMemberActivity extends AppCompatActivity implements GroupFriendsListAdapter.IFriendListCallback {
    int mSelectRadioButton;
    Intent intent_GroupFromPrevious;
    GroupDetailData groupDataFromPrev;
    protected ListView list = null;
    private ArrayList<MyFriendsInfo> myFriendsInfoArrayList = new ArrayList<>();
    private Step4_2_InviteMemberActivity.FriendsInfo friendsInfo = null;
    private final List<FriendsRequest.FriendType> friendTypeList = new ArrayList<>();
    private GroupFriendsListAdapter mAdapter = null;
    private FriendContext friendContext = null;
    private ArrayList<MyFriendsInfo> selectedFriends = new ArrayList<>();
    Button btn_add;
    String str_groupId = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent_GroupFromPrevious = getIntent();
        str_groupId = intent_GroupFromPrevious.getStringExtra("groupId");
        groupDataFromPrev =  (GroupDetailData)intent_GroupFromPrevious.getSerializableExtra("groupDetailData");
        myFriendsInfoArrayList = (ArrayList<MyFriendsInfo>) intent_GroupFromPrevious.getSerializableExtra("myFriendsInfoArrayList");
        Toast.makeText(getApplicationContext(), groupDataFromPrev.getAccountHolderId() + " ", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_step4_2_invite_member);
        String str = "";
        EditText ed_friends = findViewById(R.id.editName2);
        for( int i = 0 ; i < selectedFriends.size() ; i ++ ){
            str += " " + selectedFriends.get(i).getNickName();
        }
        ed_friends.setText(str);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case  R.id.btnStep4_2_Prev:
                onBackPressed();
                break;
            case R.id.btnStep4_2_Next:
                nextPage();
                break;
            case R.id.rbtnStep4_2_ToStep_5_1:
                mSelectRadioButton = 1;
                break;
            case R.id.rbtnStep4_2_ToStep_5_2:
                mSelectRadioButton = 2;
                break;
            case R.id.btnStep4_2_AddMember:
                //showTalkFriendListActivity();
                showAlertDialog();
                break;
            case R.id.button_confirm:
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }

    public void nextPage()
    {
        Intent intent;
        if(mSelectRadioButton == 1)
            intent = new Intent(getApplicationContext(), Step5_1_SelectTextActivity.class);
        else if(mSelectRadioButton == 2 )
            intent = new Intent(getApplicationContext(), Step5_2_RandomLadderActivity.class);
        else    //버튼이 안 눌린경우
        {
            Toast.makeText(getApplicationContext(), "수령 순서를 결정해 주세요.",Toast.LENGTH_LONG).show();
            return;
        }
        // Toast.makeText(getApplicationContext(), selectedFriends.size() + "명이 선택", Toast.LENGTH_SHORT).show();
        intent.putExtra("groupId", str_groupId);
        intent.putExtra("groupDetailData", groupDataFromPrev);
        intent.putExtra("selectedFriends", selectedFriends);
        intent.putExtra("myFriendsInfoArrayList", myFriendsInfoArrayList);
        startActivity(intent);
    }

    private void showAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_group_friends_list, null);
        builder.setView(view);
        friendTypeList.add(FriendsRequest.FriendType.KAKAO_TALK);
        friendsInfo = new Step4_2_InviteMemberActivity.FriendsInfo();
        requestFriends(friendTypeList.get(0));
        //requestFriends();
        KakaoToast.makeToast(getApplicationContext(), friendsInfo.getTotalCount() + " ", Toast.LENGTH_SHORT).show();
        list = (ListView)view.findViewById(R.id.group_friends);
        final AlertDialog dialog = builder.create();

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Button confirm = view.findViewById(R.id.button_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> arrayList = mAdapter.getChecked();
                for( int i = 0 ; i <  arrayList.size() ; i++ ){
                    FriendInfo groupFInfo = mAdapter.getItem(arrayList.get(i));
                    MyFriendsInfo myFriendsInfo = new MyFriendsInfo();
                    myFriendsInfo.setUserId(groupFInfo.getId());
                    myFriendsInfo.setNickName(groupFInfo.getProfileNickname());
                    myFriendsInfo.setProfileImagePath(groupFInfo.getProfileThumbnailImage());
                    myFriendsInfo.setThumbnailImagePath(groupFInfo.getProfileThumbnailImage());
                    selectedFriends.add(myFriendsInfo);
                    // Toast.makeText(getApplicationContext(), myFriendsInfo.getNickName(), Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });

        view.findViewById(R.id.title_back).setOnClickListener(v -> dialog.dismiss());
    }

    private void requestFriends(FriendsRequest.FriendType type) {
        // adapter = null;
        friendsInfo = new Step4_2_InviteMemberActivity.FriendsInfo();
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
            }

            @Override
            public void onNotSignedUp() {
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

                    if (mAdapter == null) {
                        mAdapter = new GroupFriendsListAdapter(friendsInfo.getFriendInfoList(), callback);
                        list.setAdapter(mAdapter);
                    } else {
                        mAdapter.setItem(friendsInfo.getFriendInfoList());
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onDidStart() {
            }

            @Override
            public void onDidEnd() {
                cancelWaitingDialog();
            }
        }, friendContext);
    }

    @Override
    public void onItemSelected(int position, FriendInfo friendInfo) {
        mAdapter.setChecked(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPreloadNext() {
        if (friendContext.hasNext()) {
            requestFriendsInner();
        }
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

}
