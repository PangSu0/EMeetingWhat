package com.example.emeetingwhat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.Data.GroupDetailData;

public class Step4_2_InviteMemberActivity extends AppCompatActivity {
    int mSelectRadioButton;
    Intent intent_GroupFromPrevious;
    GroupDetailData groupDataFromPrev;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent_GroupFromPrevious = getIntent();
        groupDataFromPrev =  (GroupDetailData)intent_GroupFromPrevious.getSerializableExtra("groupDetailData");

        setContentView(R.layout.activity_step4_2_invite_member);
    }

    public  void onClick(View v)
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
        intent.putExtra("groupDetailData", groupDataFromPrev);
        startActivity(intent);
    }
}
