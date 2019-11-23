package com.baytech.wikramahotel.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.baytech.wikramahotel.Fragment.AccountFragment;
import com.baytech.wikramahotel.Fragment.BookingFragment;
import com.baytech.wikramahotel.Fragment.HistoryFragment;
import com.baytech.wikramahotel.Fragment.HomeFragment;
import com.baytech.wikramahotel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class DashboardActivty extends AppCompatActivity {

    private long backpressedTime;
    private Toast backToast;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getUsernameLocal();
        ShowHomeFragment();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ShowHomeFragment();
                    return true;
                case R.id.navigation_booking:
                    ShowBookingFragment();
                    return true;
                case R.id.navigation_history:
                    ShowHistoryFragment();
                    return true;
                case R.id.navigation_profile:
                    ShowAccountFragment();
                    return true;
            }
            return false;
        }
    };

    private void ShowHomeFragment() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        HomeFragment mHomeFragment = new HomeFragment();
        mFragmentTransaction.replace(R.id.container, mHomeFragment, HomeFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }
    private void ShowBookingFragment() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        BookingFragment mBookingFragment = new BookingFragment();
        mFragmentTransaction.replace(R.id.container, mBookingFragment, BookingFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }
    private void ShowHistoryFragment() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        HistoryFragment mHistoryFragment = new HistoryFragment();
        mFragmentTransaction.replace(R.id.container, mHistoryFragment, HistoryFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }
    private void ShowAccountFragment() {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        AccountFragment mAccountFragment = new AccountFragment();
        mFragmentTransaction.replace(R.id.container, mAccountFragment, HistoryFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (backpressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else {
            backToast = Toast.makeText(this, "Press again to close app", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backpressedTime = System.currentTimeMillis();
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

}
