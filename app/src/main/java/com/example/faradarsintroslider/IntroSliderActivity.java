package com.example.faradarsintroslider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntroSliderActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout layoutDots;
    Button btnNext, btnSkip;

    SliderPagerAdapter pagerAdapter;

    private SliderPrefManager prefman;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);

        changeStatusBarColor();
        prefman=new SliderPrefManager(this);
        if(prefman.startSlider()){
            launchMainScreen();
            return;
        }
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        layoutDots = (LinearLayout) findViewById(R.id.layoutDots);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        pagerAdapter=new SliderPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        showDots(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //dots:
                showDots(position);
                if(position==viewPager.getAdapter().getCount()-1){
                    btnSkip.setVisibility(View.GONE);
                    btnNext.setText(R.string.GOTIT);
                }else {
                    btnSkip.setVisibility(View.VISIBLE);
                    btnNext.setText(R.string.NEXT);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage=viewPager.getCurrentItem();
                int pages=viewPager.getAdapter().getCount()-1;
                if(currentPage==pages){
                    prefman.setStartSlider(false);
                    launchMainScreen();
                }else {
                    viewPager.setCurrentItem(currentPage+1);
                }
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchMainScreen();
            }
        });
    }
    private void launchMainScreen(){
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDots(int pageNumber){
        TextView [] dots=new TextView[viewPager.getAdapter().getCount()];
        layoutDots.removeAllViews();
        for(int i=0; i<dots.length; i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(TypedValue.COMPLEX_UNIT_SP,35);
            dots[i].setTextColor(ContextCompat.getColor
                    (this,(i==pageNumber ? R.color.dot_active : R.color.dot_inactive)));

            layoutDots.addView(dots[i]);
        }
    }

    private void changeStatusBarColor(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }



    public class SliderPagerAdapter extends PagerAdapter {

        String[] slideTitles;
        String[] slideDescriptions;

        int [] bgColorIds = {R.color.Slide_1_bg_color, R.color.Slide_2_bg_color,
                R.color.Slide_3_bg_color, R.color.Slide_4_bg_color};
        int [] slideImageIds = {R.drawable.ic_food, R.drawable.ic_movie,
                R.drawable.ic_discount, R.drawable.ic_travel};

        public SliderPagerAdapter(){
            slideTitles=getResources().getStringArray(R.array.slide_titles);
            slideDescriptions=getResources().getStringArray(R.array.slide_description);
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view=LayoutInflater.from(IntroSliderActivity.this)
                    .inflate(R.layout.intro_slide,container,false);
            view.findViewById(R.id.bgLayout).setBackgroundColor(
                    ContextCompat.getColor(IntroSliderActivity.this,bgColorIds[position]));
            ((ImageView) view.findViewById(R.id.slide_image)).setImageResource(slideImageIds[position]);
            ((TextView) view.findViewById(R.id.slide_title)).setText(slideTitles[position]);
            ((TextView) view.findViewById(R.id.slide_desc)).setText(slideDescriptions[position]);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return bgColorIds.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view=(View)object;
            container.removeView(view);
        }
    }
}
