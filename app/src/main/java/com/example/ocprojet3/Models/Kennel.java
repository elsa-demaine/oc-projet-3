package com.example.ocprojet3.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kennel {
    @SerializedName("ID")
    @Expose
    private Integer id;

    @SerializedName("ADDRESS_ID")
    @Expose
    private Integer addressId;

    @SerializedName("NAME")
    @Expose
    private String name;

    @SerializedName("CAPACITY")
    @Expose
    private Integer capacity;

    /**
     * No args constructor for use in serialization
     *
     */
    public Kennel() {
    }

    /**
     *
     * @param id
     * @param addressId
     * @param name
     * @param capacity
     */
    public Kennel(Integer id, Integer addressId, String name, Integer capacity) {
        this.id = id;
        this.addressId = addressId;
        this.name = name;
        this.capacity = capacity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
