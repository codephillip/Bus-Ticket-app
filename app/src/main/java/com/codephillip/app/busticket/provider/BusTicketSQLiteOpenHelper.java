package com.codephillip.app.busticket.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.codephillip.app.busticket.BuildConfig;
import com.codephillip.app.busticket.provider.locations.LocationsColumns;
import com.codephillip.app.busticket.provider.orders.OrdersColumns;
import com.codephillip.app.busticket.provider.routes.RoutesColumns;

public class BusTicketSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = BusTicketSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "BusTicket.db";
    private static final int DATABASE_VERSION = 1;
    private static BusTicketSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final BusTicketSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_LOCATIONS = "CREATE TABLE IF NOT EXISTS "
            + LocationsColumns.TABLE_NAME + " ( "
            + LocationsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LocationsColumns.NAME + " TEXT, "
            + LocationsColumns.LATITUDE + " REAL, "
            + LocationsColumns.LONGITUDE + " REAL "
            + " );";

    public static final String SQL_CREATE_TABLE_ORDERS = "CREATE TABLE IF NOT EXISTS "
            + OrdersColumns.TABLE_NAME + " ( "
            + OrdersColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + OrdersColumns.CODE + " TEXT, "
            + OrdersColumns.VALID + " INTEGER, "
            + OrdersColumns.DATECREATED + " INTEGER, "
            + OrdersColumns.DATE + " INTEGER, "
            + OrdersColumns.CUSTOMER + " TEXT, "
            + OrdersColumns.ROUTE + " TEXT "
            + " );";

    public static final String SQL_CREATE_TABLE_ROUTES = "CREATE TABLE IF NOT EXISTS "
            + RoutesColumns.TABLE_NAME + " ( "
            + RoutesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RoutesColumns.ROUTEID + " INTEGER, "
            + RoutesColumns.CODE + " INTEGER, "
            + RoutesColumns.SOURCE + " TEXT, "
            + RoutesColumns.DESTINATION + " TEXT, "
            + RoutesColumns.BUSCOMPANYNAME + " TEXT, "
            + RoutesColumns.BUSCOMPANYIMAGE + " TEXT, "
            + RoutesColumns.PRICE + " INTEGER, "
            + RoutesColumns.ARRIVAL + " INTEGER, "
            + RoutesColumns.DEPARTURE + " INTEGER "
            + " );";

    // @formatter:on

    public static BusTicketSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static BusTicketSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static BusTicketSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new BusTicketSQLiteOpenHelper(context);
    }

    private BusTicketSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new BusTicketSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static BusTicketSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new BusTicketSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private BusTicketSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new BusTicketSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_LOCATIONS);
        db.execSQL(SQL_CREATE_TABLE_ORDERS);
        db.execSQL(SQL_CREATE_TABLE_ROUTES);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
