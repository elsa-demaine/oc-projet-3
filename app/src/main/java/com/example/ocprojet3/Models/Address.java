package com.example.ocprojet3.Models;

public class Address {
    public int Id;
    public String Street;
    public String City;
    public float Latitude;
    public float Longitutde;

    public Address(int Id, String Street, String City, float Latitude, float Longitude) {
        this.Id = Id;
        this.Street = Street;
        this.City = City;
        this.Latitude = Latitude;
        this.Longitutde = Longitude;
    }
}
