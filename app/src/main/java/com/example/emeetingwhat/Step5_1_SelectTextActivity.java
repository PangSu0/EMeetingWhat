package com.example.emeetingwhat;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Step5_1_SelectTextActivity extends AppCompatActivity {
    ArrayList<String> mTempName;    //가상 데이타 베이스
    ArrayList<MyItem> mArMember;
    int mPeopleMax;
    ListView mListMember;
    MyListAdapter MyAdapter = null;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step5_1_select_text);

        initNameList();

        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, mTempName);
        initListView();
        // ListView 위젯에 10개의 텍스트 항목을 추가

    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnStep5_1_Prev:
                onBackPressed();
                break;
            case R.id.btnStep5_1_Next:
                nextPage();
                break;
        }
    }
    public void nextPage()  //다음페이지
    {
        /*
        Intent intent = new Intent(getApplicationContext(), XXXXXXXXX.class);
        startActivity(intent);
        */
    }
    // ListView 를 초기화
    public void initNameList() {
        if (mTempName != null) {
            mTempName.clear();
            mTempName = null;
        }
        mTempName = new ArrayList<>();
        mTempName.add("은지");
        mTempName.add("수미");
        mTempName.add("창수");
        for (int i = 0; i < 1; i++) {
            mTempName.add("태엽" + (i + 1));
        }
        mPeopleMax = mTempName.size();
    }

    public void initListView() {
        if( mArMember != null ) {
            mArMember.clear();
            mArMember = null;
        }
        // ArrayList 배열 객체를 생성
        mArMember = new ArrayList<>();
        MyItem mi;
        for(int i=0; i < mPeopleMax; i++) {
            mi = new MyItem();
            mArMember.add(mi);
        }

        if( MyAdapter != null ) MyAdapter = null;
        // 어댑터 객체를 생성해서 ListView 에 지정
        MyAdapter = new MyListAdapter(this, R.layout.custom_list_item, mArMember);

        // ListView 위젯의 핸들을 구해서 멤버변수에 저장
        mListMember = findViewById(R.id.listMember);
        // ListView 의 어댑터를 지정
        mListMember.setAdapter(MyAdapter);
    }
    public class MyItem {
        View mLayoutItem = null;
    }

    // ListView 와 데이터 배열을 연결해주는 커스텀 어댑터 클래스를 정의
    public class MyListAdapter extends BaseAdapter {
        LayoutInflater mInflater;
        ArrayList<MyItem> mArSrc;
        int layout;

        // 생성자 함수에서 멤버변수 초기화
        MyListAdapter(Context context, int aLayout, ArrayList<MyItem> aarSrc) {
            mInflater = LayoutInflater.from(context);
                    /*(LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);*/
            mArSrc = aarSrc;
            layout = aLayout;
        }

        // 항목 개수를 반환
        public int getCount() {
            return mArSrc.size();
        }

        // 특정 항목의 텍스트 데이터를 반환
        public View getItem(int position) {
            return mArSrc.get(position).mLayoutItem;
        }

        // 특정 항목의 ID 를 반환
        public long getItemId(int position) {
            return position;
        }

        // ListView 아이템 내부 각각의 엘리먼트에 데이터를 입력
        public View getView(int position, View convertView, ViewGroup parent) {

            // 데이터 배열에서 해당 항목을 구한다
            MyItem mi = mArSrc.get(position);
            // 항목 Layout 이 아직 생성되지 않았다면 생성한다
            if( mi.mLayoutItem == null )
                mi.mLayoutItem = mInflater.inflate(layout, null);

            // 1번째 TextView 에 데이터 입력
            TextView textView = mi.mLayoutItem.findViewById(R.id.text1);
            textView.setText(position+1 + "번");


            Spinner spinner =  mi.mLayoutItem.findViewById(R.id.spnFriend);
            ///////// ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, mTempName);
            spinner.setAdapter(arrayAdapter);
            /*
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //Toast.makeText(getApplicationContext(), i + "가 선택되었습니다.",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

            });
            */
            return mi.mLayoutItem;
        }
    }

}