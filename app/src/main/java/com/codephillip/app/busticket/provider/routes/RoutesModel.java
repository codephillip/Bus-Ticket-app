package com.codephillip.app.busticket.provider.routes;

import com.codephillip.app.busticket.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code routes} table.
 */
public interface RoutesModel extends BaseModel {

    /**
     * Get the {@code routeid} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getRouteid();

    /**
     * Get the {@code code} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getCode();

    /**
     * Get the {@code source} value.
     * Can be {@code null}.
     */
    @Nullable
    String getSource();

    /**
     * Get the {@code destination} value.
     * Can be {@code null}.
     */
    @Nullable
    String getDestination();

    /**
     * Get the {@code buscompanyname} value.
     * Can be {@code null}.
     */
    @Nullable
    String getBuscompanyname();

    /**
     * Get the {@code buscompanyimage} value.
     * Can be {@code null}.
     */
    @Nullable
    String getBuscompanyimage();

    /**
     * Get the {@code price} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getPrice();

    /**
     * Get the {@code arrival} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getArrival();

    /**
     * Get the {@code departure} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getDeparture();
}
