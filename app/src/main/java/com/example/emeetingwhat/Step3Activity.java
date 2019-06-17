package com.example.emeetingwhat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Step3Activity extends AppCompatActivity {

    TextView txt_GroupName;
    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3);

        txt_GroupName = findViewById(R.id.txtGroupName);
        groupName = getIntent().getExtras().getString("ValueGroupName");
        txt_GroupName.setText(groupName);
    }
}
