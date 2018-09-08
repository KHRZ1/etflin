package com.etflin.etflin;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private BerandaFragment berandaFragment;
    private NoticeFragment noticeFragment;
    private TulisFragment tulisFragment;
    private AkunFragment akunFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFrame(berandaFragment);
                    return true;
                case R.id.navigation_notifications:
                    setFrame(noticeFragment);
                    return true;
                case R.id.navigation_tulis:
                    setFrame(tulisFragment);
                    return true;
                case R.id.navigation_akun:
                    setFrame(akunFragment);
                    return true;
                case R.id.navigation_chat:
                    Intent myIntent = new Intent(getBaseContext(), ChatRoom.class);
                    startActivity(myIntent);
                    return true;
            }
            return false;
        }
    };

    private void setFrame(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.myframe,  fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        berandaFragment  = new BerandaFragment();
        noticeFragment = new NoticeFragment();
        tulisFragment = new TulisFragment();
        akunFragment = new AkunFragment();

        setFrame(berandaFragment);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.mynav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
