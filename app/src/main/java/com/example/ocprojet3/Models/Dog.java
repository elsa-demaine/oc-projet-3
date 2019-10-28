package com.example.ocprojet3.Models;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.ocprojet3.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Dog implements Serializable {

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

    public Integer getId() {
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

    public void setPhoto(RequestManager glide, ImageView imageView) {
        Log.e("DogClass","settingImage");
        RequestOptions options = new RequestOptions()
                .circleCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        String url = "http://82.64.161.207:8080/oc-projet-3/api.php?photo=chug.jpg";

        glide.load(url).apply(options).into(imageView);
    }

}
