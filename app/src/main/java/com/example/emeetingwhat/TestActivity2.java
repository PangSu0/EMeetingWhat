package com.example.emeetingwhat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        abc();
    }

    private void abc()
    {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://testapi.open-platform.or.kr/user/me?user_seq_no=1100035166")
                .get()
                .header("Authorization", "Bearer 69977bd0-6f0a-4588-a100-996057c684e2")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("1", string);
            }
        });
    }
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId())
        {
            case R.id.btnTest:
                intent = new Intent(getApplicationContext(), TestActivity.class);
                break;
            case R.id.btnTest2:
                intent = new Intent(getApplicationContext(), TestActivity3.class);
                break;
        }
        startActivity(intent);
    }
}
