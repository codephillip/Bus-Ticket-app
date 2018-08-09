
package com.codephillip.app.busticket.retromodels.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bus {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("number_plate")
    @Expose
    private String numberPlate;
    @SerializedName("bus_company")
    @Expose
    private BusCompany busCompany;
    @SerializedName("seats")
    @Expose
    private Integer seats;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public BusCompany getBusCompany() {
        return busCompany;
    }

    public void setBusCompany(BusCompany busCompany) {
        this.busCompany = busCompany;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
