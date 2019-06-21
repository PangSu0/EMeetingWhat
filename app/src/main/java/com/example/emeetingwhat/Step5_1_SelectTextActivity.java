package com.example.emeetingwhat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Step5_1_SelectTextActivity extends AppCompatActivity {
    ArrayList<String> mDatabase;    //가상 데이터 베이스
    ArrayList<MyItem> mSpinnerData;    //가상 데이터 베이스
    int mPeopleMax; //가상 데이터베이스 개체 수
    ListView mListMember;
    MyListAdapter MyAdapter = null;
    ArrayAdapter<MyItem> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step5_1_select_text);

        initNameList();

        initListView();
        // ListView 위젯에 10개의 텍스트 항목을 추가
        refreshList();

    }
    public class MyItem{
        String mName;
        public MyItem(String str){
            mName = str;
        }
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
        if (mDatabase != null) {
            mDatabase.clear();
            mDatabase = null;
        }
        mDatabase = new ArrayList<>();
        mDatabase.add("은지");
        mDatabase.add("수미");
        mDatabase.add("팡수");
        for (int i = 0; i < 1; i++) {
            mDatabase.add("태엽" + (i + 1));
        }
        mPeopleMax = mDatabase.size();
        /////가상 데이터 세팅//////

        mSpinnerData = new ArrayList<>();
        mSpinnerData.add(new MyItem(""));
        for (int i = 0; i < mPeopleMax; i++) {
            mSpinnerData.add(new MyItem(mDatabase.get(i)));
        }
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, mSpinnerData);
    }

    public void initListView() {

        if( MyAdapter != null ) MyAdapter = null;
        // 어댑터 객체를 생성해서 ListView 에 지정
        MyAdapter = new MyListAdapter(this, R.layout.custom_list_item, mDatabase);

        // ListView 위젯의 핸들을 구해서 멤버변수에 저장
        mListMember = findViewById(R.id.listMember);
        // ListView 의 어댑터를 지정
        mListMember.setAdapter(MyAdapter);
    }

    public class MySpinnerListAdapter extends BaseAdapter{
        Context mMainCon;
        LayoutInflater mInflater;
        ArrayList<MyItem> mArSrc;
        int layout;
        MySpinnerListAdapter(Context context, int aLayout, ArrayList<MyItem> aarSrc){
            mMainCon = context;
            layout = aLayout;
            mArSrc = aarSrc;
        }
        public int getCount() {
            return mArSrc.size();
        }

        // 특정 항목의 텍스트 데이터를 반환
        public String getItem(int position) {
            return mArSrc.get(position).mName;
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

            // 문자열을 구한다
            String strText = mi.mTitle;

            // 1번째 TextView 에 데이터 입력
            TextView textView1 = (TextView) mi.mLayoutItem.findViewById(R.id.text1);
            textView1.setText(strText);

            // EditText 의 핸들을 ArrayList 에 저장
            mi.mEditName = (EditText) mi.mLayoutItem.findViewById(R.id.editName);

            // EditText 에 임시 데이터 입력
            if( mi.mEditName.getText().toString().length() < 1 ) {
                // 사람 이름 일때
                if( position <= mPeopleMax )
                    mi.mEditName.setText("People-" + position);
                    // 벌칙 이름 일때
                else {
                    int index = position - mPeopleMax - 1;
                    mi.mEditName.setText("Present-" + index);
                }
            }

            // 타이틀이면 EditText 를 감춘다
            if( mArSrc.get(position).mIsTitle ) {
                textView1.setTextColor(Color.rgb(0,0,192));
                mi.mEditName.setVisibility(View.INVISIBLE);
            }
            else {
                textView1.setTextColor(Color.rgb(0,0,0));
                mi.mEditName.setVisibility(View.VISIBLE);
            }

            return mi.mLayoutItem;
        }
    }

    // ListView 와 데이터 배열을 연결해주는 커스텀 어댑터 클래스를 정의
    public class MyListAdapter extends ArrayAdapter<String> {
        //LayoutInflater mInflater;
        int layout;
        Context mContext;
        // 생성자 함수에서 멤버변수 초기화
        MyListAdapter(Context context, int aLayout, ArrayList<String> aarSrc) {
            super(context,aLayout,aarSrc);
            //mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = aLayout;
            mContext = context;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if(row == null){
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(layout, parent, false);
            }

            // 문자열을 구한다
            TextView textView1 = row.findViewById(R.id.text1);
            textView1.setText(position + 1 + "번");

            // EditText 에 임시 데이터 입력
            Spinner spinner = row.findViewById(R.id.spnFriend);
            spinner.setAdapter(arrayAdapter);
            // TODO : 이전 i 값 저장 변수 만들기

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i>0) {
                        Toast.makeText(getApplicationContext(),i + "가 선택되었습니다.",Toast.LENGTH_SHORT).show();
                        /*if(mSelectedString != null)
                            mSpinnerData.add(mSelectedString);
                        mSelectedString = adapterView.getItemAtPosition(i).toString();
                        Toast.makeText(getApplicationContext(),mSelectedString,Toast.LENGTH_SHORT).show();*/
                        mSpinnerData.remove(i);

                        //String str = arSrc.get(position).spinner.getSelectedItem().toString();
                        //mNameTable.useName(i);
                        //mNameTable.refreshList();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

            });
            return row;
        }
    }

    public void refreshList() {
        MyListAdapter MyAdapter = (MyListAdapter)mListMember.getAdapter();
        MyAdapter.notifyDataSetChanged();
    }
}