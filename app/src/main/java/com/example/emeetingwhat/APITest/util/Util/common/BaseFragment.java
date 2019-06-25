package com.example.emeetingwhat.APITest.util.Util.common;

import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.emeetingwhat.APITest.util.Util.BeanUtil;
import com.example.emeetingwhat.APITest.util.Util.Constants;
import com.example.emeetingwhat.APITest.util.Util.FragmentUtil;
import com.example.emeetingwhat.APITest.util.Util.MessageUtil;
import com.example.emeetingwhat.APITest.util.Util.StringUtil;

import butterknife.ButterKnife;


/**
 * Fragment 공통 부모 클래스
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 초기화 수행
     *
     * @param view
     */
    public void initBaseFragment(View view){

        // ButterKnife의 전체 view 바인딩 (각 Fragment 내에서 ButterKnife의 Annotation을 사용할 수 있도록 하기 위해)
        ButterKnife.bind(this, view);

        // 버튼을 눌렀을 때 눌린 효과를 표현하기 위한 이벤트 바인딩
        bindButtonOnTouchEffect(view);
    }

    /**
     * 액션바 타이틀 리턴
     *
     * @return
     */
    public String getActionBarTitle(){

        return StringUtil.defaultString(this.getArguments().get(Constants.ACTIONBAR_TITLE));
    }

    /**
     * 뒤로가기 버튼을 눌렀을 때의 기본동작
     */
    public void onBackPressedForFragment(){

        // 원래는 기본 동작으로 그냥 backstack을 호출해 줄 생각이었으나,
        // 일부 Fragment에서는 특정 Fragment로 이동하고, 일부 Fragment는 backstack을 호출하고 하는 것이 일관성이 없고,
        // 루프에 빠질 우려가 있어서 기본동작을 제거하였음
        // this.getActivity().getSupportFragmentManager().popBackStackImmediate(); // backstack 호출

        MessageUtil.showToast(BeanUtil.getClassName(this)+" 에서의 뒤로가기 버튼 동작을 정의해 주십시오.");
    }

    /**
     * 각 화면 내의 버튼을 눌렀을 때, 눌린 모습을 시각적으로 표현해 주기 위해 OnTouch 이벤트 자동 바인딩 처리
     *
     * @param view
     */
    private void bindButtonOnTouchEffect(View view) {

        ViewGroup vg = (ViewGroup)view.findViewWithTag("buttonParent"); // 각 버튼을 포함하는 부모 엘리먼트에 해당 이름의 tag를 주었다.
//        Log.d("##", "@@ vg: "+vg);
        if (vg != null) {
            View v; Button btn;
            for(int i=0; i<vg.getChildCount(); i++){
                v = vg.getChildAt(i);
                if(v instanceof Button){
                    btn = (Button) v;
//                    Log.d("##", "@@ btn: "+v);
                    btn.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return FragmentUtil.onTouchSetColorFilter(v, event);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        Log.d("##", BeanUtil.getClassName(this) + " onAttach() invoked!");
    }

    @Override
    public void onDetach() {

        super.onDetach();
        Log.d("##", BeanUtil.getClassName(this) + " onDetach() invoked!");
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        Log.d("##", BeanUtil.getClassName(this) + " onDestroy() invoked!");
//        MessageUtil.showToast(BeanUtil.getClassName(this) + " onDestroy() invoked!");
    }
}
