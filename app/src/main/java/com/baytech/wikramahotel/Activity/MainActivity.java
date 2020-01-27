package com.baytech.wikramahotel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baytech.wikramahotel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btn_login;
    TextView btn_regis;
    EditText telp,password;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.contains("usernamekey")) {
            navigateToHome();
        }

        btn_login = findViewById(R.id.btn_login);
        btn_regis = findViewById(R.id.btn_to_regis);
        telp = findViewById(R.id.telp_login);
        password = findViewById(R.id.password_login);


        btn_regis.setOnClickListener(v -> {
            Intent  i = new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(i);
        });

        btn_login.setOnClickListener(v -> {
            final String xtelp = telp.getText().toString();
            final String xpassword = password.getText().toString();

            if (xtelp.equals("")|| xpassword.equals("")) {
                Toast.makeText(MainActivity.this, "Please fill all information!!", Toast.LENGTH_SHORT).show();
            }else{
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Authenticating...");
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();

                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(xtelp);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String status = dataSnapshot.child("account_status").getValue().toString();
                            progressDialog.dismiss();
                            if(status.equals("Not Activated")){
                                Toast.makeText(MainActivity.this, "Account is Not Activated!", Toast.LENGTH_SHORT).show();
                            }else{
                                String passwordFromFirebase = Objects.requireNonNull(dataSnapshot.child("password").getValue()).toString();
                                if (xpassword.equals(passwordFromFirebase)){
                                    SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(username_key,telp.getText().toString());
                                    editor.apply();

                                    progressDialog.dismiss();

                                    navigateToHome();

                                }
                                else{

                                    progressDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "No Account!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(MainActivity.this, SliderActivity.class);
        startActivity(intent);
        finish();
    }
}
