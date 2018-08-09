
package com.codephillip.app.busticket.retromodels.orders;

import com.codephillip.app.busticket.retromodels.Customer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("customer")
    @Expose
    private com.codephillip.app.busticket.retromodels.Customer customer;
    @SerializedName("route")
    @Expose
    private Route route;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public com.codephillip.app.busticket.retromodels.Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

}
