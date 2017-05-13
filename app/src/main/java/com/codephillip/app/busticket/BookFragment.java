package com.codephillip.app.busticket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.codephillip.app.busticket.adapters.BookAdapter;
import com.codephillip.app.busticket.provider.routes.RoutesCursor;
import com.codephillip.app.busticket.provider.routes.RoutesSelection;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class BookFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    RecyclerView recyclerView;
    Map<String, Integer> cropsMap = new Hashtable<>();
    public BookAdapter adapter;

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

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(getContext(), queryRoutesTable());
//        adapter = new BookAdapter(getContext());
        recyclerView.setAdapter(adapter);


        List<String> categories = new ArrayList<>();
        categories.add("Price");
        categories.add("Date");
        categories.add("Company");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        return rootView;
    }

    private RoutesCursor queryRoutesTable() {
//        return new RoutesSelection().query(getContext().getContentResolver());
        return new RoutesSelection().query(getContext().getContentResolver());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(getContext(), "Clicked " + item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //dummy
    }
}
