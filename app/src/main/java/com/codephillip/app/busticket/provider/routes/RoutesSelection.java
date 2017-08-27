package com.codephillip.app.busticket.provider.routes;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.codephillip.app.busticket.provider.base.AbstractSelection;

/**
 * Selection for the {@code routes} table.
 */
public class RoutesSelection extends AbstractSelection<RoutesSelection> {
    @Override
    protected Uri baseUri() {
        return RoutesColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code RoutesCursor} object, which is positioned before the first entry, or null.
     */
    public RoutesCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new RoutesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public RoutesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code RoutesCursor} object, which is positioned before the first entry, or null.
     */
    public RoutesCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new RoutesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public RoutesCursor query(Context context) {
        return query(context, null);
    }


    public RoutesSelection id(long... value) {
        addEquals("routes." + RoutesColumns._ID, toObjectArray(value));
        return this;
    }

    public RoutesSelection idNot(long... value) {
        addNotEquals("routes." + RoutesColumns._ID, toObjectArray(value));
        return this;
    }

    public RoutesSelection orderById(boolean desc) {
        orderBy("routes." + RoutesColumns._ID, desc);
        return this;
    }

    public RoutesSelection orderById() {
        return orderById(false);
    }

    public RoutesSelection routeid(Integer... value) {
        addEquals(RoutesColumns.ROUTEID, value);
        return this;
    }

    public RoutesSelection routeidNot(Integer... value) {
        addNotEquals(RoutesColumns.ROUTEID, value);
        return this;
    }

    public RoutesSelection routeidGt(int value) {
        addGreaterThan(RoutesColumns.ROUTEID, value);
        return this;
    }

    public RoutesSelection routeidGtEq(int value) {
        addGreaterThanOrEquals(RoutesColumns.ROUTEID, value);
        return this;
    }

    public RoutesSelection routeidLt(int value) {
        addLessThan(RoutesColumns.ROUTEID, value);
        return this;
    }

    public RoutesSelection routeidLtEq(int value) {
        addLessThanOrEquals(RoutesColumns.ROUTEID, value);
        return this;
    }

    public RoutesSelection orderByRouteid(boolean desc) {
        orderBy(RoutesColumns.ROUTEID, desc);
        return this;
    }

    public RoutesSelection orderByRouteid() {
        orderBy(RoutesColumns.ROUTEID, false);
        return this;
    }

    public RoutesSelection code(Integer... value) {
        addEquals(RoutesColumns.CODE, value);
        return this;
    }

    public RoutesSelection codeNot(Integer... value) {
        addNotEquals(RoutesColumns.CODE, value);
        return this;
    }

    public RoutesSelection codeGt(int value) {
        addGreaterThan(RoutesColumns.CODE, value);
        return this;
    }

    public RoutesSelection codeGtEq(int value) {
        addGreaterThanOrEquals(RoutesColumns.CODE, value);
        return this;
    }

    public RoutesSelection codeLt(int value) {
        addLessThan(RoutesColumns.CODE, value);
        return this;
    }

    public RoutesSelection codeLtEq(int value) {
        addLessThanOrEquals(RoutesColumns.CODE, value);
        return this;
    }

    public RoutesSelection orderByCode(boolean desc) {
        orderBy(RoutesColumns.CODE, desc);
        return this;
    }

    public RoutesSelection orderByCode() {
        orderBy(RoutesColumns.CODE, false);
        return this;
    }

    public RoutesSelection source(String... value) {
        addEquals(RoutesColumns.SOURCE, value);
        return this;
    }

    public RoutesSelection sourceNot(String... value) {
        addNotEquals(RoutesColumns.SOURCE, value);
        return this;
    }

    public RoutesSelection sourceLike(String... value) {
        addLike(RoutesColumns.SOURCE, value);
        return this;
    }

    public RoutesSelection sourceContains(String... value) {
        addContains(RoutesColumns.SOURCE, value);
        return this;
    }

    public RoutesSelection sourceStartsWith(String... value) {
        addStartsWith(RoutesColumns.SOURCE, value);
        return this;
    }

    public RoutesSelection sourceEndsWith(String... value) {
        addEndsWith(RoutesColumns.SOURCE, value);
        return this;
    }

    public RoutesSelection orderBySource(boolean desc) {
        orderBy(RoutesColumns.SOURCE, desc);
        return this;
    }

    public RoutesSelection orderBySource() {
        orderBy(RoutesColumns.SOURCE, false);
        return this;
    }

    public RoutesSelection destination(String... value) {
        addEquals(RoutesColumns.DESTINATION, value);
        return this;
    }

    public RoutesSelection destinationNot(String... value) {
        addNotEquals(RoutesColumns.DESTINATION, value);
        return this;
    }

    public RoutesSelection destinationLike(String... value) {
        addLike(RoutesColumns.DESTINATION, value);
        return this;
    }

    public RoutesSelection destinationContains(String... value) {
        addContains(RoutesColumns.DESTINATION, value);
        return this;
    }

    public RoutesSelection destinationStartsWith(String... value) {
        addStartsWith(RoutesColumns.DESTINATION, value);
        return this;
    }

    public RoutesSelection destinationEndsWith(String... value) {
        addEndsWith(RoutesColumns.DESTINATION, value);
        return this;
    }

    public RoutesSelection orderByDestination(boolean desc) {
        orderBy(RoutesColumns.DESTINATION, desc);
        return this;
    }

    public RoutesSelection orderByDestination() {
        orderBy(RoutesColumns.DESTINATION, false);
        return this;
    }

    public RoutesSelection buscompanyname(String... value) {
        addEquals(RoutesColumns.BUSCOMPANYNAME, value);
        return this;
    }

    public RoutesSelection buscompanynameNot(String... value) {
        addNotEquals(RoutesColumns.BUSCOMPANYNAME, value);
        return this;
    }

    public RoutesSelection buscompanynameLike(String... value) {
        addLike(RoutesColumns.BUSCOMPANYNAME, value);
        return this;
    }

    public RoutesSelection buscompanynameContains(String... value) {
        addContains(RoutesColumns.BUSCOMPANYNAME, value);
        return this;
    }

    public RoutesSelection buscompanynameStartsWith(String... value) {
        addStartsWith(RoutesColumns.BUSCOMPANYNAME, value);
        return this;
    }

    public RoutesSelection buscompanynameEndsWith(String... value) {
        addEndsWith(RoutesColumns.BUSCOMPANYNAME, value);
        return this;
    }

    public RoutesSelection orderByBuscompanyname(boolean desc) {
        orderBy(RoutesColumns.BUSCOMPANYNAME, desc);
        return this;
    }

    public RoutesSelection orderByBuscompanyname() {
        orderBy(RoutesColumns.BUSCOMPANYNAME, false);
        return this;
    }

    public RoutesSelection buscompanyimage(String... value) {
        addEquals(RoutesColumns.BUSCOMPANYIMAGE, value);
        return this;
    }

    public RoutesSelection buscompanyimageNot(String... value) {
        addNotEquals(RoutesColumns.BUSCOMPANYIMAGE, value);
        return this;
    }

    public RoutesSelection buscompanyimageLike(String... value) {
        addLike(RoutesColumns.BUSCOMPANYIMAGE, value);
        return this;
    }

    public RoutesSelection buscompanyimageContains(String... value) {
        addContains(RoutesColumns.BUSCOMPANYIMAGE, value);
        return this;
    }

    public RoutesSelection buscompanyimageStartsWith(String... value) {
        addStartsWith(RoutesColumns.BUSCOMPANYIMAGE, value);
        return this;
    }

    public RoutesSelection buscompanyimageEndsWith(String... value) {
        addEndsWith(RoutesColumns.BUSCOMPANYIMAGE, value);
        return this;
    }

    public RoutesSelection orderByBuscompanyimage(boolean desc) {
        orderBy(RoutesColumns.BUSCOMPANYIMAGE, desc);
        return this;
    }

    public RoutesSelection orderByBuscompanyimage() {
        orderBy(RoutesColumns.BUSCOMPANYIMAGE, false);
        return this;
    }

    public RoutesSelection price(Integer... value) {
        addEquals(RoutesColumns.PRICE, value);
        return this;
    }

    public RoutesSelection priceNot(Integer... value) {
        addNotEquals(RoutesColumns.PRICE, value);
        return this;
    }

    public RoutesSelection priceGt(int value) {
        addGreaterThan(RoutesColumns.PRICE, value);
        return this;
    }

    public RoutesSelection priceGtEq(int value) {
        addGreaterThanOrEquals(RoutesColumns.PRICE, value);
        return this;
    }

    public RoutesSelection priceLt(int value) {
        addLessThan(RoutesColumns.PRICE, value);
        return this;
    }

    public RoutesSelection priceLtEq(int value) {
        addLessThanOrEquals(RoutesColumns.PRICE, value);
        return this;
    }

    public RoutesSelection orderByPrice(boolean desc) {
        orderBy(RoutesColumns.PRICE, desc);
        return this;
    }

    public RoutesSelection orderByPrice() {
        orderBy(RoutesColumns.PRICE, false);
        return this;
    }

    public RoutesSelection arrival(Date... value) {
        addEquals(RoutesColumns.ARRIVAL, value);
        return this;
    }

    public RoutesSelection arrivalNot(Date... value) {
        addNotEquals(RoutesColumns.ARRIVAL, value);
        return this;
    }

    public RoutesSelection arrival(Long... value) {
        addEquals(RoutesColumns.ARRIVAL, value);
        return this;
    }

    public RoutesSelection arrivalAfter(Date value) {
        addGreaterThan(RoutesColumns.ARRIVAL, value);
        return this;
    }

    public RoutesSelection arrivalAfterEq(Date value) {
        addGreaterThanOrEquals(RoutesColumns.ARRIVAL, value);
        return this;
    }

    public RoutesSelection arrivalBefore(Date value) {
        addLessThan(RoutesColumns.ARRIVAL, value);
        return this;
    }

    public RoutesSelection arrivalBeforeEq(Date value) {
        addLessThanOrEquals(RoutesColumns.ARRIVAL, value);
        return this;
    }

    public RoutesSelection orderByArrival(boolean desc) {
        orderBy(RoutesColumns.ARRIVAL, desc);
        return this;
    }

    public RoutesSelection orderByArrival() {
        orderBy(RoutesColumns.ARRIVAL, false);
        return this;
    }

    public RoutesSelection departure(Date... value) {
        addEquals(RoutesColumns.DEPARTURE, value);
        return this;
    }

    public RoutesSelection departureNot(Date... value) {
        addNotEquals(RoutesColumns.DEPARTURE, value);
        return this;
    }

    public RoutesSelection departure(Long... value) {
        addEquals(RoutesColumns.DEPARTURE, value);
        return this;
    }

    public RoutesSelection departureAfter(Date value) {
        addGreaterThan(RoutesColumns.DEPARTURE, value);
        return this;
    }

    public RoutesSelection departureAfterEq(Date value) {
        addGreaterThanOrEquals(RoutesColumns.DEPARTURE, value);
        return this;
    }

    public RoutesSelection departureBefore(Date value) {
        addLessThan(RoutesColumns.DEPARTURE, value);
        return this;
    }

    public RoutesSelection departureBeforeEq(Date value) {
        addLessThanOrEquals(RoutesColumns.DEPARTURE, value);
        return this;
    }

    public RoutesSelection orderByDeparture(boolean desc) {
        orderBy(RoutesColumns.DEPARTURE, desc);
        return this;
    }

    public RoutesSelection orderByDeparture() {
        orderBy(RoutesColumns.DEPARTURE, false);
        return this;
    }
}
