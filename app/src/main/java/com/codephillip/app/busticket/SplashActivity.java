package com.codephillip.app.busticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        if (Utils.isConnectedToInternet(SplashActivity.this))
            startService(new Intent(getApplicationContext(), SetUpService.class));
        else
            Toast.makeText(this, "Please check your Internet connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}