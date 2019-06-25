package com.example.emeetingwhat.APITest.util.Util.APICall;

import android.bfop.kftc.com.useorgsampleapprenewal.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.emeetingwhat.APITest.util.Util.App;
import com.example.emeetingwhat.APITest.util.Util.Constants;
import com.example.emeetingwhat.APITest.util.Util.FragmentUtil;
import com.example.emeetingwhat.APITest.util.Util.StringUtil;
import com.example.emeetingwhat.APITest.util.Util.common.BaseFragment;
import com.example.emeetingwhat.APITest.util.Util.restclient.RetrofitCustomAdapter;
import com.example.emeetingwhat.APITest.util.Util.restclient.RetrofitUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedHashMap;
import java.util.Map;

import and.bfop.kftc.com.useorgsampleapprenewal.App;
import and.bfop.kftc.com.useorgsampleapprenewal.eventbus.FragmentInitEvent;
import and.bfop.kftc.com.useorgsampleapprenewal.layout.common.BaseFragment;
import and.bfop.kftc.com.useorgsampleapprenewal.restclient.RetrofitCustomAdapter;
import and.bfop.kftc.com.useorgsampleapprenewal.restclient.RetrofitUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.Constants;
import and.bfop.kftc.com.useorgsampleapprenewal.util.FragmentUtil;
import and.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil;
import butterknife.OnClick;


/**
 * 잔액조회 API호출 Fragment
 */
public class APICallPageBalanceInquiryFragment extends BaseFragment {

    /**
     * NameSpace
     *  - 입력폼의 값들을 SharedPreferences에 저장할 때, 각 업무별로 별도로 값을 저장하기 위해서 임의의 이름을 구분자로 줌
     */
    private static final String NS = "ABI";

    EditText etToken; // 토큰
    EditText etFintechUseNum; // 핀테크일련번호
    EditText etTranDtime; // 거래일시

    //===================================== Fragment Lifecycle Callbacks - start =====================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_apicall_balance_inquiry, container, false);
//        super.initBaseFragment(view); // BaseFragment 초기화 수행
//
//        // Fragment 초기화 이벤트를 EventBus를 통해서 post (액션바 햄버거메뉴와 뒤로가기 화살표버튼을 상호 교체하기 위해서 수행)
//        EventBus.getDefault().post(new FragmentInitEvent(this.getClass(), true));

        // 입력된 토큰, 핀테크 넘버 등 받아오기
        etToken = (EditText)view.findViewById(R.id.etToken);
        etFintechUseNum = (EditText)view.findViewById(R.id.etFintechUseNum);
        etTranDtime = (EditText)view.findViewById(R.id.etTranDtime);

        loadInputValues();

        return view;
    }
    //===================================== Fragment Lifecycle Callbacks - end =======================================

    /**
     * 잔액조회
     */
    private void accountBalance(){

        String token = Constants.TOKEN_PREFIX + etToken.getText().toString();

        Map params = new LinkedHashMap<>();
        params.put("fintech_use_num", etFintechUseNum.getText().toString());
        params.put("tran_dtime", etTranDtime.getText().toString());

        RetrofitUtil.callAsync(RetrofitCustomAdapter.getInstance().accountBalance(token, params), getActivity()); // rest client 호출

        saveInputValues();
    }

    /**
     * 입력값을 SharedPreferences 에 저장
     */
    private void saveInputValues() {

        SharedPreferences.Editor editor = App.getPref().edit();
        String es = App.getEnvSuffix(App.getEnv());
        editor.putString(NS + "token" + es, etToken.getText().toString());
        editor.putString(NS + "fintech_use_num" + es, etFintechUseNum.getText().toString());
        editor.putString(NS + "tran_dtime" + es, etTranDtime.getText().toString());
        editor.apply();
    }

    /**
     * SharedPreferences 에 저장된 입력값들을 UI에 채워 넣음
     */
    private void loadInputValues(){

        String es = App.getEnvSuffix(App.getEnv());
        etToken.setText(StringUtil.getPropString(NS + "token" + es));
        etFintechUseNum.setText(StringUtil.getPropString(NS + "fintech_use_num" + es));
        etTranDtime.setText(StringUtil.getPropString(NS + "tran_dtime" + es));
    }

    /**
     * 버튼 onclick 이벤트핸들러
     *
     * @param v
     */
//    @OnClick(R.id.btnInqrBlnc)
//    public void onClick(View v) {
//
//        switch(v.getId()){
//            case R.id.btnInqrBlnc:
//                accountBalance();
//                break;
//            default:
//                break;
//        }
//    }

    /**
     * 뒤로가기 버튼을 눌렀을 때의 동작
     */
//    @Override
//    public void onBackPressedForFragment() {
//
//        FragmentUtil.replaceNewFragment(APICallMenuFragment.class);
//    }

}
