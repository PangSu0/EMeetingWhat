package com.example.emeetingwhat;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.emeetingwhat.Util.EventDecorator;
import com.example.emeetingwhat.Util.OneDayDecorator;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.response.model.UserProfile;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MyCalendarFragment extends Fragment implements OnDateSelectedListener {
    public  final UserProfile userProfile = UserProfile.loadFromCache();
    private TextView nickName;
    MaterialCalendarView calendarWidget;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private TextView groupName;
    private TextView monthlyPayment;
    private LinearLayout userInfo;

    public MyCalendarFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycalendar_fragment, container, false);
        nickName = (TextView) view.findViewById(R.id.textView_nickname);
        nickName.setText(userProfile.getNickname());
        calendarWidget = (MaterialCalendarView) view.findViewById(R.id.calendarView);

        calendarWidget.addDecorators(
                oneDayDecorator
        );

        // 원하는 날짜에 표시를 한다.
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());

        groupName = (TextView) view.findViewById(R.id.txt_CalGroupName);
        monthlyPayment = (TextView) view.findViewById(R.id.txt_CalMonthlyPayment);
        userInfo = (LinearLayout) view.findViewById(R.id.userInfo);


        // 해당 날짜를 클릭하면 상세 화면을 보여준다.
        calendarWidget.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                int selectedDate = calendarDay.getDay();

                // 하드 코딩.
                if (selectedDate == 5) {
                    groupName.setText("모두를 위한 생일계");
                    monthlyPayment.setText("50000 원");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("모임명 : " + groupName.getText() + "\n" + "입금액 : " + monthlyPayment.getText()).setTitle("상세 일정을 확인하세요.");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else if (selectedDate == 20) {
                    groupName.setText("겨울 온천계");
                    monthlyPayment.setText("500000 원");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("모임명 : " + groupName.getText() + "\n" + "입금액 : " + monthlyPayment.getText()).setTitle("상세 일정을 확인하세요.");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("일정이 없습니다.").setTitle("상세 일정을 확인하세요.");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });


        return view;
    }

    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected) {
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 6; i < 13; i++) {
                final CalendarDay paymentDay1 = CalendarDay.from(2019, i, 5);
                final CalendarDay paymentDay2 = CalendarDay.from(2019, i, 20);

                dates.add(paymentDay1);
                dates.add(paymentDay2);
            }
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            calendarWidget.addDecorator(new EventDecorator(Color.RED, calendarDays));

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
