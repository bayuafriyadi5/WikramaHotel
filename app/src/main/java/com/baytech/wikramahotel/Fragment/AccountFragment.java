package com.baytech.wikramahotel.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.baytech.wikramahotel.Activity.EditProfileActivity;
import com.baytech.wikramahotel.R;
import com.baytech.wikramahotel.Activity.SettingsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    Button edit,setting;
    TextView name,email,password,telp;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    DatabaseReference reference;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getUsernameLocal();

        edit = getActivity().findViewById(R.id.btn_edit);
        name = getActivity().findViewById(R.id.full_name);
        email = getActivity().findViewById(R.id.email_id);
        password = getActivity().findViewById(R.id.password);
        telp = getActivity().findViewById(R.id.phone_number);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    name.setText(dataSnapshot.child("nama").getValue().toString());
                    email.setText(dataSnapshot.child("email").getValue().toString());
                    telp.setText(dataSnapshot.child("no_telp").getValue().toString());
                    password.setText(dataSnapshot.child("password").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        edit.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(i);
        });

        setting = getActivity().findViewById(R.id.btn_setting);

        setting.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), SettingsActivity.class);
            startActivity(i);
        });
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
