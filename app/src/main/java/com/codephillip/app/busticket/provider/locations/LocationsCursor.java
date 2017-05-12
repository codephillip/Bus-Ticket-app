package com.codephillip.app.busticket.provider.locations;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.codephillip.app.busticket.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code locations} table.
 */
public class LocationsCursor extends AbstractCursor implements LocationsModel {
    public LocationsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(LocationsColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getName() {
        String res = getStringOrNull(LocationsColumns.NAME);
        return res;
    }

    /**
     * Get the {@code latitude} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getLatitude() {
        Double res = getDoubleOrNull(LocationsColumns.LATITUDE);
        return res;
    }

    /**
     * Get the {@code longitude} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getLongitude() {
        Double res = getDoubleOrNull(LocationsColumns.LONGITUDE);
        return res;
    }
}
