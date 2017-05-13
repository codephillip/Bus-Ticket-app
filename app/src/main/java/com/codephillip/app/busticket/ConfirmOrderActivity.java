package com.codephillip.app.busticket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codephillip.app.busticket.mymodels.Order;
import com.codephillip.app.busticket.provider.routes.RoutesCursor;
import com.codephillip.app.busticket.retrofit.ApiClient;
import com.codephillip.app.busticket.retrofit.ApiInterface;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;

import static com.codephillip.app.busticket.Utils.picassoLoader;

public class ConfirmOrderActivity extends AppCompatActivity {

    private static final String TAG = ConfirmOrderActivity.class.getSimpleName();
    Button orderButton;
    Button cancelButton;
    private ImageView toolbarImage;
    private TextView company;
    private TextView source;
    private TextView destination;
    private TextView arrival;
    private TextView departure;
    private TextView price;
    final RoutesCursor cursor = new RoutesCursor(Utils.cursor);
//    private OrderAsyncTask orderAsyncTask = null;

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
    }

    private void makeOrder() {
        Log.d(TAG, "makeOrder: making order");
        ApiInterface apiInterface = ApiClient.getClient(Utils.BASE_URL).create(ApiInterface.class);
        //todo remove code
        int order_code = 9834;
        Order order = new Order(Utils.customer.getId(), cursor.getRouteid(), true, new Date().toString(), order_code);
        Call<Order> call = apiInterface.createOrder(order);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, retrofit2.Response<Order> response) {
                int statusCode = response.code();
                Log.d(TAG, "onResponse: #" + statusCode);
                int receiptCode = response.body().getCode();
                showComfirmationDialog(receiptCode);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
    }

//    private void startAsyncTask(Integer code) {
//        orderAsyncTask = new OrderAsyncTask(code);
//        orderAsyncTask.execute((Void) null);
//    }

    private void showComfirmationDialog(int receiptCode) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfirmOrderActivity.this);
        alertDialog.setTitle("RECEIPT");
        alertDialog.setMessage("Thank You for purchasing a bus ticket.\nReceipt number " + receiptCode);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: OK");
                    }
                });
        alertDialog.show();
    }

//    public class OrderAsyncTask extends AsyncTask<Void, Void, Boolean> {
//
//        private final Integer code;
//
//        public OrderAsyncTask(Integer code) {
//            this.code = code;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            try {
//                Log.d(TAG, "doInBackground: " + code);
//                makeOrder();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//            return true;
//        }
//
//        private void makeOrder() {
//            Log.d(TAG, "makeOrder: making order");
//            ApiInterface apiInterface = ApiClient.getClient(Utils.BASE_URL).create(ApiInterface.class);
//            //todo remove code
//            int order_code = 9834;
//            Order order = new Order(Utils.customer.getId(), cursor.getRouteid(), true, new Date().toString(), order_code);
//            Call<Order> call = apiInterface.createOrder(order);
//            call.enqueue(new Callback<Order>() {
//                @Override
//                public void onResponse(Call<Order> call, retrofit2.Response<Order> response) {
//                    int statusCode = response.code();
//                    Log.d(TAG, "onResponse: #" + statusCode);
//                    receiptCode = response.body().getCode();
//                }
//
//                @Override
//                public void onFailure(Call<Order> call, Throwable t) {
//                    Log.d(TAG, "onFailure: " + t.toString());
//                }
//            });
//        }
//
//        @Override
//        protected void onPostExecute(final Boolean success) {
//            orderAsyncTask = null;
//            if (success) {
//                showComfirmationDialog(receiptCode);
//            } else {
//                Toast.makeText(ConfirmOrderActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            orderAsyncTask = null;
//        }
//    }
}
