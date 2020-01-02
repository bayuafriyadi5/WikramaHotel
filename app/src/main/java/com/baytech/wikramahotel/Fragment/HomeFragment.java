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
import android.widget.ImageView;

import com.baytech.wikramahotel.Activity.MapsActivity;
import com.baytech.wikramahotel.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    DatabaseReference reference;
    ImageView kamar1,kamar2,kamar3;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView map = getActivity().findViewById(R.id.view_location);

        ImageView kamar1 = getActivity().findViewById(R.id.kamar1);
        ImageView kamar2 = getActivity().findViewById(R.id.kamar2);
        ImageView kamar3 = getActivity().findViewById(R.id.kamar3);

        reference = FirebaseDatabase.getInstance().getReference().child("Foto");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Picasso.get()
                        .load((dataSnapshot.child("kamar1")
                                .getValue()).toString()).centerCrop().fit()
                        .into(kamar1);
                Picasso.get()
                        .load((dataSnapshot.child("kamar2")
                                .getValue()).toString()).centerCrop().fit()
                        .into(kamar2);
                Picasso.get()
                        .load((dataSnapshot.child("kamar3")
                                .getValue()).toString()).centerCrop().fit()
                        .into(kamar3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        map.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), MapsActivity.class);
            startActivity(i);
        });
    }
}
