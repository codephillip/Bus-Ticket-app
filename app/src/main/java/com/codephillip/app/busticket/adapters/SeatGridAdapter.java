package com.codephillip.app.busticket.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codephillip.app.busticket.R;
import com.codephillip.app.busticket.Utils;

import static com.codephillip.app.busticket.Constants.HAS_BOOKED;

/**
 * Created by codephillip on 31/03/17.
 */

public class SeatGridAdapter extends RecyclerView.Adapter<SeatGridAdapter.ViewHolder> {

    private static final String TAG = SeatGridAdapter.class.getSimpleName();
    private final Utils utils;
    private LayoutInflater mInflater;
    private static Context context;
    private ItemClickListener mClickListener;
    private ImageView oldSeatView;
    private TextView oldNumberView;


    public SeatGridAdapter(Context context) {
        Log.d(TAG, "SeatGridAdapter: ATTACHED");
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        utils = new Utils(context);
        utils.savePrefBoolean(HAS_BOOKED, false);
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

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        cursor.moveToPosition(position);
        holder.seatNumberText.setText(String.valueOf(position + 1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSeatColourWhenBooked(holder.seatView, holder.seatNumberText);
                if (mClickListener != null)
                    mClickListener.onItemClick(view, position);
            }
        });
        //todo replace with actual number of seats available
        //randomly place booked seats
        changeSeatColourRandomly(holder.seatView, holder.seatNumberText);
    }

    private void changeSeatColourWhenBooked(ImageView seatView, TextView numberView) {
        if (utils.getPrefBoolean(HAS_BOOKED)) {
            changeSeatColour(seatView, numberView);
        } else {
            setSeatColour(seatView, numberView, context.getResources().getColor((R.color.colorAccent)));
        }

        oldSeatView = seatView;
        oldNumberView = numberView;
        saveSeatNumber(numberView.getText().toString());
        utils.savePrefBoolean(HAS_BOOKED, true);
    }

    private void changeSeatColour(ImageView seatView, TextView numberView) {
        if (!oldSeatView.equals(seatView) && !oldNumberView.equals(numberView)) {
            setSeatColour(oldSeatView, oldNumberView, context.getResources().getColor((R.color.grey)));
            setSeatColour(seatView, numberView, context.getResources().getColor((R.color.colorAccent)));
        }
    }

    private void changeSeatColourRandomly(ImageView seatView, TextView numberView) {
        int colorId;
        if (getRandomBoolean())
            colorId = R.color.colorAccent;
        else
            colorId = R.color.grey;
        setSeatColour(seatView, numberView, context.getResources().getColor((colorId)));
    }

    private boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }

    private void setSeatColour(ImageView seatView, TextView numberView, int color) {
        seatView.setColorFilter(color);
        numberView.setTextColor(color);
    }

    // total number of cells
    @Override
    public int getItemCount() {
//        return cursor.getCount();
        return 20;
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private void saveSeatNumber(String seatNumber) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Utils.SEAT_NUMBER, seatNumber);
        editor.apply();
    }

    private String getSeatNumber() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Utils.SEAT_NUMBER, "1");
    }
}