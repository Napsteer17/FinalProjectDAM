package org.insbaixcamp.proyectofinal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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
    String[] arrayLocations = {"Plaça Llibertat", "Skatepark Reus", "Devil scooter Indoor", "C/ del pintor bergadá", "C/ del General Morages"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //Enlazamos todos los componentes que utilizamos en el codigo.
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

        //Creamos un formato de fecha para insertarla en Firebase y así, comprobar si este usuario
        //ya se ha conectado este dia a la aplicación.
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        final Date date = new Date();
        final String fecha = dateFormat.format(date);

        username = getIntent().getStringExtra("username");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Comprobamos si el child con el nombre de usuario ya esta insertado
                //en nuestra base de datos de Firebase.
                if (dataSnapshot.hasChild(username)) {
                    reference = database.getReference("Users/" + username + "/data");

                    //En caso de que si, reojemos el valor de la ultima conexión y lo comparamos
                    //para ver si es igual al dia de hoy(Ya se ha conectado hoy)
                    Query lastQuery = reference.orderByKey().limitToLast(1);
                    lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String message = dataSnapshot.getValue().toString();
                            String number = message.substring(22, 24);
                            if (number.equals(fecha)) {
                                //Ya se ha conectado hoy, así que no hay oportunidad de desbloquear
                                //un nuevo diseño de tabla.

                            } else {
                                //En caso de que sea la primera vez que se conecta hoy, probamos suerte para ver
                                //si puede desbloquear una tabla coleccionable.
                                reference.push().setValue(fecha);
                                //En caso de que si, modificamos el valor de la tabla en Firebase para indicarnos
                                //que esa tabla ya ha sido desbloqueada, con lo cual se puede visualizar en el
                                //apartado de galeria.

                                checkReward(database);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    creationUser(database, fecha);
                    checkReward(database);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void creationUser(final FirebaseDatabase database, String fecha) {
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


    }

    public void checkReward(final FirebaseDatabase database) {
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
            //Ha conseguido premio.
            showChangeLangDialogWin();
        } else {
            //No ha conseguido premio.
            showChangeLangDialogLose();
        }
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

                //Si esta insertado el child "Locations", recojemos los valores de los
                //childs 0 y 1(Corresponden a las cordenadas) e insertamos los marcadores
                //mediante el metodo addMarker.
                if (nameKey.equals("Locations")) {

                    for (int i = 0; i < countMarkers; i++) {

                        final String nameMarker = arrayLocations[i];

                        double firstPosition = (double) dataSnapshot.child("Location" + i).child("0").getValue();
                        double secondPosition = (double) dataSnapshot.child("Location" + i).child("1").getValue();

                        LatLng marker = new LatLng(firstPosition, secondPosition);
                        addMarker(marker, nameMarker);
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

        //Inicializamos la vista del mapa en Reus.
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


    // Es una funcion, a la que llamamos cuando al usuario gana una tabla. Lo que hace dicha funcion
    // es la de llamar un layout, con una clase de Java, para que se muestre como una alerta.
    public void showChangeLangDialogWin() {

        Dialog settingsDialog = new Dialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.custom_dialog_reward_win
                , null));
        settingsDialog.show();
    }

    // Es una funcion, a la que llamamos cuando al usuario no ha ganado una tabla. Lo que hace dicha funcion
    // es la de llamar un layout, con una clase de Java, para que se muestre como una alerta.
    // Es la misma funcion que la anterior pero se llama a un layout diferente
    public void showChangeLangDialogLose() {

        Dialog settingsDialog = new Dialog(this);
        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.custom_dialog_reward_lose
                , null));
        settingsDialog.show();
    }

    //metodo que utilizamos para que compruebe cuantas localizaciones tenemos en Firebase
    //y los añade al mapa inicial.
    private void addMarker(final LatLng latitudLongitud, final String nameLocation) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users/" + username + "/Markers");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String valueMarker = dataSnapshot.child("counter").getValue().toString();
                String nameMarker = dataSnapshot.child(valueMarker).getValue().toString();
                //Tal y como tenemos estructurado nuestro Firebase, mediante este codigo podemos
                //organizar distintos diseños de marcadores para que el usuario pueda seleccionar
                //uno u otro según sus gustos y poder tener su aplicación personalizada.
                if (nameMarker.equals("markerinicial")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .title(nameLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerinicial)));
                } else if (nameMarker.equals("marcador1")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .title(nameLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador1)));
                } else if (nameMarker.equals("marcador2")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .title(nameLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador2)));
                } else if (nameMarker.equals("marcador3")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .title(nameLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador3)));
                } else if (nameMarker.equals("marcador4")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .title(nameLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador4)));
                } else if (nameMarker.equals("marcador5")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .title(nameLocation)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador5)));
                } else if (nameMarker.equals("marcador6")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .title(nameMarker)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcador6)));
                } else if (nameMarker.equals("marcador7")) {
                    mMap.addMarker(new MarkerOptions()
                            .position(latitudLongitud)
                            .title(nameLocation)
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

        //enlazamos cada ImageView con las diferentes activities para que el usuario pueda moverse
        //por las diferentes secciones que tiene la aplicación.
        if (view.getId() == R.id.ibMarkers) {
            intent = new Intent(MapsMainActivity.this, SkateActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else if (view.getId() == R.id.ibNews) {
            intent = new Intent(MapsMainActivity.this, NewsActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.ibShop) {
            intent = new Intent(MapsMainActivity.this, ShopActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.ibGallery) {
            intent = new Intent(MapsMainActivity.this, GalleryActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else if (view.getId() == R.id.ibChat) {
            intent = new Intent(MapsMainActivity.this, ChatActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }
    }

}
