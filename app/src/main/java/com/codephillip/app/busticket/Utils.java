package com.codephillip.app.busticket;

/**
 * Created by codephillip on 10/05/17.
 */

public class Utils {
    public static final String[] screenNames = {"Book", "Orders", "History", "Profile"};
    public static final String ROUTE_CODE = "route_code";
    public static final Utils ourInstance = new Utils();

    public static Utils getInstance() {
        return ourInstance;
    }
}
