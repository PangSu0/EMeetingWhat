package com.example.emeetingwhat;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.example.emeetingwhat.createGroup.Create_AccountActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.kakao.friends.request.FriendsRequest;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

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

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public  final UserProfile userProfile = UserProfile.loadFromCache();
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
                openCreateActivity();
            }
            private void openCreateActivity() {
                Intent intent = new Intent(MainActivity.this, Create_AccountActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new MainPageFragment();
        if( fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_fragment_layout, fragment);
            ft.commit();
        }
    }
    private long time= 0;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(System.currentTimeMillis()-time>=2000){
                time=System.currentTimeMillis();
                Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
            }else if(System.currentTimeMillis()-time<2000){
                finish();
            }
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

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_home) {
            fragment = new MainPageFragment();
        } else if (id == R.id.nav_gallery) {
            fragment = new MyFriendsListFragment();
        } else if (id == R.id.nav_friendslist) {
            showTalkFriendListActivity();
        } else if (id == R.id.nav_calendar) {
            fragment = new MyCalendarFragment();
            title = "My Calendar";

        } else if (id == R.id.nav_logout) {
            /**카카오톡 로그아웃 요청**/
            //한번 로그인이 성공하면 세션 정보가 남아있어서 로그인창이 뜨지 않고 바로 onSuccess()메서드를 호출합니다.
            onClickLogout();
        } else if (id == R.id.nav_unlink) {

            /**카카오톡 탈퇴 요청**/
            onClickUnlink();
        }

        if( fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_fragment_layout, fragment);
            ft.commit();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void onClickLogout() {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                redirectLoginActivity();
            }
        });
    }

    private void onClickUnlink() {
        final String appendMessage = getString(R.string.com_kakao_confirm_unlink);
        new AlertDialog.Builder(this)
                .setMessage(appendMessage)
                .setPositiveButton(getString(R.string.com_kakao_ok_button),
                        (dialog, which) -> {
                            UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                                @Override
                                public void onFailure(ErrorResult errorResult) {
                                    Logger.e(errorResult.toString());
                                }

                                @Override
                                public void onSessionClosed(ErrorResult errorResult) {
                                    redirectLoginActivity();
                                }

                                @Override
                                public void onNotSignedUp() {
                                    // redirectSignupActivity();
                                    redirectLoginActivity();
                                }

                                @Override
                                public void onSuccess(Long result) {
                                    redirectLoginActivity();
                                }
                            });
                            dialog.dismiss();
                        })
                .setNegativeButton(getString(R.string.com_kakao_cancel_button),
                        (dialog, which) -> dialog.dismiss()).show();

    }

    private void showTalkFriendListActivity() {
        Intent intent = new Intent(this, KakaoTalkFriendListActivity.class);

        String[] friendType = {FriendsRequest.FriendType.KAKAO_TALK.name()};
        intent.putExtra(FriendsMainActivity.EXTRA_KEY_SERVICE_TYPE, friendType);
        startActivity(intent);
    }
}