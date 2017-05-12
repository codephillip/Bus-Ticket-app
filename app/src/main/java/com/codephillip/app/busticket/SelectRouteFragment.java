package com.codephillip.app.busticket;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.codephillip.app.busticket.provider.locations.LocationsColumns;
import com.codephillip.app.busticket.provider.locations.LocationsCursor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class SelectRouteFragment extends Fragment implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = SelectRouteFragment.class.getSimpleName();
    Spinner destSpinner;
    Spinner sourceSpinner;
    Map<String, Long> locationsMap = new Hashtable<>();

    public SelectRouteFragment() {
    }

    public static SelectRouteFragment newInstance() {
        return new SelectRouteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_route, container, false);
        destSpinner = (Spinner) rootView.findViewById(R.id.dest_spinner);
        sourceSpinner = (Spinner) rootView.findViewById(R.id.source_spinner);
        destSpinner.setOnItemSelectedListener(this);
        sourceSpinner.setOnItemSelectedListener(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(2, null, this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Log.d(TAG, "onItemSelected: " + locationsMap.get(item));
//        Intent intent = new Intent(getContext(), BookActivity.class);
//        intent.putExtra(Utils.SOURCE, locationsMap.get(item));
//        intent.putExtra(Utils.DESTINATION, locationsMap.get(item));
//        getActivity().startService(new Intent().putExtra("crop_id", locationsMap.get(item)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //dummy
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), LocationsColumns.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished: started");
        LocationsCursor cursor = new LocationsCursor(data);
        List<String> locations = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                locations.add(cursor.getName());
                locationsMap.put(cursor.getName(), cursor.getId());
            } while (cursor.moveToNext());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, locations);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(dataAdapter);
        destSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
