package com.example.emeetingwhat.openAPI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.R;

public class FetchedDataActivity extends AppCompatActivity {
    Button click;
    public static TextView data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 첫 화면
        setContentView(R.layout.activity_fetchdata);

        click = (Button) findViewById(R.id.button);
        data = (TextView) findViewById(R.id.fetchedData);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchData process = new FetchData();
                process.execute();
            }
        });
    }
}
