package org.insbaixcamp.proyectofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MapsMainActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    ImageButton imageSkate;
    ImageButton imageShop;
    ImageButton imageNews;
    ImageButton imageWorld;
    ImageButton imageChat;
    protected String username;
    DatabaseReference ref;
    String[] arrayMarkers={"base", "modelo1", "modelo3","modelo4","modelo5","modelo7","modelo8","modelo6"};

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        final Date date = new Date();
        final String fecha = dateFormat.format(date);

        setupUsername();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(username)){
                    Log.i("YA", "YA TIENE CREADO TABLAS");
                    ref = database.getReference("Users/" + username + "/data");

                    Query lastQuery = ref.orderByKey().limitToLast(1);
                    lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String message = dataSnapshot.getValue().toString();
                            String number = message.substring(22, 24);
                            Log.i("Fecha: " + fecha, "NumeroFirebase: " + number);
                            if (number.equals(fecha)) {
                                Toast.makeText(MapsMainActivity.this, "HOY YA TE HAS CONECTADO", Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(MapsMainActivity.this, "BIENVENIDO POR PRIMERA VEZ HOY", Toast.LENGTH_LONG).show();
                                ref.push().setValue(fecha);

                                Random random = new Random();
                                int randomInt = random.nextInt(31) + 1;

                                if (randomInt <= 15) {
                                    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                                    ref = database2.getReference("Users/" + username + "/unlockedTables");
                                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                                            String count = dataSnapshot.child("counter").getValue().toString();
                                            int counter = Integer.parseInt(count) + 1;
                                            ref = database3.getReference("Users/" + username + "/unlockedTables/table" + count);
                                            ref.setValue(1);

                                            ref = database3.getReference("Users/" + username + "/unlockedTables/counter");
                                            ref.setValue(counter);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    Toast.makeText(MapsMainActivity.this, "PREMIO PARA TI", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MapsMainActivity.this, "PRUEBA OTRO DIA", Toast.LENGTH_LONG).show();
                                }


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }else{
                    for (int i = 0; i < 9; i++) {

                        ref = database.getReference("Users/" + username + "/unlockedTables/table" + i);
                        ref.setValue(0);
                    }
                    ref = database.getReference("Users/" + username + "/unlockedTables/counter");
                    ref.setValue(0);

                    ref = database.getReference("Users/" + username + "/data");
                    ref.push().setValue(0);
                    ref.push().setValue(fecha);

                    for (int j=0; j<arrayMarkers.length;j++){
                        ref = database.getReference("Users/" + username + "/Markers/"+j);
                        ref.setValue(arrayMarkers[j]);
                    }
                    ref = database.getReference("Users/" + username + "/Markers/counter");
                    ref.setValue(0);

                    //AHORA EL GETKEY ES data

                    Toast.makeText(MapsMainActivity.this, "BIENVENIDO POR PRIMERA VEZ HOY", Toast.LENGTH_LONG).show();


                    Random random = new Random();
                    int randomInt = random.nextInt(31) + 1;

                    if (randomInt <= 15) {
                        ref = database.getReference("Users/" + username + "/unlockedTables");
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String count = dataSnapshot.child("counter").getValue().toString();
                                int counter = Integer.parseInt(count) + 1;
                                ref = database.getReference("Users/" + username + "/unlockedTables/table" + count);
                                ref.setValue(1);

                                ref = database.getReference("Users/" + username + "/unlockedTables/counter");
                                ref.setValue(counter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(MapsMainActivity.this, "PREMIO PARA TI", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MapsMainActivity.this, "PRUEBA OTRO DIA", Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
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
            homepage = new Intent(MapsMainActivity.this, ChatActivity.class);
            startActivity(homepage);
        }


    }

    private void setupUsername() {
        SharedPreferences prefs = getApplication().getSharedPreferences("ToDoPrefs", 0);
        String username = prefs.getString("username", null);
        if (username == null) {
            Random r = new Random();
            username = "AndroidUser" + r.nextInt(100000);
            prefs.edit().putString("username", username).commit();
        }
        this.username = prefs.getString("username", null);
    }
}
