package makeAppointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.nal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.*;

import java.util.ArrayList;

import Constants.Constants;
import home.home;
import mySQLInteractions.sqlInteractions;

import static Constants.Constants.MAPVIEW_BUNDLE_KEY;

public class mappedSalons extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap SalonsMap;
    private MapView myMapView;
    private static ArrayList<String> SalonIds;
    private static ArrayList<salonObject> allSalonsToMap;
    private ProgressBar progressBar10;
    private Bundle mapViewBundle=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapped_salons);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        for(int i=0;i<allSalonsToMap.size();i=i+1){
            googleMap.addMarker(new MarkerOptions().position(new LatLng(allSalonsToMap.get(i).getLat(), allSalonsToMap.get(i).getLan())).title(allSalonsToMap.get(i).getName()));
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
