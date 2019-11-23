package com.baytech.wikramahotel.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baytech.wikramahotel.R;

public class SliderActivity extends AppCompatActivity {

    private ViewPager slideViewPager;
    private LinearLayout dotsLayout;
    private TextView[] mDots;
    private Button nextBtn;
    private Button preBtn;
    private Button finishBtn;
    private SliderAdapter sliderAdapter;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        slideViewPager = findViewById(R.id.slideViewPager);
        dotsLayout = findViewById(R.id.dotsLayout);

        nextBtn = findViewById(R.id.nextBtn);
        preBtn = findViewById(R.id.prevBtn);
        finishBtn= findViewById(R.id.finishBtn);

        finishBtn.setEnabled(false);

        sliderAdapter = new SliderAdapter(this);

        slideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        slideViewPager.addOnPageChangeListener(viewListener);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchNextPage();
                finish();
            }
        });
    }

    public void addDotsIndicator(int position) {

        mDots = new TextView[3];
        dotsLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.WhiteTransparant));


            dotsLayout.addView(mDots[i]);
        }
        if (mDots.length > 0 ){

            mDots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }


    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {


        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);

            mCurrentPage = i;

            if (i == 0){
                nextBtn.setEnabled(true);
                preBtn.setEnabled(false);
                preBtn.setVisibility(View.INVISIBLE);

                nextBtn.setText("Next");
                preBtn.setText("");

                finishBtn.setEnabled(false);
                finishBtn.setVisibility(View.INVISIBLE);
            }
            else if (i == mDots.length - 1){
                nextBtn.setEnabled(false);
                preBtn.setEnabled(true);
                preBtn.setVisibility(View.VISIBLE);

                nextBtn.setText("");
                preBtn.setText("Back");

                finishBtn.setEnabled(true);
                finishBtn.setVisibility(View.VISIBLE);

            }
            else {
                nextBtn.setEnabled(true);
                preBtn.setEnabled(true);
                preBtn.setVisibility(View.VISIBLE);


                nextBtn.setText("Next");
                preBtn.setText("Back");

                finishBtn.setEnabled(false);
                finishBtn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private void launchNextPage() {
        Intent i = new Intent (SliderActivity.this,DashboardActivty.class);
        startActivity(i);
        finish();
    }
}
