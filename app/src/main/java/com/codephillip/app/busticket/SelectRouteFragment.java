package com.codephillip.app.busticket;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.codephillip.app.busticket.provider.locations.LocationsColumns;
import com.codephillip.app.busticket.provider.locations.LocationsCursor;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class SelectRouteFragment extends Fragment implements MaterialSpinner.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = SelectRouteFragment.class.getSimpleName();
    private MaterialSpinner destSpinner;
    private MaterialSpinner sourceSpinner;
    private Button selectButton;
    private String destination;
    private String source;

    public SelectRouteFragment() {
    }

    public static SelectRouteFragment newInstance() {
        return new SelectRouteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_route, container, false);
        destSpinner = rootView.findViewById(R.id.dest_spinner);
        sourceSpinner = rootView.findViewById(R.id.source_spinner);

        destSpinner.setOnItemSelectedListener(this);
        sourceSpinner.setOnItemSelectedListener(this);

        selectButton = rootView.findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (source.equals(destination)) {
                    Toast.makeText(getContext(), "Choose a different Destination", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getContext(), BookActivity.class);
                    intent.putExtra(Utils.SOURCE, source);
                    intent.putExtra(Utils.DESTINATION, destination);
                    getActivity().startActivity(intent);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(2, null, this);
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
            } while (cursor.moveToNext());
        }

        // Set default route values
        source = locations.get(0);
        destination = locations.get(0);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, locations);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(dataAdapter);
        destSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object itemObject) {
        Snackbar.make(view, "Clicked " + itemObject.toString(), Snackbar.LENGTH_LONG).show();
        String item = itemObject.toString();
        Log.d(TAG, "onItemSelected: " + item);

        if (view.getId() == destSpinner.getId()) {
            Log.d(TAG, "onItemSelected: clicked dest");
            destination = item;
        } else {
            Log.d(TAG, "onItemSelected: clicked source");
            source = item;
        }
    }
}
