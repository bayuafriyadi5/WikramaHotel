package com.baytech.wikramahotel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baytech.wikramahotel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    Button btn_register;
    ImageView back;
    EditText nama,email,password,telp;
    DatabaseReference reference,userref;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = findViewById(R.id.sign_up_btn);
        back = findViewById(R.id.login_act);
        nama = findViewById(R.id.nama_register);
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        telp = findViewById(R.id.phone_register);

        back.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(i);
        });

        btn_register.setOnClickListener(v -> {
            v.startAnimation(buttonClick);
            final String xname = nama.getText().toString();
            final String xpassword = password.getText().toString();
            final String xtelp = telp.getText().toString();
            final String xemail = email.getText().toString();


            final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();

            userref = FirebaseDatabase.getInstance().getReference().child("Users").child(xtelp);
            userref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (xname.equals("") || xpassword.equals("") || xtelp.equals("") || xemail.equals("")){
                        Toast.makeText(RegisterActivity.this, "Please Fill All Data!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else if(xtelp.length() > 11 ){
                        Toast.makeText(RegisterActivity.this, "Invalid Format", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else if (dataSnapshot.exists()){
                            Toast.makeText(RegisterActivity.this, "User Already Exists!", Toast.LENGTH_SHORT).show();
                        }
                    else{
                            SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(username_key,xtelp);
                            editor.apply();

                            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(xtelp);
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    dataSnapshot.getRef().child("nama").setValue(xname);
                                    dataSnapshot.getRef().child("email").setValue(xemail);
                                    dataSnapshot.getRef().child("password").setValue(xpassword);
                                    dataSnapshot.getRef().child("no_telp").setValue(xtelp);
                                    dataSnapshot.getRef().child("account_status").setValue("Not Activated");
                                    progressDialog.dismiss();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(RegisterActivity.this, "Format Incorrect", Toast.LENGTH_SHORT).show();
                                }
                            });


                            Intent intent = new Intent(RegisterActivity.this,VerivyActivity.class);
                            intent.putExtra("mobile", xtelp);
                            startActivity(intent);
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        });


    }
}
