package com.example.ocprojet3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ocprojet3.Models.Dog;
import com.example.ocprojet3.Models.Kennel;
import com.example.ocprojet3.Utils.ItemClickSupport;
import com.example.ocprojet3.Utils.OcProjet3Stream;
import com.example.ocprojet3.Views.MyListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class KennelActivity extends AppCompatActivity {

    private Disposable disposable;
    private List<Dog> dogs;
    private MyListAdapter<Dog> adapter;
    private RecyclerView rvDogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kennel);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Kennel kennel = (Kennel) intent.getSerializableExtra("KennelClick");

        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.executeHttpRequest(Objects.requireNonNull(kennel).getId().toString());

        TextView kennelName = findViewById(R.id.tvKennelName);
        kennelName.setText(kennel.getName());

        Log.e("K", "end of create");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private void configureRecyclerView(){
        this.dogs = new ArrayList<>();
        // Create adapter passing in the sample user data
        this.adapter = new MyListAdapter<Dog>(this, this.dogs, Glide.with(this));
        // Attach the adapter to the recyclerview to populate items
        rvDogs = findViewById(R.id.recyclerView_Dogs);
        rvDogs.setAdapter(this.adapter);
        // Set layout manager to position the items
        rvDogs.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(rvDogs, R.layout.content_main)
            .setOnItemClickListener((recyclerView, position, v) -> {
                Dog value = adapter.getItem(position);
                Intent intentDog = new Intent(this, InformationActivity.class);
                intentDog.putExtra("DogClick", value);
                startActivity(intentDog);
            });
    }

    private void executeHttpRequest(String kennelId){
        this.disposable = OcProjet3Stream.streamGetDogs(kennelId).subscribeWith(new DisposableObserver<List<Dog>>() {
            public void onNext(List<Dog> dogs) {
                updateUI(dogs);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("HttpReqK", Objects.requireNonNull(e.getMessage()));
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void updateUI(List<Dog> dogs) {
        this.dogs.clear();
        this.dogs.addAll(dogs);
        adapter.notifyDataSetChanged();
    }
}
