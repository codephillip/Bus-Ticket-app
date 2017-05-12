package com.codephillip.app.busticket;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.codephillip.app.busticket.mymodels.Customer;

/**
 * Created by codephillip on 12/05/17.
 */

public class Utils {
    public static final String[] screenNames = {"Book", "Orders", "History", "Profile"};
    public static final String ROUTE_CODE = "route_code";
    //local environment
    //use http://10.0.3.2 when using genymotion emulator
//    public static final String BASE_URL = "http://10.0.3.2:8000";
    //production environment
    public static final String BASE_URL = "https://busticket-backend.herokuapp.com";

    public static final Utils ourInstance = new Utils();
    public static final String CURSOR_POSITION = "cursor_position";
    public static final String SOURCE = "source";
    public static final String DESTINATION = "destination";
    public static final String ORDERS = "orders";
    public static final String HISTORY = "history";
    public static Cursor cursor;
    public static Customer customer;

    public static Utils getInstance() {
        return ourInstance;
    }

    public static boolean isConnectedToInternet(Activity activity) {
        ConnectivityManager connectivity = (ConnectivityManager) activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

//    public static void picassoLoader(Context context, ImageView imageView, String url) {
//        Log.d("PICASSO", "loading image");
//        Picasso.with(context)
//                .load(url)
////                .load("http://192.168.56.1/images/ahagzjsozh.jpg")
//                .placeholder(R.drawable.nav_image)
//                .error(R.drawable.nav_image)
//                .into(imageView);
//    }
}
