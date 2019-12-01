package com.baytech.wikramahotel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baytech.wikramahotel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class PickDateActivity extends AppCompatActivity {

    Button next;
    CardView pick_check_in,pick_check_out;
    ImageView back;
    TextView check_in_date,check_out_date;
    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    Integer valueselisih = 0;
    Integer valuecheckin = 0;
    Integer valuecheckout = 0;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    final String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date);

        getUsernameLocal();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        back = findViewById(R.id.back_to);
        pick_check_in = findViewById(R.id.pick_check_in_date);
        pick_check_out = findViewById(R.id.pick_check_out_date);
        check_in_date = findViewById(R.id.in);
        check_out_date = findViewById(R.id.out);
        next = findViewById(R.id.next);

        if (check_in_date.getText().equals("Check In") || check_out_date.getText().equals("Check Out")){
            next.setVisibility(View.INVISIBLE);
            next.setEnabled(false);
        }

        pick_check_in.setOnClickListener(v -> {
            showDateDialogIn();
        });

        pick_check_out.setOnClickListener(v -> {
            showDateDialogOut();
            next.setVisibility(View.VISIBLE);
            next.setEnabled(true);
        });

        back.setOnClickListener(v -> {
            finish();
        });
        Date cal = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String dateToday = dateFormat.format(cal);

        next.setOnClickListener(v -> {
            validasitgl();
            reference = FirebaseDatabase.getInstance().getReference().child("Booked").child(username_key_new).child(currentTime);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (valueselisih <= 0){
                       Toast.makeText(PickDateActivity.this, "Incorrect Date Format!", Toast.LENGTH_SHORT).show();
                    }if(valueselisih >=1 ){
                        reference.getRef().child("check_in").setValue(check_in_date.getText().toString());
                        reference.getRef().child("check_out").setValue(check_out_date.getText().toString());
                        reference.getRef().child("date_booking").setValue(dateToday);
                        reference.getRef().child("time_booking").setValue(currentTime);

                        final ProgressDialog progressDialog = new ProgressDialog(PickDateActivity.this);
                        progressDialog.setMessage("Please wait...");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        Intent gotoroom = new Intent(PickDateActivity.this, BookingActivity.class);
                        gotoroom.putExtra("Booked",currentTime);
                        startActivity(gotoroom);

                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        });
    }

    private void validasitgl() {
        reference = FirebaseDatabase.getInstance().getReference().child("Booked").child(username_key_new).child(currentTime);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String in = check_in_date.getText().toString();
                String out = check_out_date.getText().toString();

                valuecheckin = Integer.parseInt(in.replaceAll("[\\D]", ""));
                valuecheckout = Integer.parseInt(out.replaceAll("[\\D]", ""));

                Integer awal_in = valuecheckin/1000000;
                Integer awal_out = valuecheckout/1000000;

                valueselisih =  awal_out - awal_in;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showDateDialogIn(){

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {

            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);

            check_in_date.setText(dateFormatter.format(newDate.getTime()));
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showDateDialogOut(){

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {

            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);

            check_out_date.setText(dateFormatter.format(newDate.getTime()));
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
