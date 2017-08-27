package com.codephillip.app.busticket;

import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codephillip.app.busticket.provider.orders.OrdersCursor;
import com.codephillip.app.busticket.provider.orders.OrdersSelection;
import com.codephillip.app.busticket.provider.routes.RoutesCursor;
import com.codephillip.app.busticket.provider.routes.RoutesSelection;

import static com.codephillip.app.busticket.Utils.picassoLoader;

public class OrderDetailsActivity extends AppCompatActivity {

    private static final String TAG = OrderDetailsActivity.class.getSimpleName();
    Button cancelButton;
    private ImageView toolbarImage;
    private TextView company;
    private TextView source;
    private TextView destination;
    private TextView arrival;
    private TextView departure;
    private TextView price;
    private TextView code;
    private TextView valid;
    private TextView dateCreated;
    private OrdersCursor cursor;
    private int cursorPosition;
    private OrdersCursor ordersCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
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
        code = (TextView) findViewById(R.id.receipt_number_view);
        valid = (TextView) findViewById(R.id.valid_view);
        dateCreated = (TextView) findViewById(R.id.date);

        try {
            cursorPosition = getIntent().getIntExtra(Utils.CURSOR_POSITION, 0);
            Log.d(TAG, "onCreate: ###" + cursorPosition);
            if (Utils.HISTROY_FRAG_ACTIVE)
                ordersCursor = new OrdersSelection().valid(false).orderById(true).query(getContentResolver());
            else
                ordersCursor = new OrdersSelection().orderById(true).query(getContentResolver());

            ordersCursor.moveToPosition(cursorPosition);
            Log.d(TAG, "onCreate: CURSOR###" + ordersCursor.getCode() + ordersCursor.getValid());
            if (ordersCursor == null)
                throw new CursorIndexOutOfBoundsException("Cursor out of bounds");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Log.d(TAG, "onCreate: ROUTE_ID ###" + ordersCursor.getRoute());
            Log.d(TAG, "onCreate: ROUTE_ID ###" + ordersCursor.getCode());
            Log.d(TAG, "onCreate: ROUTE_ID ###" + ordersCursor.getCustomer());
            RoutesCursor routesCursor = new RoutesSelection().routeid(Integer.valueOf(ordersCursor.getRoute())).query(getContentResolver());
            routesCursor.moveToFirst();
            Log.d(TAG, "onCreate: ROUTE COMPANY" + routesCursor.getBuscompanyname());
            company.setText(routesCursor.getBuscompanyname());
            source.setText(routesCursor.getSource());
            destination.setText(routesCursor.getDestination());
            arrival.setText(routesCursor.getArrival().toString());
            departure.setText(routesCursor.getDeparture().toString());
            price.setText(String.valueOf(routesCursor.getPrice()));
            Log.d(TAG, "onCreate: " + routesCursor.getBuscompanyimage());
            picassoLoader(this, toolbarImage, routesCursor.getBuscompanyimage());
            code.setText(ordersCursor.getCode());
            valid.setText(ordersCursor.getValid().toString());
            dateCreated.setText(ordersCursor.getDate().toString().substring(0, 10));
            ordersCursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}