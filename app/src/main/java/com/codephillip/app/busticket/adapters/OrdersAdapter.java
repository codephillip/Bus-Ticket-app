package com.codephillip.app.busticket.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codephillip.app.busticket.OrderDetailsActivity;
import com.codephillip.app.busticket.R;
import com.codephillip.app.busticket.Utils;
import com.codephillip.app.busticket.provider.orders.OrdersCursor;
import com.codephillip.app.busticket.provider.routes.RoutesCursor;
import com.codephillip.app.busticket.provider.routes.RoutesSelection;

import static com.codephillip.app.busticket.Utils.picassoLoader;

/**
 * Created by codephillip on 12/05/17.
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private static final String TAG = OrdersAdapter.class.getSimpleName();
    private OrdersCursor dataCursor;
    private static Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView companyNameView;
        private TextView sourceView;
        private TextView destinationView;
        private TextView departureView;
        private TextView priceView;
        private TextView validView;
        private TextView codeView;

        private ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.image);
            companyNameView = (TextView) v.findViewById(R.id.company_view);
            sourceView = (TextView) v.findViewById(R.id.source_view);
            destinationView = (TextView) v.findViewById(R.id.dest_view);
            departureView = (TextView) v.findViewById(R.id.departure_view);
            priceView = (TextView) v.findViewById(R.id.price_view);
            validView = (TextView) v.findViewById(R.id.valid_view);
            codeView = (TextView) v.findViewById(R.id.code_view);
        }
    }

    public OrdersAdapter(Context context, OrdersCursor cursor) {
        Utils.getInstance();
        dataCursor = cursor;
        Utils.cursor = cursor;
        this.context = context;
    }

    public OrdersAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_row, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        dataCursor.moveToPosition(position);
        Log.d(TAG, "onBindViewHolder: attach");
        try {
            attackDataToViews(holder, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                context.startActivity(new Intent(context, OrderDetailsActivity.class).putExtra(Utils.CURSOR_POSITION, position));
            }
        });
    }

    private void attackDataToViews(ViewHolder holder, int position) {
        Log.d(TAG, "attackDataToViews: ROUTE_ID" + dataCursor.getRoute());
        RoutesCursor routeCursor = new RoutesSelection().routeid(Integer.parseInt(dataCursor.getRoute())).query(context.getContentResolver());
        if (routeCursor.moveToFirst()) {
            try {
                holder.priceView.setText(String.valueOf(routeCursor.getPrice()));
                holder.companyNameView.setText(routeCursor.getBuscompanyname());
                holder.sourceView.setText("From: " + routeCursor.getSource());
                holder.destinationView.setText("To: " + routeCursor.getDestination());
                holder.departureView.setText("Departure: " + Utils.formatDateString(routeCursor.getDeparture().toString()));
                picassoLoader(context, holder.imageView, routeCursor.getBuscompanyimage());
                routeCursor.close();
                holder.validView.setText("Valid: " + dataCursor.getValid());
                holder.codeView.setText("Receipt: " + dataCursor.getCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        // any number other than zero will cause a bug
        return (dataCursor == null) ? 0 : dataCursor.getCount();
    }
}