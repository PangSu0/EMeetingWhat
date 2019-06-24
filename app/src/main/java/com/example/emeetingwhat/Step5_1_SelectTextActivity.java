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
    ArrayList<MyItem> mMyItem;
    NameTable mNameTable;
    int mPeopleMax; //가상 데이터베이스 개체 수
    ListView mListMember;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step5_1_select_text);

        initNameList();

        initListView();
        // ListView 위젯에 10개의 텍스트 항목을 추가

    }
    public class NameTable {
        String[] mNameList;
        boolean[] isUsedList;
        int[] indexList;
        NameTable(){
            isUsedList = new boolean[mPeopleMax];
        }
        public void refreshList(){
            if(mNameList != null)
                mNameList = null;
            mNameList = new String[notUsedCount()+1];
            indexList = new int[notUsedCount()+1];
            int index = 0;
            mNameList[index++] = "";
            for(int i = 0 ; i < mPeopleMax; i ++) {
                if(isUsedList[i] == false) {
                    mNameList[index] = mDatabase.get(i);
                    indexList[index] = i;
                    index++;
                }
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
            isUsedList[indexList[index]] = true;
        }
        public void unUseName(int index)
        {
            isUsedList[indexList[index]] = false;
        }
    }

    public class MyItem{
        TextView textView = null;
        int mIndex;
        MyItem()
        {
            mIndex = -1;
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
        Toast.makeText(getApplicationContext(), mMyItem.get(0).textView.getText().toString(),Toast.LENGTH_SHORT).show();

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
        //for (int i = 0; i < 1; i++) {
        //  mDatabase.add("태엽" + (i + 1));
        //}
        mPeopleMax = mDatabase.size();
        mMyItem = new ArrayList<>();
        for(int i = 0 ; i < mPeopleMax ; i++)
            mMyItem.add(new MyItem());
        //////가상 데이터 세팅//////

        mNameTable = new NameTable();
        mNameTable.refreshList();

        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, mNameTable.mNameList);
    }

    public void initListView() {

        MyListAdapter MyAdapter = new MyListAdapter(this, R.layout.custom_list_item, mMyItem);
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
        boolean[] isNotFirstStartList;

        // 생성자 함수에서 멤버변수 초기화
        MyListAdapter(Context context, int aLayout, ArrayList<MyItem> aarSrc) {
            super(context,aLayout,aarSrc);
            //mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = aLayout;
            mContext = context;
            arSrc = aarSrc;
            isNotFirstStartList = new boolean[mPeopleMax];
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

            arSrc.get(position).textView =  row.findViewById(R.id.itemTextView);
            if(arSrc.get(position).mIndex == -1)
                arSrc.get(position).textView.setText("");
            else
                arSrc.get(position).textView.setText( mDatabase.get(arSrc.get(position).mIndex));

            // EditText 에 임시 데이터 입력
            Spinner spinner = row.findViewById(R.id.spnFriend);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext(), position + "실행" + i, Toast.LENGTH_SHORT).show();
                    if (isNotFirstStartList[position] == true) {
                        if (i > 0) {
                            if (arSrc.get(position).mIndex != -1)
                                mNameTable.isUsedList[arSrc.get(position).mIndex] = false;
                            arSrc.get(position).mIndex = mNameTable.indexList[i];
                            mNameTable.useName(i);
                        }
                        else if (i == 0)
                        {
                            if(arSrc.get(position).mIndex != -1){
                                mNameTable.isUsedList[arSrc.get(position).mIndex] = false;
                                arSrc.get(position).mIndex = -1;
                            }
                        }
                        mNameTable.refreshList();
                        for(int j = 0 ; j < mPeopleMax ; j++)
                            isNotFirstStartList[j] = false;
                        refreshList();
                    }
                    else
                        isNotFirstStartList[position] = true;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    Toast.makeText(getApplicationContext(), "가 선택되었습니다.",Toast.LENGTH_SHORT).show();
                }
            });
            return row;
        }
    }

    public void refreshList() {
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, mNameTable.mNameList);
        MyListAdapter MyAdapter = (MyListAdapter)mListMember.getAdapter();
        MyAdapter.notifyDataSetChanged();
    }
}