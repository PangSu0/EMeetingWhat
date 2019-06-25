package com.example.emeetingwhat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.emeetingwhat.common.widget.KakaoToast;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.helper.log.Logger;

import java.util.Map;

public class SignupActivity extends BaseActivity {
    /**
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 me를 호출한다.
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    protected void showSignup() {
        setContentView(R.layout.layout_signup);
        final ExtraUserPropertyLayout extraUserPropertyLayout = findViewById(R.id.extra_user_property);
        Button signupButton = findViewById(R.id.buttonSignup);
        signupButton.setOnClickListener(view -> requestSignUp(extraUserPropertyLayout.getProperties()));
    }

    private void requestSignUp(final Map<String, String> properties) {
        UserManagement.getInstance().requestSignup(new ApiResponseCallback<Long>() {
            @Override
            public void onNotSignedUp() {
            }

            @Override
            public void onSuccess(Long result) {
                requestMe();

            }

            @Override
            public void onFailure(ErrorResult errorResult) {
                final String message = "UsermgmtResponseCallback : failure : " + errorResult;
                com.kakao.util.helper.log.Logger.w(message);
                KakaoToast.makeToast(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
            }
        }, properties);
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() {
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                int result = errorResult.getErrorCode();
                if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                    KakaoToast.makeToast(getApplicationContext(), getString(R.string.error_message_for_service_unavailable), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Logger.e("onSessionClosed");
                redirectLoginActivity();
            }

            @Override
            public void onSuccess(MeV2Response result) {
                if (result.hasSignedUp() == OptionalBoolean.FALSE) {
                    showSignup();
                } else {
                    redirectMainActivity();
                }
            }
        });
    }

    protected void redirectMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
