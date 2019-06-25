package com.example.emeetingwhat.APITest.util.Util;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import and.bfop.kftc.com.useorgsampleapprenewal.App;

/**
 * Created by LeeHyeonJae on 2017-02-21.
 */
public class StringUtil {

    public static final String EMPTY = "";

    /**
     * NVL 처리
     *
     * @param str
     * @param defaultStr
     * @return
     */
    public static String defaultString(final String str, final String defaultStr) {
        return str == null ? defaultStr : str;
    }

    /**
     * NVL 처리
     *
     * @param str
     * @return
     */
    public static String defaultString(final String str) {
        return str == null ? EMPTY : str;
    }

    /**
     * NVL 처리
     *
     * @param src
     * @param defaultStr
     * @return
     */
    public static String defaultString(Object src, final String defaultStr){
        if(src != null){
            if(src instanceof String){
                return (String)src;
            }else{
                return String.valueOf(src);
            }
        }else{
            return (defaultStr == null) ? EMPTY : defaultStr;
        }
    }

    /**
     * NVL 처리
     *
     * @param src
     * @return
     */
    public static String defaultString(Object src){
        return defaultString(src, null);
    }

    /**
     * 빈 값이면 true 리턴
     *
     * @param cs
     * @return
     */
    public static boolean isBlank(final CharSequence cs){
        int strLen;
        if(cs == null || (strLen = cs.length()) == 0){
            return true;
        }
        for(int i=0; i<strLen; i++){
            if(Character.isWhitespace(cs.charAt(i)) == false){
                return false;
            }
        }
        return true;
    }

    /**
     * 빈 값이면 false 리턴
     *
     * @param cs
     * @return
     */
    public static boolean isNotBlank(final CharSequence cs){
        return !StringUtil.isBlank(cs);
    }

    /**
     * Constants의 정적멤버 이름을 매개변수로 받아서 값을 리턴한다.
     *
     * @param constantsFieldName
     * @return
     */
    public static String getConstantsVal(String constantsFieldName) {

        Field field = null;
        Object value = null;
        try {
            field = Constants.class.getDeclaredField(constantsFieldName);
            field.setAccessible(true);
            value = field.get(null);
        } catch (NoSuchFieldException e) {
            Log.i("##", "Constants 에 해당 필드["+constantsFieldName+"]가 존재하지 않습니다.");

        } catch (IllegalAccessException | SecurityException e) {
            e.printStackTrace();
        }
        String ret = StringUtil.defaultString(value);
        Log.d("##", "ret:" + ret);
        return ret;
    }

    /**
     * 프로퍼티 키를 입력받아서
     *  (1) SharedPreferences 에서 해당 값을 찾고,
     *  (2) 없을 경우 Constants에서 해당 값을 찾아 리턴한다.
     *
     * 주의! : (2)의 경우 필드명 reflection을 사용하기 때문에, propKey는 Constants의 필드명에 정의된 것을 사용해야만 한다.
     *
     * @param propKey
     * @return
     */
    public static String getPropString(String propKey){

        return App.getPref().getString(propKey, getConstantsVal(propKey));
    }

    /**
     * 현재 호출서버 환경을 반영한 getPropString()
     *
     * @param propKey
     * @return
     */
    public static String getPropStringForEnv(String propKey){

        String keyForEnv = propKey + App.getEnvSuffix(App.getEnv());
        return App.getPref().getString(keyForEnv, getConstantsVal(keyForEnv));
    }


    /**
     * url 문자열에 포함된 특정 파라미터의 값을 리턴한다.
     *
     * @param url
     * @param paramKey
     * @return
     */
    public static String getParamValFromUrlString(String url, String paramKey){

        Log.d("## url", url);
        String[] urlParamPair = url.split("\\?");
        if(urlParamPair.length < 2){
            Log.d("##", "파라미터가 존재하지 않는 URL 입니다.");
        }
        String queryString = urlParamPair[1];
        Log.d("## queryString", queryString);
        StringTokenizer st = new StringTokenizer(queryString, "&");
        while(st.hasMoreTokens()){
            String pair = st.nextToken();
            Log.d("## pair", pair);
            String[] pairArr = pair.split("=");
            if(paramKey.equals(pairArr[0])){
                return pairArr[1]; // 찾았을 경우
            }
        }
        return "";
    }

    /**
     * URL encoding wrapper
     *
     * @param src
     * @return
     */
    public static String urlEncode(String src){

        String ret = "";
        try {
            ret = URLEncoder.encode(StringUtil.defaultString(src), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * URL decoding wrapper
     *
     * @param src
     * @return
     */
    public static String urlDecode(String src){

        String ret = "";
        try {
            ret = URLDecoder.decode(StringUtil.defaultString(src), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Map을 querystring으로 변환하기
     *
     * @param paramMap
     * @return
     */
    public static String convertMapToQuerystring(Map<?,?> paramMap) {

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> e : paramMap.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s", urlEncode(StringUtil.defaultString(e.getKey())), urlEncode(StringUtil.defaultString(e.getValue()))));
        }
        return sb.toString();
    }

    /**
     * Map의 요소를 순회하면서 특정 요소의 값을 url encoding 해 준다.
     *
     * @param map
     * @param applyKeys
     */
    public static void urlEncodeMapValues(Map<String, String> map, List<String> applyKeys){

        if(map == null || applyKeys == null){
            Log.e("##", "urlEncodeMapValues() 의 필수 파라미터가 존재하지 않습니다.");
        }
        Set<Map.Entry<String, String>> eSet = map.entrySet();
        String key, curVal, newVal = StringUtil.EMPTY;
        for (Map.Entry<String, String> e : eSet) {
            key = e.getKey();
            if(! applyKeys.contains(key)){ continue; }
            curVal = StringUtil.defaultString(e.getValue());
            newVal = StringUtil.urlEncode(curVal);
            Log.d("##", "urlEncodeMapValues() > curVal:["+curVal+"], newVal:["+newVal+"]");
            e.setValue(newVal);
        }
    }
}
