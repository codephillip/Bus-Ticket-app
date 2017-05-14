package com.codephillip.app.busticket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codephillip.app.busticket.adapters.SeatGridAdapter;
import com.codephillip.app.busticket.provider.routes.RoutesCursor;

import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.codephillip.app.busticket.Utils.picassoLoader;
import static com.codephillip.app.busticket.Utils.randInt;

public class ConfirmOrderActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Utils.getInstance();

        toolbarImage = (ImageView) findViewById(R.id.image);

        company = (TextView) findViewById(R.id.company_view);
        source = (TextView) findViewById(R.id.source_view);
        destination = (TextView) findViewById(R.id.dest_view);
        arrival = (TextView) findViewById(R.id.arrival_view);
        departure = (TextView) findViewById(R.id.departure_view);
        price = (TextView) findViewById(R.id.price_view);

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
            arrival.setText(cursor.getArrival());
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
//                startAsyncTask(cursor.getCode());
                makeOrder();
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
        recyclerView.setAdapter(gridAdapter);
    }

    private void makeOrder() {
        Log.d(TAG, "makeOrder: making order");

        String soapString = "<?xml version=\"1.0\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:b2b=\"http://b2b.mobilemoney.mtn.zm_v1.0\">\n" +
                "  <soapenv:Header>\n" +
                "    <RequestSOAPHeader xmlns=\"http://www.huawei.com.cn/schema/common/v2_1\">\n" +
                "      <spId>2560110004782</spId>\n" +
                "      <spPassword>A1FEA6B5D61038B82EEB9A5703D313BC</spPassword>\n" +
                "      <bundleID/>\n" +
                "      <serviceId/>\n" +
                "      <timeStamp>20170503144638</timeStamp>\n" +
                "    </RequestSOAPHeader>\n" +
                "  </soapenv:Header>\n" +
                "  <soapenv:Body>\n" +
                "    <b2b:processRequest>\n" +
                "      <serviceId>200</serviceId>\n" +
                "      <parameter>\n" +
                "        <name>DueAmount</name>\n" +
                "        <value>500</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>MSISDNNum</name>\n" +
                "        <value>256789900760</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>ProcessingNumber</name>\n" +
                "        <!-- generate random java value -->\n" +
                "        <value>" + UUID.randomUUID().toString()+ "</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>serviceId</name>\n" +
                "        <value>appchallenge11.sp</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>AcctRef</name>\n" +
                "         <value>101</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>AcctBalance</name>\n" +
                "        <value>300000</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>MinDueAmount</name>\n" +
                "        <value>200</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>Narration</name>\n" +
                "        <value>You have made payment for a bus ticket</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>PrefLang</name>\n" +
                "        <value>en</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>OpCoID</name>\n" +
                "        <value>25601</value>\n" +
                "      </parameter>\n" +
                "      <parameter>\n" +
                "        <name>CurrCode</name>\n" +
                "        <value>UGX</value>\n" +
                "      </parameter>\n" +
                "    </b2b:processRequest>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        try {
            String responseString = post("http://40.68.208.28:9002/ThirdPartyServiceUMMImpl/UMMServiceService/RequestPayment/v17", soapString);
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


    public static final MediaType JSON
            = MediaType.parse("application/xml; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    public String post(String url, String json) throws IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            showComfirmationDialog(randInt(100000, 999999));
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Network failure";
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

    private void saveSeatNumber(String seatNumber) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Utils.SEAT_NUMBER, seatNumber);
        editor.apply();
    }

    private String getSeatNumber() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getString(Utils.SEAT_NUMBER, "1");
    }
}
