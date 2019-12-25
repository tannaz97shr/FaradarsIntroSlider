package com.example.faradarsintroslider;

import android.content.Context;
import android.content.SharedPreferences;

public class SliderPrefManager {

    private Context context;
    private SharedPreferences pref;

    private static final String PREF_NAME = "slider_pref";
    private static final String KEY_START ="show";
    public SliderPrefManager(Context context){
        this.context=context;
        pref=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }
    public boolean startSlider(){
        return pref.getBoolean(KEY_START,true);
    }
    public void setStartSlider(boolean start){
        pref.edit().putBoolean(KEY_START,start).apply();

    }

}
