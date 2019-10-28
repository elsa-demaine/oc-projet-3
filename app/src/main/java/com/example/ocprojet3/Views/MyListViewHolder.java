package com.example.ocprojet3.Views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.ocprojet3.Models.Dog;
import com.example.ocprojet3.Models.Kennel;
import com.example.ocprojet3.R;

class MyListViewHolder extends RecyclerView.ViewHolder {
    private TextView titleText;
    private ImageView imageView;
    private TextView subtitleText;

    MyListViewHolder(View view){
        super(view);
        titleText = view.findViewById(R.id.title);
        imageView = view.findViewById(R.id.icon);
        subtitleText = view.findViewById(R.id.subtitle);
    }

    <T> void updateWithT(T t, RequestManager glide){
        Class tClass = t.getClass();

        if (Dog.class.equals(tClass)) {
            updateWithDogs((Dog) t, glide);
        } else if (Kennel.class.equals(tClass)) {
            updateWithKennels((Kennel) t);
        }
    }

    private void updateWithDogs(Dog dog, RequestManager glide){
        titleText.setText(dog.getName());
        dog.setPhoto(glide, imageView);
        subtitleText.setText(dog.getBreed());
    }

    private void updateWithKennels(Kennel kennel){
        titleText.setText(kennel.getName());
        subtitleText.setText(kennel.getAddressId().toString());
    }
}
