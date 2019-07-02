package com.example.emeetingwhat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.common.widget.KakaoToast;
import com.kakao.usermgmt.response.model.UserProfile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Step5_1_SelectTextActivity extends AppCompatActivity {
    ArrayList<String> mDatabase;    //가상 데이터 베이스
    ArrayList<MyItem> mMyItem;
    NameTable mNameTable;
    int mPeopleMax; //가상 데이터베이스 개체 수
    ListView mListMember;
    MyListAdapter MyAdapter;
    private final UserProfile userProfile = UserProfile.loadFromCache();
    MyFriendsInfo myInfo= new MyFriendsInfo();
    GroupDetailData groupDataFromPrev;
    private ArrayList<MyFriendsInfo> selectedFriends = new ArrayList<>();
    String str_groupId = "";
    private static String IP_ADDRESS = "61.108.100.36";
    private static String TAG = "inserttest";
    private String mJsonString;
    ArrayList<MyFriendsInfo> myFriendsInfoArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step5_1_select_text);
        Intent intent_GroupFromPrevious = getIntent();
        str_groupId = intent_GroupFromPrevious.getStringExtra("groupId");
        groupDataFromPrev =  (GroupDetailData)intent_GroupFromPrevious.getSerializableExtra("groupDetailData");
        selectedFriends = (ArrayList<MyFriendsInfo>) intent_GroupFromPrevious.getSerializableExtra("selectedFriends");
        // Toast.makeText(getApplicationContext(), groupDataFromPrev.getAccountHolderId() + " ", Toast.LENGTH_SHORT).show();
        myInfo.setUserId(userProfile.getId());
        myInfo.setNickName(userProfile.getNickname());
        myInfo.setProfileImagePath(userProfile.getProfileImagePath());
        myInfo.setThumbnailImagePath(userProfile.getThumbnailImagePath());
        selectedFriends.add(myInfo);
        initNameList(selectedFriends);

        initListView();
        // ListView 위젯에 10개의 텍스트 항목을 추가

       /* GroupFriendsDetailFragment.InsertData task = new GroupFriendsDetailFragment.InsertData();
        KakaoToast.makeToast(getActivity(), userProfile.getNickname(), Toast.LENGTH_SHORT).show();
        task.execute("http://" + IP_ADDRESS + "/insertGroupFriends.php"
                , Long.toString(groupFInfo.getId())
                , groupId
                , groupFInfo.getProfileNickname()
                , groupFInfo.getProfileThumbnailImage()
                , groupFInfo.getProfileThumbnailImage()
        );*/

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
        if(resultCode==1234) {
            int index = data.getIntExtra("index", -1);
            int selectIndex = data.getIntExtra("selectIndex", -1);
            if(selectIndex >= 0)
            {
                MyItem mi = mMyItem.get(index);
                if(mi.mIndex!=-1)
                    mNameTable.isUsedList[mi.mIndex] = false;
                if(selectIndex > 0) {
                    mi.mIndex=mNameTable.getIndex(selectIndex);
                    mNameTable.isUsedList[mNameTable.getIndex(selectIndex)] = true;
                }
                else
                    mi.mIndex= -1;
                mNameTable.refreshList();
                if(mi.mIndex!=-1)
                    mMyItem.get(index).textView.setText(mDatabase.get(mi.mIndex));
                else
                    mMyItem.get(index).textView.setText("");
            }
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
                Intent intent =  new Intent(this, Step5_1_SubListViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mNameTable",mNameTable);
                intent.putExtras(bundle);
                intent.putExtra("index", position );
                startActivityForResult(intent,1111);
        }
    }
    public void nextPage()  //다음페이지
    {
        if(mNameTable.mNameList.length > 1)
            Toast.makeText(getApplicationContext(), "순서를 모두 설정해 주십시오.",Toast.LENGTH_SHORT).show();
        else {
            for(int i=0;i< selectedFriends.size();i++) {
                int index = 0;
                for(int j = 0; j < selectedFriends.size(); j++) {
                    index = j;
                    if (mMyItem.get(j).mIndex == i)
                        break;
                }
                myFriendsInfoArrayList.add(selectedFriends.get(index)); // todo : 여기 넣었어요
                // Toast.makeText(getApplicationContext(), myFriendsInfoArrayList.get(i).getNickName()+ " 성공", Toast.LENGTH_SHORT).show();
                if( myFriendsInfoArrayList.get(i).getUserId() == groupDataFromPrev.getAccountHolderId()){
                    Step5_1_SelectTextActivity.UpdateData task = new Step5_1_SelectTextActivity.UpdateData();
                    // KakaoToast.makeToast(getActivity(), userProfile.getNickname(), Toast.LENGTH_SHORT).show();
                    task.execute("http://" + IP_ADDRESS + "/updateAccountHolderOrderNumber.php"
                            , Integer.toString(i+1)
                            , Long.toString(myFriendsInfoArrayList.get(i).getUserId())
                            , str_groupId
                    );
                }else{
                    Step5_1_SelectTextActivity.InsertData task = new Step5_1_SelectTextActivity.InsertData();
                    // KakaoToast.makeToast(getActivity(), userProfile.getNickname(), Toast.LENGTH_SHORT).show();
                    task.execute("http://" + IP_ADDRESS + "/insertIndividualFriends.php"
                            , Long.toString(myFriendsInfoArrayList.get(i).getUserId())
                            , str_groupId
                            , Integer.toString(i+1)
                            , myFriendsInfoArrayList.get(i).getNickName()
                            , myFriendsInfoArrayList.get(i).getThumbnailImagePath()
                            , myFriendsInfoArrayList.get(i).getThumbnailImagePath()
                    );
                }

            }
            Intent intent = new Intent(this, MainActivity.class);

            intent.putExtra("groupId", str_groupId);
            intent.putExtra("groupDetailData", groupDataFromPrev);
            intent.putExtra("selectedFriends", selectedFriends);
            startActivity(intent);
        }

    }
    // ListView 를 초기화
    public void initNameList(ArrayList<MyFriendsInfo> info) {
        if (mDatabase != null) {
            mDatabase.clear();
            mDatabase = null;
        }
        mDatabase = new ArrayList<>();
        for( int i = 0 ; i < info.size() ; i ++ ){
            mDatabase.add(info.get(i).getNickName());
        }
        mPeopleMax = mDatabase.size();
        mMyItem = new ArrayList<>();
        for(int i = 0 ; i < info.size() ; i++)
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
            if(mi.mIndex!=-1)
                mi.textView.setText(mDatabase.get(mi.mIndex));
            else
                mi.textView.setText("");

            convertView.findViewById(R.id.btnItem).setTag(position);

            return convertView;
        }
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // progressDialog = ProgressDialog.show(getApplicationContext(),
            //        "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // progressDialog.dismiss();
            // mTextViewResult.setText(result);
            // KakaoToast.makeToast(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {
            String userId = (String)params[1];
            String groupId = (String)params[2];
            String orderNumber = (String) params[3];
            String nickname = (String)params[4];
            String thumbnailPath = (String)params[5];
            String profilePath = (String)params[6];
            try {
                String serverURL = (String)params[0];
                String postParameters = "userId=" + userId + "&groupId=" + groupId + "&orderNumber=" + orderNumber+  "&nickName=" +nickname +
                        "&thumbnailImagePath=" +thumbnailPath+ "&profileImagePath=" +profilePath;
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            }  catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

    class UpdateData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            progressDialog = ProgressDialog.show(getApplicationContext(),
//                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

           //  progressDialog.dismiss();
            // mTextViewResult.setText(result);
            KakaoToast.makeToast(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {

            String orderNumber = (String) params[1];
            String userId = (String)params[2];
            String groupId = (String)params[3];
            try {
                String serverURL = (String)params[0];
                String postParameters = "orderNumber=" + orderNumber + "&userId=" + userId + "&groupId=" + groupId;
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            }  catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}