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
import com.example.emeetingwhat.R;
import com.example.emeetingwhat.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateDetailsActivity extends AppCompatActivity {

    private Button btn3Next;
    private Button btn3Prev;
    private EditText et_TargetAmount;
    private TextView tv_TargetAmountInfo;
    private EditText et_amount;
    private EditText editText_monthlyPayment;

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

    // 처음 보여지는 날짜를 Korea Locale로 세팅.
    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    Date currentTime = new Date();
    String getCurrentTime = transFormat.format(currentTime);

    TextView txt_GroupName;
    String groupName;
    int targetAmount;
    int monthlyPayment;

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
        et_TargetAmount = (EditText)findViewById(R.id.et_TargetAmount);
        tv_TargetAmountInfo = (TextView)findViewById(R.id.tv_TargetAmountInfo);
        et_amount = (EditText)findViewById(R.id.et_Amount);

        // 월별 입금 금액
        editText_monthlyPayment = (EditText) findViewById(R.id.editText_monthlyPayment);

        intent_GroupFromPrevious = getIntent();
        intent_AccountFromPrevious = getIntent();

        getGroupDetailData();
        getAccountDetailData();

        txt_GroupName = findViewById(R.id.txt3GroupName);
        txt_GroupName.setText(groupName);

        btn3Next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // 목표 금액 Validation Check
                if (Validator.isEmpty(et_TargetAmount)) {
                    tv_TargetAmountInfo.setText("목표금액을 입력하세요");

                } else {
                    fillGroupDetailData(startDate, endDate, et_TargetAmount);
                // TODO: 친구 초대 페이지로 이동 (수정 필요)
                Intent intent = new Intent(CreateDetailsActivity.this, CreateFriendsActivity.class);
                targetAmount = Integer.parseInt(et_amount.getText().toString());
                monthlyPayment = Integer.parseInt(editText_monthlyPayment.getText().toString());
                groupDetailData.setCreateDate(startDate);
                groupDetailData.setEndDate(endDate);
                groupDetailData.setTargetAmount(targetAmount);
                groupDetailData.setMonthlyPayment(monthlyPayment);
                groupDetailData.setName(groupDataFromPrev.getName());
                groupDetailData.setGroupType(groupDataFromPrev.getGroupType());
                groupDetailData.setPaymentDay(groupDataFromPrev.getPaymentDay());
                groupDetailData.setBankName(groupDataFromPrev.getBankName());
                groupDetailData.setAccountNumber(groupDataFromPrev.getAccountNumber());
                // accountDetailData.setBankName(accountDataFromPrev.getBankName());

                intent.putExtra("groupDetailData", groupDetailData);
                // intent.putExtra("accountDetailData", accountDataFromPrev);

                    Intent intent = new Intent(CreateDetailsActivity.this, CreateFriendsActivity.class);
                    intent.putExtra("groupDetailData", groupDetailData);
                    intent.putExtra("accountDetailData", accountDataFromPrev);
                    startActivity(intent);
                }
            }
        });

        // Prev 버튼을 눌렀을 때
        btn3Prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CreateDetailsActivity.this, CreateNameActivity.class);
                startActivity(intent);
            }
        });


        // 모임 기간 세팅 (시작일)
        et_StartDate = (EditText) findViewById(R.id.et_StartDate);
        et_StartDate.setText(getCurrentTime);

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
        et_EndDate.setText(getCurrentTime);

        endDay = calendar.get(Calendar.DAY_OF_MONTH);
        endMonth = calendar.get(Calendar.MONTH);
        endYear = calendar.get(Calendar.YEAR);

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
                calendar.set(year, monthOfYear, dayOfMonth);
                startDate = calendar.getTime();

                String dateSelected = transFormat.format(startDate);
                et_StartDate.setText(dateSelected);

            }};
        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, startYear, startMonth, startDay);
        dpDialog.show();
    }

    public void endDateDialog() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                endDate = calendar.getTime();

                String dateSelected = transFormat.format(endDate);
                et_EndDate.setText(dateSelected);
            }};
        DatePickerDialog dpDialog = new DatePickerDialog(this, listener, endYear, endMonth, endDay);
        dpDialog.show();
    }

    private void fillGroupDetailData(Date startDate, Date endDate, EditText et_TargetAmount) {
        // 개인 수령일 때: 사용자가 입력한 모임명과 모임 유형을 Name으로 세팅한다.
        targetAmount = Integer.parseInt(et_TargetAmount.getText().toString());

        if (startDate == null)
            startDate = currentTime;

        if (endDate == null)
            endDate = currentTime;

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
