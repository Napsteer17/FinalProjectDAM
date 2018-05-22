package org.insbaixcamp.proyectofinal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import static android.os.SystemClock.sleep;


public class FullscreenActivity extends AppCompatActivity implements ValueEventListener, ChildEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        final ProgressBar progressBar = findViewById(R.id.loadingBar);

        Timer timer = new Timer();


        int timeout = 4;


        timer.schedule(new TimerTask() {


            @Override
            public void run() {
                while (progressBar.getProgress() < 100) {

                    progressBar.incrementProgressBy(2);


                    Random random = new Random();
                    int randomSleepTime = random.nextInt(100 - 10) + 65;
                    sleep(4000);
                }

                finish();

                Intent intentMapsMain = new Intent(FullscreenActivity.this, MapsMainActivity.class);
                startActivity(intentMapsMain);

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
