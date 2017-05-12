
package com.codephillip.app.busticket.mymodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("customer")
    @Expose
    private Integer customer;
    @SerializedName("route")
    @Expose
    private Integer route;

    public Order(Integer customerId, Integer routeId, boolean valid, String date) {
        this.customer = customerId;
        this.route = routeId;
        this.valid = true;
        this.date = date;
    }

    public Order(Integer customerId, Integer routeId, boolean valid, String date, int order_code) {
        this.customer = customerId;
        this.route = routeId;
        this.valid = true;
        this.date = date;
        this.code = order_code;
    }
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public Integer getRoute() {
        return route;
    }

    public void setRoute(Integer route) {
        this.route = route;
    }

}
