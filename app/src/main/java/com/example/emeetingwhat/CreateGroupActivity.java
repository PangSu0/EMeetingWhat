package com.example.emeetingwhat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CreateGroupActivity extends AppCompatActivity {

    private Button btnNext;
    private Button btnPrev;
    private Button btnLogout;
    public EditText groupName;
    String groupNameInput;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputgroupname);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroupType);
        btnNext = (Button)findViewById(R.id.btnNext);
        btnPrev = (Button)findViewById(R.id.btnPrev);
        btnLogout = (Button)findViewById(R.id.btnLogout);
        groupName = findViewById(R.id.inputGroupName);

        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CreateGroupActivity.this, Step3Activity.class);
                groupNameInput = groupName.getText().toString();
                intent.putExtra("ValueGroupName", groupNameInput);
                startActivity(intent);
                finish();
            }
        });

//        btnLogout.setOnClickListener(new View.onClickListener() {
//           public void onClick(View v) {
//
//           }
//        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GoBackToGroupList();
            }
        });
    }

    private void GoBackToGroupList() {
        Intent intent = new Intent(this, Grouplist.class);
        startActivity(intent);
    }


}
