package com.baytech.wikramahotel.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baytech.wikramahotel.Activity.BookingActivity;
import com.baytech.wikramahotel.Activity.NotAvailableActivity;
import com.baytech.wikramahotel.Activity.PickDateActivity;
import com.baytech.wikramahotel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {

    CardView deluxe,superior,reguler;
    TextView harga;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        deluxe = getActivity().findViewById(R.id.deluxe_room);
        harga = getActivity().findViewById(R.id.harga_kamar);

        reference = FirebaseDatabase.getInstance().getReference().child("Room").child("Reguler");

        reference.limitToLast(100).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                harga.setText("Rp."+ dataSnapshot.child("harga").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        deluxe.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), NotAvailableActivity.class);
            startActivity(i);
        });

        superior = getActivity().findViewById(R.id.superior_room);

        superior.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(),NotAvailableActivity.class);
            startActivity(i);
        });

        reguler = getActivity().findViewById(R.id.reguler_room);

        reguler.setOnClickListener(v -> {
            Intent gotoroom = new Intent(getActivity(), PickDateActivity.class);
            gotoroom.putExtra("Room","Reguler");
            startActivity(gotoroom);
        });
    }
}
