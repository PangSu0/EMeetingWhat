package com.example.emeetingwhat.createGroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.R;

public class Create_DetailsActivity extends AppCompatActivity {

    private Button btn3Next;
    private Button btn3Prev;
    private EditText et_amount;

    TextView txt_GroupName;
    String groupName;
    int targetAmount;

    String bankName;
    int paymentDay;
    String groupType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_details);

        // 앞, 뒤로 가기 버튼
        btn3Next = (Button)findViewById(R.id.btn3Next);
        btn3Prev = (Button)findViewById(R.id.btn3Prev);

        // 목표 금액
        et_amount = (EditText)findViewById(R.id.et_Amount);

        // 모임 기간 설정
//        btnStartDate = (Button)findViewById(R.id.btnStartDate);
//        btnEndDate = (Button)findViewById(R.id.btnEndDate);

//        btnStartDate.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//            }
//        });

       Bundle valuesFromNameActivity = getIntent().getExtras();

        if (valuesFromNameActivity != null) {
            bankName = valuesFromNameActivity.getString("selectedBankName");
            paymentDay = valuesFromNameActivity.getInt("selectedPaymentDay");
            groupName = valuesFromNameActivity.getString("ValueGroupName");
            groupType = valuesFromNameActivity.getString("selectedGroupType");
        }

        txt_GroupName = findViewById(R.id.txt3GroupName);
        txt_GroupName.setText(groupName);

        btn3Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: 친구 초대 페이지로 이동 (수정 필요)
                Intent intent = new Intent(Create_DetailsActivity.this, Create_FriendsActivity.class);
                targetAmount = Integer.parseInt(et_amount.getText().toString());
                intent.putExtra("ValueGroupName", groupName); // name으로부터
                intent.putExtra("selectedGroupType", groupType); // name으로부터
                intent.putExtra("selectedBankName", bankName); // account로부터
                intent.putExtra("selectedBankName", paymentDay); // account로부터
                intent.putExtra("GroupMoney", targetAmount); // this

                startActivity(intent);
            }
        });

        // Prev 버튼을 눌렀을 때
        btn3Prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Create_DetailsActivity.this, Create_NameActivity.class);
                startActivity(intent);
            }
        });
    }
}
