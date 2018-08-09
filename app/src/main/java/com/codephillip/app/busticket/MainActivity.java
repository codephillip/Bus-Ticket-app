package com.codephillip.app.busticket;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.codephillip.app.busticket.provider.orders.OrdersContentValues;
import com.codephillip.app.busticket.retrofit.ApiClient;
import com.codephillip.app.busticket.retrofit.ApiInterface;
import com.codephillip.app.busticket.retromodels.Order;
import com.codephillip.app.busticket.retromodels.orders.Orders;
import com.codephillip.app.busticket.retromodels.orders.Result;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
import retrofit2.Call;
import retrofit2.Callback;

import static com.codephillip.app.busticket.Utils.screenNames;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //populate the first default fragment
        Fragment fragment = SelectRouteFragment.newInstance();
        getSupportActionBar().setTitle(screenNames[0]);
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();

        try {
            loadOrders();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up orderButton, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("Navigation bar", "onNavigationItemSelected: " + id);
        Fragment fragment;
        if (id == R.id.nav_home) {
            fragment = SelectRouteFragment.newInstance();
            getSupportActionBar().setTitle(screenNames[0]);
        }
        else if (id == R.id.nav_history) {
            fragment = HistoryFragment.newInstance();
            getSupportActionBar().setTitle(screenNames[1]);
        } else if (id == R.id.nav_profile) {
            fragment = ProfileFragment.newInstance();
            getSupportActionBar().setTitle(screenNames[2]);
        }
// else if (id == R.id.nav_settings) {
//            startActivity(new Intent(this, SettingsActivity.class));
//            return true;
//        }
        else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        else {
            return true;
        }
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadOrders() {
        Log.d(TAG, "loadOrders: started");
        ApiInterface apiInterface = ApiClient.getClient(Utils.BASE_URL).create(ApiInterface.class);
        Log.d(TAG, "loadOrders: Customer ID " + Utils.customer.getId().toString());
        String url = Utils.BASE_URL + "/api/v1/customers/" + Utils.customer.getId().toString() + "/orders";
        Log.d(TAG, "loadOrders: " + url);
        Call<Orders> call = apiInterface.getCustomerOrders(url);
        call.enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, retrofit2.Response<Orders> response) {
                Log.d("RETROFIT#$", "onResponse: " + response.code());
                Log.d("RETROFIT#$", "onResponse: " + response.message());
                Log.d("RETROFIT#$", "onResponse: " + new Gson().toJson(response.body()));
                com.codephillip.app.busticket.retromodels.orders.Orders orders = response.body();
                saveOrders(orders);
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                Log.e("RETROFIT#", "onFailure: " + t.toString());
            }
        });
    }

    private void saveOrders(com.codephillip.app.busticket.retromodels.orders.Orders orders) {
        Log.d("INSERT: ", "starting");
        if (orders == null)
            throw new NullPointerException("Orders not found");
        List<Result> orderList = orders.getResults();
        for (Result order : orderList) {
            Log.d(TAG, "saveOrder: " + order.getRoute().getId().toString() + 
                    order.getValid().toString() + order.getCode().toString());
            OrdersContentValues values = new OrdersContentValues();
            values.putCode(String.valueOf(order.getCode()));
            values.putValid(order.getValid());
            values.putRoute(String.valueOf(order.getRoute().getId()));
            values.putCustomer(String.valueOf(order.getCustomer()));
            //todo get correct date from server
            values.putDate(new Date());
            final Uri uri = values.insert(getContentResolver());
            Log.d("INSERT: ", "inserting" + uri.toString());
        }
    }
}