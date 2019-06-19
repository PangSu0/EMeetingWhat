package com.example.emeetingwhat;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.kakao.usermgmt.response.model.UserProfile;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String IP_ADDRESS = "61.108.100.36";

    private static String TAG = "phptest";
    private TextView mTextViewResult;
    private ArrayList<GroupDetailData> mArrayList;
    private GroupsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;
    private ListView m_group_listview = null;
    private LinearLayoutManager mLinearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // 데이터베이스 테스트
        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);
        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        mArrayList = new ArrayList<>();

        mAdapter = new GroupsAdapter(this, mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/selectGroupList.php", "");
        // RecyclerView의 줄(row) 사이에 수평선을 넣기위해 사용됩니다.
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext()
                , mRecyclerView, new ClickListener()
                {

                    @Override
                    public void onClick(View view, int position) {
                        GroupDetailData groupData = mArrayList.get(position);
                        Toast.makeText(getApplicationContext(), groupData.getGroupType(), Toast.LENGTH_SHORT).show();
                        if( groupData.getGroupType().equals("group")){
                            Intent intent = new Intent(MainPageActivity.this, GroupDetailActivity.class);
                            startActivity(intent);
                            finish();
                        }else if( groupData.getGroupType().equals("individual")){
                            Intent intent = new Intent(MainPageActivity.this, IndividualDetailActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        final UserProfile userProfile = UserProfile.loadFromCache();
        TextView tv_nickname = (TextView) findViewById(R.id.profileTextView);
        TextView tv_email = (TextView) findViewById(R.id.emailTextView);
        tv_nickname.setText(userProfile.getNickname());
        tv_email.setText(userProfile.getEmail());
        if (userProfile != null) {
            NetworkImageView im_profile = (NetworkImageView) findViewById(R.id.profileImageView);
            String profileUrl = userProfile.getThumbnailImagePath();
            Application app  = GlobalApplication.getGlobalApplicationContext();
            if (profileUrl != null && profileUrl.length() > 0) {
                im_profile.setImageUrl(profileUrl, ((GlobalApplication) app).getImageLoader());
            } else {
                im_profile.setImageResource(R.drawable.thumb_story);
            }
        }

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainPageActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

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
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult() {
        String TAG_JSON = "groupList";
        String TAG_GROUPID = "GroupId";
        String TAG_USERID = "UserId";
        String TAG_NAME = "Name";
        String TAG_CREATEDATE = "CreateDate";
        String TAG_ENDDATE = "EndDate";
        String TAG_TARGETAMOUNT = "TargetAmount";
        String TAG_PAYMENTDATE = "PaymentDate";
        String TAG_MONTHLYPAYMENT = "MonthlyPayment";
        String TAG_GROUPTYPE = "GroupType";
        String TAG_ACCOUNTHOLDERID = "AccountHolderId";
        String TAG_PAYMENTDAY = "PaymentDay";


        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                int groupId = item.getInt(TAG_GROUPID);
                int userId = item.getInt(TAG_USERID);
                String name = item.getString(TAG_NAME);
                String s_createDate = item.getString(TAG_CREATEDATE);
                String s_endDate = item.getString(TAG_ENDDATE);
                int targetAmount = item.getInt(TAG_TARGETAMOUNT);
                String s_paymentDate = item.getString(TAG_PAYMENTDATE);
                int monthlyPayment = item.getInt(TAG_MONTHLYPAYMENT);
                String groupType = item.getString(TAG_GROUPTYPE);
                int accountHolderId = item.getInt(TAG_ACCOUNTHOLDERID);
                int paymentDay = item.getInt(TAG_PAYMENTDAY);

                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date createDate = null;
                Date endDate = null;
                Date paymentDate = null;
                try {
                    createDate = transFormat.parse(s_createDate);
                    endDate = transFormat.parse(s_endDate);
                    paymentDate = transFormat.parse(s_paymentDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                GroupDetailData groupDetailData = new GroupDetailData();

                groupDetailData.setGroupId(groupId);
                groupDetailData.setUserId(userId);
                groupDetailData.setName(name);
                groupDetailData.setCreateDate(createDate);
                groupDetailData.setEndDate(endDate);
                groupDetailData.setTargetAmount(targetAmount);
                groupDetailData.setPaymentDate(paymentDate);
                groupDetailData.setMonthlyPayment(monthlyPayment);
                groupDetailData.setGroupType(groupType);
                groupDetailData.setAccountHolderId(accountHolderId);
                groupDetailData.setPaymentDay(paymentDay);


                mArrayList.add(groupDetailData);
                mAdapter.notifyDataSetChanged();
            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainPageActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainPageActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

}
