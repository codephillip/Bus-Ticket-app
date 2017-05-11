
package com.codephillip.app.busticket.mymodels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customers {

    @SerializedName("customers")
    @Expose
    private List<Customer> customers = null;

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

}
