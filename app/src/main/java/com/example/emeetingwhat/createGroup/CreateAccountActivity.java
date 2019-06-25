package com.example.emeetingwhat.createGroup;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.BankSpinnerAdapter;
import com.example.emeetingwhat.BanksAdapter;
import com.example.emeetingwhat.Data.AccountDetailData;
import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.MainActivity;
import com.example.emeetingwhat.R;
import com.example.emeetingwhat.common.widget.KakaoToast;
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

public class CreateAccountActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "61.108.100.36";
    private static String TAG = "inserttest";
    protected ArrayList<AccountDetailData> mArrayList;
    protected BanksAdapter mAdapter;
    private Spinner bankSpinner;
    // private ArrayAdapter<CharSequence> bankAdapter;
    private String mJsonString;
    private Spinner paymentDateSpinner;
    BankSpinnerAdapter bankAdapter;
    // private ArrayList<String> bankList;
    private ArrayAdapter<CharSequence> paymentDateAdapter;
    public  final UserProfile userProfile = UserProfile.loadFromCache();
    Button btn1Prev;
    Button btn1Next;
    String selectedBankName;
    String selectedAccountNumber;

    int selectedPaymentDay;

    GroupDetailData groupDetailData =new GroupDetailData();
    AccountDetailData accountDetailData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        CreateAccountActivity.GetData task = new CreateAccountActivity.GetData();
        task.execute( "http://" + IP_ADDRESS + "/selectBankList.php", Long.toString(userProfile.getId()));
        // 1. bankName Spinner
        bankSpinner = (Spinner)findViewById(R.id.sp_bank);
        bankSpinner.setPrompt("은행 계좌를 선택하세요.");

        mArrayList = new ArrayList<>();

        mAdapter = new BanksAdapter(this, mArrayList);

        bankAdapter = new BankSpinnerAdapter(this, mArrayList);
        bankSpinner.setAdapter(bankAdapter);

            bankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?>  parent, View view, int position, long id) {
//                // 확인용


                    // 사용자가 선택한 은행명으로 BankName을 세팅한다.
                    accountDetailData = (AccountDetailData) bankAdapter.getItem(position);
                    selectedBankName = accountDetailData.getBankName();
                    selectedAccountNumber = accountDetailData.getAccountNumber();
                    Toast.makeText(CreateAccountActivity.this,
                            selectedBankName + "의 "  + selectedAccountNumber + "을 선택했습니다.", Toast.LENGTH_LONG).show();

                    groupDetailData.setBankName(selectedBankName);
                    groupDetailData.setAccountNumber(selectedAccountNumber);
                }
                public void onNothingSelected(AdapterView  parent) {
                    // TODO: validation check (반드시 선택하도록 한다).
                    accountDetailData = (AccountDetailData) bankAdapter.getItem(0);
                    selectedBankName = accountDetailData.getBankName();
                    selectedAccountNumber = accountDetailData.getAccountNumber();
                    Toast.makeText(CreateAccountActivity.this,
                            selectedBankName + "의 "  + selectedAccountNumber + "을 선택했습니다.", Toast.LENGTH_LONG).show();
                    groupDetailData.setBankName(selectedBankName);
                    groupDetailData.setAccountNumber(selectedAccountNumber);
                }
            });


        // 2. paymentday Spinner
        paymentDateSpinner = (Spinner)findViewById(R.id.sp_paymentDate);
        paymentDateAdapter = ArrayAdapter.createFromResource(this, R.array.paymentDate, android.R.layout.simple_spinner_item);

        paymentDateAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        paymentDateSpinner.setAdapter(paymentDateAdapter);

        paymentDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?>  parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        paymentDateAdapter.getItem(position) + "을 선택했습니다.", Toast.LENGTH_LONG).show();

//                // 사용자가 선택한 입금날짜로 paymentDate를 세팅한다.
                selectedPaymentDay = Integer.parseInt(paymentDateAdapter.getItem(position).toString());


                groupDetailData.setPaymentDay(selectedPaymentDay);
            }
            public void onNothingSelected(AdapterView  parent) {
                // TODO: validation check (반드시 선택하도록 한다).
            }
        });


        // 앞, 뒤로가기 버튼
        btn1Next = (Button)findViewById(R.id.btn1Next);
        btn1Prev = (Button)findViewById(R.id.btn1Prev);

        // Prev 버튼을 눌렀을 때
        btn1Prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                // redirect로 보내기.
                startActivity(intent);
            }
        });

        btn1Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        selectedBankName + "을 선택 했습니다.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CreateAccountActivity.this, CreateNameActivity.class);

                // Data 클래스 넘기기.
                intent.putExtra("groupDetailData", groupDetailData);
                intent.putExtra("accountDetailData", accountDetailData);

                startActivity(intent);
            }
        });
    }


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(CreateAccountActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            // mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){
                KakaoToast.makeToast(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
                // mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = "UserId=" + params[1];

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
        String TAG_JSON = "bankList";
        String TAG_ACCOUNTID = "AccountId";
        String TAG_ACCOUNTNUMBER = "AccountNumber";
        String TAG_BANKNAME = "BankName";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                int accountId = item.getInt(TAG_ACCOUNTID);
                String bankName = item.getString(TAG_BANKNAME);
                String accountNumber = item.getString(TAG_ACCOUNTNUMBER);

                AccountDetailData bankData = new AccountDetailData();
                bankData.setAccountId(accountId);
                bankData.setBankName(bankName);
                bankData.setAccountNumber(accountNumber);
                // bankList.add(accountNumber);
                mArrayList.add(bankData);
                // mAdapter.notifyDataSetChanged();
                bankAdapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}
