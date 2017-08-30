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

//        animation = (AVLoadingIndicatorView) findViewById(R.id.animation);
//        animation.startAnimation();

//        new CountDownTimer(30000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                if (millisUntilFinished % 1000 == 0) {
//                    animation.smoothToShow();
//                } else {
//                    animation.smoothToHide();
//                }
//            }
//
//            public void onFinish() {
//            }
//
//        }.start();

        //todo activate setup service
//        if (Utils.isConnectedToInternet(this)) {
//            startService(new Intent(this, SetUpService.class));
//        }

        //todo check if service has finished. use broadcast
        //then end animation and move to sign in screen
        //or directly get credentials and move to main activity


//        final int animTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);
//        final ImageView logo = (ImageView) findViewById(R.id.imageView2);
//        //todo add animation
////        while (true) {
//            logo.animate().setDuration(animTime).alpha(1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
////                    logo.animate().setDuration(animTime).alpha(0);
//                }
//            });
//        }

//    public void startAnimation(View view) {
////        animation.smoothToShow();
//        animation.show();
//    }
//
//    public void stopAnimation(View view) {
//        animation.smoothToHide();
//    }

        ballPulseAnimLoader();

        ballClipRotateMultipleAnimateLoader();

        lineScaleLoaderExample();

        ballRotateLoaderAndroidExample();

        lineSpinFadeLoaderLoader();

        androidBallGridBeatLoaderBar();

        androidBallSpinFadeLoaderProgressBar();

        ballScaleRippleProgressLoaderExample();
    }

    void ballPulseAnimLoader() {
        findViewById(R.id.material_design_ball_pulse_loader_progress).setVisibility(View.VISIBLE);
    }

    void ballClipRotateMultipleAnimateLoader() {
        findViewById(R.id.material_design_ball_clip_rotate_multiple_loader).setVisibility(View.VISIBLE);
    }

    void lineScaleLoaderExample() {
        findViewById(R.id.material_design_linear_scale_progress_loader).setVisibility(View.VISIBLE);
    }

    void ballRotateLoaderAndroidExample() {
        findViewById(R.id.material_design_ball_rotate_loader).setVisibility(View.VISIBLE);
    }

    void lineSpinFadeLoaderLoader() {
        findViewById(R.id.material_design_linear_spin_fade_loader).setVisibility(View.VISIBLE);
    }

    void androidBallGridBeatLoaderBar() {
        findViewById(R.id.material_design_ball_grid_beat_loader).setVisibility(View.VISIBLE);
    }

    void androidBallSpinFadeLoaderProgressBar() {
        findViewById(R.id.material_design_ball_spin_fade_loader).setVisibility(View.VISIBLE);
    }

    void ballScaleRippleProgressLoaderExample() {
        findViewById(R.id.material_design_ball_scale_ripple_loader).setVisibility(View.VISIBLE);
    }
}