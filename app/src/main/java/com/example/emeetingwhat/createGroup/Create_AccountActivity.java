package com.example.emeetingwhat.createGroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.Data.AccountDetailData;
import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.MainPageActivity;
import com.example.emeetingwhat.R;

import java.security.acl.Group;
import java.util.ArrayList;

public class Create_AccountActivity extends AppCompatActivity {

    Spinner bankSpinner;
    ArrayAdapter<CharSequence> bankAdapter;

    Spinner paymentDateSpinner;
    ArrayAdapter<CharSequence> paymentDateAdapter;

    Button btn1Prev;
    Button btn1Next;
    String selectedBankName;
    int selectedPaymentDay;

    GroupDetailData groupDetailData;
    AccountDetailData accountDetailData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // 1. bankName Spinner
        bankSpinner = (Spinner)findViewById(R.id.sp_bank);
        bankAdapter = ArrayAdapter.createFromResource(this, R.array.bankName, android.R.layout.simple_spinner_item);

            bankAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
            bankSpinner.setAdapter(bankAdapter);

            bankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?>  parent, View view, int position, long id) {
//                // 확인용
//                Toast.makeText(Create_AccountActivity.this,
//                        bankAdapter.getItem(position) + "을 선택했습니다.", Toast.LENGTH_LONG).show();

                    // 사용자가 선택한 은행명으로 BankName을 세팅한다.
                    selectedBankName = bankAdapter.getItem(position).toString();

                    accountDetailData = new AccountDetailData();
                    accountDetailData.setBankName(selectedBankName);
                }
                public void onNothingSelected(AdapterView  parent) {
                    // TODO: validation check (반드시 선택하도록 한다).
                }
            });


        // 2. bankNAme Spinner
        paymentDateSpinner = (Spinner)findViewById(R.id.sp_paymentDate);
        paymentDateAdapter = ArrayAdapter.createFromResource(this, R.array.paymentDate, android.R.layout.simple_spinner_item);

        paymentDateAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        paymentDateSpinner.setAdapter(paymentDateAdapter);

        paymentDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?>  parent, View view, int position, long id) {
                Toast.makeText(Create_AccountActivity.this,
                        paymentDateAdapter.getItem(position) + "을 선택했습니다.", Toast.LENGTH_LONG).show();

//                // 사용자가 선택한 입금날짜로 paymentDate를 세팅한다.
                selectedPaymentDay = Integer.parseInt(paymentDateAdapter.getItem(position).toString());

                groupDetailData = new GroupDetailData();
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
                Intent intent = new Intent(Create_AccountActivity.this, MainPageActivity.class);
                // redirect로 보내기.
                startActivity(intent);
            }
        });

        btn1Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        selectedBankName + "을 선택 했습니다.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Create_AccountActivity.this, Create_NameActivity.class);

                // Data 클래스 넘기기.
                intent.putExtra("groupDetailData", groupDetailData);
                intent.putExtra("accountDetailData", accountDetailData);

                startActivity(intent);
            }
        });
    }
}
