package com.example.emeetingwhat;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.HashMap;

public class GroupDetailActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "61.108.100.36";
    private static String TAG = "dbtest";
    private String mJsonString;
    private GroupsAdapter mAdapter;
    private GroupDetailData groupDetailData = new GroupDetailData();
    private String TAG_JSON = "groupDetail";
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
    private TextView mTextViewResult;
    private TextView mTextViewName;
    private TextView mTextViewTargetAmount;
    private TextView mTextViewPaymentDay;
    private TextView mTextViewAccountHolderId;
    private TextView mTextViewNickname;
    private final UserProfile userProfile = UserProfile.loadFromCache();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grouplist);
        mTextViewResult = (TextView)findViewById(R.id.textView_result_test);
        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        GroupDetailActivity.GetData task = new GroupDetailActivity.GetData();
        String str_groupId;
        if( savedInstanceState == null ){
            Bundle extras = getIntent().getExtras();
            if(extras == null )
                str_groupId = null;
            else
                str_groupId = Integer.toString(extras.getInt("groupId"));
        }else{
            str_groupId = (String) savedInstanceState.getSerializable("groupId");
        }

        task.execute( "http://" + IP_ADDRESS + "/selectGroupDetail.php", str_groupId, Long.toString(userProfile.getId()));




    }


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(GroupDetailActivity.this,
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
            String postParameters = "GroupId=" + params[1]+"&UserId="+ params[2];

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

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                int groupId = item.getInt(TAG_GROUPID);
                String name = item.getString(TAG_NAME);
                String s_createDate = item.getString(TAG_CREATEDATE);
                String s_endDate = item.getString(TAG_ENDDATE);
                int targetAmount = item.getInt(TAG_TARGETAMOUNT);
                int monthlyPayment = item.getInt(TAG_MONTHLYPAYMENT);
                String groupType = item.getString(TAG_GROUPTYPE);
                int accountHolderId = item.getInt(TAG_ACCOUNTHOLDERID);
                int paymentDay = item.getInt(TAG_PAYMENTDAY);
                int orderNumber = item.getInt(TAG_ORDERNUMBER);

                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date createDate = null;
                Date endDate = null;
                Date paymentDate = null;
                try {
                    createDate = transFormat.parse(s_createDate);
                    endDate = transFormat.parse(s_endDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                groupDetailData.setGroupId(groupId);
                groupDetailData.setName(name);
                groupDetailData.setCreateDate(createDate);
                groupDetailData.setEndDate(endDate);
                groupDetailData.setTargetAmount(targetAmount);
                groupDetailData.setMonthlyPayment(monthlyPayment);
                groupDetailData.setGroupType(groupType);
                groupDetailData.setAccountHolderId(accountHolderId);
                groupDetailData.setPaymentDay(paymentDay);
                groupDetailData.setOrderNumber(orderNumber);
                Toast.makeText(getApplicationContext(), groupDetailData.getName(), Toast.LENGTH_SHORT).show();
                mTextViewName = (TextView)findViewById(R.id.textView_groupdetails_name);
                mTextViewName.setText(groupDetailData.getName());

                mTextViewTargetAmount = (TextView)findViewById(R.id.textView_groupdetails_targetamount);
                mTextViewTargetAmount.setText(Integer.toString(targetAmount));

                mTextViewPaymentDay = (TextView)findViewById(R.id.textView_groupdetails_paymentday);
                mTextViewPaymentDay.setText(groupDetailData.getName());
                mTextViewNickname = (TextView)findViewById(R.id.textView_groupdetails_nickname);
                mTextViewNickname.setText(userProfile.getNickname());
                mTextViewAccountHolderId = (TextView)findViewById(R.id.textView_groupdetails_accountholderid);
                mTextViewAccountHolderId.setText(Integer.toString(accountHolderId));
                // mArrayList.add(groupDetailData);
                // mAdapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}