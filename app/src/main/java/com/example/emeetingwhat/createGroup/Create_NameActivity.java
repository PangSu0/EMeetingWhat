package com.example.emeetingwhat.createGroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.GroupType;
import com.example.emeetingwhat.R;
import com.example.emeetingwhat.Validator;

public class Create_NameActivity extends AppCompatActivity {

    private Button btn2Next;
    private Button btn2Prev;
    private EditText groupName;
    String groupNameInput;

    GroupDetailData groupDetailData = new GroupDetailData();

    String bankName;
    int paymentDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_name);

        final RadioButton individual = (RadioButton) findViewById(R.id.radioIndividual);
        final RadioButton group = (RadioButton) findViewById(R.id.radioGroup);

        btn2Next = (Button)findViewById(R.id.btn2Next);
        btn2Prev = (Button)findViewById(R.id.btn2Prev);
        groupName = findViewById(R.id.et_GroupName);

        Bundle valuesFromAccountActivity = getIntent().getExtras();

        if (valuesFromAccountActivity != null) {
            bankName = valuesFromAccountActivity.getString("selectedBankName");
            paymentDay = valuesFromAccountActivity.getInt("selectedPaymentDay");
            //The key argument here must match that used in the other activity
        }

        btn2Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // groupName validation check
                if (Validator.isEmpty(groupName)) {
                    Toast.makeText(getApplicationContext(), "그룹명을 입력하세요",Toast.LENGTH_LONG).show();
                } else {
                    // radioButton validation check
                    if(individual.isChecked()) {
                        Intent intent = new Intent(Create_NameActivity.this, Create_DetailsActivity.class);
                        groupNameInput = groupName.getText().toString();
                        intent.putExtra("ValueGroupName", groupNameInput);
                        intent.putExtra("selectedBankName", bankName);
                        intent.putExtra("selectedBankName", paymentDay);
                        intent.putExtra("selectedGroupType", GroupType.Individual.name());

                        // 확인용
                        Toast.makeText(getApplicationContext(), "개별 수령 선택",Toast.LENGTH_LONG).show();

                        // 개인 수령일 때: 사용자가 입력한 모임명과 모임 유형을 Name으로 세팅한다.
//                        groupDetailData.setName(groupNameInput);
//                        groupDetailData.setGroupType(GroupType.Individual.name());

                        startActivity(intent);
                        finish();
                    } else if (group.isChecked()){
                        // TODO: 그룹을 선택했을 때 다른  activity로 넘겨준다. (Create_DetailsActivity 자리에 넣어줌)
                        Intent intent = new Intent(Create_NameActivity.this, Create_DetailsActivity.class);

                        groupNameInput = groupName.getText().toString();
                        intent.putExtra("ValueGroupName", groupNameInput);
                        Toast.makeText(getApplicationContext(), "공동 사용 선택",Toast.LENGTH_LONG).show();

                        // 공동 사용일 때: 사용자가 입력한 모임명과 모임 유형을 Name으로 세팅한다.
//                        GroupDetailData groupDetailData = new GroupDetailData();
//                        groupDetailData.setName(groupNameInput);
//                        groupDetailData.setGroupType(GroupType.Group.name());

                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "모임 타입을 선택하세요.",Toast.LENGTH_LONG).show();
                    }
                }

//                // 만약에 라디오 버튼이 each이고, 모임 이름이 null이 아니면
//                if (each.isChecked() && groupName != null) {
//                    Intent intent = new Intent(Create_NameActivity.this, Create_DetailsActivity.class);
//                    groupNameInput = groupName.getText().toString();
//                    intent.putExtra("ValueGroupName", groupNameInput);
//                    startActivity(intent);
//                    finish();
//                    // 만약에 라디오 버튼이 each이고, 모임 이름이 null이 아니면
//                } else if (group.isChecked() && groupName != null) {
//                    // TODO: 그룹을 선택했을 때 다른  activity로 넘겨준다.
//                    Intent intent = new Intent(Create_NameActivity.this, MainPageActivity.class);
//                    startActivity(intent);
//                } else
//                {
//                    Toast.makeText(getApplicationContext(), "그룹명이 입력되지 않았거나 그룹 유형이 선택되지 않았습니다. " +
//                            "다시 확인하세요.",Toast.LENGTH_LONG).show();
//                }
            }
        });

        // Prev 버튼을 눌렀을 때
        btn2Prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCreateAccount();
            }
        });
    }

    private void openCreateAccount() {
        Intent intent = new Intent(this, Create_AccountActivity.class);
        startActivity(intent);
    }
}
