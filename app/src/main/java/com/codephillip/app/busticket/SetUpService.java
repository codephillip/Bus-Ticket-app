package com.codephillip.app.busticket;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.codephillip.app.busticket.broadcasts.MyReceiver;
import com.codephillip.app.busticket.retromodels.Order;
import com.codephillip.app.busticket.retromodels.Orders;
import com.codephillip.app.busticket.retromodels.location.Location;
import com.codephillip.app.busticket.retromodels.location.Locations;
import com.codephillip.app.busticket.provider.locations.LocationsColumns;
import com.codephillip.app.busticket.provider.locations.LocationsContentValues;
import com.codephillip.app.busticket.provider.orders.OrdersColumns;
import com.codephillip.app.busticket.provider.orders.OrdersContentValues;
import com.codephillip.app.busticket.provider.routes.RoutesColumns;
import com.codephillip.app.busticket.provider.routes.RoutesContentValues;
import com.codephillip.app.busticket.retrofit.ApiClient;
import com.codephillip.app.busticket.retrofit.ApiInterface;
import com.codephillip.app.busticket.retromodels.route.Route;
import com.codephillip.app.busticket.retromodels.route.Routes;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SetUpService extends IntentService {

    private static final String TAG = SetUpService.class.getSimpleName();
    private ApiInterface apiInterface;

    public SetUpService() {
        super("SetUpService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: started service");
        apiInterface = ApiClient.getClient(Utils.BASE_URL).create(ApiInterface.class);

        try {
            deleteData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            loadLocations();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            loadRoutes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteData() {
        getContentResolver().delete(LocationsColumns.CONTENT_URI, null, null);
        getContentResolver().delete(RoutesColumns.CONTENT_URI, null, null);
        getContentResolver().delete(OrdersColumns.CONTENT_URI, null, null);
    }

    private void loadLocations() {
        Call<Locations> call = apiInterface.allLocations();
        call.enqueue(new Callback<Locations>() {
            @Override
            public void onResponse(Call<Locations> call, retrofit2.Response<Locations> response) {
                Log.d("RETROFIT#", "onResponse: " + response.headers());
                Locations locations = response.body();
                saveLocations(locations);
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable t) {
                Log.e("RETROFIT#", "onFailure: " + t.toString());
            }
        });
    }

    private void saveLocations(Locations locations) {
        Log.d("INSERT: ", "starting");
        if (locations == null)
            throw new NullPointerException("Locations not found");
        List<Location> locationList = locations.getLocations();
        for (Location location : locationList) {
            Log.d(TAG, "saveLocation: " + location.getName() + location.getId());
            LocationsContentValues values = new LocationsContentValues();
            values.putName(location.getName());
            values.putLatitude(location.getLatitude().doubleValue());
            values.putLongitude(location.getLongitude().doubleValue());
            final Uri uri = values.insert(getContentResolver());
            Log.d("INSERT: ", "inserting" + uri.toString());
        }
        Log.d(TAG, "saveLocations: finished");
    }

    private void loadRoutes() {
        Log.d(TAG, "loadRoutes: ROUTES FETCHING");
        Call<Routes> call = apiInterface.allRoutes();
        call.enqueue(new Callback<Routes>() {
            @Override
            public void onResponse(Call<Routes> call, retrofit2.Response<Routes> response) {
                Log.d("RETROFIT#", "onResponse: " + response.headers());
                Routes routes = response.body();
                saveRoutes(routes);
            }

            @Override
            public void onFailure(Call<Routes> call, Throwable t) {
                Log.e("RETROFIT#", "onFailure: " + t.toString());
            }
        });
    }

    private void saveRoutes(Routes routes) {
        Log.d("INSERT: ", "starting");
        if (routes == null)
            throw new NullPointerException("Routes not found");
        List<Route> routeList = routes.getRoutes();
        for (Route route : routeList) {
            Log.d(TAG, "saveRoute: " + route.getArrival() + route.getCode() + route.getSource());
            RoutesContentValues values = new RoutesContentValues();
            values.putCode(route.getCode());
            Log.d(TAG, "saveRoutes: ROUTE_ID" + route.getId());
            values.putRouteid(route.getId());
            values.putSource(route.getSource().getName());
            values.putDestination(route.getDestination().getName());
            values.putPrice(route.getPrice());
            values.putDeparture(route.getArrival());
            values.putArrival(route.getDeparture());
            values.putBuscompanyname(route.getBus().getBusCompany().getName());
            values.putBuscompanyimage(route.getBus().getBusCompany().getImage());
            final Uri uri = values.insert(getContentResolver());
            Log.d("INSERT: ", "inserting" + uri.toString());
        }
        startBroadcast();
    }

    private void startBroadcast() {
        // send broadcast to start MainActivity after all server request are complete
        Log.d(TAG, "onHandleIntent: start broadcast");
        Intent mainIntent = new Intent(getApplicationContext(), MyReceiver.class);
        sendBroadcast(mainIntent);
    }
}