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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    ProgressBar progressBar;
    boolean existUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        progressBar = findViewById(R.id.loadingBar);

        showChangeLangDialogUser();


    }

    public void showChangeLangDialogUser() {

        //Leemos si el archivo del usuario existe.
        readOnFile();

        //Si ese usuario no existe, creamos un pop up para que permita introducir el
        //nombre de usuario que guardaremos en Firebase.
        if (existUser == false) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
            dialogBuilder.setView(dialogView);

            final EditText etName = (EditText) dialogView.findViewById(R.id.etName);

            dialogBuilder.setTitle(R.string.title_username);
            dialogBuilder.setPositiveButton(R.string.title_accept, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    username = etName.getText().toString();

                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference reference=database.getReference("Users");

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(username)){
                                Toast.makeText(FullscreenActivity.this, R.string.user_duplicated, Toast.LENGTH_SHORT).show();
                                Intent intentFullScreen = new Intent(FullscreenActivity.this, FullscreenActivity.class);
                                startActivity(intentFullScreen);
                            }else{
                                writeOnFile(username);
                                //Creamos un timer para que la barra de carga inicie. Cuando la barra se complete
                                //nos redirige a la activity MapsMainActivity
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

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });





                }
            });
            AlertDialog b = dialogBuilder.create();
            b.show();
        } else {
            //Si el usuario existe, directamente empieza la carga de la barra y redirige
            //a MapsMainActivity
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

    //Comprueba si existe el archivo user.txt en el dispositivo.
    public void readOnFile() {
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(openFileInput("user.txt")));

            username = fin.readLine();
            fin.close();
            existUser = true;
        } catch (Exception ex) {
            existUser = false;
        }
    }

    //Metodo que crea el archivo user.txt con el nombre de usuario que pasaremos por parametro.
    private void writeOnFile(String user) {
        try {
            OutputStreamWriter escrituraEnDisco = new OutputStreamWriter(openFileOutput("user.txt", Context.MODE_PRIVATE));
            escrituraEnDisco.write(user);
            escrituraEnDisco.close();
        } catch (Exception ex) {

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
