package com.codephillip.app.busticket.retrofit;

import com.codephillip.app.busticket.retromodels.Customer;
import com.codephillip.app.busticket.retromodels.Customers;
import com.codephillip.app.busticket.retromodels.Order;
import com.codephillip.app.busticket.retromodels.Orders;
import com.codephillip.app.busticket.retromodels.location.Locations;
import com.codephillip.app.busticket.retromodels.route.Routes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @POST("/api/v1/customers")
    Call<Customer> signUpCustomer(@Body Customer customer);

    @PUT("/api/v1/customers")
    Call<Customers> signInCustomer(@Body Customer customer);

    @GET("/api/v1/routes?format=json")
    Call<Routes> allRoutes();

    @GET("/api/v1/orders?format=json")
    Call<Orders> allOrders();

    @GET("api/v1/customers/{customer_id}/orders")
    Call<Orders> getCustomerOrders(@Path(value = "customer_id", encoded = true) String customer_id);

    @POST("api/v1/customers/{customer_id}/orders")
    Call<Order> createCustomerOrder(@Path(value = "customer_id", encoded = true) String customer_id, @Body Order order);

    @GET("/api/v1/locations?format=json")
    Call<Locations> allLocations();

    @POST("/api/v1/orders")
    Call<Order> createOrder(@Body Order order);
    
    
}
