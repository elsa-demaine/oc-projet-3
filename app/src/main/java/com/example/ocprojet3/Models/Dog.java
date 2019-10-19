package com.example.ocprojet3.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dog {

    @SerializedName("ID")
    @Expose
    private Integer id;

    @SerializedName("KENNEL_ID")
    @Expose
    private Integer kennelId;

    @SerializedName("NAME")
    @Expose
    private String name;

    @SerializedName("BREED")
    @Expose
    private String breed;

    @SerializedName("AGE")
    @Expose
    private Integer age;

    @SerializedName("PHOTO")
    @Expose
    private String photo;


    /**
     * No args constructor for use in serialization
     *
     */
    public Dog() {
    }

    /**
     *
     * @param breed
     * @param photo
     * @param name
     * @param id
     * @param age
     * @param kennelId
     */
    public Dog(Integer id, Integer kennelId, String name, String breed, Integer age, String photo) {
        super();
        this.id = id;
        this.kennelId = kennelId;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.photo = photo;
    }

    public Integer getID() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKennelId() {
        return kennelId;
    }

    public void setKennelId(Integer kennelId) {
        this.kennelId = kennelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
