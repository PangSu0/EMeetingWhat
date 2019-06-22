package com.example.emeetingwhat.createGroup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.Data.AccountDetailData;
import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.FriendsMainActivity;
import com.example.emeetingwhat.KakaoTalkFriendListActivity;
import com.example.emeetingwhat.MainActivity;
import com.example.emeetingwhat.R;
import com.example.emeetingwhat.common.widget.KakaoToast;
import com.kakao.friends.request.FriendsRequest;
import com.kakao.usermgmt.response.model.UserProfile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Create_FriendsActivity extends AppCompatActivity {
    private static String IP_ADDRESS = "61.108.100.36";
    private static String TAG = "inserttest";
    private Button btn4Prev;
    private Button btn4Next;
    // private TextView mTextViewResult;
    GroupDetailData groupDetailData = new GroupDetailData();
    AccountDetailData accountDetailData = new AccountDetailData();
    private final UserProfile userProfile = UserProfile.loadFromCache();
    Intent intent_GroupFromPrevious;
    Intent intent_AccountFromPrevious;

    GroupDetailData groupDataFromPrev;
    AccountDetailData accountDataFromPrev;

    private Button btn_friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_friends);

        intent_GroupFromPrevious = getIntent();
        intent_AccountFromPrevious = getIntent();

        groupDataFromPrev =  (GroupDetailData)intent_GroupFromPrevious.getSerializableExtra("groupDetailData");
        accountDataFromPrev =  (AccountDetailData)intent_AccountFromPrevious.getSerializableExtra("accountDetailData");

        // 앞, 뒤로가기 버튼
        btn4Prev = (Button)findViewById(R.id.btn4Prev);
        btn4Next = (Button)findViewById(R.id.btn4Next);
        btn_friendsList = (Button) findViewById(R.id.btnFriendsList);
        groupDetailData.setTargetAmount(groupDataFromPrev.getTargetAmount());
        groupDetailData.setMonthlyPayment(groupDataFromPrev.getMonthlyPayment());
        groupDetailData.setName(groupDataFromPrev.getName());
        groupDetailData.setGroupType(groupDataFromPrev.getGroupType());
        groupDetailData.setPaymentDay(groupDataFromPrev.getPaymentDay());
        accountDetailData.setBankName(accountDataFromPrev.getBankName());

        btn4Prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Create_FriendsActivity.this, Create_DetailsActivity.class);
                startActivity(intent);
            }
        });

        // Prev 버튼을 눌렀을 때
        btn4Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: 내가 참여하고 있는 모임 리스트로 보내기.
                InsertData task = new InsertData();
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
                Date currentTime = new Date();
                String endDate = transFormat.format(currentTime);
                KakaoToast.makeToast(getApplicationContext(), Long.toString(userProfile.getId()), Toast.LENGTH_SHORT).show();
                task.execute("http://" + IP_ADDRESS + "/insertGroupIndividualMode.php"
                        , groupDetailData.getName(), endDate, Integer.toString(groupDetailData.getTargetAmount())
                        , Integer.toString(groupDetailData.getMonthlyPayment()), Long.toString(userProfile.getId())
                        , Integer.toString(groupDetailData.getPaymentDay()));

                Intent intent = new Intent(Create_FriendsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_friendsList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showTalkFriendListActivity();
            }
        });
    }

    private void showTalkFriendListActivity() {
        Intent intent = new Intent(this, KakaoTalkFriendListActivity.class);

        String[] friendType = {FriendsRequest.FriendType.KAKAO_TALK.name()};
        intent.putExtra(FriendsMainActivity.EXTRA_KEY_SERVICE_TYPE, friendType);
        startActivity(intent);
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Create_FriendsActivity.this,
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
            String endDate = (String)params[2];
            String targetAmount = (String)params[3];
            String monthlyPayment = (String)params[4];
            int accountholderId = Integer.parseInt((String)params[5]);
            String paymentDay = (String)params[6];
            String from = "2013-04-08 10:10:10";
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            try {
                Date to = transFormat.parse(endDate);
                String serverURL = (String)params[0];
                String postParameters = "name=" + name + "&endDate=" + from + "&targetAmount=" +targetAmount +
                        "&monthlyPayment=" +monthlyPayment + "&accountHolderId=" +accountholderId + "&paymentDay=" + paymentDay;
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


            }  catch (ParseException e) {
                e.printStackTrace();
                return new String("Error: " + e.getMessage());
            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }


}

