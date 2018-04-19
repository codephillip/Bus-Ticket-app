package com.codephillip.app.busticket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.codephillip.app.busticket.adapters.BookAdapter;
import com.codephillip.app.busticket.provider.routes.RoutesCursor;
import com.codephillip.app.busticket.provider.routes.RoutesSelection;
import com.google.firebase.analytics.FirebaseAnalytics;

public class BookFragment extends Fragment {

    private static final String TAG = BookFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private LinearLayout errorLayout;
    private FirebaseAnalytics mFirebaseAnalytics;

    public BookFragment() {
    }

    public static BookFragment newInstance() {
        return new BookFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book, container, false);

        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
            Utils.trackEvent(mFirebaseAnalytics, TAG, "Selecting bus: started");
            Log.d(TAG, "onCreate: added analytics");
        } catch (Exception e) {
            e.printStackTrace();
        }

        errorLayout = rootView.findViewById(R.id.error_layout);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            RoutesCursor cursor = null;
            cursor = queryRoutesTable();
            int count = cursor.getCount();
            if(count <= 0) {
                recyclerView.setVisibility(View.GONE);
                errorLayout.setVisibility(View.VISIBLE);
                Utils.trackEvent(mFirebaseAnalytics, TAG, "Selecting bus:  No routes found");
            } else {
                adapter = new BookAdapter(getContext(), cursor);
                recyclerView.setAdapter(adapter);
            }
            Utils.trackEvent(mFirebaseAnalytics, TAG, "Selecting bus: " + Utils.SOURCE + " # " + Utils.DESTINATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    private RoutesCursor queryRoutesTable() {
//        return new RoutesSelection().query(getContext().getContentResolver());
        Log.d(TAG, "queryRoutesTable: ### " + getActivity().getIntent().getStringExtra(Utils.SOURCE));
        return new RoutesSelection()
                .source(getActivity().getIntent().getStringExtra(Utils.SOURCE))
                .and()
                .destination(getActivity().getIntent().getStringExtra(Utils.DESTINATION))
                .query(getContext().getContentResolver());
    }
}
