package org.insbaixcamp.proyectofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spark.submitbutton.SubmitButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import in.goodiebag.carouselpicker.CarouselPicker;

public class SkateActivity extends AppCompatActivity implements View.OnClickListener {
    CarouselPicker imageCarousel;
    TextView tvSelected;
    SubmitButton smButton;
    protected String username;
    int positionSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skate);
        imageCarousel = (CarouselPicker) findViewById(R.id.imageCarousel);
        tvSelected = (TextView) findViewById(R.id.tvSelectedItem);
        smButton = findViewById(R.id.smButton);

        //Inicializamos todos los componentes que utilizaremos en nuestra actividad.
        List<CarouselPicker.PickerItem> imageItems = new ArrayList<>();
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.base));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo1));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo2));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo3));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo4));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo5));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo6));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo7));
        CarouselPicker.CarouselViewAdapter imageAdapter = new CarouselPicker.CarouselViewAdapter(this, imageItems, 0);
        imageCarousel.setAdapter(imageAdapter);
        smButton.setOnClickListener(this);

        imageCarousel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Cada vez que vayamos pasando una tabla, guardamos su posicion en esta variable que utilizaremos
                //mas tarde.
                positionSelected = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        //cuando cliquemos en el boton para selecionar un diseño de marcador.
        if (view.getId() == R.id.smButton) {

            //Recojemos el valor de usuario para modificar el marcador de ese usuario en concreto.
            username = getIntent().getStringExtra("username");
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference refMarker = database.getReference("Users/" + username + "/Markers");

            refMarker.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Insertamos en el child "counter" el valor de la posicion que hemos selecionado. En el mapsMainActivity,
                    //según el valor del child "counter", escojerá un marcador u otro, que son los valores que hemos insertado
                    //en Firebase.
                    refMarker.child("counter").setValue(positionSelected);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        //Una vez escogido un diseño, redirigimos a MapsMainActivity para que el usuario vea su marcador
        //modificado.
        Intent intent = new Intent(SkateActivity.this, MapsMainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

}