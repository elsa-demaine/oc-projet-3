package com.example.ocprojet3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.ocprojet3.Models.Dog;

import java.util.Locale;


public class InformationActivity extends AppCompatActivity {

    private Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Intent intent = getIntent();
        dog = (Dog)intent.getSerializableExtra("DogClick");

        if (dog != null) {
            TextView name = findViewById(R.id.dogName);
            name.setText(dog.getName());
            TextView breed = findViewById(R.id.dogBreed);
            breed.setText(dog.getBreed());
            TextView age = findViewById(R.id.dogAge);
            age.setText(String.format(Locale.FRENCH, "%d ans", dog.getAge()));
            ImageView photo = findViewById(R.id.dogPhoto);
            dog.setPhoto(Glide.with(this), photo);
        }

        Button adopt = findViewById(R.id.btn_adopt_dog);
        adopt.setOnClickListener(v -> Toast.makeText(getApplicationContext(),"Félicitations vous venez d'adopté "+dog.getName()+" !", Toast.LENGTH_SHORT).show());
    }

}
