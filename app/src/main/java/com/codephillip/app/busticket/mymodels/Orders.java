
package com.codephillip.app.busticket.mymodels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orders {

    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

}
