package com.example.emeetingwhat;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.Data.AccountDetailData;
import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.createGroup.CreateDetailsActivity;
import com.example.emeetingwhat.createGroup.CreateNameActivity;

public class Step3_2_TargetAmountActivity extends AppCompatActivity {
    EditText editName;
    Intent intent_GroupFromPrevious;
    GroupDetailData groupDataFromPrev;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3_2_target_amount);

        intent_GroupFromPrevious = getIntent();
        groupDataFromPrev =  (GroupDetailData)intent_GroupFromPrevious.getSerializableExtra("groupDetailData");

        setEnterKey();
    }
    public  void setEnterKey()
    {
        editName = findViewById(R.id.editTextStep3_2_EditName);
        editName.setOnKeyListener((v, keyCode, event) -> {
            //Enter key Action
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                nextPage();
                return true;
            }
            return false;
        });
    }
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnStep3_2_Prev:
                onBackPressed();
                break;
            case R.id.btnStep3_2_Next:

                nextPage();
                break;
        }
    }

    public  void nextPage()
    {
        Intent intent = new Intent(getApplicationContext(), Step4_2_InviteMemberActivity.class);
        editName = findViewById(R.id.editTextStep3_2_EditName);
        groupDataFromPrev.setTargetAmount(Integer.parseInt(editName.getText().toString()));
        intent.putExtra("groupDetailData", groupDataFromPrev);
        startActivity(intent);
    }
}
