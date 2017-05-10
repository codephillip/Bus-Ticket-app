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
 * Created by codephillip on 10/05/17.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private static final String TAG = BookAdapter.class.getSimpleName();
//    private BooktableCursor dataCursor;
    private static Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView numberView;
        private TextView titleView;

        private ViewHolder(View v) {
            super(v);
//            numberView = (ImageView) v.findViewById(R.id.numberImageView);
//            titleView = (TextView) v.findViewById(R.id.title_view);
//            titleView.setTypeface(typeface);
        }
    }

//    public BookAdapter(Context mContext, BooktableCursor cursor) {
//        dataCursor = cursor;
//        context = mContext;
//        Utils.getInstance();
//        colourQueue = new ColourQueue();
//    }

    public BookAdapter() {

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_row2, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        dataCursor.moveToPosition(position);
//        try {
//            String name = dataCursor.getName();
//            holder.titleView.setText(name);
//            holder.numberView.setImageDrawable(Utils.generateTextDrawable(name.substring(0, 1), colourQueue));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: ");
//                dataCursor.moveToPosition(position);
//                context.startActivity(new Intent(context, AllSongsActivity.class).putExtra(Utils.CATEGORY, dataCursor.getName()));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        // any number other than zero will cause a bug
//        return (dataCursor == null) ? 0 : dataCursor.getCount();
        return 10;
    }
}