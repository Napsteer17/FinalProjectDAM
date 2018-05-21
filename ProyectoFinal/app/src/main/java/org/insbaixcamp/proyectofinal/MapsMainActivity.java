package org.insbaixcamp.proyectofinal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.os.SystemClock.sleep;

public class MapsMainActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    ImageButton imageSkate;
    ImageButton imageShop;
    ImageButton imageNews;
    ImageButton imageWorld;
    ImageButton imageChat;
    public String user;
    DatabaseReference ref;
    FirebaseDatabase database;
    boolean existUser;

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
        imageChat = findViewById(R.id.ibChat);

        imageSkate.setOnClickListener(this);
        imageShop.setOnClickListener(this);
        imageNews.setOnClickListener(this);
        imageWorld.setOnClickListener(this);
        imageChat.setOnClickListener(this);
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
        database = FirebaseDatabase.getInstance();

        showChangeLangDialog();

        final DatabaseReference myRef = database.getReference();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String laKey = dataSnapshot.getKey();
                int countMarkers = (int) dataSnapshot.getChildrenCount();
                Log.e("-" + laKey + "-", countMarkers + "");

                if (laKey.equals("Locations")) {

                    for (int i = 0; i < countMarkers; i++) {

                        double firstPosition = (double) dataSnapshot.child("Location" + i).child("0").getValue();
                        double secondPosition = (double) dataSnapshot.child("Location" + i).child("1").getValue();

                        LatLng marker = new LatLng(firstPosition, secondPosition);
                        afegirMarcador(marker, "Location" + i);
                    }


                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraPosition camPosition = new CameraPosition.Builder()
                .target(new LatLng(41.1504625, 1.0896585))
                .zoom(10)
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
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerinicial)));
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
        } else if (view.getId() == R.id.ibChat) {
            Intent i = new Intent (MapsMainActivity.this, ChatActivity.class);
            i.putExtra("user", user);
            startActivity(i);
        }
    }

    private void writeOnFile(String user){
        try
        {
            OutputStreamWriter escrituraEnDisco = new OutputStreamWriter(openFileOutput("user.txt", Context.MODE_PRIVATE));
            escrituraEnDisco.write(user);
            escrituraEnDisco.close();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
    }

    private void setupUsername(String user) {

        SharedPreferences prefs = getApplication().getSharedPreferences("ToDoPrefs", 0);

        if (user == null) {
            //Random r = new Random();
            //username = "AndroidUser" + r.nextInt(100000);
            prefs.edit().putString("username", user).commit();
        }
        //user = prefs.getString("username", user);

        ref = database.getReference("Users/" + user + "/data");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        final Date date = new Date();
        final String fecha = dateFormat.format(date);

        ref.push().setValue(fecha);
    }

    public void ReadOnFile(){
        try
        {
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("user.txt")));

            user = fin.readLine();
            fin.close();
            setupUsername(user);
            Toast.makeText(MapsMainActivity.this, user, Toast.LENGTH_LONG).show();
            existUser=true;
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
            existUser=false;
        }
    }

    public void showChangeLangDialog() {

        ReadOnFile();

        if (existUser == false) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
            dialogBuilder.setView(dialogView);

            final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

            dialogBuilder.setTitle("Introduce el nombre de usuario deseado:");
            dialogBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //do something with edt.getText().toString();
                    user = edt.getText().toString();
                    writeOnFile(user);
                    setupUsername(user);
                }
            });
            AlertDialog b = dialogBuilder.create();
            b.show();
        }
    }
}
