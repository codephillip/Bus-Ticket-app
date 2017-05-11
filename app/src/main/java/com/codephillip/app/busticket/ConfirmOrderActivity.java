package com.codephillip.app.busticket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

public class ConfirmOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView toolbarImage = (ImageView) findViewById(R.id.image);
        //todo load using picasso
        toolbarImage.setImageDrawable(getResources().getDrawable(R.drawable.nav_image));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
