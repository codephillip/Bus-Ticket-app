package com.codephillip.app.busticket.provider.orders;

import android.net.Uri;
import android.provider.BaseColumns;

import com.codephillip.app.busticket.provider.BusTicketProvider;
import com.codephillip.app.busticket.provider.orders.OrdersColumns;
import com.codephillip.app.busticket.provider.routes.RoutesColumns;

/**
 * Columns for the {@code orders} table.
 */
public class OrdersColumns implements BaseColumns {
    public static final String TABLE_NAME = "orders";
    public static final Uri CONTENT_URI = Uri.parse(BusTicketProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String CODE = "Code";

    public static final String DALID = "Dalid";

    public static final String DATE = "Date";

    public static final String CUSTOMER = "Customer";

    public static final String ROUTE = "Route";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            CODE,
            DALID,
            DATE,
            CUSTOMER,
            ROUTE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(CODE) || c.contains("." + CODE)) return true;
            if (c.equals(DALID) || c.contains("." + DALID)) return true;
            if (c.equals(DATE) || c.contains("." + DATE)) return true;
            if (c.equals(CUSTOMER) || c.contains("." + CUSTOMER)) return true;
            if (c.equals(ROUTE) || c.contains("." + ROUTE)) return true;
        }
        return false;
    }

}
