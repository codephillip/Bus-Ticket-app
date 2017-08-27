package com.codephillip.app.busticket.provider.orders;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.codephillip.app.busticket.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code orders} table.
 */
public class OrdersContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return OrdersColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable OrdersSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable OrdersSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public OrdersContentValues putCode(@Nullable String value) {
        mContentValues.put(OrdersColumns.CODE, value);
        return this;
    }

    public OrdersContentValues putCodeNull() {
        mContentValues.putNull(OrdersColumns.CODE);
        return this;
    }

    public OrdersContentValues putValid(@Nullable Boolean value) {
        mContentValues.put(OrdersColumns.VALID, value);
        return this;
    }

    public OrdersContentValues putValidNull() {
        mContentValues.putNull(OrdersColumns.VALID);
        return this;
    }

    public OrdersContentValues putDatecreated(@Nullable Date value) {
        mContentValues.put(OrdersColumns.DATECREATED, value == null ? null : value.getTime());
        return this;
    }

    public OrdersContentValues putDatecreatedNull() {
        mContentValues.putNull(OrdersColumns.DATECREATED);
        return this;
    }

    public OrdersContentValues putDatecreated(@Nullable Long value) {
        mContentValues.put(OrdersColumns.DATECREATED, value);
        return this;
    }

    public OrdersContentValues putDate(@Nullable Date value) {
        mContentValues.put(OrdersColumns.DATE, value == null ? null : value.getTime());
        return this;
    }

    public OrdersContentValues putDateNull() {
        mContentValues.putNull(OrdersColumns.DATE);
        return this;
    }

    public OrdersContentValues putDate(@Nullable Long value) {
        mContentValues.put(OrdersColumns.DATE, value);
        return this;
    }

    public OrdersContentValues putCustomer(@Nullable String value) {
        mContentValues.put(OrdersColumns.CUSTOMER, value);
        return this;
    }

    public OrdersContentValues putCustomerNull() {
        mContentValues.putNull(OrdersColumns.CUSTOMER);
        return this;
    }

    public OrdersContentValues putRoute(@Nullable String value) {
        mContentValues.put(OrdersColumns.ROUTE, value);
        return this;
    }

    public OrdersContentValues putRouteNull() {
        mContentValues.putNull(OrdersColumns.ROUTE);
        return this;
    }
}
