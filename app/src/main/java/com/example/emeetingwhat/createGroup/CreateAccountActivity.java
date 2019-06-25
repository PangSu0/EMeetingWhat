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

public class CreateAccountActivity extends AppCompatActivity {

    private Spinner bankSpinner;
    private ArrayAdapter<CharSequence> bankAdapter;

    private Spinner paymentDateSpinner;
    private ArrayAdapter<CharSequence> paymentDateAdapter;

    private Button btn1Prev;
    private Button btn1Next;

    private GroupDetailData groupDetailData;
    private AccountDetailData accountDetailData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        setDataToBankNameSpinner();

        setDataToPaymentDaySpinner();

        // 앞, 뒤로가기 버튼
        btn1Next = (Button)findViewById(R.id.btn1Next);
        btn1Prev = (Button)findViewById(R.id.btn1Prev);

        // Prev 버튼을 눌렀을 때
        btn1Prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, MainPageActivity.class);
                // redirect로 보내기.
                startActivity(intent);
            }
        });

        btn1Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, CreateNameActivity.class);

                // Data 클래스 넘기기.
                intent.putExtra("groupDetailData", groupDetailData);
                intent.putExtra("accountDetailData", accountDetailData);

                startActivity(intent);
            }
        });
    }

    private void setDataToPaymentDaySpinner() {
        // paymentDay Spinner
        paymentDateSpinner = (Spinner)findViewById(R.id.sp_paymentDate);
        paymentDateAdapter = ArrayAdapter.createFromResource(this, R.array.paymentDate, android.R.layout.simple_spinner_item);

        paymentDateAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        paymentDateSpinner.setAdapter(paymentDateAdapter);
        paymentDateSpinner.setPrompt("매월 입금일");

        paymentDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?>  parent, View view, int position, long id) {
                // 확인용
                Toast.makeText(CreateAccountActivity.this,
                        paymentDateAdapter.getItem(position) + "을 선택했습니다.", Toast.LENGTH_SHORT).show();

                // 사용자가 선택한 입금날짜로 paymentDay를 세팅한다.
                int selectedPaymentDay = Integer.parseInt(paymentDateAdapter.getItem(position).toString());
                fillGroupDetailData(selectedPaymentDay);
            }
            public void onNothingSelected(AdapterView  parent) {
                Toast.makeText(CreateAccountActivity.this, "입금날짜를 선택하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataToBankNameSpinner() {
        // bankName Spinner
        bankSpinner = (Spinner)findViewById(R.id.sp_bank);
        bankAdapter = ArrayAdapter.createFromResource(this, R.array.bankName, android.R.layout.simple_spinner_item);

        bankAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        bankSpinner.setAdapter(bankAdapter);
        bankSpinner.setPrompt("연동할 계좌 은행");

        bankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?>  parent, View view, int position, long id) {
                // 확인용
                Toast.makeText(CreateAccountActivity.this,
                        bankAdapter.getItem(position) + "을 선택했습니다.", Toast.LENGTH_SHORT).show();

                // 사용자가 선택한 은행명으로 BankName을 세팅한다.
                String selectedBankName = bankAdapter.getItem(position).toString();

                fillAccountDetailData(selectedBankName);
            }
            public void onNothingSelected(AdapterView  parent) {
                Toast.makeText(CreateAccountActivity.this, "은행을 선택하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillGroupDetailData(int bankName) {
        groupDetailData = new GroupDetailData();
        groupDetailData.setPaymentDay(bankName);
    }

    private void fillAccountDetailData(String paymentDay) {
        accountDetailData = new AccountDetailData();
        accountDetailData.setBankName(paymentDay);
    }
}
