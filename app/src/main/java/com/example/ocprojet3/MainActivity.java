package com.example.ocprojet3;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ocprojet3.Models.Kennel;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;
        ArrayList<Kennel> kennels = new ArrayList<>();

        //Connection Base de données
        /*RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.BASE_URL+"?kennels", response -> {
                    Log.d("RESPONSE", response);

                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        // Check for error node in json
                        if (!error) {
                            // success
                            int i = 0;
                            while(jObj.length()-1 > i){
                                JSONObject kennel = jObj.getJSONObject(Integer.toString(i));
                                String id = kennel.getString("ID");
                                String address_id = kennel.getString("ADDRESS_ID");
                                String name = kennel.getString("NAME");
                                String capacity = kennel.getString("CAPACITY");
                                kennels.add( new Kennel(Integer.parseInt(id), Integer.parseInt(address_id),
                                        name, Integer.parseInt(capacity)));
                                i++;
                            }
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

        queue.add(strReq);
        Log.d("QUEUE","added in queue");

        MapView myMap = findViewById(R.id.mapView);

        final MyListAdapter adapter = new MyListAdapter (this, kennels);

        ListView lvKennels = findViewById(R.id.listView_Kennels);
        lvKennels.setAdapter(adapter);
        lvKennels.setOnItemClickListener((adapterView, view, position, l) -> {
            Kennel value = (Kennel) adapter.getItems(position);
            Toast.makeText(getApplicationContext(),value.Id,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, KennelActivity.class);
            intent.putExtra("KennelClick", value.Id);
            startActivity(intent);
        });*/
    }
}
