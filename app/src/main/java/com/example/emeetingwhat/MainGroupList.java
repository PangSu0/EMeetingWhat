package com.example.emeetingwhat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainGroupList extends AppCompatActivity
{
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.main_group_list);

        String[] items = {"답십리 계모임", "화곡 계모임", "강남 계모임"};
        ListAdapter adapter = new ImageAdapter(this, items);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                        String item = String.valueOf(parent.getItemAtPosition(i));
                        Toast.makeText(MainGroupList.this, item, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
