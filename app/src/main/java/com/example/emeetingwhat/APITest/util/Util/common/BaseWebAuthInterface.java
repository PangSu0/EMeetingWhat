package com.example.emeetingwhat.APITest.util.Util.common;

import java.util.Map;

/**
 * authcode를 발급받는 일부 Fragment에서, callback page redirection으로 전달받는 authcode 정보를 intercept 하여,
 * token 발급 프로세스까지 이어가도록 하기 위해서 정의한 interface.
 * (이것은 업무상 필수는 아니며, authcode 발급부터 token 발급까지의 데이터를 눈으로 잘 볼 수 있게 하기 위한 가이드의 성격을 지닌다.)
 *
 * Created by LeeHyeonJae on 2017-06-27.
 */
public interface BaseWebAuthInterface {

    public void onAuthCodeResponse(Map<String, Object> pMap);
}
