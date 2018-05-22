package org.insbaixcamp.proyectofinal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class GalleryActivity extends AppCompatActivity {
    DatabaseReference reference;
    protected String username;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        final ImageView ivImage0 = (ImageView) findViewById(R.id.ivImage0);
        final ImageView ivImage1 = (ImageView) findViewById(R.id.ivImage1);
        final ImageView ivImage2 = (ImageView) findViewById(R.id.ivImage2);
        final ImageView ivImage3 = (ImageView) findViewById(R.id.ivImage3);
        final ImageView ivImage4 = (ImageView) findViewById(R.id.ivImage4);
        final ImageView ivImage5 = (ImageView) findViewById(R.id.ivImage5);
        final ImageView ivImage6 = (ImageView) findViewById(R.id.ivImage6);
        final ImageView ivImage7 = (ImageView) findViewById(R.id.ivImage7);
        final ImageView ivImage8 = (ImageView) findViewById(R.id.ivImage8);

        setupUsername();
        Log.i("USERNAME ", username);
        reference = database.getReference("Users/" + username + "/unlockedTables");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("ENTREO: ", "ENTREO");
                if (dataSnapshot.child("table0").getValue().toString().equals("1")) {
                    ivImage0.setImageResource(R.drawable.hawk);
                }
                if (dataSnapshot.child("table1").getValue().toString().equals("1")) {
                    Log.i("IGUAL A: ", "0");
                    ivImage1.setImageResource(R.drawable.chris_cole_warrior);
                }
                if (dataSnapshot.child("table2").getValue().toString().equals("1")) {
                    Log.i("IGUAL A: ", "0");
                    ivImage2.setImageResource(R.drawable.centery);
                }
                if (dataSnapshot.child("table3").getValue().toString().equals("1")) {
                    Log.i("IGUAL A: ", "0");
                    ivImage3.setImageResource(R.drawable.boo);
                }
                if (dataSnapshot.child("table4").getValue().toString().equals("1")) {
                    Log.i("IGUAL A: ", "0");
                    ivImage4.setImageResource(R.drawable.huston);
                }
                if (dataSnapshot.child("table5").getValue().toString().equals("1")) {
                    Log.i("IGUAL A: ", "0");
                    ivImage5.setImageResource(R.drawable.joslin);
                }
                if (dataSnapshot.child("table6").getValue().toString().equals("1")) {
                    Log.i("IGUAL A: ", "0");
                    ivImage6.setImageResource(R.drawable.malto);
                }
                if (dataSnapshot.child("table7").getValue().toString().equals("1")) {
                    Log.i("IGUAL A: ", "0");
                    ivImage7.setImageResource(R.drawable.mullen);
                }
                if (dataSnapshot.child("table8").getValue().toString().equals("1")) {
                    Log.i("IGUAL A: ", "0");
                    ivImage8.setImageResource(R.drawable.sablone);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
