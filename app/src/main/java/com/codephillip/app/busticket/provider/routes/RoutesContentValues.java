package com.codephillip.app.busticket.provider.routes;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.codephillip.app.busticket.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code routes} table.
 */
public class RoutesContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return RoutesColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable RoutesSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable RoutesSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public RoutesContentValues putRouteid(@Nullable Integer value) {
        mContentValues.put(RoutesColumns.ROUTEID, value);
        return this;
    }

    public RoutesContentValues putRouteidNull() {
        mContentValues.putNull(RoutesColumns.ROUTEID);
        return this;
    }

    public RoutesContentValues putCode(@Nullable Integer value) {
        mContentValues.put(RoutesColumns.CODE, value);
        return this;
    }

    public RoutesContentValues putCodeNull() {
        mContentValues.putNull(RoutesColumns.CODE);
        return this;
    }

    public RoutesContentValues putSource(@Nullable String value) {
        mContentValues.put(RoutesColumns.SOURCE, value);
        return this;
    }

    public RoutesContentValues putSourceNull() {
        mContentValues.putNull(RoutesColumns.SOURCE);
        return this;
    }

    public RoutesContentValues putDestination(@Nullable String value) {
        mContentValues.put(RoutesColumns.DESTINATION, value);
        return this;
    }

    public RoutesContentValues putDestinationNull() {
        mContentValues.putNull(RoutesColumns.DESTINATION);
        return this;
    }

    public RoutesContentValues putBuscompanyname(@Nullable String value) {
        mContentValues.put(RoutesColumns.BUSCOMPANYNAME, value);
        return this;
    }

    public RoutesContentValues putBuscompanynameNull() {
        mContentValues.putNull(RoutesColumns.BUSCOMPANYNAME);
        return this;
    }

    public RoutesContentValues putBuscompanyimage(@Nullable String value) {
        mContentValues.put(RoutesColumns.BUSCOMPANYIMAGE, value);
        return this;
    }

    public RoutesContentValues putBuscompanyimageNull() {
        mContentValues.putNull(RoutesColumns.BUSCOMPANYIMAGE);
        return this;
    }

    public RoutesContentValues putPrice(@Nullable Integer value) {
        mContentValues.put(RoutesColumns.PRICE, value);
        return this;
    }

    public RoutesContentValues putPriceNull() {
        mContentValues.putNull(RoutesColumns.PRICE);
        return this;
    }

    public RoutesContentValues putArrival(@Nullable Date value) {
        mContentValues.put(RoutesColumns.ARRIVAL, value == null ? null : value.getTime());
        return this;
    }

    public RoutesContentValues putArrivalNull() {
        mContentValues.putNull(RoutesColumns.ARRIVAL);
        return this;
    }

    public RoutesContentValues putArrival(@Nullable Long value) {
        mContentValues.put(RoutesColumns.ARRIVAL, value);
        return this;
    }

    public RoutesContentValues putDeparture(@Nullable Date value) {
        mContentValues.put(RoutesColumns.DEPARTURE, value == null ? null : value.getTime());
        return this;
    }

    public RoutesContentValues putDepartureNull() {
        mContentValues.putNull(RoutesColumns.DEPARTURE);
        return this;
    }

    public RoutesContentValues putDeparture(@Nullable Long value) {
        mContentValues.put(RoutesColumns.DEPARTURE, value);
        return this;
    }
}
