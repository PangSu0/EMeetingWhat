package com.example.emeetingwhat.createGroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.Data.AccountDetailData;
import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.MainPageActivity;
import com.example.emeetingwhat.R;

public class Create_FriendsActivity extends AppCompatActivity {

    private Button btn4Prev;
    private Button btn4Next;

    String bankName;
    int paymentDay;
    String groupName;
    String groupType;
    int targetMoney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_friends);


        // 앞, 뒤로가기 버튼
        btn4Prev = (Button)findViewById(R.id.btn4Prev);
        btn4Next = (Button)findViewById(R.id.btn4Next);

        Bundle valuesFromDetailActivity = getIntent().getExtras();

        if (valuesFromDetailActivity != null) {
            bankName = valuesFromDetailActivity.getString("selectedBankName");
            paymentDay = valuesFromDetailActivity.getInt("selectedPaymentDay");
            groupName = valuesFromDetailActivity.getString("ValueGroupName");
            groupType = valuesFromDetailActivity.getString("selectedGroupType");
            targetMoney = valuesFromDetailActivity.getInt("GroupMoney");
        }

        GroupDetailData groupDetailData = new GroupDetailData();
        AccountDetailData accountDetailData = new AccountDetailData();

        groupDetailData.setName(groupName);
        groupDetailData.setPaymentDay(paymentDay);
        groupDetailData.setTargetAmount(targetMoney);
        groupDetailData.setGroupType(groupType);
        accountDetailData.setBankName(bankName);


        btn4Prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Create_FriendsActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        // Prev 버튼을 눌렀을 때
        btn4Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Create_FriendsActivity.this, Create_DetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
