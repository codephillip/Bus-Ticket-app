package com.codephillip.app.busticket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

public class SplashActivity extends AppCompatActivity {

    private AVLoadingIndicatorView animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        //todo activate setup service
//        if (Utils.isConnectedToInternet(this)) {
//            startService(new Intent(this, SetUpService.class));
//        }

        //todo check if service has finished. use broadcast
        //then end animation and move to sign in screen
        //or directly get credentials and move to main activity

        lineScaleLoaderExample();
    }

    private void lineScaleLoaderExample() {
        findViewById(R.id.material_design_linear_scale_progress_loader).setVisibility(View.VISIBLE);
    }
}