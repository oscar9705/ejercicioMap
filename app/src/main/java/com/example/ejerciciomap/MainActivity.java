package com.example.ejerciciomap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    TextView tv;
    double lat, log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.acces_token));
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        tv = (TextView) findViewById(R.id.messagetextView);
        // LocationManager obtain GPS location
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener MLocationListener = new MyLocationListener();
        MLocationListener.setMainActivity(this);
        /*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    (LocationListener) MLocationListener);*/





        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(3.449500, -76.534004))
                        .title("UAD")
                        .snippet("ubicacion san fernando"));


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    public void setLocation(Location location){
        if(location.getLatitude() != 0.0 && location.getLongitude()!= 0){
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                lat = location.getLatitude();
                log = location.getLongitude();
                List<Address> list = geocoder.getFromLocation(
                  location.getLatitude(), location.getLongitude(),1);
                if(!list.isEmpty()){
                    Address address = list.get(0);


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void addMarker(View v){
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(3.225261, -76.509696))
                        .position(new LatLng(lat, log))
                        .title("ubicacion reciente")
                        .snippet("se encuentra aquí"));


            }
        });

    }

    public class MyLocationListener  implements  LocationListener{

        MainActivity mainActivity;

        public MainActivity getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(@NonNull Location location) {
            location.getLatitude();
            location.getLongitude();
            String text = "Mi ubicación actual es: " +
                    "\n Lat = " + location.getLatitude()+
                    "\n Log = " + location.getLongitude();
            tv.setText(text);
            this.mainActivity.setLocation(location);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            tv.setText("GPS activado");
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            tv.setText("GPS desactivado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }

}

