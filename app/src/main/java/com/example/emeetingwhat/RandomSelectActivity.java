package com.example.emeetingwhat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class RandomSelectActivity extends AppCompatActivity {
    ArrayList<String> mTempName;
    int mPeopleMax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_select);

        if(mTempName != null) {
            mTempName.clear();
            mTempName = null;
        }
        mTempName = new ArrayList<>();
        mTempName.add("은지");
        mTempName.add("수미");
        mTempName.add("창수");
        for (int i = 0; i < 7; i++) {
            mTempName.add("태엽" + (i + 1));
        }

    }
    public void onClick(View v) {

        switch (v.getId()) {
            case  R.id.btnStart:
                startLadderGame();
                break;
        }
    }
    public  void startLadderGame()
    {
        mPeopleMax = mTempName.size();

        Intent intent = new Intent(getApplicationContext(), Step5_2_RandomLadderActivity.class);
        intent.putExtra("PeopleMax", mPeopleMax);

        for (int i = 0; i < mPeopleMax; i++) {
            intent.putExtra("Name" + i, mTempName.get(i));
            startActivity(intent);
        }
    }
}