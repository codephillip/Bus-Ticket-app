package com.codephillip.app.busticket;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.codephillip.app.busticket.adapters.SeatGridAdapter;
import com.codephillip.app.busticket.provider.routes.RoutesCursor;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import static com.codephillip.app.busticket.Constants.HAS_BOOKED;
import static com.codephillip.app.busticket.Utils.MM_CODE_PATTERN;
import static com.codephillip.app.busticket.Utils.MM_URL;
import static com.codephillip.app.busticket.Utils.PHONE_PATTERN;
import static com.codephillip.app.busticket.Utils.displayErrorDialog;
import static com.codephillip.app.busticket.Utils.displayInfoDialog;
import static com.codephillip.app.busticket.Utils.formatDateString;
import static com.codephillip.app.busticket.Utils.picassoLoader;
import static com.codephillip.app.busticket.Utils.randInt;
import static com.codephillip.app.busticket.Utils.validateData;

public class ConfirmOrderActivity extends AppCompatActivity implements SeatGridAdapter.ItemClickListener {

    private static final String TAG = ConfirmOrderActivity.class.getSimpleName();
    private Button orderButton;
    private Button cancelButton;
    private SeatGridAdapter gridAdapter;
    private RecyclerView recyclerView;
    private ImageView toolbarImage;
    private TextView company;
    private TextView source;
    private TextView destination;
    private TextView arrival;
    private TextView departure;
    private TextView price;
    final RoutesCursor cursor = new RoutesCursor(Utils.cursor);
    private ProgressDialog pd;
    private NestedScrollView scrollView;
    private Utils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Utils.getInstance();
        utils = new Utils(this);

        pd = new ProgressDialog(this);
        toolbarImage = (ImageView) findViewById(R.id.image);
        company = (TextView) findViewById(R.id.company_view);
        source = (TextView) findViewById(R.id.source_view);
        destination = (TextView) findViewById(R.id.dest_view);
        arrival = (TextView) findViewById(R.id.arrival_view);
        departure = (TextView) findViewById(R.id.departure_view);
        price = (TextView) findViewById(R.id.price_view);
        scrollView = (NestedScrollView) findViewById(R.id.scroll_view);

        try {
            final int cursorPosition = getIntent().getIntExtra(Utils.CURSOR_POSITION, 0);
            cursor.moveToPosition(cursorPosition);
            if (cursor == null)
                throw new CursorIndexOutOfBoundsException("Cursor out of bounds");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            company.setText(cursor.getBuscompanyname());
            source.setText(cursor.getSource());
            destination.setText(cursor.getDestination());
            arrival.setText(formatDateString(cursor.getArrival().toString()));
            departure.setText(formatDateString(cursor.getDeparture().toString()));
            price.setText(String.valueOf(cursor.getPrice()));
            picassoLoader(this, toolbarImage, cursor.getBuscompanyimage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        orderButton = (Button) findViewById(R.id.order_button);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startAsyncTask(cursor.getCode());
                if (utils.getPrefBoolean(HAS_BOOKED)) {
                    displayOrderInputDialog();
                } else {
                    displayErrorDialog(ConfirmOrderActivity.this, getString(R.string.error), "Please first book a seat.");
                }
            }
        });

        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int numberOfColumns = 4;
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        gridAdapter = new SeatGridAdapter(this);
        gridAdapter.setClickListener(this);
        recyclerView.setAdapter(gridAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        // scroll to bottom of the recycler view when user selects a seat
        Log.d(TAG, "onCreate: adding onclick");
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }

    private void displayOrderInputDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.order_input_dialog);

        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText("Confirm");
        final EditText phoneNumberEdit = (EditText) dialog.findViewById(R.id.phoneNumber);
        final EditText pinEdit = (EditText) dialog.findViewById(R.id.pin);

        Button cancel = (Button) dialog.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button proceed = (Button) dialog.findViewById(R.id.proceed_button);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo compare with server pin
                validateUserData(dialog ,phoneNumberEdit.getText().toString(), pinEdit.getText().toString());
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void validateUserData(Dialog dialog, String phoneNumber, String pin) {
        Log.d(TAG, "VerificationCode: " + pin);
        Log.d(TAG, "PhoneNumber: " + phoneNumber);
        if (validateData(pin, MM_CODE_PATTERN) && validateData(phoneNumber, PHONE_PATTERN)) {
            makeOrder();
        } else {
            displayErrorDialog(this, getString(R.string.error), "Please insert correct information");
            Toast.makeText(ConfirmOrderActivity.this, "Hint PHONE:0756878567 CODE:4444", Toast.LENGTH_LONG).show();
        }
        utils.savePrefBoolean(HAS_BOOKED, false);
        dialog.dismiss();
    }

    private void makeOrder() {
        Log.d(TAG, "makeOrder: making order");
        final String UUIDString = UUID.randomUUID().toString();
        String serverData = String.format(Utils.soapString, UUIDString);

        try {
            displayProgressDialog();
            //todo get MM api
            String responseString = post(MM_URL, serverData);
            Log.d(TAG, "makeOrder: ####RESPONSE: " + responseString);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "makeOrder: Server error");
            displayErrorDialog(this, getString(R.string.error), "Please check Internet connection");
        }
    }

    private void displayProgressDialog() {
        Log.d(TAG, "displayProgressDialog: showing###");
        pd.setMessage("Making payment...");
        pd.show();
    }

    public String post(String url, String json) throws IOException {
        //TODO REMOVE IMMEDIATELY
        dismissProgressDialog();
        //todo remove and use asyncTask
        return "Network failure";
    }

    private void dismissProgressDialog() {
        Log.d(TAG, "dismissProgressDialog: GONE");
        if (pd.isShowing())
            pd.dismiss();
        //todo get receipt number from server response
        displayInfoDialog(this, getString(R.string.receipt), "Receipt number: " + randInt(100000, 999999)
                + "\nSeat number: " + getSeatNumber()
                + "\nDate: " + formatDateString(new Date().toString()));
    }

    private String getSeatNumber() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString(Utils.SEAT_NUMBER, "1");
    }
}
