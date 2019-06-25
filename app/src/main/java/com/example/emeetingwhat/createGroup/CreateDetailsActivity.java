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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kong.unirest.Unirest;

public class CreateDetailsActivity extends AppCompatActivity {

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

    TextView txt_GroupName;
    String groupName;
    int targetAmount;
    int monthlyPayment;

    GroupDetailData groupDetailData = new GroupDetailData();

    Intent intent_GroupFromPrevious;
    Intent intent_AccountFromPrevious;

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

        txt_GroupName = findViewById(R.id.txt3GroupName);
        txt_GroupName.setText(groupName);

        btn3Next.setOnClickListener(v -> {

            if (startDate == null)
                startDate = currentTime;

            if (endDate == null)
                endDate = currentTime;

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

            intent.putExtra("groupDetailData", groupDetailData);
            // intent.putExtra("accountDetailData", accountDataFromPrev);

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
}
