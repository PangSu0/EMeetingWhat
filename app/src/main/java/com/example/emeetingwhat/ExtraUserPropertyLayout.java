/*
  Copyright 2014-2017 Kakao Corp.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.example.emeetingwhat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * 추가로 받고자 하는 사용자 정보를 나타내는 layout
 * 이름, 나이, 성별을 입력할 수 있다.
 * @author MJ
 */
public class ExtraUserPropertyLayout extends FrameLayout {
    // property key
    private  static final String BIRTHDAY_KEY = "birthday";
    private EditText birthday;
    public ExtraUserPropertyLayout(Context context) {
        super(context);
        initView();
    }

    public ExtraUserPropertyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ExtraUserPropertyLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        final View view = inflate(getContext(), R.layout.layout_extra_user_property, this);
        birthday = view.findViewById(R.id.usermgmg_birthday);
    }

    public HashMap<String, String> getProperties(){
        final String birthdayValue = birthday.getText().toString();

        HashMap<String, String> properties = new HashMap<>();
        properties.put(BIRTHDAY_KEY, birthdayValue);
        return properties;
    }

    void showProperties(final Map<String, String> properties) {
        final String birthdayValue = properties.get(BIRTHDAY_KEY);
        if (birthdayValue != null)
            birthday.setText(birthdayValue);
    }
}
