package com.codephillip.app.busticket.provider.routes;

import android.net.Uri;
import android.provider.BaseColumns;

import com.codephillip.app.busticket.provider.BusTicketProvider;
import com.codephillip.app.busticket.provider.locations.LocationsColumns;
import com.codephillip.app.busticket.provider.orders.OrdersColumns;
import com.codephillip.app.busticket.provider.routes.RoutesColumns;

/**
 * Columns for the {@code routes} table.
 */
public class RoutesColumns implements BaseColumns {
    public static final String TABLE_NAME = "routes";
    public static final Uri CONTENT_URI = Uri.parse(BusTicketProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String ROUTEID = "routeId";

    public static final String CODE = "Code";

    public static final String SOURCE = "source";

    public static final String DESTINATION = "Destination";

    public static final String BUSCOMPANYNAME = "BusCompanyName";

    public static final String BUSCOMPANYIMAGE = "BusCompanyImage";

    public static final String PRICE = "Price";

    public static final String ARRIVAL = "Arrival";

    public static final String DEPARTURE = "Departure";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            ROUTEID,
            CODE,
            SOURCE,
            DESTINATION,
            BUSCOMPANYNAME,
            BUSCOMPANYIMAGE,
            PRICE,
            ARRIVAL,
            DEPARTURE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(ROUTEID) || c.contains("." + ROUTEID)) return true;
            if (c.equals(CODE) || c.contains("." + CODE)) return true;
            if (c.equals(SOURCE) || c.contains("." + SOURCE)) return true;
            if (c.equals(DESTINATION) || c.contains("." + DESTINATION)) return true;
            if (c.equals(BUSCOMPANYNAME) || c.contains("." + BUSCOMPANYNAME)) return true;
            if (c.equals(BUSCOMPANYIMAGE) || c.contains("." + BUSCOMPANYIMAGE)) return true;
            if (c.equals(PRICE) || c.contains("." + PRICE)) return true;
            if (c.equals(ARRIVAL) || c.contains("." + ARRIVAL)) return true;
            if (c.equals(DEPARTURE) || c.contains("." + DEPARTURE)) return true;
        }
        return false;
    }

}
