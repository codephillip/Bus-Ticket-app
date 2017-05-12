package com.codephillip.app.busticket.provider;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.codephillip.app.busticket.BuildConfig;
import com.codephillip.app.busticket.provider.base.BaseContentProvider;
import com.codephillip.app.busticket.provider.locations.LocationsColumns;
import com.codephillip.app.busticket.provider.orders.OrdersColumns;
import com.codephillip.app.busticket.provider.routes.RoutesColumns;

public class BusTicketProvider extends BaseContentProvider {
    private static final String TAG = BusTicketProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.codephillip.app.busticket.provider";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_LOCATIONS = 0;
    private static final int URI_TYPE_LOCATIONS_ID = 1;

    private static final int URI_TYPE_ORDERS = 2;
    private static final int URI_TYPE_ORDERS_ID = 3;

    private static final int URI_TYPE_ROUTES = 4;
    private static final int URI_TYPE_ROUTES_ID = 5;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, LocationsColumns.TABLE_NAME, URI_TYPE_LOCATIONS);
        URI_MATCHER.addURI(AUTHORITY, LocationsColumns.TABLE_NAME + "/#", URI_TYPE_LOCATIONS_ID);
        URI_MATCHER.addURI(AUTHORITY, OrdersColumns.TABLE_NAME, URI_TYPE_ORDERS);
        URI_MATCHER.addURI(AUTHORITY, OrdersColumns.TABLE_NAME + "/#", URI_TYPE_ORDERS_ID);
        URI_MATCHER.addURI(AUTHORITY, RoutesColumns.TABLE_NAME, URI_TYPE_ROUTES);
        URI_MATCHER.addURI(AUTHORITY, RoutesColumns.TABLE_NAME + "/#", URI_TYPE_ROUTES_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return BusTicketSQLiteOpenHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_LOCATIONS:
                return TYPE_CURSOR_DIR + LocationsColumns.TABLE_NAME;
            case URI_TYPE_LOCATIONS_ID:
                return TYPE_CURSOR_ITEM + LocationsColumns.TABLE_NAME;

            case URI_TYPE_ORDERS:
                return TYPE_CURSOR_DIR + OrdersColumns.TABLE_NAME;
            case URI_TYPE_ORDERS_ID:
                return TYPE_CURSOR_ITEM + OrdersColumns.TABLE_NAME;

            case URI_TYPE_ROUTES:
                return TYPE_CURSOR_DIR + RoutesColumns.TABLE_NAME;
            case URI_TYPE_ROUTES_ID:
                return TYPE_CURSOR_ITEM + RoutesColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_LOCATIONS:
            case URI_TYPE_LOCATIONS_ID:
                res.table = LocationsColumns.TABLE_NAME;
                res.idColumn = LocationsColumns._ID;
                res.tablesWithJoins = LocationsColumns.TABLE_NAME;
                res.orderBy = LocationsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_ORDERS:
            case URI_TYPE_ORDERS_ID:
                res.table = OrdersColumns.TABLE_NAME;
                res.idColumn = OrdersColumns._ID;
                res.tablesWithJoins = OrdersColumns.TABLE_NAME;
                res.orderBy = OrdersColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_ROUTES:
            case URI_TYPE_ROUTES_ID:
                res.table = RoutesColumns.TABLE_NAME;
                res.idColumn = RoutesColumns._ID;
                res.tablesWithJoins = RoutesColumns.TABLE_NAME;
                res.orderBy = RoutesColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_LOCATIONS_ID:
            case URI_TYPE_ORDERS_ID:
            case URI_TYPE_ROUTES_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
