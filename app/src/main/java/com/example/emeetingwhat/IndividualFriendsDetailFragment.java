package com.example.emeetingwhat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.common.log.Logger;
import com.example.emeetingwhat.common.widget.KakaoToast;
import com.kakao.friends.FriendContext;
import com.kakao.friends.FriendsService;
import com.kakao.friends.request.FriendsRequest;
import com.kakao.friends.response.FriendsResponse;
import com.kakao.friends.response.model.FriendInfo;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.response.model.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.emeetingwhat.WaitingDialog.cancelWaitingDialog;

public class IndividualFriendsDetailFragment extends Fragment implements View.OnClickListener{
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MyFriendsListAdapter adapter = null;

    private static String IP_ADDRESS = "61.108.100.36";
    private static String TAG = "inserttest";
    private String mJsonString;
    private GroupDetailData groupDetailData = new GroupDetailData();
    private Button nextBtn;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private String groupId;
    protected ListView list = null;
    private final UserProfile userProfile = UserProfile.loadFromCache();


    private ArrayList<MyFriendsInfo> myFriendsInfo=new ArrayList<>();
    private ArrayList<Long> selectedFriends=new ArrayList<>();
    private ArrayList<MyFriendsInfo> myFriendsInfoArrayList = new ArrayList<>();
    public IndividualFriendsDetailFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_friends_detail, container, false);
        nextBtn = (Button) view.findViewById(R.id.button_next);
        String str_groupId = "";

        if( getArguments() != null){
            str_groupId = getArguments().getString("groupId");
            groupId= str_groupId;
            groupDetailData = (GroupDetailData) getArguments().getSerializable("groupDetails");
        }
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new IndividualDetailFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putSerializable("groupDetails", groupDetailData);
                bundle.putSerializable("myFriendsInfoArrayList", myFriendsInfoArrayList);
                fragment.setArguments(bundle);
                ft.replace(R.id.content_fragment_layout, fragment);
                ft.commit();
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.friends_list);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        // mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        myFriendsInfo= new ArrayList<>();
        adapter = new MyFriendsListAdapter(getActivity(), myFriendsInfo);
        mRecyclerView.setAdapter(adapter);
        myFriendsInfo.clear();
        adapter.notifyDataSetChanged();


        Button btn_add = (Button) view.findViewById(R.id.button_add_friends);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Step4_2_InviteMemberActivity.class);
                intent.putExtra("groupId", groupId);
                intent.putExtra("myFriendsInfoArrayList", myFriendsInfoArrayList);
                startActivity(intent);
            }
        });
        IndividualFriendsDetailFragment.GetData task = new IndividualFriendsDetailFragment.GetData();
        task.execute( "http://" + IP_ADDRESS + "/selectGroupDetails.php", str_groupId);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {

    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;
        int objectCount = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            // mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                // mTextViewResult.setText(errorString);
            }
            else {
                mJsonString = result;
                showResult();
            }
        }

        public int getObjectCount(){
            return objectCount;
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "GroupId=" + params[1];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

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
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult() {
        String TAG_JSON = "groupDetails";
        String TAG_GROUPID = "GroupId";
        String TAG_USERID = "UserId";
        String TAG_ORDERNUMBER = "OrderNumber";
        String TAG_ACCOUNTNUMBER = "AccountNumber";
        String TAG_NICKNAME = "NickName";
        String TAG_THUNMBNAILIMAGEPATH = "ThumbnailImagePath";
        String TAG_PROFILEIMAGEPATH = "ProfileImagePath";
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);
                int groupId = item.getInt(TAG_GROUPID);
                int userId = item.getInt(TAG_USERID);
                int orderNumber = item.getInt(TAG_ORDERNUMBER);
                String nickName = item.getString(TAG_NICKNAME);
                String thumbnail = item.getString(TAG_THUNMBNAILIMAGEPATH);
                String profile = item.getString(TAG_PROFILEIMAGEPATH);
                MyFriendsInfo info = new MyFriendsInfo();
                info.setNickName(nickName);
                info.setUserId(userId);
                info.setThumbnailImagePath(thumbnail);
                info.setProfileImagePath(profile);
                myFriendsInfo.add(info);
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            // mTextViewResult.setText(result);
            KakaoToast.makeToast(getActivity(), result, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {
            String userId = (String)params[1];
            String groupId = (String)params[2];
            String nickname = (String)params[3];
            String thumbnailPath = (String)params[4];
            String profilePath = (String)params[5];
            try {
                String serverURL = (String)params[0];
                String postParameters = "userId=" + userId + "&groupId=" + groupId + "&nickName=" +nickname +
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
}
