package org.insbaixcamp.proyectofinal;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.os.SystemClock.sleep;


public class FullscreenActivity extends AppCompatActivity implements ValueEventListener, ChildEventListener {
    boolean addId = true;
    protected String username;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        final ProgressBar progressBar = findViewById(R.id.determinateBar);

        Timer timer = new Timer();

        setupUsername();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users/" + username + "/data");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        final Date date = new Date();
        final String fecha = dateFormat.format(date);

        ref.push().setValue(fecha);

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
