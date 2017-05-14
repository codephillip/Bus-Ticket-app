package com.codephillip.app.busticket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codephillip.app.busticket.R;

/**
 * Created by codephillip on 31/03/17.
 */

public class SeatGridAdapter extends RecyclerView.Adapter<SeatGridAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private static Context context;
    private boolean hasBooked = false;

    // hymns is passed into the constructor
    public SeatGridAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView seatNumberText;
        public ImageView seatView;

        public ViewHolder(View itemView) {
            super(itemView);
            seatNumberText = (TextView) itemView.findViewById(R.id.seat_number);
            seatView = (ImageView) itemView.findViewById(R.id.seat_image);
        }
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the hymns to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        cursor.moveToPosition(position);
//        holder.seatNumberText.setText(position + 1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSeatColourWhenBooked(holder.seatView, holder.seatNumberText);
            }
        });
        changeSeatColourRandomly(holder.seatView, holder.seatNumberText);
    }

    private void changeSeatColourWhenBooked(ImageView seatView, TextView numberView) {
        if (!hasBooked) {
            seatView.setColorFilter(context.getResources().getColor((R.color.colorAccent)));
            numberView.setTextColor(context.getResources().getColor((R.color.colorAccent)));
            hasBooked = true;
        }
    }

    private void changeSeatColourRandomly(ImageView seatView, TextView numberView) {
        int colorId;
        if (getRandomBoolean())
            colorId = R.color.colorAccent;
        else
            colorId = R.color.grey;
        seatView.setColorFilter(context.getResources().getColor((colorId)));
        numberView.setTextColor(context.getResources().getColor((colorId)));
    }

    private boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    // total number of cells
    @Override
    public int getItemCount() {
//        return cursor.getCount();
        return 20;
    }
}