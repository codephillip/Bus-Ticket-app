package com.codephillip.app.busticket;

import android.app.DatePickerDialog;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.codephillip.app.busticket.provider.locations.LocationsColumns;
import com.codephillip.app.busticket.provider.locations.LocationsCursor;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SelectRouteFragment extends Fragment implements MaterialSpinner.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor>, DatePickerDialog.OnDateSetListener {

    private static final String TAG = SelectRouteFragment.class.getSimpleName();
    private MaterialSpinner timeSpinner;
    private Button selectButton;
    private String destination;
    private String source;
    private AutoCompleteTextView sourceAutoComp, destAutoComp;
    private EditText dateTextView;
    private DatePickerDialog datePickerDialog;

    public SelectRouteFragment() {
    }

    public static SelectRouteFragment newInstance() {
        return new SelectRouteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_route, container, false);

        sourceAutoComp = rootView.findViewById(R.id.from_auto_complete);
        destAutoComp = rootView.findViewById(R.id.to_auto_complete);
        dateTextView = rootView.findViewById(R.id.departure_date);

        timeSpinner = rootView.findViewById(R.id.time_spinner);
        timeSpinner.setOnItemSelectedListener(this);

        selectButton = rootView.findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (source.equals(destination)) {
                    Toast.makeText(getContext(), "Choose a different Destination", Toast.LENGTH_SHORT).show();
                } else {
                    source = sourceAutoComp.getText().toString();
                    destination = destAutoComp.getText().toString();
                    Intent intent = new Intent(getContext(), BookActivity.class);
                    intent.putExtra(Utils.SOURCE, source);
                    intent.putExtra(Utils.DESTINATION, destination);
                    getActivity().startActivity(intent);
                }
            }
        });

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        initializeDate();
        return rootView;
    }

    private void initializeDate() {
        Date date = Calendar.getInstance().getTime();
        Log.d(TAG, "initializeDate: " + date.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-dd-MMM");
        String formattedDate = df.format(date);
        dateTextView.setText(formattedDate);

        datePickerDialog = new DatePickerDialog(
                getContext(), this, date.getYear(), date.getMonth(), date.getDay());
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
        List<String> destLocations = new ArrayList<>();
        List<String> sourceLocations = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                destLocations.add(cursor.getName());
                if (cursor.getName().equalsIgnoreCase("Kampala")) {
                    // Spinner won't show single item
                    sourceLocations.add(cursor.getName());
                    sourceLocations.add(cursor.getName());
                }
            } while (cursor.moveToNext());
        }

        // Set default route values
        source = sourceLocations.get(0);
        destination = destLocations.get(0);

        String time[] = {"Morning", "Afternoon", "Evening"};
        ArrayAdapter<String> sourceDataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, sourceLocations);
        sourceDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceAutoComp.setAdapter(sourceDataAdapter);

        ArrayAdapter<String> destDataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, destLocations);
        sourceDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destAutoComp.setAdapter(destDataAdapter);

        ArrayAdapter<String> timeDataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, time);
        sourceDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeDataAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object itemObject) {
        String item = itemObject.toString();
        Log.d(TAG, "onItemSelected: " + item);

        if (view.getId() == timeSpinner.getId()) {
            Log.d(TAG, "onItemSelected: clicked dest");
            destination = item;
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String formattedDate = "%d-%d-%d";
        formattedDate = String.format(Locale.ENGLISH, formattedDate, year, month, day);
        Log.d(TAG, "onDateSet: " + formattedDate);
        dateTextView.setText(formattedDate);
    }
}
