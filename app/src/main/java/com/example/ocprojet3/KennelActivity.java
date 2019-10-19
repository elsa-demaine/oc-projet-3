package com.example.ocprojet3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ocprojet3.Models.Dog;

import java.io.Serializable;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class KennelActivity extends AppCompatActivity {

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kennel);

        // Get the Intent that started this activity and extract the string
        //Intent intent = getIntent();
        //String kennelId = intent.getStringExtra("KennelId");

        final Context context = this;

        String kennelId = "1";
        this.executeHttpRequest(kennelId, context);

        Log.e("TAG", "end of create");

       /* RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.BASE_URL+"?kennelId=2", response -> {
            Log.d("RESPONSE", response);

            try {
                JSONObject jObj = new JSONObject(response);
                boolean error = jObj.getBoolean("error");

                // Check for error node in json
                if (!error) {
                    // success
                    int i = 0;
                    while(jObj.length()-1 > i){
                        JSONObject dog = jObj.getJSONObject(Integer.toString(i));
                        String id = dog.getString("ID");
                        String kennel_id = dog.getString("KENNEL_ID");
                        String name = dog.getString("NAME");
                        String breed = dog.getString("BREED");
                        String age = dog.getString("AGE");
                        String photo = dog.getString("PHOTO");
                        dogs.add( new Dog(Integer.parseInt(id), Integer.parseInt(kennel_id),
                                name, breed, Integer.parseInt(age), R.drawable.ic_launcher_background));
                        i++;
                    }

                    final MyListAdapter adapter = new MyListAdapter (this, dogs);

                    ListView lvDogs = findViewById(R.id.listView_dogs);
                    lvDogs.setAdapter(adapter);
                    lvDogs.setOnItemClickListener((adapterView, view, position, l) -> {
                        Dog value = (Dog) adapter.getItems(position);
                        Toast.makeText(getApplicationContext(),value.Id,Toast.LENGTH_SHORT).show();
                        Intent intentDog = new Intent(context, InformationActivity.class);
                        intentDog.putExtra("DogClick", value);
                        startActivity(intentDog);
                    });

                } else {
                    // Error
                    Log.e("JSON", "error!!!!");
                }
            }
            catch (JSONException e) {
                // JSON error
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, error -> Log.d("URL","this didn't work !!")
        );

        queue.add(strReq);*/
    }

    private void executeHttpRequest(String kennelId, Context context){
        this.updateUIWhenStartingHTTPRequest();
        this.disposable = OcProjet3Stream.streamGetDogs(kennelId).subscribeWith(new DisposableObserver<List<Dog>>() {
            public void onNext(List<Dog> dogs) {
                Log.e("TAG","On Next");
                updateUIWithListOfDogs(dogs, context);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error"+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete !!");
            }
        });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private void updateUIWithListOfDogs(List<Dog> dogs, Context context) {
        final MyListAdapter<Dog> adapter = new MyListAdapter<>(this, dogs);

        ListView lvDogs = findViewById(R.id.listView_dogs);
        lvDogs.setAdapter(adapter);

        updateUIWhenStopingHTTPRequest();

        lvDogs.setOnItemClickListener((adapterView, view, position, l) -> {
            Dog value = adapter.getItems(position);
            Toast.makeText(getApplicationContext(),value.getID(),Toast.LENGTH_SHORT).show();
            Intent intentDog = new Intent(context, InformationActivity.class);
            intentDog.putExtra("DogClick", (Serializable) value);
            startActivity(intentDog);
        });
    }

    private void updateUIWhenStartingHTTPRequest() {
        Log.e("TAG","Updating UI");
    }

    private void updateUIWhenStopingHTTPRequest(){
        Log.e("TAG","Stoping UI");
    }
}
