package com.example.emeetingwhat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class Step5_1_SelectTextActivity extends AppCompatActivity {
    ArrayList<String> mDatabase;    //가상 데이터 베이스
    ArrayList<MyItem> mMyItem;
    NameTable mNameTable;
    int mPeopleMax; //가상 데이터베이스 개체 수
    ListView mListMember;
    MyListAdapter MyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step5_1_select_text);

        initNameList();

        initListView();
        // ListView 위젯에 10개의 텍스트 항목을 추가

    }
    public class MyItem{
        TextView textView = null;
        int mIndex;
        MyItem()
        {
            mIndex = -1;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(),   " 실행 "   ,Toast.LENGTH_SHORT).show();
        if(resultCode==1234) {
            int index = data.getIntExtra("index", -1);
            int selectIndex = data.getIntExtra("selectIndex", -1);
            Toast.makeText(getApplicationContext(), index + " " + selectIndex ,Toast.LENGTH_SHORT).show();
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
            case R.id.btnItem:
                int position = Integer.parseInt(v.getTag().toString());
                Toast.makeText(getApplicationContext(), " " + position ,Toast.LENGTH_SHORT).show();
                Intent intent =  new Intent(getApplicationContext(), Step5_1_SubListViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mNameTable",mNameTable);
                intent.putExtras(bundle);
                intent.putExtra("index", position );
                startActivityForResult(intent,1111);
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
        for (int i = 0; i < 10; i++) {
            mDatabase.add("태엽" + (i + 1));
        }
        mPeopleMax = mDatabase.size();
        mMyItem = new ArrayList<>();
        for(int i = 0 ; i < mPeopleMax ; i++)
            mMyItem.add(new MyItem());
        //////가상 데이터 세팅//////

        mNameTable = new NameTable(mDatabase);
        mNameTable.refreshList();
    }

    public void initListView() {
        MyAdapter = new MyListAdapter(this, R.layout.custom_list_item, mMyItem);
        // ListView 위젯의 핸들을 구해서 멤버변수에 저장
        mListMember = findViewById(R.id.listMember);
        // ListView 의 어댑터를 지정
        mListMember.setAdapter(MyAdapter);
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

            // 1번째 TextView 에 데이터 입력
            TextView textView1 = convertView.findViewById(R.id.text1);
            textView1.setText(position + 1 + "번");

            MyItem mi = mArSrc.get(position);
            // EditText 의 핸들을 ArrayList 에 저장
            mi.textView = convertView.findViewById(R.id.itemTextView);
            mi.textView.setText("");

            convertView.findViewById(R.id.btnItem).setTag(position);

            return convertView;
        }
    }
}