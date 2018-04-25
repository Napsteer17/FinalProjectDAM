package org.insbaixcamp.proyectofinal;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.os.SystemClock.sleep;


public class FullscreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        final ProgressBar progressBar = findViewById(R.id.determinateBar);

        Timer timer = new Timer();

        int timeout = 4000; // make the activity visible for 4 seconds


        timer.schedule(new TimerTask() {


            @Override
            public void run() {
                while (progressBar.getProgress() < 100) {
                    progressBar.incrementProgressBy(1);

                    Random r = new Random();
                    int i1 = r.nextInt(100 - 10) + 65;
                    sleep(i1);
                }

                finish();
                Intent homepage = new Intent(FullscreenActivity.this, MainActivity.class);
                startActivity(homepage);
            }
        }, timeout);

    }

}
