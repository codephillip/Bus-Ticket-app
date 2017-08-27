package com.codephillip.app.busticket.provider.orders;

import com.codephillip.app.busticket.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code orders} table.
 */
public interface OrdersModel extends BaseModel {

    /**
     * Get the {@code code} value.
     * Can be {@code null}.
     */
    @Nullable
    String getCode();

    /**
     * Get the {@code valid} value.
     * Can be {@code null}.
     */
    @Nullable
    Boolean getValid();

    /**
     * Get the {@code datecreated} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getDatecreated();

    /**
     * Get the {@code date} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getDate();

    /**
     * Get the {@code customer} value.
     * Can be {@code null}.
     */
    @Nullable
    String getCustomer();

    /**
     * Get the {@code route} value.
     * Can be {@code null}.
     */
    @Nullable
    String getRoute();
}
