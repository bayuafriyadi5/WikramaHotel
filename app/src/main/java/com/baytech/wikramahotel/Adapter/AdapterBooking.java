package com.baytech.wikramahotel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.baytech.wikramahotel.Activity.StrukActivity;
import com.baytech.wikramahotel.Model.Booking;
import com.baytech.wikramahotel.R;
import java.util.ArrayList;
import java.util.Random;

public class AdapterBooking extends RecyclerView.Adapter<AdapterBooking.MyViewHolder> {

    private Context context;
    private ArrayList<Booking> bookings;
    final Integer nomor_transaksi = new Random().nextInt();

    public AdapterBooking(Context c, ArrayList<Booking> p){
        context = c;
        bookings = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.history,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.harga.setText("Rp. " + bookings.get(i).getTotal_harga());
        myViewHolder.date.setText(bookings.get(i).getDate_booking());
        myViewHolder.time.setText(bookings.get(i).getTime_booking());
        myViewHolder.status.setText(bookings.get(i).getStatus());

        final String getTotalharga = bookings.get(i).getTime_booking();

        myViewHolder.itemView.setOnClickListener(v -> {

            Intent detail = new Intent(context, StrukActivity.class);
            detail.putExtra("total_harga",getTotalharga);
            context.startActivity(detail);
        });

    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView harga,date,time,status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            harga = itemView.findViewById(R.id.harga_history);
            date = itemView.findViewById(R.id.date_book);
            time = itemView.findViewById(R.id.time_book);
            status = itemView.findViewById(R.id.status);

        }
    }
}
