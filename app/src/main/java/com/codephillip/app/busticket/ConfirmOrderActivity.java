package com.codephillip.app.busticket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ConfirmOrderActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView toolbarImage = (ImageView) findViewById(R.id.image);
        //todo load using picasso
        toolbarImage.setImageDrawable(getResources().getDrawable(R.drawable.bus));

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showComfirmationDialog();
            }
        });

    }

    private void showComfirmationDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfirmOrderActivity.this);
        alertDialog.setTitle("RECEIPT");
        alertDialog.setMessage("Thank You for purchasing a bus ticket.'\n Receipt number 4434");
//                alertDialog.setIcon(R.drawable.delete);

        alertDialog.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialog.show();
    }
}
