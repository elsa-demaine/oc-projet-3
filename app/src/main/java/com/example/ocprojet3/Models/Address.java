package com.example.ocprojet3.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {

    @SerializedName("ID")
    @Expose
    private int Id;

    @SerializedName("STREET")
    @Expose
    private String Street;

    @SerializedName("CITY")
    @Expose
    private String City;

    @SerializedName("LATITUDE")
    @Expose
    private float Latitude;

    @SerializedName("LONGITUDE")
    @Expose
    private float Longitude;

    /**
     * No args constructor for use in serialization
     *
     */
    public Address() {
    }

    /**
     *
     * @param id
     * @param street
     * @param city
     * @param latitude
     * @param longitude
     */
    public Address(int id, String street, String city, float latitude, float longitude) {
        this.Id = id;
        this.Street = street;
        this.City = city;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }
}
