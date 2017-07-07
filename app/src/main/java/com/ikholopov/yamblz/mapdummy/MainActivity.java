package com.ikholopov.yamblz.mapdummy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private final static String MARKERS_COORDS = "markersCoords";

    private GoogleMap googleMap = null;
    private ArrayList<Marker> markers = new ArrayList<>();
    private ArrayList<LatLng> markersCoords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(googleMap != null) {
                    LatLng startPointer = googleMap.getCameraPosition().target;
                    Marker marker = googleMap.addMarker(new MarkerOptions().position(startPointer)
                            .title("marker").draggable(true));
                    markers.add(marker);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(startPointer));
                }
            }
        });
        if(savedInstanceState != null) {
            double[] coords = savedInstanceState.getDoubleArray(MARKERS_COORDS);
            for(int i = 0; i < coords.length; i += 2) {
                markersCoords.add(new LatLng(coords[i], coords[i + 1]));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        double[] markersCoordsArray = new double[markers.size() * 2];
        for (int i = 0; i < markers.size(); ++i) {
            LatLng coords = markers.get(i).getPosition();
            markersCoordsArray[2 * i] = coords.latitude;
            markersCoordsArray[2 * i + 1] = coords.longitude;
        }
        savedState.putDoubleArray(MARKERS_COORDS, markersCoordsArray);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMarkerDragListener(this);
        for(LatLng markerCoords: markersCoords ) {
            Marker marker = googleMap.addMarker(new MarkerOptions().position(markerCoords)
                    .title("marker").draggable(true));
            markers.add(marker);
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }
}
