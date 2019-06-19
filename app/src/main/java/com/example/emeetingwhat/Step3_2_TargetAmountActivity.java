package com.example.emeetingwhat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Step3_2_TargetAmountActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3_2_target_amount);

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
        startActivity(intent);
    }
}
