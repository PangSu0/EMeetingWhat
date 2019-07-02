package com.example.emeetingwhat;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity  {
    private WebView mWebView;
    private String myUrl = "https://testapi.open-platform.or.kr/oauth/2.0/authorize2?response_type=code&client_id=l7xxcd20e3180c184c489be47d5abab0fbab&redirect_uri=http://localhost:8880/html/callback.html&scope=login%20inquiry%20transfer"; // 접속 URL (내장HTML의 경우 왼쪽과 같이 쓰고 아니면 걍 URL)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mWebView =  findViewById(R.id.webViewTest);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(myUrl); // 접속 URL
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClientClass());

        //getUserInfo();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            view.loadUrl(url);
            return true;
        }
    }
    public void getUserInfo() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://testapi.open-platform.or.kr/oauth/2.0/authorize2?response_type=code&client_id=l7xxcd20e3180c184c489be47d5abab0fbab&redirect_uri=http://localhost:8880/html/callback.html&scope=login%20inquiry%20transfer")
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();

            }
        });
    }
}
