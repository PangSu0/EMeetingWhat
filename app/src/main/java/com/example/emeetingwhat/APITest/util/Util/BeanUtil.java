package com.example.emeetingwhat.APITest.util.Util;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.Set;

import static and.bfop.kftc.com.useorgsampleapprenewal.util.StringUtil.defaultString;

/**
 * java 객체 핸들링 관련 유틸 클래스
 *
 * Created by LeeHyeonJae on 2017-06-27.
 */
public class BeanUtil {

    /**
     * Gson 파서 초기화
     *
     *  - json beautify 처리 포함
     *  - 변환후 일부 특수문자들이 유니코드화 되는 것을 막기 위해 (Base64의 == 라던가..) html ecsape 처리도 넣어줌.
     */
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    /**
     * 클래스명 리턴
     *
     * @param o
     * @return
     */
    public static String getClassName(Object o){

        if(o == null){
            Log.e("##", "BeanUtil.getClassName(o) > 매개변수 o 가 null 입니다.");
            return "";
        }

        return o.getClass().getSimpleName();
    }

    /**
     * Map의 key, value를 String으로 변환하여, Bundle 객체에 병합시킨다.
     *
     * @param args
     * @param map
     * @return
     */
    public static Bundle putAllMapToBundle(Bundle args, Map<?, ?> map){

        args = (args == null) ? new Bundle() : args; // 없으면 새로 만들고 있으면 그대로 쓴다.
        Set<? extends Map.Entry<?, ?>> eSet = map.entrySet();
        for(Map.Entry e : eSet){
            args.putString(StringUtil.defaultString(e.getKey()), defaultString(e.getValue()));
        }
        return args;
    }
}
