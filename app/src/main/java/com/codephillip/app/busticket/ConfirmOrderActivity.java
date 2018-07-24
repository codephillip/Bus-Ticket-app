package com.codephillip.app.busticket;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.drawable.Animatable;
import android.os.AsyncTask;
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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.codephillip.app.busticket.adapters.SeatGridAdapter;
import com.codephillip.app.busticket.provider.routes.RoutesCursor;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import design.ivisionblog.apps.reviewdialoglibrary.FeedBackActionsListeners;
import design.ivisionblog.apps.reviewdialoglibrary.FeedBackDialog;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.codephillip.app.busticket.Constants.HAS_BOOKED;
import static com.codephillip.app.busticket.Utils.MM_CODE_PATTERN;
import static com.codephillip.app.busticket.Utils.MM_URL;
import static com.codephillip.app.busticket.Utils.PHONE_PATTERN;
import static com.codephillip.app.busticket.Utils.base64String;
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
    private TextView departure;
    private TextView price;
    final RoutesCursor cursor = new RoutesCursor(Utils.cursor);
    private ProgressDialog pd;
    private NestedScrollView scrollView;
    private Utils utils;
    private ImageView tickImage;
    private LinearLayout routeDetailsLayout;
    private TextView successMessageTextView;
    private EditText phoneNumberEdit;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Utils.getInstance();
        utils = new Utils(this);

        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            Utils.trackEvent(mFirebaseAnalytics, TAG, "Confirm order: started");
            Log.d(TAG, "onCreate: added analytics");
        } catch (Exception e) {
            e.printStackTrace();
        }

        pd = new ProgressDialog(this);
        toolbarImage = (ImageView) findViewById(R.id.image);
        company = (TextView) findViewById(R.id.company_view);
        source = (TextView) findViewById(R.id.source_view);
        destination = (TextView) findViewById(R.id.dest_view);
        departure = (TextView) findViewById(R.id.departure_view);
        price = (TextView) findViewById(R.id.price_view);
        scrollView = (NestedScrollView) findViewById(R.id.scroll_view);
        tickImage = (ImageView) findViewById(R.id.tickImage);
        phoneNumberEdit = findViewById(R.id.phoneNumber);
        routeDetailsLayout = findViewById(R.id.route_details_layout);
        successMessageTextView = findViewById(R.id.success_message);


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
            departure.setText(cursor.getDeparture());
            price.setText(String.valueOf(cursor.getPrice()));
            picassoLoader(this, toolbarImage, cursor.getBuscompanyimage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        orderButton = (Button) findViewById(R.id.order_button);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phoneNumberEdit.getText().toString();
                if (Utils.validateData(phoneNumber, Utils.PHONE_PATTERN_CODE)
                        || Utils.validateData(phoneNumber, Utils.PHONE_PATTERN)) {
                    displaySuccessMessage();
                    sendToBroker(phoneNumber);
                } else {
                    displayErrorDialog(ConfirmOrderActivity.this, getString(R.string.error), "Please insert valid phone number");
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

    private void displaySuccessMessage() {
        try {
            postToServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String values[] = {
                    phoneNumberEdit.getText().toString(),
                    source.getText().toString(),
                    destination.getText().toString(),
            };

            SmsAsyncTask task = new SmsAsyncTask();
            task.execute(values);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
        }

        routeDetailsLayout.setVisibility(View.GONE);
        tickImage.setVisibility(View.VISIBLE);
        successMessageTextView.setVisibility(View.VISIBLE);
        phoneNumberEdit.setVisibility(View.GONE);
        orderButton.setVisibility(View.GONE);
        cancelButton.setText("OK");
    }

    private void postToServer() {
        //TODO post order to server
    }

    private String sendSms(String[] values) throws IOException {
        String url = "https://api.twilio.com/2010-04-01/Accounts/AC447a79dddb6ff5bfddb3a662e1e8e59a/Messages.json";
        String result = postToTwillio(url, values[0], values[1], values[2]);
        Log.d(TAG, "sendSms: " + result);
        return result;
    }

    public String postToTwillio( String url, String to, String from, String phoneNumber) throws IOException {
        OkHttpClient client = new OkHttpClient();
        final String basic = "Basic " + base64String;
        String message = "JustGo. Client %s. From: %s - To: %s";
        message = String.format(Locale.ENGLISH, message, phoneNumber, from, to);
        Utils.trackEvent(mFirebaseAnalytics, TAG, "Confirm order: " + message);
        Log.d(TAG, "postToTwillio: " + message);

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("To", "+256779226226")
                .addFormDataPart("From", "+18477448802")
                .addFormDataPart("Body", message)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", basic)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.d(TAG, "postToTwillio: >>> " + response.toString());

            if (response.code() == 201)
                return response.body().string();
            else
                throw new IOException("Not found on server");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
        return "Network failure";
    }

    private void sendToBroker(String phoneNumber) {
        Log.d(TAG, "sendToBroker: " + phoneNumber);
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

    private class SmsAsyncTask extends AsyncTask<String[], String, String> {
        private String resp = "Failed";

        @Override
        protected String doInBackground(String[]... params) {
            try {
                return sendSms(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "onPostExecute: " + result);
            displayInfoDialog(ConfirmOrderActivity.this, getString(R.string.receipt), "Receipt number: " + randInt(100000, 999999)
                    + "\nSource: " + source.getText().toString()
                    + "\nDestination: " + destination.getText().toString()
                    + "\nAmount: " + price.getText().toString()
                    + "\nDate: " + formatDateString(new Date().toString()));
            ((Animatable) tickImage.getDrawable()).start();
            utils.savePrefBoolean(HAS_BOOKED, true);
        }
    }
}
