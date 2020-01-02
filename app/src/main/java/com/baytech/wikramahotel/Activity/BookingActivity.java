package com.baytech.wikramahotel.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baytech.wikramahotel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingActivity extends AppCompatActivity {

    DatabaseReference reference,booking,date,room_value,cancel_book;
    ImageView back;
    CardView book,min_bed,add_bed,min_room,add_room;
    TextView jml,harga,textjumlahroom,textjumlahbed,check_in,check_out;
    Integer valuejumlahroom = 0;
    Integer valuejumlahbed = 0;
    Integer harga_room = 0;
    Integer harga_bed = 0;
    Integer valuetotal = 0;
    Integer total_room = 0;
    Integer valuetotalbed = 0;
    Integer valueselisih = 0;
    Integer valuecheckin = 0;
    Integer valuecheckout = 0;
    Integer jumlah_room = 0;
    Integer total = 0;
    Switch extra_bed;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    private boolean is_extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        getUsernameLocal();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String room = bundle.getString("Booked");

        textjumlahroom = findViewById(R.id.jumlah_room);
        textjumlahbed = findViewById(R.id.jumlah_bed);
        min_bed = findViewById(R.id.min_bed);
        add_bed = findViewById(R.id.add_bed);
        add_room = findViewById(R.id.add_room);
        min_room = findViewById(R.id.min_room);
        harga = findViewById(R.id.harga_kamar);
        jml = findViewById(R.id.total_harga);
        check_in = findViewById(R.id.check_in);
        check_out = findViewById(R.id.check_out);

        min_bed.animate().alpha(0).setDuration(300).start();
        add_bed.animate().alpha(0).setDuration(300).start();
        textjumlahbed.animate().alpha(0).setDuration(300).start();
        min_bed.setEnabled(false);
        add_bed.setEnabled(false);

        min_room.animate().alpha(0).setDuration(300).start();
        min_room.setEnabled(false);

        textjumlahroom.setText(valuejumlahroom.toString());
        textjumlahbed.setText(valuejumlahbed.toString());

        date = FirebaseDatabase.getInstance().getReference().child("Booked").child(username_key_new).child(room);
        date.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) {
                    return;
                }

                check_in.setText(dataSnapshot.child("check_in").getValue().toString());
                check_out.setText(dataSnapshot.child("check_out").getValue().toString());

                String in = check_in.getText().toString();
                String out = check_out.getText().toString();

                valuecheckin = Integer.parseInt(in.replaceAll("[\\D]", ""));
                valuecheckout = Integer.parseInt(out.replaceAll("[\\D]", ""));

                Integer awal_in = valuecheckin/1000000;
                Integer awal_out = valuecheckout/1000000;

                valuetotal = harga_room * valuejumlahroom ;
                valuetotalbed = harga_bed * valuejumlahbed;
                valueselisih =  awal_out - awal_in;
                total = valuetotalbed + valuetotal * valueselisih;
                jml.setText("Rp." + total);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Room").child("Reguler");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                harga_room = Integer.valueOf(dataSnapshot.child("harga").getValue().toString());
                harga.setText("Rp." + harga_room);

                harga_bed = Integer.valueOf(dataSnapshot.child("extra_bed").getValue().toString());
                jumlah_room = Integer.valueOf(dataSnapshot.child("jumlah_kamar").getValue().toString());

                valuetotal = harga_room * valuejumlahroom ;
                valuetotalbed = harga_bed * valuejumlahbed;
//
                total = valuetotalbed + valuetotal * valueselisih;
                jml.setText("Rp." + total);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        add_room.setOnClickListener(v -> {
            valuejumlahroom += 1;
            textjumlahroom.setText(valuejumlahroom.toString());
            if (valuejumlahroom >1 ){
                min_room.animate().alpha(1).setDuration(300).start();
                min_room.setEnabled(true);
            }
            valuetotal = harga_room * valuejumlahroom;
            total_room = jumlah_room - valuejumlahroom;
            total = valuetotalbed + valuetotal * valueselisih;
            jml.setText("Rp." + total);
            if (valuejumlahroom >= 6){
                add_room.animate().alpha(0).setDuration(300).start();
                add_room.setEnabled(false);
            }
        });

        min_room.setOnClickListener(v -> {
            valuejumlahroom -= 1;
            textjumlahroom.setText(valuejumlahroom.toString());
            if (valuejumlahroom < 2 ){
                min_room.animate().alpha(0).setDuration(300).start();
                min_room.setEnabled(false);
            }
            valuetotal = harga_room * valuejumlahroom;
            total = valuetotalbed + valuetotal * valueselisih;
            jml.setText("Rp." + total);
            if (valuejumlahroom < 6){
                add_room.animate().alpha(1).setDuration(300).start();
                add_room.setEnabled(true);
            }
        });

        back = findViewById(R.id.back_to_book);

        back.bringToFront();

        back.setOnClickListener(v -> finish());

        book = findViewById(R.id.btn_book);

        book.setOnClickListener(v -> {

            if (jumlah_room < valuejumlahroom){
                Toast.makeText(this, "Rooms Not available for this amount!", Toast.LENGTH_SHORT).show();
            }else if(valuejumlahroom <= 0){
                Toast.makeText(this, "Please Input Your Room Quantity", Toast.LENGTH_SHORT).show();
            }else{
                booking = FirebaseDatabase.getInstance().getReference().child("Booked").child(username_key_new).child(room);
                booking.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        booking.getRef().child("total_harga").setValue(total.toString());
                        booking.getRef().child("harga_extra_bed").setValue(valuetotalbed.toString());
                        booking.getRef().child("harga_room").setValue(valuetotal.toString());
                        booking.getRef().child("status").setValue("In Progress");
                        booking.getRef().child("no_telp").setValue(username_key_new);
                        booking.getRef().child("jumlah_kamar").setValue(valuejumlahroom);

                        valueselisih = 0;


                        final ProgressDialog progressDialog = new ProgressDialog(BookingActivity.this);
                        progressDialog.setMessage("Please wait...");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCancelable(false);
                        progressDialog.show();


                        AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                        builder.setMessage(getString(R.string.dilalog));

                        builder.setNeutralButton("Ok", (dialog, id) -> {
                            Intent gotosuccess = new Intent(BookingActivity.this, DashboardActivty.class);
                            startActivity(gotosuccess);
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();


                        progressDialog.dismiss();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                room_value = FirebaseDatabase.getInstance().getReference().child("Room").child("Reguler");
                room_value.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        room_value.getRef().child("jumlah_kamar").setValue(total_room);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


        extra_bed = findViewById(R.id.switch_extra_bed);

        extra_bed.setOnClickListener(v -> {
            is_extra = extra_bed.isChecked();
            if (is_extra){
                add_bed.animate().alpha(1).setDuration(300).start();
                textjumlahbed.animate().alpha(1).setDuration(300).start();
                add_bed.setEnabled(true);

            }else{
                min_bed.animate().alpha(0).setDuration(300).start();
                add_bed.animate().alpha(0).setDuration(300).start();
                textjumlahbed.animate().alpha(0).setDuration(300).start();
                min_bed.setEnabled(false);
                add_bed.setEnabled(false);
            }
        });

        add_bed.setOnClickListener(v -> {
            valuejumlahbed += 1;
            textjumlahbed.setText(valuejumlahbed.toString());
            if (valuejumlahbed > 0 ){
                min_bed.animate().alpha(1).setDuration(300).start();
                min_bed.setEnabled(true);
            }
            valuetotalbed = harga_bed * valuejumlahbed;
            total = valuetotalbed + valuetotal * valueselisih;
            jml.setText("Rp." + total);
            if (valuejumlahbed >= 3){
                add_bed.animate().alpha(0).setDuration(300).start();
                add_bed.setEnabled(false);
            }
        });

        min_bed.setOnClickListener(v -> {
            valuejumlahbed -= 1;
            textjumlahbed.setText(valuejumlahbed.toString());
            if (valuejumlahbed < 1 ){
                min_bed.animate().alpha(0).setDuration(300).start();
                min_bed.setEnabled(false);
            }
            valuetotalbed = harga_bed * valuejumlahbed;
            total = valuetotalbed + valuetotal * valueselisih;
            jml.setText("Rp." + total);
            if (valuejumlahbed < 3){
                add_bed.animate().alpha(1).setDuration(300).start();
                add_bed.setEnabled(true);
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
//        builder.setMessage(getString(R.string.dilalog));
//
//        builder.setPositiveButton("Yes", (dialog, id) -> {
//            cancel_book = FirebaseDatabase.getInstance().getReference().child("Booked").child(username_key_new).child(room);
//            cancel_book.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    dataSnapshot.getRef().setValue(null);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//            Intent gotosuccess = new Intent(BookingActivity.this, DashboardActivty.class);
//            startActivity(gotosuccess);
//        });
//        builder.setNegativeButton(
//                "No",
//                (dialog, id) -> dialog.cancel());
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }


    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }

}
