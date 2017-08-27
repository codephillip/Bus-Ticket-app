package com.codephillip.app.busticket;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

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


        final int animTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);
        final ImageView logo = (ImageView) findViewById(R.id.imageView2);
        //todo add animation
//        while (true) {
            logo.animate().setDuration(animTime).alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
//                    logo.animate().setDuration(animTime).alpha(0);
                }
            });
//        }

    }
}
