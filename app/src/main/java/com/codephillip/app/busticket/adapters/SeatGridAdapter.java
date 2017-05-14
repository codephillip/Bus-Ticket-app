package com.codephillip.app.busticket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        public ImageView numberView;

        public ViewHolder(View itemView) {
            super(itemView);
            numberView = (ImageView) itemView.findViewById(R.id.numberImageView);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeSeatColourWhenBooked(holder.numberView);
            }
        });
        changeSeatColourRandomly(holder.numberView);
    }

    private void changeSeatColourWhenBooked(ImageView numberView) {
        if (!hasBooked) {
            numberView.setColorFilter(context.getResources().getColor((R.color.colorAccent)));
            hasBooked = true;
        }
    }

    private void changeSeatColourRandomly(ImageView numberView) {
        int colorId;
        if (getRandomBoolean())
            colorId = R.color.colorAccent;
        else
            colorId = R.color.grey;
        numberView.setColorFilter(context.getResources().getColor((colorId)));
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