package com.example.emeetingwhat.createGroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.Data.AccountDetailData;
import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.GroupType;
import com.example.emeetingwhat.R;
import com.example.emeetingwhat.Validator;

import java.security.acl.Group;

public class Create_NameActivity extends AppCompatActivity {

    private Button btn2Next;
    private Button btn2Prev;
    private EditText groupName;
    private TextView groupNameInfo;
    String groupNameInput;

    GroupDetailData groupDetailData = new GroupDetailData();
    AccountDetailData accountDetailData = new AccountDetailData();

    Intent intent_GroupFromPrevious;
    Intent intent_AccountFromPrevious;

    GroupDetailData groupDataFromPrev;
    AccountDetailData accountDataFromPrev;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_name);

        final RadioButton individual = (RadioButton) findViewById(R.id.radioIndividual);
        final RadioButton group = (RadioButton) findViewById(R.id.radioGroup);

        btn2Next = (Button)findViewById(R.id.btn2Next);
        btn2Prev = (Button)findViewById(R.id.btn2Prev);
        groupName = findViewById(R.id.et_GroupName);
        groupNameInfo = findViewById(R.id.tv_GroupNameInfo);

        intent_GroupFromPrevious = getIntent();
        intent_AccountFromPrevious = getIntent();

        groupDataFromPrev =  (GroupDetailData)intent_GroupFromPrevious.getSerializableExtra("groupDetailData");
        accountDataFromPrev =  (AccountDetailData)intent_AccountFromPrevious.getSerializableExtra("accountDetailData");

        btn2Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // groupName validation check
                if (Validator.isEmpty(groupName)) {
                    Toast.makeText(getApplicationContext(), "그룹명을 입력하세요",Toast.LENGTH_LONG).show();
                    groupNameInfo.setText("그룹명을 입력하세요");
                } else {
                    // radioButton validation check
                    if(individual.isChecked()) {
                        groupNameInfo.setText("");
                        Intent intent = new Intent(Create_NameActivity.this, Create_DetailsActivity.class);
                        groupNameInput = groupName.getText().toString();

                        // 개인 수령일 때: 사용자가 입력한 모임명과 모임 유형을 Name으로 세팅한다.
                        groupDetailData.setName(groupNameInput);
                        groupDetailData.setGroupType(GroupType.Individual.name());
                        groupDetailData.setPaymentDay(groupDataFromPrev.getPaymentDay());
                        accountDetailData.setBankName(accountDataFromPrev.getBankName());

                        intent.putExtra("groupDetailData", groupDetailData);
                        intent.putExtra("accountDetailData", accountDataFromPrev);

                        startActivity(intent);
                        finish();
                    } else if (group.isChecked()){
                        groupNameInfo.setText("");
                        // TODO: 그룹을 선택했을 때 다른  activity로 넘겨준다. (Create_DetailsActivity 자리에 넣어줌)
                        Intent intent = new Intent(Create_NameActivity.this, Create_DetailsActivity.class);

                        groupNameInput = groupName.getText().toString();

                        Toast.makeText(getApplicationContext(), "공동 사용 선택",Toast.LENGTH_LONG).show();

                        groupDetailData.setName(groupNameInput);
                        groupDetailData.setGroupType(GroupType.Group.name());
                        groupDetailData.setPaymentDay(groupDataFromPrev.getPaymentDay());
                        accountDetailData.setBankName(accountDataFromPrev.getBankName());

                        intent.putExtra("groupDetailData", groupDetailData);
                        intent.putExtra("accountDetailData", accountDataFromPrev);

                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "모임 타입을 선택하세요.",Toast.LENGTH_LONG).show();
                    }
                }
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
