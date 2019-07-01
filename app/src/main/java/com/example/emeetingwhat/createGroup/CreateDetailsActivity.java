package com.example.emeetingwhat.createGroup;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.Data.AccountDetailData;
import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.MainActivity;
import com.example.emeetingwhat.R;
import com.example.emeetingwhat.common.widget.KakaoToast;
import com.kakao.usermgmt.response.model.UserProfile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kong.unirest.Unirest;

public class CreateDetailsActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "61.108.100.36";
    private static String TAG = "inserttest";
    AccountDetailData accountDetailData = new AccountDetailData();
    private final UserProfile userProfile = UserProfile.loadFromCache();
    Intent intent_GroupFromPrevious;
    Intent intent_AccountFromPrevious;

    private Button btn_friendsList;
    private Button btn3Next;
    private Button btn3Prev;
    private EditText et_amount;
    private EditText editText_monthlyPayment;

    private Calendar calendar;

    private int startDay;
    private int startMonth;
    private int startYear;
    private int endDay;
    private int endMonth;
    private int endYear;

    private EditText et_StartDate;
    private EditText et_EndDate;

    private Date startDate;
    private Date endDate;

    GroupDetailData groupDetailData = new GroupDetailData();
    GroupDetailData groupDataFromPrev;
    AccountDetailData accountDataFromPrev;

    // 처음 보여지는 날짜를 Korea Locale로 세팅.
    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    Date currentTime = new Date();
    String getCurrentTime = transFormat.format(currentTime);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_details);

        // 앞, 뒤로 가기 버튼
        btn3Next = (Button)findViewById(R.id.btn3Next);
        btn3Prev = (Button)findViewById(R.id.btn3Prev);

        // 목표 금액
        et_amount = (EditText)findViewById(R.id.et_Amount);

        // 월별 입금 금액
        editText_monthlyPayment = (EditText) findViewById(R.id.editText_monthlyPayment);

        intent_GroupFromPrevious = getIntent();
        intent_AccountFromPrevious = getIntent();
    // Unirest.get();
        groupDataFromPrev =  (GroupDetailData)intent_GroupFromPrevious.getSerializableExtra("groupDetailData");
        accountDataFromPrev =  (AccountDetailData)intent_AccountFromPrevious.getSerializableExtra("accountDetailData");

        btn3Next.setOnClickListener(v -> {

            if (startDate == null)
                startDate = currentTime;

            if (endDate == null)
                endDate = currentTime;
            CreateDetailsActivity.InsertData task = new CreateDetailsActivity.InsertData();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentTime = new Date();
            String createDate = transFormat.format(startDate);
            String strendDate = transFormat.format(endDate);
            KakaoToast.makeToast(getApplicationContext(), userProfile.getNickname(), Toast.LENGTH_SHORT).show();
            task.execute("http://" + IP_ADDRESS + "/insertGroupGroupMode.php"
                    , groupDataFromPrev.getName()
                    , createDate
                    , strendDate
                    , et_amount.getText().toString()
                    , editText_monthlyPayment.getText().toString()
                    , Long.toString(userProfile.getId())
                    , Integer.toString(groupDataFromPrev.getPaymentDay())
                    , groupDataFromPrev.getBankName()
                    , groupDataFromPrev.getAccountNumber()
                    , userProfile.getNickname()
                    , userProfile.getThumbnailImagePath()
                    , userProfile.getProfileImagePath()
            );

            Intent intent = new Intent(CreateDetailsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Prev 버튼을 눌렀을 때
        btn3Prev.setOnClickListener(v -> {
            Intent intent = new Intent(CreateDetailsActivity.this, CreateNameActivity.class);
            startActivity(intent);
        });

        // 모임 기간 세팅
        et_StartDate = (EditText) findViewById(R.id.et_StartDate);
        et_StartDate.setText(getCurrentTime);

        calendar = Calendar.getInstance();
        startDay = calendar.get(Calendar.DAY_OF_MONTH);
        startMonth = calendar.get(Calendar.MONTH);
        startYear = calendar.get(Calendar.YEAR);

        et_StartDate.setOnClickListener(v -> startDateDialog());

        // 모임 기간 세팅
        et_EndDate = (EditText) findViewById(R.id.et_EndDate);
        et_EndDate.setText(getCurrentTime);

        endDay = calendar.get(Calendar.DAY_OF_MONTH);
        endMonth = calendar.get(Calendar.MONTH);
        endYear = calendar.get(Calendar.YEAR);

        et_EndDate.setOnClickListener(v -> endDateDialog());
    }
    public void startDateDialog() {
        DatePickerDialog.OnDateSetListener listener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(year, monthOfYear, dayOfMonth);
            startDate = calendar.getTime();

            String dateSelected = transFormat.format(startDate);
            et_StartDate.setText(dateSelected);
        };
        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, startYear, startMonth, startDay);
        dpDialog.show();
    }

    public void endDateDialog() {
        DatePickerDialog.OnDateSetListener listener = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(year, monthOfYear, dayOfMonth);
            endDate = calendar.getTime();

            String dateSelected = transFormat.format(endDate);
            et_EndDate.setText(dateSelected);
        };
        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, endYear, endMonth, endDay);
        dpDialog.show();
    }
    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(CreateDetailsActivity.this,
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
            String createDate = (String)params[2];
            String endDate = (String)params[3];
            String targetAmount = (String)params[4];
            String monthlyPayment = (String)params[5];
            int accountholderId = Integer.parseInt((String)params[6]);
            int paymentDay = Integer.parseInt((String)params[7]);
            String bankName = (String)params[8];
            String accountNumber = (String)params[9];
            String nickname = (String)params[10];
            String thumbnailPath = (String)params[11];
            String profilePath = (String)params[12];
            try {
                String serverURL = (String)params[0];
                String postParameters = "name=" + name + "&createDate=" + createDate + "&endDate=" + endDate + "&targetAmount=" +targetAmount +
                        "&monthlyPayment=" +monthlyPayment + "&accountHolderId=" +accountholderId + "&paymentDay=" + paymentDay +
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

