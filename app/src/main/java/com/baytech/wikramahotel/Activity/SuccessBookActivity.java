package com.baytech.wikramahotel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baytech.wikramahotel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SuccessBookActivity extends AppCompatActivity {


    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    Button btn_next;
    TextView total;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_book);

        getUsernameLocal();

        btn_next = findViewById(R.id.btn_next);
        total = findViewById(R.id.total_harga_success);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String booked = bundle.getString("total");

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new).child("Booked").child("Reguler");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                total.setText(dataSnapshot.child("total_harga").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuccessBookActivity.this,DashboardActivty.class);
                startActivity(i);
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
