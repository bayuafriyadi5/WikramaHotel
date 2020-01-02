package com.baytech.wikramahotel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.baytech.wikramahotel.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {

        this.context=context;

    }

    public int []  slide_images = {

            R.drawable.ic_undraw_booking_33fn,
            R.drawable.agreement,
            R.drawable.sleep
    };


    public String []  slide_headings = {

            "Book Your Room",
            "Check in immediately",
            "Comfortable Room"
    };
    public String []  slide_desc = {

            "\"just book a room easily and quickly\n" +
                    " to get a comfortable hotel with\n" +
                    "the best price.\"",
            "\"After booking a room can \n" +
                    "immediately check in and enjoy a \n" +
                    "comfortable room.\"",
            "\"Get a comfortable hotel room \n" +
                    "and complete facilities to \n" +
                    "create a pleasant visit.\""
    };
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.slide_images);
        TextView slideHeadings = view.findViewById(R.id.slide_headings);
        TextView slideDesc =  view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeadings.setText(slide_headings[position]);
        slideDesc.setText(slide_desc[position]);

        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
