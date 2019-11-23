package com.baytech.wikramahotel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class StrukActivity extends AppCompatActivity {

    DatabaseReference reference,cancel_book;
    ImageView back;
    Button cancel;
    TextView total_harga,judul;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_struk);

        getUsernameLocal();

        back = findViewById(R.id.back_history);
        total_harga = findViewById(R.id.total_harga);
        judul = findViewById(R.id.judul);
        cancel = findViewById(R.id.cancel_book);

        back.bringToFront();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String total = bundle.getString("total_harga");

        reference = FirebaseDatabase.getInstance().getReference().child("Booked").child(username_key_new).child("Book").child(total);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("status").getValue().equals("Canceled")){
                    cancel.setVisibility(View.INVISIBLE);
                    cancel.setEnabled(false);
                }
                total_harga.setText("Rp. " + (dataSnapshot.child("total_harga").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        
        judul.setOnClickListener(v -> {
            Toast.makeText(this, "anjay", Toast.LENGTH_SHORT).show();
        });

        back.setOnClickListener(v -> {
            finish();
        });

        cancel.setOnClickListener(v -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(StrukActivity.this);
            builder1.setMessage("Are you sure want to cancel?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    (dialog, id) -> {
                        cancel_book = FirebaseDatabase.getInstance().getReference().child("Booked").child(username_key_new).child("Book").child(total);
                        cancel_book.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                cancel_book.getRef().child("status").setValue("Canceled");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                       Intent i = new Intent(StrukActivity.this,DashboardActivty.class);
                       startActivity(i);
                    });

            builder1.setNegativeButton(
                    "No",
                    (dialog, id) -> dialog.cancel());

            AlertDialog alert11 = builder1.create();
            alert11.show();
        });
    }

    private void to_dashboard() {
        Intent i = new Intent(StrukActivity.this,DashboardActivty.class);
        startActivity(i);
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
