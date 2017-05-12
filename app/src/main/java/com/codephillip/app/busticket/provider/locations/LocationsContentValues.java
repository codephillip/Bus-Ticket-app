package com.codephillip.app.busticket.provider.locations;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.codephillip.app.busticket.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code locations} table.
 */
public class LocationsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return LocationsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable LocationsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable LocationsSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public LocationsContentValues putName(@Nullable String value) {
        mContentValues.put(LocationsColumns.NAME, value);
        return this;
    }

    public LocationsContentValues putNameNull() {
        mContentValues.putNull(LocationsColumns.NAME);
        return this;
    }

    public LocationsContentValues putLatitude(@Nullable Double value) {
        mContentValues.put(LocationsColumns.LATITUDE, value);
        return this;
    }

    public LocationsContentValues putLatitudeNull() {
        mContentValues.putNull(LocationsColumns.LATITUDE);
        return this;
    }

    public LocationsContentValues putLongitude(@Nullable Double value) {
        mContentValues.put(LocationsColumns.LONGITUDE, value);
        return this;
    }

    public LocationsContentValues putLongitudeNull() {
        mContentValues.putNull(LocationsColumns.LONGITUDE);
        return this;
    }
}
