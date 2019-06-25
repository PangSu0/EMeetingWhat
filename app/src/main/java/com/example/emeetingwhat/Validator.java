package com.example.emeetingwhat;

import android.widget.EditText;

public class Validator {

    public static boolean isEmpty(EditText editText) {

        String input = editText.getText().toString().trim();
        return input.length() == 0;
    }
}
