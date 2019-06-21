package com.example.emeetingwhat;

import android.widget.EditText;

public class Validator {

    private static int maxWordsCount = 7;

    public static boolean isEmpty(EditText editText) {

        String input = editText.getText().toString().trim();
        return input.length() == 0;
    }

    // 그룹명이 일곱글자 이하면 true
    public static boolean isLessThanMaxWordsCount(String groupName) {
        String input = groupName.trim();
        return input.length() <= maxWordsCount;
    }
}
