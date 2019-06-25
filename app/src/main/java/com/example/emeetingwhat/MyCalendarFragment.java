package com.example.emeetingwhat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.response.model.UserProfile;

public class MyCalendarFragment extends Fragment {
    public  final UserProfile userProfile = UserProfile.loadFromCache();
    private TextView nickName;
    public MyCalendarFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycalendar_fragment, container, false);
        nickName = (TextView) view.findViewById(R.id.textView_nickname);
        nickName.setText(userProfile.getNickname());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
