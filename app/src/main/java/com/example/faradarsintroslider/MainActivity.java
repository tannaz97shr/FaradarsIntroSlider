package com.example.faradarsintroslider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_slides).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SliderPrefManager(MainActivity.this).setStartSlider(false);
                Intent intent=new Intent(MainActivity.this,IntroSliderActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //SliderPrefManager prefman=new SliderPrefManager(this);

    }

}
