package com.example.emeetingwhat.createGroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.Data.AccountDetailData;
import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.FriendsMainActivity;
import com.example.emeetingwhat.KakaoTalkFriendListActivity;
import com.example.emeetingwhat.MainActivity;
import com.example.emeetingwhat.R;
import com.kakao.friends.request.FriendsRequest;

public class Create_FriendsActivity extends AppCompatActivity {

    private Button btn4Prev;
    private Button btn4Next;

    GroupDetailData groupDetailData = new GroupDetailData();
    AccountDetailData accountDetailData = new AccountDetailData();

    Intent intent_GroupFromPrevious;
    Intent intent_AccountFromPrevious;

    GroupDetailData groupDataFromPrev;
    AccountDetailData accountDataFromPrev;

    private Button btn_friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_friends);

        intent_GroupFromPrevious = getIntent();
        intent_AccountFromPrevious = getIntent();

        groupDataFromPrev =  (GroupDetailData)intent_GroupFromPrevious.getSerializableExtra("groupDetailData");
        accountDataFromPrev =  (AccountDetailData)intent_AccountFromPrevious.getSerializableExtra("accountDetailData");

        // 앞, 뒤로가기 버튼
        btn4Prev = (Button)findViewById(R.id.btn4Prev);
        btn4Next = (Button)findViewById(R.id.btn4Next);
        btn_friendsList = (Button) findViewById(R.id.btnFriendsList);
        groupDetailData.setTargetAmount(groupDataFromPrev.getTargetAmount());
        groupDetailData.setName(groupDataFromPrev.getName());
        groupDetailData.setGroupType(groupDataFromPrev.getGroupType());
        groupDetailData.setPaymentDay(groupDataFromPrev.getPaymentDay());
        accountDetailData.setBankName(accountDataFromPrev.getBankName());

        btn4Prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Create_FriendsActivity.this, Create_DetailsActivity.class);
                startActivity(intent);
            }
        });

        // Prev 버튼을 눌렀을 때
        btn4Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: 내가 참여하고 있는 모임 리스트로 보내기.
                Intent intent = new Intent(Create_FriendsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_friendsList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTalkFriendListActivity();
            }
        });
    }

    private void showTalkFriendListActivity() {
        Intent intent = new Intent(this, KakaoTalkFriendListActivity.class);

        String[] friendType = {FriendsRequest.FriendType.KAKAO_TALK.name()};
        intent.putExtra(FriendsMainActivity.EXTRA_KEY_SERVICE_TYPE, friendType);
        startActivity(intent);
    }
}
