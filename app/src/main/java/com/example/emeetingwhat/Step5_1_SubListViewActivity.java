package com.example.emeetingwhat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Step5_1_SubListViewActivity extends AppCompatActivity {
    NameTable mNameTable;
    int index;
    int selectIndex;
    ListView mListMember;
    MyListAdapter MyAdapter;
    ArrayList<MyItem> mMyItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step5_1_sub_listview);
        readIntent(getIntent());
        setupList();
        initListView();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("index", index);
        intent.putExtra("selectIndex", selectIndex);
        setResult(1234, intent);
        finish();
    }

    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.btnAddfriendItem2:
                selectIndex =  Integer.parseInt(v.getTag().toString());
                onBackPressed();
        }
    }
    public void setupList() {
        mMyItem = new ArrayList<>();
        for(int i = 0 ; i < mNameTable.mNameList.length ; i++){
            mMyItem.add(new MyItem());
        }
    }
    public void initListView() {
        MyAdapter = new MyListAdapter(this, R.layout.custom_list_item2 , mMyItem);
        // ListView 위젯의 핸들을 구해서 멤버변수에 저장
        mListMember = findViewById(R.id.listviewStep5_1_Sub);
        // ListView 의 어댑터를 지정
        mListMember.setAdapter(MyAdapter);
    }

    public void readIntent(Intent intent) {
        index = intent.getIntExtra("index",-1);
        mNameTable = (NameTable)intent.getSerializableExtra("mNameTable");
    }

    public class MyItem{
        TextView textView = null;
    }

    public class MyListAdapter extends BaseAdapter {
        Context mMainCon;
        LayoutInflater mInflater;
        ArrayList<MyItem> mArSrc;
        int layout;

        // 생성자 함수에서 멤버변수 초기화
        MyListAdapter(Context context, int aLayout, ArrayList<MyItem> aarSrc) {
            mMainCon = context;
            mInflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            mArSrc = aarSrc;
            layout = aLayout;
        }

        // 항목 개수를 반환
        public int getCount() {
            return mArSrc.size();
        }

        // 특정 항목의 텍스트 데이터를 반환
        public TextView getItem(int position) {
            return mArSrc.get(position).textView;
        }

        // 특정 항목의 ID 를 반환
        public long getItemId(int position) {
            return position;
        }

        // ListView 아이템 내부 각각의 엘리먼트에 데이터를 입력
        public View getView(int position, View convertView, ViewGroup parent) {
            // 항목 Layout 이 아직 생성되지 않았다면 생성한다
            if( convertView == null )
                convertView = mInflater.inflate(layout, null);

            MyItem mi = mArSrc.get(position);
            // EditText 의 핸들을 ArrayList 에 저장
            mi.textView = convertView.findViewById(R.id.textViewItem2);
            mi.textView.setText(mNameTable.mNameList[position]);

            convertView.findViewById(R.id.btnAddfriendItem2).setTag(position);

            return convertView;
        }
    }
}
