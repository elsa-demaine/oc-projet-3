package com.example.ocprojet3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ocprojet3.Models.Kennel;
import com.example.ocprojet3.Utils.ItemClickSupport;
import com.example.ocprojet3.Utils.OcProjet3Stream;
import com.example.ocprojet3.Views.MyListAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import static com.google.android.gms.location.LocationServices.API;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private Disposable disposable;
    private List<Kennel> kennels;
    private MyListAdapter<Kennel> adapter;
    private RecyclerView rvKennels;

    private GoogleMap mMap; // Current Google Map
    private GoogleApiClient mGoogleApiClient; // Current Google Api Client
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99; // Request Location permission

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtain the MapView and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.executeHttpRequest();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    //RecyclerView methodes
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private void configureRecyclerView(){
        this.kennels = new ArrayList<>();
        // Create adapter passing in the sample user data
        this.adapter = new MyListAdapter<>(this, this.kennels, Glide.with(this));
        // Attach the adapter to the recyclerview to populate items
        rvKennels = findViewById(R.id.recyclerView_Kennels);
        rvKennels.setAdapter(this.adapter);
        // Set layout manager to position the items
        rvKennels.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(rvKennels, R.layout.content_main)
            .setOnItemClickListener((recyclerView, position, v) -> {
                Kennel value = adapter.getItem(position);
                Intent intentKennel = new Intent(this, KennelActivity.class);
                intentKennel.putExtra("KennelClick", value);
                startActivity(intentKennel);
            }
        );
    }

    private void executeHttpRequest(){
        this.disposable = OcProjet3Stream.streamGetKennels().subscribeWith(new DisposableObserver<List<Kennel>>() {
            public void onNext(List<Kennel> kennels) {
                updateUI(kennels);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("HttpRequestMain", Objects.requireNonNull(e.getLocalizedMessage()));
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void updateUI(List<Kennel> kennels) {
        this.kennels.clear();
        this.kennels.addAll(kennels);
        adapter.notifyDataSetChanged();
        updateMarkers(kennels);
    }

    //Maps methodes
    private void updateMarkers(List<Kennel> kennels){
        if(mMap != null){
            mMap.clear();
            for(Kennel kennel : kennels){
                LatLng position = new LatLng(kennel.getAddress().getLatitude(), kennel.getAddress().getLongitude());
                String title = kennel.getName();
                mMap.addMarker(new MarkerOptions().position(position).title(title));
            }
        }
    }

    /**
     * A method to build Google Api Client
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)    // this line is to configure client
                .addConnectionCallbacks(this)       // add the callbacks when client is connected or disconnected
                .addOnConnectionFailedListener(this)    // handle the failure of connecting client to service
                .addApi(API)       // add the LocationServices API from Google Play Services
                .build();

        // Connect the client before executing any operation
        mGoogleApiClient.connect();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Set the Google Map on our map
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("I am in Sydney!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Set the map on hybrid type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                // If permission is granted, build the Google API Client and enable the user's
                // location
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // If the device's version is older than SDK 23, then directly build the Google API
            // Client and enable user's location
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    /**
     * In this method, we update current location of the user regularly. We also note current
     * velocity (vitesse), altitude,... thanks to location object that contains these informations.
     * We extract these informations with fused location provider.
     * Fused Location Provider analyses GPS, network and Wi-Fi location data in order to provide
     * the highest accuracy. It uses different sensors to know if the user is walking, riding a
     * bike, driving, or standing, in order to adapt the frequency of location updates.
     * @param bundle bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Instanciate Location request
        // Current Location Request
        LocationRequest mLocationRequest = new LocationRequest();

        // Set interval to 1 second
        mLocationRequest.setInterval(1000);

        // Set the fastest interval to 1 second
        mLocationRequest.setFastestInterval(1000);

        // Set the priority to balanced so that the battery will not be drained quickly
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        // Check if the permission is granted
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            // Use the Fused Location Provider API to get the updates
            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Get new location and move Camera
                    LatLng newLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(newLatLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
                }
            });
        }
    }

    /**
     * This method is launched everytime the user's location changes
     * @param location newest location
     */
    @Override
    public void onLocationChanged(Location location) {
        // Put current location marker with Magenta color
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        // move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
    }

    /**
     * This method is launched if the user changes the permissions after installation
     * @param requestCode the code requested for Location Permission
     * @param permissions the current available and granted permissions
     * @param grantResults the results that are granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Permission was granted.
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    if (mGoogleApiClient == null) {
                        buildGoogleApiClient();
                    }
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                // Permission denied, Disable the functionality that depends on this permission.
                Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}
}
