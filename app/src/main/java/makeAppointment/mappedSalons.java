package makeAppointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nal.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.*;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

import Constants.Constants;
import home.home;
import mySQLInteractions.sqlInteractions;

import static Constants.Constants.MAPVIEW_BUNDLE_KEY;
import static com.google.android.gms.maps.GoogleMap.*;

public class mappedSalons extends AppCompatActivity implements OnMapReadyCallback  {
    private GoogleMap SalonsMap;
    private MapView myMapView;
    private static ArrayList<String> SalonIds;
    private static ArrayList<salonObject> allSalonsToMap;
    private HashMap<Marker,salonObject> hm=new HashMap<>();
    private ProgressBar progressBar10;
    private Bundle mapViewBundle=null;
    public static salonObject chosenSalon;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapped_salons);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        ActionBar actionBar= getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }

        progressBar10=findViewById(R.id.progressBar10);
        myMapView=(MapView) findViewById(R.id.myMapView);
        if (savedInstanceState != null) {
            mapViewBundle=savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        myMapView.onCreate(mapViewBundle);
        new getIDsAsyncAndObjects().execute();
    }
    private void fetchLastLocation(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task =fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation=location;
                }
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng=new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions myLocation=new MarkerOptions().position(latLng).title("Вы Здесь");
        for(int i=0;i<allSalonsToMap.size();i=i+1){
            Marker marker=googleMap.addMarker(new MarkerOptions().position(new LatLng(allSalonsToMap.get(i).getLat(), allSalonsToMap.get(i).getLan())).title(allSalonsToMap.get(i).getName()));
            hm.put(marker,allSalonsToMap.get(i));
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
        googleMap.addMarker(myLocation);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(!(marker.getTitle().equals("Вы Здесь"))) {
                    chosenSalon = hm.get(marker);
                    Intent intent = new Intent(getApplicationContext(), showMappedSalon.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }

    class getIDsAsyncAndObjects extends AsyncTask<Void, Void,Void > {

        @Override
        protected Void doInBackground(Void... voids) {
            SalonIds= sqlInteractions.getSalonIds();
            allSalonsToMap=sqlInteractions.getMapObjectsSalon(SalonIds);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar10.setVisibility(View.INVISIBLE);
            myMapView.setVisibility(View.VISIBLE);

            myMapView.getMapAsync(mappedSalons.this);

        }
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle=outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if(mapViewBundle==null){
            mapViewBundle=new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY,mapViewBundle);
        }
        myMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onStart() {
        super.onStart();
        myMapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        myMapView.onResume();
    }


    @Override
    public void onStop() {
        super.onStop();
        myMapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        myMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        myMapView.onLowMemory();
    }

    public void onClickHome(View view) {
        Intent intent=new Intent(this, home.class);
        startActivity(intent);
    }
}
