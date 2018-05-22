package org.insbaixcamp.proyectofinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    int positionSelected=0;
    String[] arrayMarkers={"base", "modelo1", "modelo2","modelo3","modelo4","modelo5","modelo6","modelo7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skate);
        imageCarousel = (CarouselPicker) findViewById(R.id.imageCarousel);
        tvSelected = (TextView) findViewById(R.id.tvSelectedItem);
        smButton= findViewById(R.id.smButton);

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
                //tvSelected.setText("has seleccionado la tabla nº: "+position+1);
                Toast.makeText(SkateActivity.this,position+"",Toast.LENGTH_LONG).show();
                positionSelected=position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.smButton){
            username = getIntent().getStringExtra("username");
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            final DatabaseReference refMarker= database.getReference("Users/" + username + "/Markers");

            refMarker.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    refMarker.child("counter").setValue(positionSelected);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
        Intent intent=new Intent(SkateActivity.this, MapsMainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
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