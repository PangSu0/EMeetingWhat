package com.example.emeetingwhat;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity3 extends AppCompatActivity {
    UserInformation userInformation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);

        userInformation = new UserInformation();
        userInformation.url = "https://testapi.open-platform.or.kr/user/me?user_seq_no=1100035155";
        userInformation.token = "192b451b-bf01-4208-a197-1e31f0edae27";

        getUserInfo();
        setAccountTextView();
    }

    public class UserInformation {
        String userName;
        int accountNumber;
        ArrayList<String> bankNamesList;
        ArrayList<String> accountList;
        String url;
        String token;

        UserInformation() {
            bankNamesList = new ArrayList<>();
            accountList = new ArrayList<>();
        }
    }

    public void getUserInfo() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://testapi.open-platform.or.kr/user/me?user_seq_no=1100035155")
                .get()
                .header("Authorization", "Bearer 192b451b-bf01-4208-a197-1e31f0edae27")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = new JSONObject(string);
                    userInformation.userName = jsonObject.getString("user_name");
                    int countBank = Integer.parseInt(jsonObject.getString("res_cnt"));
                    userInformation.accountNumber = countBank;
                    JSONArray jsonArray = jsonObject.getJSONArray("res_list");
                    for (int i = 0; i < countBank; i++) {
                        JSONObject tempJsonObject = new JSONObject(jsonArray.get(i).toString());
                        userInformation.bankNamesList.add(tempJsonObject.getString("bank_name"));
                        userInformation.accountList.add(tempJsonObject.getString("account_num_masked"));
                    }
                    setAccountTextView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setAccountTextView() {
        TextView textView = findViewById(R.id.testText);
        textView.setText("");
        int accountNumber = userInformation.accountNumber;
        textView.setText(textView.getText() + "이름 : " + userInformation.userName + '\n');
        textView.setText(textView.getText() + "개수 : " + accountNumber+ '\n');
        for (int i = 0; i < accountNumber; i++) {
            textView.setText(textView.getText() + "은행이름 : " + userInformation.bankNamesList.get(i)+ '\n');
            textView.setText(textView.getText() + ".계좌번호 : " + userInformation.accountList.get(i)+ '\n');
        }
    }
}
