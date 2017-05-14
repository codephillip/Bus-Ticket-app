package com.codephillip.app.busticket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codephillip.app.busticket.adapters.OrdersAdapter;
import com.codephillip.app.busticket.provider.orders.OrdersCursor;
import com.codephillip.app.busticket.provider.orders.OrdersSelection;

public class OrdersFragment extends Fragment {

    RecyclerView recyclerView;
    public OrdersAdapter adapter;

    public OrdersFragment() {
    }

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        Utils.HISTROY_FRAG_ACTIVE = false;
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrdersAdapter(getContext(), queryOrdersTable());
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    private OrdersCursor queryOrdersTable() {
        return new OrdersSelection().valid(true).orderById(true).query(getContext().getContentResolver());
    }
}
