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

        //Inicializamos las variable que se comunican con la parte .xml
        final ImageView ivImage0 = (ImageView) findViewById(R.id.ivImage0);
        final ImageView ivImage1 = (ImageView) findViewById(R.id.ivImage1);
        final ImageView ivImage2 = (ImageView) findViewById(R.id.ivImage2);
        final ImageView ivImage3 = (ImageView) findViewById(R.id.ivImage3);
        final ImageView ivImage4 = (ImageView) findViewById(R.id.ivImage4);
        final ImageView ivImage5 = (ImageView) findViewById(R.id.ivImage5);
        final ImageView ivImage6 = (ImageView) findViewById(R.id.ivImage6);
        final ImageView ivImage7 = (ImageView) findViewById(R.id.ivImage7);
        final ImageView ivImage8 = (ImageView) findViewById(R.id.ivImage8);

        //recojemos el nombre de usuario
        username = getIntent().getStringExtra("username");
        reference = database.getReference("Users/" + username + "/unlockedTables");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Hacemos la comprobacion de que tablas tienen un valor igual a 1,
                //lo cual implica que se muestre la tabla en el apartado de galeria.
                if (dataSnapshot.child("table0").getValue().toString().equals("1")) {
                    ivImage0.setImageResource(R.drawable.hawk);
                }
                if (dataSnapshot.child("table1").getValue().toString().equals("1")) {
                    ivImage1.setImageResource(R.drawable.chris_cole_warrior);
                }
                if (dataSnapshot.child("table2").getValue().toString().equals("1")) {
                    ivImage2.setImageResource(R.drawable.centery);
                }
                if (dataSnapshot.child("table3").getValue().toString().equals("1")) {
                    ivImage3.setImageResource(R.drawable.boo);
                }
                if (dataSnapshot.child("table4").getValue().toString().equals("1")) {
                    ivImage4.setImageResource(R.drawable.huston);
                }
                if (dataSnapshot.child("table5").getValue().toString().equals("1")) {
                    ivImage5.setImageResource(R.drawable.joslin);
                }
                if (dataSnapshot.child("table6").getValue().toString().equals("1")) {
                    ivImage6.setImageResource(R.drawable.malto);
                }
                if (dataSnapshot.child("table7").getValue().toString().equals("1")) {
                    ivImage7.setImageResource(R.drawable.mullen);
                }
                if (dataSnapshot.child("table8").getValue().toString().equals("1")) {
                    ivImage8.setImageResource(R.drawable.sablone);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
