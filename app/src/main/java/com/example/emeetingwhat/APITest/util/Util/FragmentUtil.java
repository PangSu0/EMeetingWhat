package com.example.emeetingwhat.APITest.util.Util;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedHashMap;
import java.util.Map;

import and.bfop.kftc.com.useorgsampleapprenewal.App;
import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentReplaceEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.MainFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.apicall.APICallMenuFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.apicall.APICallPageBalanceInquiryFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.apicall.APICallPageDeposit2TransferFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.apicall.APICallPageDepositTransferFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.apicall.APICallPageRealNameInquiryFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.apicall.APICallPageTransactionRecordInquiryFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.apicall.APICallPageUserInfoInquiryFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.apicall.APICallPageWithdrawTransferFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb.AuthNewWebCommonWebViewFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb.AuthNewWebMenuFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb.AuthNewWebPageAuthorize2Case1Fragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb.AuthNewWebPageAuthorize2Case2Fragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb.AuthNewWebPageAuthorize2Case3Fragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.authnewweb.AuthNewWebPageAuthorize2TabFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.autholdweb.AuthOldWebMenuFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.autholdweb.AuthOldWebPageAuthorizeAccountFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.autholdweb.AuthOldWebPageAuthorizeFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.autholdweb.AuthOldWebPageRegisterAccountFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.APICallResultFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.BaseFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.OSSLicensePageFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.TokenRequestFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.settings.SettingsFragment;

/**
 * Fragment 관련 유틸 클래스
 *
 * Created by LeeHyeonJae on 2017-06-30.
 */
public class FragmentUtil {

    public static final Map<Class<? extends BaseFragment>, String> FRAGMENT_NAME_MAP = getFragmentNameMap();

    /**
     * Fragment 클래스와 해당 Fragment의 페이지명을 정의하여 Map으로 리턴한다.
     *
     * @return
     */
    public static Map<Class<? extends BaseFragment>, String> getFragmentNameMap(){

        Map<Class<? extends BaseFragment>, String> map = new LinkedHashMap<>();
        map.put(MainFragment.class, "이용기관 샘플앱 메인");
        map.put(AuthNewWebMenuFragment.class, "사용자인증 개선버전");
        map.put(AuthOldWebMenuFragment.class, "사용자인증 기존버전 (웹 방식)");
        map.put(APICallMenuFragment.class, "API 거래기능");
        map.put(APICallPageBalanceInquiryFragment.class, "잔액조회");
        map.put(APICallPageRealNameInquiryFragment.class, "계좌실명조회");
        map.put(APICallPageTransactionRecordInquiryFragment.class, "거래내역조회");
        map.put(APICallPageWithdrawTransferFragment.class, "출금이체");
        map.put(APICallPageDepositTransferFragment.class, "입금이체(핀테크이용번호)");
        map.put(APICallPageDeposit2TransferFragment.class, "입금이체(계좌번호)");
        map.put(APICallPageUserInfoInquiryFragment.class, "사용자정보조회 API");
        map.put(SettingsFragment.class, "설정");
        map.put(AuthNewWebPageAuthorize2TabFragment.class, "사용자인증 개선버전");
        map.put(AuthNewWebPageAuthorize2Case1Fragment.class, "사용자인증 개선버전 (Case1)");
        map.put(AuthNewWebPageAuthorize2Case2Fragment.class, "사용자인증 개선버전 (Case2)");
        map.put(AuthNewWebPageAuthorize2Case3Fragment.class, "사용자인증 개선버전 (Case3)");
        map.put(AuthNewWebCommonWebViewFragment.class, "사용자인증 개선버전"); // 어차피 override 되는 값이다.
        map.put(AuthOldWebPageAuthorizeFragment.class, "사용자인증 기존버전 (웹 방식)");
        map.put(AuthOldWebPageRegisterAccountFragment.class, "계좌등록 기존버전 (웹 방식)");
        map.put(AuthOldWebPageAuthorizeAccountFragment.class, "계좌등록확인 기존버전 (웹 방식)");
        map.put(TokenRequestFragment.class, "Token 발급 요청");
        map.put(OSSLicensePageFragment.class, "오픈소스 라이선스");
        return map;
    }

    /**
     * Fragment 클래스를 입력받아 Fragment 명을 리턴한다.
     *
     * @param clazz
     * @return
     */
    public static String getFragmentName(Class<? extends BaseFragment> clazz){

        String name = StringUtil.defaultString(FRAGMENT_NAME_MAP.get(clazz));
        if(StringUtil.isBlank(name)){
            MessageUtil.showToast(BeanUtil.getClassName(clazz)+" 에 대한 Fragment 명을 정의해 주십시오");
        }
        return name;
    }

    /**
     * Fragment를 신규 생성하여 리턴한다. (동적 생성)
     *
     *   - 추가기능으로 액션바의 타이틀을 arguments 에 넣어준다.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends BaseFragment> T newFragment(Class<T> clazz){

        T ret = null;
        try {
            ret = clazz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(ret != null){
            Bundle args = new Bundle();
            args.putString(Constants.ACTIONBAR_TITLE, FragmentUtil.getFragmentName(clazz)); // 액션바 타이틀
            ret.setArguments(args);
        }

        return ret;

    }

    /**
     * 매개변수로 받은 Fragment 클래스를 신규 생성하여 기존 활성화된 Fragment를 신규 생성한 Fragment로 교체하는 요청을 담은 EventBus 메시지를 호출한다.
     *
     * @param clazz
     */
    public static void replaceNewFragment(Class<? extends BaseFragment> clazz){

        replaceFragment(FragmentUtil.newFragment(clazz));
    }

    /**
     * 매개변수로 받은 fragment로 기존 활성화된 fragment를 교체하는 요청을 담은 EventBus 메시지를 호출한다.
     *
     * @param fragment
     */
    public static void replaceFragment(BaseFragment fragment){

        EventBus.getDefault().post(new FragmentReplaceEvent(fragment));
    }

    /**
     * view 하위의 특정 TableLayout의 TableRow를 순회하면서 EditText를 찾고,
     * 각 EditText의 id 문자열로 SharedPreferences(혹은 Constants)를 조회한 결과값을 EditText에 채워 넣는다.
     *
     * @param rootView
     * @param tableLayoutId
     */
    public static void fillSavedDataToForm(View rootView, int tableLayoutId) {

        TableLayout tableLayout = (TableLayout) rootView.findViewById(tableLayoutId);
        TableRow tableRow;
        View tmpView;
        EditText et;
        String id, key, val;
        for(int i=0; i<tableLayout.getChildCount(); i++){
            tableRow = (TableRow)tableLayout.getChildAt(i);
            for(int j=0; j<tableRow.getChildCount(); j++){
                tmpView = tableRow.getChildAt(j);
                if(tmpView instanceof EditText){
                    et = (EditText)tmpView;
                    id = et.getResources().getResourceEntryName(et.getId());
                    key = id.substring(3); // id에서 "et_" 제거
                    val = StringUtil.getPropStringForEnv(key);
                    et.setText(val);
                    Log.d("##", "EditText id:["+id+"], key:["+key+"], val:["+val+"]");
                }
            }
        }
    }

    /**
     * view 하위의 특정 TableLayout의 TableRow를 순회하면서 EditText를 찾고,
     * 각 EditText의 값을, id 문자열을 key로 하여 SharedPreferences 에 저장한다.
     */
    public static void saveFormData(View rootView, int tableLayoutId) {

        SharedPreferences.Editor editor = App.getPref().edit();
        String env = App.getEnv();
        editor.putString("ENV", env);
        String es = App.getEnvSuffix(env);

        TableLayout tableLayout = (TableLayout) rootView.findViewById(tableLayoutId);
        TableRow tableRow;
        View tmpView;
        EditText et;
        String id, key, val;
        for(int i=0; i<tableLayout.getChildCount(); i++){
            tableRow = (TableRow)tableLayout.getChildAt(i);
            for(int j=0; j<tableRow.getChildCount(); j++){
                tmpView = tableRow.getChildAt(j);
                if(tmpView instanceof EditText){
                    et = (EditText)tmpView;
                    id = et.getResources().getResourceEntryName(et.getId());
                    key = id.substring(3); // id에서 "et_" 제거
                    val = StringUtil.getPropStringForEnv(key);
                    editor.putString(key + es, et.getText().toString());
//                    Log.d("##", "EditText id:["+id+"], key:["+key+"], val:["+val+"]");
                }
            }
        }
        editor.apply();
//        MessageUtil.showToast("저장되었습니다.", 1500);
    }

    /**
     * EditText의 값을 리턴한다.
     *
     * @param rootView
     * @param etId
     * @return
     */
    public static String getEtVal(View rootView, int etId){

        return ((EditText)rootView.findViewById(etId)).getText().toString().trim();
    }

    /**
     * 버튼을 눌렀을 때, 누른 표시가 나게 하기 위해서 touch event에 color filter를 걸어주었음.
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean onTouchSetColorFilter(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                v.getBackground().setColorFilter(0xe02D4B64, PorterDuff.Mode.SRC_ATOP); // 파란색에 대한 음영색을 적당히 고름
                v.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                v.getBackground().clearColorFilter();
                v.invalidate();
                break;
            }
        }
        return false;
    }

    /**
     * 새 DialogFragment를 생성하여 API 호출 결과를 출력한다.
     *
     * @param activity
     * @param rspJson
     */
    public static void createResultFragmentAndShowResult(FragmentActivity activity, String rspJson) {

        FragmentManager fm = activity.getSupportFragmentManager();
        APICallResultFragment dialogFragment = new APICallResultFragment();
        Bundle args = new Bundle();
        args.putString(Constants.RSP_JSON, rspJson); // 결과값 전달
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, null);
    }

}
