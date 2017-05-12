package com.codephillip.app.busticket;

/**
 * Created by codephillip on 10/05/17.
 */

public class Utils {
    public static final String[] screenNames = {"Book", "Orders", "History", "Profile"};
    public static final String ROUTE_CODE = "route_code";
    //local environment
    //use http://10.0.3.2 when using genymotion emulator
    public static final String BASE_URL = "http://10.0.3.2:8000";
    //production environment
//    public static final String BASE_URL = "https://busticket.herokuapp.com";

    public static final Utils ourInstance = new Utils();

    public static Utils getInstance() {
        return ourInstance;
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
