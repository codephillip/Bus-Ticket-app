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

import com.codephillip.app.busticket.ConfirmOrderActivity;
import com.codephillip.app.busticket.R;
import com.codephillip.app.busticket.Utils;
import com.codephillip.app.busticket.provider.orders.OrdersCursor;
import com.codephillip.app.busticket.provider.routes.RoutesCursor;


/**
 * Created by codephillip on 10/05/17.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private static final String TAG = BookAdapter.class.getSimpleName();
    private RoutesCursor dataCursor;
    private static Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView companyNameView;
        private TextView sourceView;
        private TextView destinationView;
        private TextView departureView;
        private TextView priceView;

        private ViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.image);
            companyNameView = (TextView) v.findViewById(R.id.company_view);
            sourceView = (TextView) v.findViewById(R.id.source_view);
            destinationView = (TextView) v.findViewById(R.id.dest_view);
            departureView = (TextView) v.findViewById(R.id.departure_view);
            priceView = (TextView) v.findViewById(R.id.price_view);
        }
    }

    public BookAdapter(Context context, RoutesCursor cursor) {
        Utils.getInstance();
        Utils.cursor = cursor;
        dataCursor = cursor;
        this.context = context;
        Utils.getInstance();
    }

    public BookAdapter(Context context, OrdersCursor ordersCursor) {
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_row2, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        dataCursor.moveToPosition(position);
        try {
            //todo add bus imageView
            holder.priceView.setText(String.valueOf(dataCursor.getPrice()));
            holder.companyNameView.setText(dataCursor.getBuscompanyname());
            //todo use string builder %s
            holder.sourceView.setText("From: " + dataCursor.getSource());
            holder.destinationView.setText("To: " + dataCursor.getDestination());
            //todo truncate date
            holder.departureView.setText("Departure: " + Utils.formatDateString(dataCursor.getDeparture().toString()));

//            picassoLoader(context, holder.imageView, dataCursor.getBuscompanyimage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                dataCursor.moveToPosition(position);
                context.startActivity(new Intent(context, ConfirmOrderActivity.class).putExtra(Utils.CURSOR_POSITION, position));
            }
        });
    }

    @Override
    public int getItemCount() {
        // any number other than zero will cause a bug
        return (dataCursor == null) ? 0 : dataCursor.getCount();
    }
}