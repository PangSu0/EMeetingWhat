package com.example.emeetingwhat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }
    public void onClick(View v)
    {
        Intent intent;
        switch (v.getId())
        {
            case R.id.btnTest:
                intent = new Intent(getApplicationContext(), Step5_1_SelectTextActivity.class);
                startActivity(intent);
                break;
        }
    }
}
