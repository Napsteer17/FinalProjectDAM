package org.insbaixcamp.proyectofinal;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;

public class SkateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skate);

        CarouselPicker carouselPicker = (CarouselPicker) findViewById(R.id.carousel);

// Case 1 : To populate the picker with images
        List<CarouselPicker.PickerItem> imageItems = new ArrayList<>();
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.base));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo1));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo3));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo4));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo5));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo7));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo8));
        imageItems.add(new CarouselPicker.DrawableItem(R.drawable.modelo6));
//Create an adapter
        CarouselPicker.CarouselViewAdapter imageAdapter = new CarouselPicker.CarouselViewAdapter(this, imageItems, 0);
//Set the adapter
        carouselPicker.setAdapter(imageAdapter);

        carouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //position of the selected item
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}