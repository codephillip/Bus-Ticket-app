package com.codephillip.app.busticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // start animation
        lineScaleLoaderExample();

        if (Utils.isConnectedToInternet(SplashActivity.this)) {
            startService(new Intent(getApplicationContext(), SetUpService.class));
        }
    }

    private void lineScaleLoaderExample() {
        findViewById(R.id.material_design_linear_scale_progress_loader).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // prevent activity from surfacing when MainActivity is started
        finish();
    }
}