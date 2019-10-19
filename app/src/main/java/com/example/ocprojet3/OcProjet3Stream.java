package com.example.ocprojet3;

import com.example.ocprojet3.Models.Dog;
import com.example.ocprojet3.Models.Kennel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class OcProjet3Stream {
    static Observable<List<Kennel>> streamGetKennels(){
        OcProjet3Service ocProjet3Service = OcProjet3Service.retrofit.create(OcProjet3Service.class);
        return ocProjet3Service.getKennels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.MINUTES);
    }

    static Observable<List<Dog>> streamGetDogs(String kennelId){
        OcProjet3Service ocProjet3Service = OcProjet3Service.retrofit.create(OcProjet3Service.class);
        return ocProjet3Service.getDogs(kennelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.MINUTES);
    }

    static Observable<Dog> streamGetDog(String dogId){
        OcProjet3Service ocProjet3Service = OcProjet3Service.retrofit.create(OcProjet3Service.class);
        return ocProjet3Service.getDog(dogId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.MINUTES);
    }
}
