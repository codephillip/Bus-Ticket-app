package com.codephillip.app.busticket.retrofit;

import com.codephillip.app.busticket.mymodels.Customer;
import com.codephillip.app.busticket.mymodels.Customers;
import com.codephillip.app.busticket.mymodels.Locations;
import com.codephillip.app.busticket.mymodels.Order;
import com.codephillip.app.busticket.mymodels.Orders;
import com.codephillip.app.busticket.mymodels.routeobject.Routes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface ApiInterface {

    @POST("/api/v1/customers")
    Call<Customer> createCustomer(@Body Customer customer);

    @PUT("/api/v1/customers")
    Call<Customers> signInCustomer(@Body Customer customer);

    @GET("/api/v1/routes?format=json")
    Call<Routes> allRoutes();

    @GET("/api/v1/orders?format=json")
    Call<Orders> allOrders();

    @GET("/api/v1/locations?format=json")
    Call<Locations> allLocations();

    @POST("/api/v1/orders")
    Call<Order> createOrder(@Body Order order);
    
    
}
