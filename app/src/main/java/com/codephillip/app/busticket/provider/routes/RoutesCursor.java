package com.codephillip.app.busticket.provider.routes;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.codephillip.app.busticket.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code routes} table.
 */
public class RoutesCursor extends AbstractCursor implements RoutesModel {
    public RoutesCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(RoutesColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code routeid} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getRouteid() {
        Integer res = getIntegerOrNull(RoutesColumns.ROUTEID);
        return res;
    }

    /**
     * Get the {@code code} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getCode() {
        Integer res = getIntegerOrNull(RoutesColumns.CODE);
        return res;
    }

    /**
     * Get the {@code source} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getSource() {
        String res = getStringOrNull(RoutesColumns.SOURCE);
        return res;
    }

    /**
     * Get the {@code destination} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getDestination() {
        String res = getStringOrNull(RoutesColumns.DESTINATION);
        return res;
    }

    /**
     * Get the {@code buscompanyname} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getBuscompanyname() {
        String res = getStringOrNull(RoutesColumns.BUSCOMPANYNAME);
        return res;
    }

    /**
     * Get the {@code buscompanyimage} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getBuscompanyimage() {
        String res = getStringOrNull(RoutesColumns.BUSCOMPANYIMAGE);
        return res;
    }

    /**
     * Get the {@code price} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getPrice() {
        Integer res = getIntegerOrNull(RoutesColumns.PRICE);
        return res;
    }

    /**
     * Get the {@code arrival} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getArrival() {
        Date res = getDateOrNull(RoutesColumns.ARRIVAL);
        return res;
    }

    /**
     * Get the {@code departure} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getDeparture() {
        Date res = getDateOrNull(RoutesColumns.DEPARTURE);
        return res;
    }
}
