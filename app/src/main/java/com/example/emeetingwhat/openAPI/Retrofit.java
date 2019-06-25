package com.example.emeetingwhat.openAPI;

import java.util.Map;

import retrofit2.Call;

public class Retrofit implements RetrofitInterface {
    @Override
    public Call<Map> token(Map<String, String> params) {
        return null;
    }

    @Override
    public Call<Map> userMe(String token, Map<String, String> params) {
        return null;
    }

    @Override
    public Call<Map> accountBalance(String token, Map<String, String> params) {
        return null;
    }

    @Override
    public Call<Map> accountTrasactionList(String token, Map<String, String> params) {
        return null;
    }

    @Override
    public Call<Map> inquiryRealName(String token, Map<String, String> params) {
        return null;
    }

    @Override
    public Call<Map> trasnferWithdraw(String token, Map<String, String> params) {
        return null;
    }

    @Override
    public Call<Map> transferDeposit(String token, Map<String, String> params) {
        return null;
    }

    @Override
    public Call<Map> transferDeposit2(String token, Map<String, String> params) {
        return null;
    }
}
