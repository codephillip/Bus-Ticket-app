package com.codephillip.app.busticket;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.codephillip.app.busticket.adapters.OrdersAdapter;
import com.codephillip.app.busticket.provider.orders.OrdersCursor;
import com.codephillip.app.busticket.provider.orders.OrdersSelection;

public class HistoryFragment extends Fragment {

    private static final String TAG = HistoryFragment.class.getSimpleName();
    RecyclerView recyclerView;
    private LinearLayout errorLinearLayout;
    public OrdersAdapter adapter;

    public HistoryFragment() {
    }

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        Utils.HISTROY_FRAG_ACTIVE = true;
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        errorLinearLayout = (LinearLayout) rootView.findViewById(R.id.error_layout);

        OrdersCursor cursor = queryOrdersTable();
        showErrorMessage(cursor);
        adapter = new OrdersAdapter(getContext(), cursor);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    private OrdersCursor queryOrdersTable() {
        return new OrdersSelection().orderById(true).query(getContext().getContentResolver());
    }

    private void showErrorMessage(Cursor cursor) {
        Log.d(TAG, "showErrorMessage: started");
        if (!cursor.moveToFirst()) {
            recyclerView.setVisibility(View.GONE);
            errorLinearLayout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            errorLinearLayout.setVisibility(View.GONE);
        }
    }
}
