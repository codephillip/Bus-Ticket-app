package com.codephillip.app.busticket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.codephillip.app.busticket.adapters.BookAdapter;
import com.codephillip.app.busticket.provider.routes.RoutesCursor;
import com.codephillip.app.busticket.provider.routes.RoutesSelection;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = BookFragment.class.getSimpleName();
    private Spinner spinner, spinner2;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<String> categories = new ArrayList<>();
    //category package is set according to which category is selected
    private List<String> categoryPackages = new ArrayList<>();

    public BookFragment() {
    }

    public static BookFragment newInstance() {
        return new BookFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book, container, false);
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        spinner2 = (Spinner) rootView.findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(getContext(), queryRoutesTable());
        recyclerView.setAdapter(adapter);

        initializeSpinners();
        return rootView;
    }

    private void initializeSpinners() {
        categories.add("Price");
        categories.add("Date");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        
        categoryPackages.add("20000");
        categoryPackages.add("30000");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoryPackages);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Log.d(TAG, "onItemSelected: item#" + item);
        Log.d(TAG, "onItemSelected: id#" + view.getId());
//        Log.d(TAG, "onItemSelected: parent id#" + parent.get);
        Log.d(TAG, "onItemSelected: position#" + position);
        Log.d(TAG, "onItemSelected: id#" + id);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //dummy
    }
}
