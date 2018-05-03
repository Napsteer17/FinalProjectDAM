package org.insbaixcamp.proyectofinal;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsMainActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    ImageButton imageSkate;
    ImageButton imageShop;
    ImageButton imageNews;
    ImageButton imageWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        imageSkate = findViewById(R.id.ibSkate);
        imageShop = findViewById(R.id.ibShop);
        imageNews = findViewById(R.id.ibNews);
        imageWorld = findViewById(R.id.ibWorld);

        imageSkate.setOnClickListener(this);
        imageShop.setOnClickListener(this);
        imageNews.setOnClickListener(this);
        imageWorld.setOnClickListener(this);
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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng reus = new LatLng(41.1495401, 1.1054653);
        afegirMarcador(reus, "Reus");


        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraPosition camPosition = new CameraPosition.Builder()
                .target(reus)
                .zoom(15)
                .bearing(0)
                .tilt(5)
                .build();

        CameraUpdate camUpdate = CameraUpdateFactory.newCameraPosition(camPosition);
        mMap.animateCamera(camUpdate);
    }

    private void afegirMarcador(LatLng latitudLongitud, String titol) {
        // Possibles colors: HUE_RED, HUE_AZURE, HUE_BLUE, HUE_CYAN, HUE_GREEN, HUE_MAGENTA, HUE_ORANGEHUE_ROSE, HUE_VIOLET, HUE_YELLOW
        mMap.addMarker(new MarkerOptions()
                .position(latitudLongitud)
                .title(titol)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.skateicon)));
    }


    @Override
    public void onClick(View view) {
        Intent homepage = null;

        if (view.getId() == R.id.ibSkate) {
            homepage = new Intent(MapsMainActivity.this, SkateActivity.class);
            startActivity(homepage);
        } else if (view.getId() == R.id.ibNews) {
            homepage = new Intent(MapsMainActivity.this, NewsActivity.class);
            startActivity(homepage);
        } else if (view.getId() == R.id.ibShop) {
            homepage = new Intent(MapsMainActivity.this, ShopActivity.class);
            startActivity(homepage);
        } else if (view.getId() == R.id.ibWorld) {
            homepage = new Intent(MapsMainActivity.this, WorldActivity.class);
            startActivity(homepage);
        }



    }
}
