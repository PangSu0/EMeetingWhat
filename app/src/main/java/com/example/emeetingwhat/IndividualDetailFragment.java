package com.example.emeetingwhat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emeetingwhat.Data.GroupDetailData;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IndividualDetailFragment extends Fragment {
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static String IP_ADDRESS = "61.108.100.36";
    private static String TAG = "dbtest";
    private String mJsonString;
    private GroupsAdapter mAdapter;
    private GroupDetailData groupDetailData = new GroupDetailData();
    private String TAG_JSON = "individualDetail";
    private String TAG_GROUPID = "GroupId";
    private String TAG_NAME = "Name";
    private String TAG_CREATEDATE = "CreateDate";
    private String TAG_ENDDATE = "EndDate";
    private String TAG_TARGETAMOUNT = "TargetAmount";
    private String TAG_MONTHLYPAYMENT = "MonthlyPayment";
    private String TAG_GROUPTYPE = "GroupType";
    private String TAG_ACCOUNTHOLDERID = "AccountHolderId";
    private String TAG_PAYMENTDAY = "PaymentDay";
    private String TAG_USERID = "UserId";
    private String TAG_ORDERNUMBER = "OrderNumber";
    private String TAG_COMPONENTID = "UserId";
    private TextView mTextViewResult;
    private TextView mTextViewName;
    private TextView mTextViewTargetAmount;
    private TextView mTextViewPaymentDay;
    private TextView mTextViewAccountHolderId;
    private TextView mTextViewNickname;
    private MyFriendsListAdapter adapter = null;
    private ArrayList<MyFriendsInfo> myFriendsInfo=new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private final UserProfile userProfile = UserProfile.loadFromCache();
    public IndividualDetailFragment() {
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
        View view = inflater.inflate(R.layout.fragment_individual_detail, container, false);
        mTextViewResult = (TextView)view.findViewById(R.id.textView_result_test);
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.individual_friends_list);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        myFriendsInfo= new ArrayList<>();
        adapter = new MyFriendsListAdapter(getActivity(), myFriendsInfo);
        mRecyclerView.setAdapter(adapter);
        myFriendsInfo.clear();
        adapter.notifyDataSetChanged();

        Button btn_add = (Button) view.findViewById(R.id.button_add_friends_i);
        btn_add.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Step4_2_InviteMemberActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        IndividualDetailFragment.GetData task = new IndividualDetailFragment.GetData();

        String str_groupId = "";

        if( getArguments() != null){
            str_groupId = getArguments().getString("groupId");
            groupDetailData = (GroupDetailData) getArguments().getSerializable("groupDetails");
        }
        task.execute( "http://" + IP_ADDRESS + "/selectGroupDetails.php", str_groupId);
//
//        IndividualDetailFragment.GetData task2 = new IndividualDetailFragment.GetData();
//        task2.execute( "http://" + IP_ADDRESS + "/selectGroupComponent.php", str_groupId);
        mTextViewName = (TextView)view.findViewById(R.id.textView_individualdetails_name);
        mTextViewName.setText(groupDetailData.getName());

        mTextViewTargetAmount = (TextView)view.findViewById(R.id.textView_individualdetails_targetamount);
        mTextViewTargetAmount.setText(Integer.toString(groupDetailData.getTargetAmount()));

        mTextViewPaymentDay = (TextView)view.findViewById(R.id.textView_individualdetails_paymentday);
        mTextViewPaymentDay.setText(groupDetailData.getName());
        mTextViewNickname = (TextView)view.findViewById(R.id.textView_individualdetails_nickname);
        mTextViewNickname.setText(userProfile.getNickname());
        mTextViewAccountHolderId = (TextView)view.findViewById(R.id.textView_individualdetails_accountholderid);
        mTextViewAccountHolderId.setText(Integer.toString(groupDetailData.getAccountHolderId()));
        // Inflate the layout for this fragment
        return view;
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

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
            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {
                mJsonString = result;
                showResult();
            }
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

}
