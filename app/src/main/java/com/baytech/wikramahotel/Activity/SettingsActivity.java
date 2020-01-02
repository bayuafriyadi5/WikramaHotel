package com.baytech.wikramahotel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import com.baytech.wikramahotel.R;
import com.baytech.wikramahotel.Resources.AppPreference;
import com.baytech.wikramahotel.Service.DailyReminderService;

public class SettingsActivity extends AppCompatActivity {

    private boolean isDaily;
    private DailyReminderService dailyReminderService;
    private AppPreference appPreference;
    ImageView back,language;
    Button logout;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Switch dailySwitch = findViewById(R.id.daily_switch);

        getUsernameLocal();

        dailyReminderService = new DailyReminderService();

        appPreference = new AppPreference(this);

        back = findViewById(R.id.back);
        language = findViewById(R.id.change_language);
        logout = findViewById(R.id.btn_log_out);

        if (appPreference.isDaily()){
            dailySwitch.setChecked(true);
        }else {
            dailySwitch.setChecked(false);
        }

        dailySwitch.setOnClickListener(v -> {
            isDaily = dailySwitch.isChecked();
            if (isDaily){
                dailySwitch.setEnabled(true);
                appPreference.setDaily(isDaily);
                dailyReminderService.setRepeatingAlarm(SettingsActivity.this, DailyReminderService.TYPE_REPEATING,
                        "10:00", getString(R.string.message_notif_daily));
            }else {
                dailySwitch.setChecked(false);
                appPreference.setDaily(isDaily);
                dailyReminderService.cancelAlarm(SettingsActivity.this, DailyReminderService.TYPE_REPEATING);
            }
        });

        language.setOnClickListener(v -> {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        });

        logout.setOnClickListener(v -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(SettingsActivity.this);
            builder1.setMessage("Are you sure want to sign out?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    (dialog, id) -> {
                        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(username_key,null);
                        editor.apply();

                        Intent intent = new Intent(SettingsActivity.this,MainActivity.class);
                        intent.putExtra("finish", true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK); // To clean up all activities
                        startActivity(intent);
                        finish();
                    });

            builder1.setNegativeButton(
                    "No",
                    (dialog, id) -> dialog.cancel());

            AlertDialog alert11 = builder1.create();
            alert11.show();
        });

        back.setOnClickListener(v -> finish());
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
