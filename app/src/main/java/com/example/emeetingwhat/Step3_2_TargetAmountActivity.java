package com.example.emeetingwhat;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.Data.AccountDetailData;
import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.common.widget.KakaoToast;
import com.example.emeetingwhat.createGroup.CreateDetailsActivity;
import com.kakao.usermgmt.response.model.UserProfile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Step3_2_TargetAmountActivity extends AppCompatActivity {
    EditText editName;
    Intent intent_GroupFromPrevious;
    GroupDetailData groupDataFromPrev;
    private final UserProfile userProfile = UserProfile.loadFromCache();
    private static String IP_ADDRESS = "61.108.100.36";
    private static String TAG = "inserttest";
    private Button btn3_2Next;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3_2_target_amount);

        intent_GroupFromPrevious = getIntent();
        groupDataFromPrev =  (GroupDetailData)intent_GroupFromPrevious.getSerializableExtra("groupDetailData");
        editName = findViewById(R.id.editTextStep3_2_EditName);
        // setEnterKey();

        btn3_2Next = (Button)  findViewById(R.id.btnStep3_2_Next);
    }
    public  void setEnterKey()
    {
        editName = findViewById(R.id.editTextStep3_2_EditName);
        editName.setOnKeyListener((v, keyCode, event) -> {
            //Enter key Action
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                nextPage();
                return true;
            }
            return false;
        });
    }
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnStep3_2_Prev:
                onBackPressed();
                break;
            case R.id.btnStep3_2_Next:
                Toast.makeText(getApplicationContext(), editName.getText(), Toast.LENGTH_SHORT).show();
                Step3_2_TargetAmountActivity.InsertData task = new Step3_2_TargetAmountActivity.InsertData();
                task.execute("http://" + IP_ADDRESS + "/insertGroupIndividualMode.php"
                        , groupDataFromPrev.getName()
                        , editName.getText().toString()
                        , Long.toString(userProfile.getId())
                        , Integer.toString(groupDataFromPrev.getPaymentDay())
                        , groupDataFromPrev.getBankName()
                        , groupDataFromPrev.getAccountNumber()
                        , userProfile.getNickname()
                        , userProfile.getThumbnailImagePath()
                        , userProfile.getProfileImagePath()
                );
                nextPage();
                break;
        }
    }

    public  void nextPage()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Step3_2_TargetAmountActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            // mTextViewResult.setText(result);
            KakaoToast.makeToast(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {
            String name = (String)params[1];
            String monthlyPayment = (String)params[2];
            int accountholderId = Integer.parseInt((String)params[3]);
            int paymentDay = Integer.parseInt((String)params[4]);
            String bankName = (String)params[5];
            String accountNumber = (String)params[6];
            String nickname = (String)params[7];
            String thumbnailPath = (String)params[8];
            String profilePath = (String)params[9];
            try {
                String serverURL = (String)params[0];
                String postParameters = "name=" + name + "&monthlyPayment=" +monthlyPayment + "&accountHolderId=" +accountholderId + "&paymentDay=" + paymentDay +
                        "&bankName=" +bankName + "&accountNumber=" +accountNumber + "&nickName=" +nickname +
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

