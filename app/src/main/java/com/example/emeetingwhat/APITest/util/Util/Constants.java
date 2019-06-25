package com.example.emeetingwhat.APITest.util.Util;

/**
 * Created by LeeHyeonJae on 2017-02-21.
 */
public interface Constants {

    String ACTIONBAR_TITLE = "ACTIONBAR_TITLE";

    /**
     * SharedPreferences 설정 이름
     */
    String APP_SETTING = "APP_SETTING"; // 이용기관샘플앱에서 사용하는 SharedPreference 의 namespace

    /**
     * 호출서버 환경의 선택지
     */
    String ENV_TEST = "TEST"; // 테스트서버
    String ENV_PRD = "PRD"; // 운영서버

    String ENV_NAME_TEST = "테스트서버";
    String ENV_NAME_PRD = "운영서버";

    /**
     * 기본 호출서버 환경
     *      - SharedPreferences 에 ENV가 저장되어 있지 않을 경우 사용할 기본값.
     */
    String ENV_DEFAULT = ENV_TEST ;

    /**
     * API 호출 BASE URI
     */
    String API_BASE_URI_TEST = "https://testapi.open-platform.or.kr"; // 테스트
    String API_BASE_URI_PRD = "https://openapi.open-platform.or.kr"; // 운영

    //================================================== 사용자정의 설정 기본값 (공통) - start ==================================================
    // 기본 값은 여기에 정의된 것을 사용하지만, SharedPreferences 를 사용하여 수정한 정보를 영구적으로 단말에 저장할 수 있다.
    /**
     * 오픈플랫폼에서 발급한 이용기관의 앱의 Key 기본값
     */
    String APP_KEY_TEST = "l7xx2387628cf42a4845b221f88029ea5a0a"; // kftcedu00
    String APP_KEY_PRD = "l7xx2387628cf42a4845b221f88029ea5a0a"; // kftcedu00

    /**
     * 오픈플랫폼에서 발급한 이용기관의 앱 Client Secret 기본값
     */
    String APP_SECRET_TEST = "75518cc321b841059889d0e40f4d1f0b"; // kftcedu00
    String APP_SECRET_PRD = "75518cc321b841059889d0e40f4d1f0b"; // kftcedu00

    /**
     * 오픈플랫폼에 등록된 이용기관 앱의 Redirect URL (웹 방식 호출의 경우) 기본값
     */
    String WEB_CALLBACK_URL_TEST = "https://developers.open-platform.or.kr/tpt/test/getAuthCode"; // kftcedu00
    String WEB_CALLBACK_URL_PRD = "http://localhost:8090/openapi/test/callback.html"; // kftcedu00

    /**
     * 조회서비스 or 출금서비스 범위 지정자 기본값
     */
    String SCOPE_TEST = "inquiry"; // 테스트
    String SCOPE_PRD = "inquiry"; // 운영
    //================================================== 사용자정의 설정 기본값 (공통) - end ====================================================


    //=========================================== 사용자정의 설정 기본값 (사용자인증 개선버전) - start ==========================================
    // ANW (AuthNewWeb) => 사용자인증 개선버전 의 이니셜
    /**
     * scope
     */
    String ANW_SCOPE_TEST = "login transfer"; // 테스트
    String ANW_SCOPE_PRD = "login transfer"; // 운영

    /**
     * client_info
     */
    String ANW_CLIENT_INFO_TEST = "[test] whatever you want";
    String ANW_CLIENT_INFO_PRD = "[prd] whatever you want";

    /**
     * bg_color
     */
    String ANW_BG_COLOR_TEST = "#FBEFF2";
    String ANW_BG_COLOR_PRD = "#FBEFF2";

    /**
     * txt_color
     */
    String ANW_TXT_COLOR_TEST = "#088A08";
    String ANW_TXT_COLOR_PRD = "#088A08";

    /**
     * btn1_color
     */
    String ANW_BTN1_COLOR_TEST = "#FF8000";
    String ANW_BTN1_COLOR_PRD = "#FF8000";

    /**
     * btn2_color
     */
    String ANW_BTN2_COLOR_TEST = "#F3E2A9";
    String ANW_BTN2_COLOR_PRD = "#F3E2A9";


    /**
     * Kftc-Bfop-UserSeqNo
     */
    String ANW_USER_SEQ_NO_TEST = "0000000000";
    String ANW_USER_SEQ_NO_PRD = "0000000000";

    /**
     * Kftc-Bfop-UserCIf
     */
    String ANW_USER_CI_TEST = "abcd1234";
    String ANW_USER_CI_PRD = "abcd1234";

    /**
     * Kftc-Bfop-UserName
     */
    String ANW_USER_NAME_TEST = "김핀텍";
    String ANW_USER_NAME_PRD = "김핀텍";

    /**
     * Kftc-Bfop-UserInfo
     */
    String ANW_USER_INFO_TEST = "19871212";
    String ANW_USER_INFO_PRD = "19871212";

    /**
     * Kftc-Bfop-UserCellNo
     */
    String ANW_USER_CELL_NO_TEST = "01077778888";
    String ANW_USER_CELL_NO_PRD = "01077778888";

    /**
     * Kftc-Bfop-UserEmail
     */
    String ANW_USER_EMAIL_TEST = "abc@inter.net";
    String ANW_USER_EMAIL_PRD = "abc@inter.net";

    /**
     * Kftc-Bfop-AccessToken
     */
    String ANW_ACCESS_TOKEN_TEST = "abcd1234";
    String ANW_ACCESS_TOKEN_PRD = "abcd1234";

    //=========================================== 사용자정의 설정 기본값 (사용자인증 개선버전) - end ============================================

    /**
     * WebView가 있는 Fragment의 주소표시줄 옆의 버튼명
     */
    String BTN_NAME_FOLD = "접음";
    String BTN_NAME_UNFOLD = "펼침";

    /**
     * API 호출시 토큰 전달에 사용하는 prefix
     */
    String TOKEN_PREFIX = "Bearer ";

    /**
     * 데이터 전달시 사용하는 키값 모음
     */
    String RSP_JSON = "RSP_JSON";
}