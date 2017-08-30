package com.codephillip.app.busticket;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.codephillip.app.busticket.provider.locations.LocationsColumns;
import com.codephillip.app.busticket.provider.locations.LocationsCursor;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class SelectRouteFragment extends Fragment implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = SelectRouteFragment.class.getSimpleName();
    Spinner destSpinner;
    Spinner sourceSpinner;
    Button selectButton;
    Map<String, Long> locationsMap = new Hashtable<>();
    private String destination;
    private String source;
    private SliderLayout mDemoSlider;

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
        mDemoSlider = (SliderLayout) rootView.findViewById(R.id.slider);

        destSpinner.setOnItemSelectedListener(this);
        sourceSpinner.setOnItemSelectedListener(this);

        selectButton = (Button) rootView.findViewById(R.id.select_button);
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


        //todo make a list of sponsors. query them from the server
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("MTN Uganda", "https://seeklogo.com/images/M/MTN-logo-459AAF9482-seeklogo.com.png");
        url_maps.put("MTN Uganda2", "https://seeklogo.com/images/M/MTN-logo-459AAF9482-seeklogo.com.png");
        url_maps.put("MTN Uganda3", "https://seeklogo.com/images/M/MTN-logo-459AAF9482-seeklogo.com.png");

        //todo swap with sponsor_urls
        //todo fix the out of memory exception. doing too much work on its main thread
        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(2, null, this);
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onStart() {
        mDemoSlider.startAutoCycle();
        super.onStart();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Log.d(TAG, "onItemSelected: " + item);

        if (parent.getId() == destSpinner.getId()) {
            Log.d(TAG, "onItemSelected: clicked dest");
            destination = item;
        } else {
            Log.d(TAG, "onItemSelected: clicked source");
            source = item;
        }
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
