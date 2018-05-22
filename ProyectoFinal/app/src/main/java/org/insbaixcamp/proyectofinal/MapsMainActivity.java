package org.insbaixcamp.proyectofinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MapsMainActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    ImageButton imageMarkers;
    ImageButton imageShop;
    ImageButton imageNews;
    ImageButton imageGallery;
    ImageButton imageChat;
    protected String username;
    DatabaseReference reference;
    String[] arrayMarkers = {"markerinicial", "marcador1", "marcador2", "marcador3", "marcador4", "marcador5", "marcador6", "marcador7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        imageMarkers = findViewById(R.id.ibMarkers);
        imageShop = findViewById(R.id.ibShop);
        imageNews = findViewById(R.id.ibNews);
        imageGallery = findViewById(R.id.ibGallery);
        imageChat = findViewById(R.id.ibChat);

        imageMarkers.setOnClickListener(this);
        imageShop.setOnClickListener(this);
        imageNews.setOnClickListener(this);
        imageGallery.setOnClickListener(this);
        imageChat.setOnClickListener(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        final Date date = new Date();
        final String fecha = dateFormat.format(date);

        setupUsername();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(username)) {
                    Log.i("YA", "YA TIENE CREADO TABLAS");
                    reference = database.getReference("Users/" + username + "/data");

                    Query lastQuery = reference.orderByKey().limitToLast(1);
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
                                reference.push().setValue(fecha);

                                Random random = new Random();
                                int randomInt = random.nextInt(31) + 1;

                                if (randomInt <= 15) {
                                    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                                    reference = database2.getReference("Users/" + username + "/unlockedTables");
                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                                            String count = dataSnapshot.child("counter").getValue().toString();
                                            int counter = Integer.parseInt(count) + 1;
                                            reference = database3.getReference("Users/" + username + "/unlockedTables/table" + count);
                                            reference.setValue(1);

                                            reference = database3.getReference("Users/" + username + "/unlockedTables/counter");
                                            reference.setValue(counter);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                    showChangeLangDialog();
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


                } else {
                    for (int i = 0; i < 9; i++) {

                        reference = database.getReference("Users/" + username + "/unlockedTables/table" + i);
                        reference.setValue(0);
                    }
                    reference = database.getReference("Users/" + username + "/unlockedTables/counter");
                    reference.setValue(0);

                    reference = database.getReference("Users/" + username + "/data");
                    reference.push().setValue(0);
                    reference.push().setValue(fecha);

                    for (int j = 0; j < arrayMarkers.length; j++) {
                        reference = database.getReference("Users/" + username + "/Markers/" + j);
                        reference.setValue(arrayMarkers[j]);
                    }
                    reference = database.getReference("Users/" + username + "/Markers/counter");
                    reference.setValue(0);

                    Toast.makeText(MapsMainActivity.this, "BIENVENIDO POR PRIMERA VEZ HOY", Toast.LENGTH_LONG).show();


                    Random random = new Random();
                    int randomInt = random.nextInt(31) + 1;

                    if (randomInt <= 15) {
                        reference = database.getReference("Users/" + username + "/unlockedTables");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String count = dataSnapshot.child("counter").getValue().toString();
                                int counter = Integer.parseInt(count) + 1;
                                reference = database.getReference("Users/" + username + "/unlockedTables/table" + count);
                                reference.setValue(1);

                                reference = database.getReference("Users/" + username + "/unlockedTables/counter");
                                reference.setValue(counter);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        showChangeLangDialog();
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
        final DatabaseReference referenceMarkers = database.getReference();

        referenceMarkers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String nameKey = dataSnapshot.getKey();
                int countMarkers = (int) dataSnapshot.getChildrenCount();
                Log.e("-" + nameKey + "-", countMarkers + "");

                if (nameKey.equals("Locations")) {

                    for (int i = 0; i < countMarkers; i++) {

                        double firstPosition = (double) dataSnapshot.child("Location" + i).child("0").getValue();
                        double secondPosition = (double) dataSnapshot.child("Location" + i).child("1").getValue();

                        LatLng marker = new LatLng(firstPosition, secondPosition);
                        addMarker(marker);
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

    public void showChangeLangDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Introduce el nombre de usuario deseado:");
        dialogBuilder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                username = edt.getText().toString();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void addMarker(final LatLng latitudLongitud) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users/" + username + "/Markers");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String valueMarker = dataSnapshot.child("counter").getValue().toString();
                String nameMarker = dataSnapshot.child(valueMarker).getValue().toString();

                if (nameMarker.equals("markerinicial")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerinicial)));
                } else if (nameMarker.equals("marcador1")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador1)));
                } else if (nameMarker.equals("marcador2")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador2)));
                } else if (nameMarker.equals("marcador3")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador3)));
                } else if (nameMarker.equals("marcador4")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador4)));
                } else if (nameMarker.equals("marcador5")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador5)));
                } else if (nameMarker.equals("marcador6")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador6)));
                } else if (nameMarker.equals("marcador7")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador7)));
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        Intent intent = null;

        if (view.getId() == R.id.ibMarkers) {
            intent = new Intent(MapsMainActivity.this, SkateActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.ibNews) {
            intent = new Intent(MapsMainActivity.this, NewsActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.ibShop) {
            intent = new Intent(MapsMainActivity.this, ShopActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.ibGallery) {
            intent = new Intent(MapsMainActivity.this, GalleryActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.ibChat) {
            intent = new Intent (MapsMainActivity.this, ChatActivity.class);
            intent.putExtra("user", username);
            startActivity(intent);
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
