package com.example.googlemapsandroidapiexample;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MapLoading extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5500; //splash screen will be shown for 2 seconds

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mapIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(mapIntent);
                finish();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
