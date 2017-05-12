package com.codephillip.app.busticket.provider.locations;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.codephillip.app.busticket.provider.base.AbstractSelection;

/**
 * Selection for the {@code locations} table.
 */
public class LocationsSelection extends AbstractSelection<LocationsSelection> {
    @Override
    protected Uri baseUri() {
        return LocationsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code LocationsCursor} object, which is positioned before the first entry, or null.
     */
    public LocationsCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new LocationsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public LocationsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code LocationsCursor} object, which is positioned before the first entry, or null.
     */
    public LocationsCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new LocationsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public LocationsCursor query(Context context) {
        return query(context, null);
    }


    public LocationsSelection id(long... value) {
        addEquals("locations." + LocationsColumns._ID, toObjectArray(value));
        return this;
    }

    public LocationsSelection idNot(long... value) {
        addNotEquals("locations." + LocationsColumns._ID, toObjectArray(value));
        return this;
    }

    public LocationsSelection orderById(boolean desc) {
        orderBy("locations." + LocationsColumns._ID, desc);
        return this;
    }

    public LocationsSelection orderById() {
        return orderById(false);
    }

    public LocationsSelection name(String... value) {
        addEquals(LocationsColumns.NAME, value);
        return this;
    }

    public LocationsSelection nameNot(String... value) {
        addNotEquals(LocationsColumns.NAME, value);
        return this;
    }

    public LocationsSelection nameLike(String... value) {
        addLike(LocationsColumns.NAME, value);
        return this;
    }

    public LocationsSelection nameContains(String... value) {
        addContains(LocationsColumns.NAME, value);
        return this;
    }

    public LocationsSelection nameStartsWith(String... value) {
        addStartsWith(LocationsColumns.NAME, value);
        return this;
    }

    public LocationsSelection nameEndsWith(String... value) {
        addEndsWith(LocationsColumns.NAME, value);
        return this;
    }

    public LocationsSelection orderByName(boolean desc) {
        orderBy(LocationsColumns.NAME, desc);
        return this;
    }

    public LocationsSelection orderByName() {
        orderBy(LocationsColumns.NAME, false);
        return this;
    }

    public LocationsSelection latitude(Double... value) {
        addEquals(LocationsColumns.LATITUDE, value);
        return this;
    }

    public LocationsSelection latitudeNot(Double... value) {
        addNotEquals(LocationsColumns.LATITUDE, value);
        return this;
    }

    public LocationsSelection latitudeGt(double value) {
        addGreaterThan(LocationsColumns.LATITUDE, value);
        return this;
    }

    public LocationsSelection latitudeGtEq(double value) {
        addGreaterThanOrEquals(LocationsColumns.LATITUDE, value);
        return this;
    }

    public LocationsSelection latitudeLt(double value) {
        addLessThan(LocationsColumns.LATITUDE, value);
        return this;
    }

    public LocationsSelection latitudeLtEq(double value) {
        addLessThanOrEquals(LocationsColumns.LATITUDE, value);
        return this;
    }

    public LocationsSelection orderByLatitude(boolean desc) {
        orderBy(LocationsColumns.LATITUDE, desc);
        return this;
    }

    public LocationsSelection orderByLatitude() {
        orderBy(LocationsColumns.LATITUDE, false);
        return this;
    }

    public LocationsSelection longitude(Double... value) {
        addEquals(LocationsColumns.LONGITUDE, value);
        return this;
    }

    public LocationsSelection longitudeNot(Double... value) {
        addNotEquals(LocationsColumns.LONGITUDE, value);
        return this;
    }

    public LocationsSelection longitudeGt(double value) {
        addGreaterThan(LocationsColumns.LONGITUDE, value);
        return this;
    }

    public LocationsSelection longitudeGtEq(double value) {
        addGreaterThanOrEquals(LocationsColumns.LONGITUDE, value);
        return this;
    }

    public LocationsSelection longitudeLt(double value) {
        addLessThan(LocationsColumns.LONGITUDE, value);
        return this;
    }

    public LocationsSelection longitudeLtEq(double value) {
        addLessThanOrEquals(LocationsColumns.LONGITUDE, value);
        return this;
    }

    public LocationsSelection orderByLongitude(boolean desc) {
        orderBy(LocationsColumns.LONGITUDE, desc);
        return this;
    }

    public LocationsSelection orderByLongitude() {
        orderBy(LocationsColumns.LONGITUDE, false);
        return this;
    }
}
