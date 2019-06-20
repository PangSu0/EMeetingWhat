package com.example.emeetingwhat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;

public class SplashActivity extends Activity {
    private ISessionCallback callback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);

        callback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                goToMainActivity();
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                redirectToLoginActivity();
            }
        };

        Session.getCurrentSession().addCallback(callback);
        findViewById(R.id.splash).postDelayed(() -> {
            if (!Session.getCurrentSession().checkAndImplicitOpen()) {
                redirectToLoginActivity();
            }
        }, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void redirectToLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
