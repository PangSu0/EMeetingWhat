package com.example.emeetingwhat;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.usermgmt.response.model.UserProfile;

public class MyCalendarActivity extends AppCompatActivity {
    private TextView txt_nickName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_calendar);
        txt_nickName = (TextView) findViewById(R.id.textView_nickname);
        final UserProfile userProfile = UserProfile.loadFromCache();
        txt_nickName.setText(userProfile.getNickname());
    }
}