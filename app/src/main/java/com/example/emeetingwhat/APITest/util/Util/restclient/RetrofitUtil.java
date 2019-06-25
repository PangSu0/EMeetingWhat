package com.example.emeetingwhat.APITest.util.Util.restclient;


import androidx.fragment.app.FragmentActivity;

import com.example.emeetingwhat.APITest.util.Util.BeanUtil;
import com.example.emeetingwhat.APITest.util.Util.FragmentUtil;

import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Retrofit 관련 유틸 클래스
 *
 * Created by LeeHyeonJae on 2017-07-07.
 */
public class RetrofitUtil {

    /**
     * retrofit의 Call 객체를 매개변수로 받아서 비동기 호출을 수행하고, 결과값을 받아서 새 DialogFragment 에 출력해 준다.
     *
     * @param call
     * @param activity
     */
    public static void callAsync(Call<Map> call, final FragmentActivity activity) {

        // retrofit 비동기 호출 (동기호출시 NetworkOnMainThreadException 발생)
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(Call<Map> call, Response<Map> response) {

                // API 호출 결과 출력
                FragmentUtil.createResultFragmentAndShowResult(activity, BeanUtil.GSON.toJson(response.body()));
            }
            @Override
            public void onFailure(Call<Map> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

}
