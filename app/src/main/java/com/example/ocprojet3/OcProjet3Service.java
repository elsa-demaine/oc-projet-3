package com.example.ocprojet3;

import com.example.ocprojet3.Models.Dog;
import com.example.ocprojet3.Models.Kennel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OcProjet3Service {
    @GET("api.php?kennels")
    Observable<List<Kennel>> getKennels();

    //@GET("api.php?dogs")
    //Observable<List<Dog>> getDogs();

    @GET("api.php")
    Observable<List<Dog>> getDogs(@Query("kennelId") String kennelId);

    @GET("api.php")
    Observable<Dog> getDog(@Query("dogId") String dogId);

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://82.64.161.207:8080/oc-projet-3/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
}
