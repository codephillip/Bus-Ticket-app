package com.codephillip.app.busticket;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.codephillip.app.busticket.adapters.SeatGridAdapter;
import com.codephillip.app.busticket.provider.routes.RoutesCursor;

import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.codephillip.app.busticket.Utils.MM_CODE_PATTERN;
import static com.codephillip.app.busticket.Utils.PHONE_PATTERN;
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
    private LinearLayout linearLayout;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Utils.getInstance();

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
            arrival.setText(cursor.getArrival().toString());
            departure.setText(cursor.getDeparture().toString());
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
                displayInputDialog();
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

    private void makeOrder() {
        Log.d(TAG, "makeOrder: making order");
        final String UUIDString = UUID.randomUUID().toString();
        String serverData = String.format(Utils.soapString, UUIDString);

        try {
            displayProgressDialog();
            //todo get MM api
            String responseString = post("http://40.68.208.28:9002/ThirdPartyServiceUMMImpl/UMMServiceService/RequestPayment/v17", serverData);
            Log.d(TAG, "makeOrder: ####RESPONSE: " + responseString);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "makeOrder: Server error");
        }


//        ApiInterface apiInterface = ApiClient.getClient(Utils.BASE_URL).create(ApiInterface.class);
//        //todo remove code
//        int order_code = 9834;
//        Order order = new Order(Utils.customer.getId(), cursor.getRouteid(), true, new Date().toString(), order_code);
//        Call<Order> call = apiInterface.createOrder(order);
//        call.enqueue(new Callback<Order>() {
//            @Override
//            public void onResponse(Call<Order> call, retrofit2.Response<Order> response) {
//                int statusCode = response.code();
//                Log.d(TAG, "onResponse: #" + statusCode);
//                int receiptCode = response.body().getCode();
//                showComfirmationDialog(receiptCode);
//                //update data
//                startService(new Intent(getApplicationContext(), SetUpService.class));
//            }
//
//            @Override
//            public void onFailure(Call<Order> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t.toString());
//            }
//        });
    }

    private void displayProgressDialog() {
        Log.d(TAG, "displayProgressDialog: showing###");
        pd.setMessage("Making payment...");
        pd.show();
    }


    public static final MediaType JSON
            = MediaType.parse("application/xml; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    public String post(String url, String json) throws IOException {
        //todo remove and use asyncTask
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            dismissProgressDialog();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Network failure";
    }

    private void dismissProgressDialog() {
        if (pd.isShowing())
            pd.dismiss();
        showComfirmationDialog(randInt(100000, 999999));
    }

    private void showComfirmationDialog(int receiptCode) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfirmOrderActivity.this);
        alertDialog.setTitle("RECEIPT");
        alertDialog.setMessage("Thank You.\nReceipt number: " + receiptCode
                + "\nSeat number: " + getSeatNumber());
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: OK");
                    }
                });
        alertDialog.show();
    }

    private String getSeatNumber() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString(Utils.SEAT_NUMBER, "1");
    }

    private void displayInputDialog() {
        final EditText phoneNumberEdit = new EditText(this);
        final EditText codeEdit = new EditText(this);

        phoneNumberEdit.setHint("0772123456");
        codeEdit.setHint("1234");

        new AlertDialog.Builder(this)
                .setTitle("Verification Code")
                .setMessage("Enter Mobile Money code")
                .setView(phoneNumberEdit)
                .setView(codeEdit)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        validateUserData(dialog);
                    }

                    private void validateUserData(DialogInterface dialog) {
                        String verificationCode = codeEdit.getText().toString();
                        String phoneNumber = phoneNumberEdit.getText().toString();
                        Log.d(TAG, "VerificationCode: " + verificationCode);
                        Log.d(TAG, "PhoneNumber: " + phoneNumber);

                        if (validateData(verificationCode, MM_CODE_PATTERN) && validateData(phoneNumber, PHONE_PATTERN)) {
                            dialog.dismiss();
                            makeOrder();
                        } else {
                            dialog.dismiss();
                            //todo replace with dialog
                            Toast.makeText(ConfirmOrderActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }
}
