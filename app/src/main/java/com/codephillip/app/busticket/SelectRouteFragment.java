package com.codephillip.app.busticket;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.codephillip.app.busticket.provider.locations.LocationsColumns;
import com.codephillip.app.busticket.provider.locations.LocationsCursor;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.codephillip.app.busticket.Constants.HAS_BOOKED;

public class SelectRouteFragment extends Fragment implements MaterialSpinner.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor>, DatePickerDialog.OnDateSetListener {

    private static final String TAG = SelectRouteFragment.class.getSimpleName();
    private MaterialSpinner timeSpinner;
    private Button selectButton;
    private String destination;
    private String source;
    private AutoCompleteTextView sourceAutoComp, destAutoComp;
    private EditText dateTextView;
    private DatePickerDialog datePickerDialog;
    private FirebaseAnalytics mFirebaseAnalytics;
    private Utils utils;

    public SelectRouteFragment() {
    }

    public static SelectRouteFragment newInstance() {
        return new SelectRouteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_route, container, false);

        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
            Utils.trackEvent(mFirebaseAnalytics, TAG, "Select route: started");
            utils = Utils.getInstance();
            utils = new Utils(getContext());
            if (utils.getPrefBoolean(HAS_BOOKED))
                utils.showFeedBackDialog(getContext(), mFirebaseAnalytics);
                utils.savePrefBoolean(HAS_BOOKED, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sourceAutoComp = rootView.findViewById(R.id.from_auto_complete);
        destAutoComp = rootView.findViewById(R.id.to_auto_complete);
        dateTextView = rootView.findViewById(R.id.departure_date);

        timeSpinner = rootView.findViewById(R.id.time_spinner);
        timeSpinner.setOnItemSelectedListener(this);

        selectButton = rootView.findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                source = sourceAutoComp.getText().toString();
                destination = destAutoComp.getText().toString();
                if (source.equals(destination)) {
                    Toast.makeText(getContext(), "Choose a different Destination", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, TAG);
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Select route " + source + " # " + destination);
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                    Intent intent = new Intent(getContext(), BookActivity.class);
                    intent.putExtra(utils.SOURCE, source);
                    intent.putExtra(utils.DESTINATION, destination);
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
        Calendar.getInstance().setTimeInMillis(System.currentTimeMillis());
        Date date = Calendar.getInstance().getTime();
        Log.d(TAG, "initializeDate: " + date.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(date);
        dateTextView.setText(formattedDate);
        Log.d(TAG, "initializeDate: " + date.getYear() + " # " +  date.getMonth() + " # " +  date.getDay());
        datePickerDialog = new DatePickerDialog(getContext(), this, date.getYear(), date.getMonth(), date.getDay());
        datePickerDialog.updateDate(date.getYear(), date.getMonth(), date.getDay());
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
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
        try {
            source = sourceLocations.get(0);
            destination = destLocations.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String formattedDate = "%d-%d-%d";
        // month counts from 0
        month += 1;
        formattedDate = String.format(Locale.ENGLISH, formattedDate, year, month, day);
        Log.d(TAG, "onDateSet: " + formattedDate);
        dateTextView.setText(formattedDate);
        Utils.trackEvent(mFirebaseAnalytics, TAG,"select date");
    }
}
