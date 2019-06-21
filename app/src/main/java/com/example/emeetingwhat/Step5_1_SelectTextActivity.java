package com.example.emeetingwhat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Step5_1_SelectTextActivity extends AppCompatActivity {
    ArrayList<String> mDatabase;    //가상 데이터 베이스
    ArrayList<MyItem> mMyItemList;    //가상 데이터 베이스
    NameTable mNameTable;
    int mPeopleMax; //가상 데이터베이스 개체 수
    ListView mListMember;
    MyListAdapter MyAdapter = null;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step5_1_select_text);

        initNameList();

        initListView();
        // ListView 위젯에 10개의 텍스트 항목을 추가
        refreshList();

    }
    public class NameTable {
        String[] mNameList;
        boolean[] isUsedList;
        NameTable(){
            isUsedList = new boolean[mPeopleMax];
        }
        public void refreshList(){
            if(mNameList != null)
                mNameList = null;
            mNameList = new String[notUsedCount()+1];
            int index = 0;
            mNameList[index++] = "";
            for(int i = 0 ; i < mPeopleMax; i ++) {
                if(isUsedList[i] == false)
                    mNameList[index++] = mDatabase.get(i);
            }
        }
        public int notUsedCount(){
            int count = 0;
            for(int i = 0 ; i < mPeopleMax; i ++){
                if(isUsedList[i] == false)
                    count++;
            }
            return  count;
        }
        public void useName(int index)
        {
            isUsedList[index] = true;
        }
    }

    public class MyItem{
        Spinner spinner;
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

        mNameTable = new NameTable();
        mNameTable.refreshList();

        mMyItemList = new ArrayList<>();
        for (int i = 0; i < mPeopleMax; i++) {
            mMyItemList.add(new MyItem());
        }
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, mNameTable.mNameList);
    }

    public void initListView() {

        if( MyAdapter != null ) MyAdapter = null;
        // 어댑터 객체를 생성해서 ListView 에 지정
        MyAdapter = new MyListAdapter(this, R.layout.custom_list_item, mMyItemList);

        // ListView 위젯의 핸들을 구해서 멤버변수에 저장
        mListMember = findViewById(R.id.listMember);
        // ListView 의 어댑터를 지정
        mListMember.setAdapter(MyAdapter);
    }

    // ListView 와 데이터 배열을 연결해주는 커스텀 어댑터 클래스를 정의
    public class MyListAdapter extends ArrayAdapter<MyItem> {
        //LayoutInflater mInflater;
        int layout;
        Context mContext;
        ArrayList<MyItem> arSrc;

        // 생성자 함수에서 멤버변수 초기화
        MyListAdapter(Context context, int aLayout, ArrayList<MyItem> aarSrc) {
            super(context,aLayout,aarSrc);
            //mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = aLayout;
            mContext = context;
            arSrc = aarSrc;
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

            MyItem mi = arSrc.get(position);
            // EditText 에 임시 데이터 입력
            mi.spinner = row.findViewById(R.id.spnFriend);
            mi.spinner.setAdapter(arrayAdapter);
            // TODO : 이전 i 값 저장 변수 만들기
            mi.spinner.getSelectedItem();
            mi.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i>0) {
                        Toast.makeText(getApplicationContext(),i + "가 선택되었습니다.",Toast.LENGTH_SHORT).show();
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