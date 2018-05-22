package org.insbaixcamp.proyectofinal;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.os.SystemClock.sleep;


public class FullscreenActivity extends AppCompatActivity implements ValueEventListener, ChildEventListener {
    String username;
    boolean go = false;
    ProgressBar progressBar;
    boolean existUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        progressBar = findViewById(R.id.loadingBar);

        showChangeLangDialog();


    }

    public void showChangeLangDialog() {

        readOnFile();

        if (existUser == false) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
            dialogBuilder.setView(dialogView);

            final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

            dialogBuilder.setTitle(R.string.title_username);
            dialogBuilder.setPositiveButton(R.string.title_accept, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    //do something with edt.getText().toString();
                    username = edt.getText().toString();
                    writeOnFile(username);

                    int timeout = 4;
                    Timer timer = new Timer();
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

                            Intent intentMapsMain = new Intent(FullscreenActivity.this, MapsMainActivity.class);
                            intentMapsMain.putExtra("username", username);
                            startActivity(intentMapsMain);

                        }

                    }, timeout);


                }
            });
            AlertDialog b = dialogBuilder.create();
            b.show();
        } else {
            int timeout = 4;
            Timer timer = new Timer();
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

                    Intent intentMapsMain = new Intent(FullscreenActivity.this, MapsMainActivity.class);
                    intentMapsMain.putExtra("username", username);
                    startActivity(intentMapsMain);

                }

            }, timeout);
        }


    }

    public void readOnFile() {
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("user.txt")));

            username = fin.readLine();
            fin.close();
            Toast.makeText(FullscreenActivity.this, username, Toast.LENGTH_LONG).show();
            existUser = true;
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde memoria interna");
            existUser = false;
        }
    }

    private void writeOnFile(String user) {
        try {
            OutputStreamWriter escrituraEnDisco = new OutputStreamWriter(openFileOutput("user.txt", Context.MODE_PRIVATE));
            escrituraEnDisco.write(user);
            escrituraEnDisco.close();
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
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
