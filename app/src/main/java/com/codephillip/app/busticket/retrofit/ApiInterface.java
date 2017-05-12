package com.codephillip.app.busticket.retrofit;

import com.codephillip.app.busticket.mymodels.Customer;
import com.codephillip.app.busticket.mymodels.Order;
import com.codephillip.app.busticket.mymodels.Orders;
import com.codephillip.app.busticket.mymodels.routeobject.Routes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST("/api/v1/customers")
    Call<Customer> createCustomer(@Body Customer customer);

    @GET("/api/v1/routes?format=json")
    Call<Routes> allRoutes();

    @GET("/api/v1/orders?format=json")
    Call<Orders> allOrders();

    @POST("/api/v1/orders")
    Call<Order> createOrder(@Body Order order);
}
