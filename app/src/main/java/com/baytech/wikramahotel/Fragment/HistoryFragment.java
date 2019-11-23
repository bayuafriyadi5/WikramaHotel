package com.baytech.wikramahotel.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baytech.wikramahotel.Adapter.AdapterBooking;
import com.baytech.wikramahotel.Model.Booking;
import com.baytech.wikramahotel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    RecyclerView booked;
    ArrayList<Booking> list;
    DatabaseReference reference;
    AdapterBooking adapterBooking;
    Button cancel;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getUsernameLocal();

        booked = getActivity().findViewById(R.id.history);
        booked.setHasFixedSize(true);
        cancel = getActivity().findViewById(R.id.cancel_book);

        booked.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();


        reference = FirebaseDatabase.getInstance().getReference().child("Booked").child(username_key_new).child("Book");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                        Booking p = dataSnapshot1.getValue(Booking.class);
                        list.add(p);

                    }
                    adapterBooking = new AdapterBooking(getActivity(),list);
                    booked.setAdapter(adapterBooking);
                    adapterBooking.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
