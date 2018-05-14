package org.insbaixcamp.proyectofinal;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.os.SystemClock.sleep;


public class FullscreenActivity extends AppCompatActivity implements ValueEventListener, ChildEventListener {
    boolean addId = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        final ProgressBar progressBar = findViewById(R.id.determinateBar);

        Timer timer = new Timer();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("name")) {
                    addId = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (addId) {
            database.child(Secure.getString(this.getContentResolver(), Secure.ANDROID_ID))
                    .setValue("");
        }


        //Device ID
        //Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);

//4000
        int timeout = 4; // make the activity visible for 4 seconds


        timer.schedule(new TimerTask() {


            @Override
            public void run() {
                while (progressBar.getProgress() < 100) {

                    progressBar.incrementProgressBy(2);


                    Random random = new Random();
                    int randomSleepTime = random.nextInt(100 - 10) + 65;
                    sleep(randomSleepTime);
                }

                finish();


                Intent homepage = new Intent(FullscreenActivity.this, MapsMainActivity.class);
                startActivity(homepage);

            }

        }, timeout);

    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

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
    public void onDataChange(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
