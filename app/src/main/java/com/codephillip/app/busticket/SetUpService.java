package com.codephillip.app.busticket;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.codephillip.app.busticket.mymodels.Orders;
import com.codephillip.app.busticket.mymodels.routeobject.Route;
import com.codephillip.app.busticket.mymodels.routeobject.Routes;
import com.codephillip.app.busticket.provider.routes.RoutesContentValues;
import com.codephillip.app.busticket.retrofit.ApiClient;
import com.codephillip.app.busticket.retrofit.ApiInterface;

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
            loadRoutes();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            loadOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRoutes() {
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
                Log.d("RETROFIT#", "onFailure: " + t.toString());
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
            values.putSource(route.getSource());
            values.putDestination(route.getDestination());
            values.putPrice(route.getPrice());
            values.putDeparture(route.getDeparture());
            values.putArrival(route.getArrival());
            values.putBuscompanyname(route.getBus().getBusCompany().getName());
            values.putBuscompanyimage(route.getBus().getBusCompany().getImage());
            final Uri uri = values.insert(getContentResolver());
            Log.d("INSERT: ", "inserting" + uri.toString());
        }
    }

    private void loadOrders() {
        Call<Orders> call = apiInterface.allOrders();
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, retrofit2.Response<Orders> response) {
                Log.d("RETROFIT#", "onResponse: " + response.headers());
                Orders orders = response.body();
//                saveOrders(orders);
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                Log.d("RETROFIT#", "onFailure: " + t.toString());
            }
        });
    }
}
