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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Create_DetailsActivity extends AppCompatActivity {

    private Button btn3Next;
    private Button btn3Prev;
    private EditText et_amount;

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

    TextView txt_GroupName;
    String groupName;
    int targetAmount;

    GroupDetailData groupDetailData = new GroupDetailData();
    AccountDetailData accountDetailData = new AccountDetailData();

    Intent intent_GroupFromPrevious;
    Intent intent_AccountFromPrevious;

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
        et_amount = (EditText)findViewById(R.id.et_Amount);

        intent_GroupFromPrevious = getIntent();
        intent_AccountFromPrevious = getIntent();

        groupDataFromPrev =  (GroupDetailData)intent_GroupFromPrevious.getSerializableExtra("groupDetailData");
        accountDataFromPrev =  (AccountDetailData)intent_AccountFromPrevious.getSerializableExtra("accountDetailData");

        txt_GroupName = findViewById(R.id.txt3GroupName);
        txt_GroupName.setText(groupName);

        btn3Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: 친구 초대 페이지로 이동 (수정 필요)
                Intent intent = new Intent(Create_DetailsActivity.this, Create_FriendsActivity.class);
                targetAmount = Integer.parseInt(et_amount.getText().toString());

                groupDetailData.setCreateDate(startDate);
                groupDetailData.setEndDate(endDate);
                groupDetailData.setTargetAmount(targetAmount);
                groupDetailData.setName(groupDataFromPrev.getName());
                groupDetailData.setGroupType(groupDataFromPrev.getGroupType());
                groupDetailData.setPaymentDay(groupDataFromPrev.getPaymentDay());
                accountDetailData.setBankName(accountDataFromPrev.getBankName());

                intent.putExtra("groupDetailData", groupDetailData);
                intent.putExtra("accountDetailData", accountDataFromPrev);

                startActivity(intent);
            }
        });

        // Prev 버튼을 눌렀을 때
        btn3Prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Create_DetailsActivity.this, Create_NameActivity.class);
                startActivity(intent);
            }
        });

        // 모임 기간 세팅
        et_StartDate = (EditText) findViewById(R.id.et_StartDate);
        calendar = Calendar.getInstance();
        startDay = calendar.get(Calendar.DAY_OF_MONTH);
        startMonth = calendar.get(Calendar.MONTH);
        startYear = calendar.get(Calendar.YEAR);

        et_StartDate.setText(startYear + " /" + (startMonth + 1) + " /" + startDay);

        et_StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateDialog();
            }
        });

        // 모임 기간 세팅
        et_EndDate = (EditText) findViewById(R.id.et_EndDate);

        endDay = calendar.get(Calendar.DAY_OF_MONTH);
        endMonth = calendar.get(Calendar.MONTH);
        endYear = calendar.get(Calendar.YEAR);

        et_EndDate.setText(endYear + " /" + (endMonth + 1) + " /" + endDay);

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
}
