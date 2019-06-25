package com.example.emeetingwhat;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.toolbox.NetworkImageView;
import com.example.emeetingwhat.createGroup.CreateAccountActivity;
import com.example.emeetingwhat.openAPI.FetchData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.kakao.usermgmt.response.model.UserProfile;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //
    Button click;
    public static TextView data;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

//
        click = (Button) findViewById(R.id.button);
        data = (TextView) findViewById(R.id.fetchedData);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchData process = new FetchData();
                process.execute();
            }
        });
  //



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateActivity();
            }
            private void openCreateActivity() {
                Intent intent = new Intent(MainPageActivity.this, CreateAccountActivity.class);
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

        TextView tv_nickname;
        TextView tv_email;
        NetworkImageView iv_Thumbnail;

        // Inflate the menu; this adds items to the action bar if it is present.
        final UserProfile userProfile = UserProfile.loadFromCache();
        tv_nickname = (TextView) findViewById(R.id.txtNickname);
        tv_email = (TextView) findViewById(R.id.txtEmail);

        tv_nickname.setText(userProfile.getNickname());
        tv_email.setText(userProfile.getEmail());

//        if (userProfile != null) {
//            iv_Thumbnail = (NetworkImageView) findViewById(R.id.iv_profile);
//            String profileUrl = userProfile.getThumbnailImagePath();
//            Application app  = GlobalApplication.getGlobalApplicationContext();
//            if (profileUrl != null && profileUrl.length() > 0) {
//                iv_Thumbnail.setImageUrl(profileUrl, ((GlobalApplication) app).getImageLoader());
//            } else {
//                iv_Thumbnail.setImageResource(R.drawable.kakao_account_logo);
//            }
//        }

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
}
