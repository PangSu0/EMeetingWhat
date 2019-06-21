package com.example.emeetingwhat.createGroup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emeetingwhat.Data.AccountDetailData;
import com.example.emeetingwhat.Data.GroupDetailData;
import com.example.emeetingwhat.GroupType;
import com.example.emeetingwhat.R;
import com.example.emeetingwhat.Validator;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Create_DetailsActivity extends AppCompatActivity {

    private Button btn3Next;
    private Button btn3Prev;
    private EditText et_Targetamount;
    private TextView tv_TargetAmountInfo;

    private Calendar calendar;

    private int endDay;
    private int endMonth;
    private int endYear;

    private int startDay;
    private int startMonth;
    private int startYear;

    private EditText et_StartDate;
    private EditText et_EndDate;

    private Date startDate;
    private Date endDate;

    TextView txt_GroupName;
    String groupName;
    int targetAmount;

    GroupDetailData groupDetailData = new GroupDetailData();
    AccountDetailData accountDetailData = new AccountDetailData();

    GroupDetailData groupDataFromPrev;
    AccountDetailData accountDataFromPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_details);

        // 앞, 뒤로 가기 버튼
        btn3Next = (Button)findViewById(R.id.btn3Next);
        btn3Prev = (Button)findViewById(R.id.btn3Prev);

        // 목표 금액
        et_Targetamount = (EditText)findViewById(R.id.et_TargetAmount);
        tv_TargetAmountInfo = (TextView)findViewById(R.id.tv_TargetAmountInfo);

        getGroupDetailData();
        getAccountDetailData();

        txt_GroupName = findViewById(R.id.txt3GroupName);
        txt_GroupName.setText(groupName);

        btn3Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // 목표 금액 Validation Check
                if (Validator.isEmpty(et_Targetamount)) {
                    tv_TargetAmountInfo.setText("목표금액을 입력하세요");

                } else {
                    fillGroupDetailData(startDate, endDate, et_Targetamount);

                    Intent intent = new Intent(Create_DetailsActivity.this, Create_FriendsActivity.class);
                    intent.putExtra("groupDetailData", groupDetailData);
                    intent.putExtra("accountDetailData", accountDataFromPrev);
                    startActivity(intent);
                }
            }
        });

        // Prev 버튼을 눌렀을 때
        btn3Prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Create_DetailsActivity.this, Create_NameActivity.class);
                startActivity(intent);
            }
        });


        // 모임 기간 세팅 (시작일)
        et_StartDate = (EditText) findViewById(R.id.et_StartDate);
        calendar = Calendar.getInstance();
        startDay = calendar.get(Calendar.DAY_OF_MONTH);
        startMonth = calendar.get(Calendar.MONTH);
        startYear = calendar.get(Calendar.YEAR);
        et_StartDate.setText(startYear + "/" + (startMonth + 1) + "/" + startDay);

        et_StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateDialog();
            }
        });

        // 모임 기간 세팅(마지막일)
        et_EndDate = (EditText) findViewById(R.id.et_EndDate);
        endDay = calendar.get(Calendar.DAY_OF_MONTH);
        endMonth = calendar.get(Calendar.MONTH);
        endYear = calendar.get(Calendar.YEAR);
        et_EndDate.setText(endYear + "/" + (endMonth + 1) + "/" + endDay);

        et_EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDateDialog();
            }
        });
    }

    public void startDateDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                et_StartDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                calendar.set(year, monthOfYear, dayOfMonth);
                startDate = calendar.getTime();
            }};
        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, startYear, startMonth, startDay);
        dpDialog.show();
    }

    public void endDateDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                et_EndDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                calendar.set(year, monthOfYear, dayOfMonth);
                endDate = calendar.getTime();
            }};
        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, endYear, endMonth, endDay);
        dpDialog.show();
    }

    private void fillGroupDetailData(Date startDate, Date endDate, EditText et_Targetamount) {
        // 개인 수령일 때: 사용자가 입력한 모임명과 모임 유형을 Name으로 세팅한다.
        targetAmount = Integer.parseInt(et_Targetamount.getText().toString());

        groupDetailData.setCreateDate(startDate);
        groupDetailData.setEndDate(endDate);
        groupDetailData.setTargetAmount(targetAmount);
        groupDetailData.setName(groupDataFromPrev.getName());
        groupDetailData.setGroupType(groupDataFromPrev.getGroupType());
        groupDetailData.setPaymentDay(groupDataFromPrev.getPaymentDay());
        accountDetailData.setBankName(accountDataFromPrev.getBankName());
    }

    private void getGroupDetailData() {
        Intent intent_GroupFromPrevious = getIntent();
        groupDataFromPrev = (GroupDetailData)intent_GroupFromPrevious.getSerializableExtra("groupDetailData");
    }

    private void getAccountDetailData() {
        Intent intent_AccountFromPrevious = getIntent();
        accountDataFromPrev = (AccountDetailData)intent_AccountFromPrevious.getSerializableExtra("accountDetailData");
    }

}
