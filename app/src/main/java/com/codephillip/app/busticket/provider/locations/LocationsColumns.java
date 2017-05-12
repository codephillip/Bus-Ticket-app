package com.codephillip.app.busticket.provider.locations;

import android.net.Uri;
import android.provider.BaseColumns;

import com.codephillip.app.busticket.provider.BusTicketProvider;
import com.codephillip.app.busticket.provider.locations.LocationsColumns;
import com.codephillip.app.busticket.provider.orders.OrdersColumns;
import com.codephillip.app.busticket.provider.routes.RoutesColumns;

/**
 * Columns for the {@code locations} table.
 */
public class LocationsColumns implements BaseColumns {
    public static final String TABLE_NAME = "locations";
    public static final Uri CONTENT_URI = Uri.parse(BusTicketProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String NAME = "name";

    public static final String LATITUDE = "latitude";

    public static final String LONGITUDE = "longitude";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            NAME,
            LATITUDE,
            LONGITUDE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(LATITUDE) || c.contains("." + LATITUDE)) return true;
            if (c.equals(LONGITUDE) || c.contains("." + LONGITUDE)) return true;
        }
        return false;
    }

}
