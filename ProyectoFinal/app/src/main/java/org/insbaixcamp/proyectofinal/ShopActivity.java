package org.insbaixcamp.proyectofinal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    ArrayList<SkateArticles> alSkates;
    RecyclerView rvSkates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        buildRecyclerView();

    }

    //inicializamos los skates para poder mostrarlos después en el RecyclerView del activity
    private void makeSkates() {
        alSkates.add(new SkateArticles("Nyjah Rise Up Lion 7.75", "113,18€", R.drawable.skate1));
        alSkates.add(new SkateArticles("Cut Out Seal 7.7", "139,99€", R.drawable.skate2));
        alSkates.add(new SkateArticles("Shrapnel Tie Dye", "92,82€", R.drawable.skate3));
        alSkates.add(new SkateArticles("Elemento X negro ovejas 10 Año colabo sello", "107,52€", R.drawable.skate4));
        alSkates.add(new SkateArticles("Script Nam Palm 7.75", "129,99€", R.drawable.skate5));
        alSkates.add(new SkateArticles("Santa Cruz Deck Dance with Death Guzman", "81,78€", R.drawable.skate6));
        alSkates.add(new SkateArticles("Nyjah King 7.75", "124,99€", R.drawable.skate7));
        alSkates.add(new SkateArticles("Nyjah Dialed 7.7", "94,54€", R.drawable.skate8));
        alSkates.add(new SkateArticles("Mr. Element 04CP1Y", "104,86€", R.drawable.skate9));
        alSkates.add(new SkateArticles("River Camo 7.5 Assorted U", "99,00€", R.drawable.skate10));

    }

    //Metodo que se encarga de recojer los valores de las tablas que tenemos insertado en Firebase y
    //crear el RecyclerView que utilizamos.
    private void buildRecyclerView() {
        alSkates = new ArrayList<>();
        rvSkates = (RecyclerView) findViewById(R.id.rvItems);


        rvSkates.setLayoutManager(new LinearLayoutManager(this));


        makeSkates();

        SkateAdapter adapter = new SkateAdapter(alSkates);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference refAllTables = FirebaseDatabase.getInstance().getReference("Skates");

                int tablePosition = rvSkates.getChildAdapterPosition(view);

                //dependiendo de la posición de la tabla, recojeremos el valor de un nombre u otro, para
                //poder pasarlo mas tarde al metodo makeListener y nos permita visualizar el producto en la
                //tienda.
                if (tablePosition == 0) {
                    DatabaseReference reference = refAllTables.child("Skate-Completo-Element-Nyjah-Rise");
                    final String tableName = reference.getKey();

                    makeListener(reference, tableName);
                } else if (tablePosition == 1) {
                    DatabaseReference reference = refAllTables.child("Skate-Completo-Element-Cut-Seal");
                    final String tableName = reference.getKey();

                    makeListener(reference, tableName);
                } else if (tablePosition == 2) {
                    DatabaseReference reference = refAllTables.child("Bullet-Skateboard-Complete-Shrapnel-BULDEKSHTD");
                    final String tableName = reference.getKey();

                    makeListener(reference, tableName);
                } else if (tablePosition == 3) {
                    DatabaseReference reference = refAllTables.child("Elemento-ovejas-10-Año-Complete-Exclusivo");
                    final String tableName = reference.getKey();

                    makeListener(reference, tableName);
                } else if (tablePosition == 4) {
                    DatabaseReference reference = refAllTables.child("Skate-Completo-Element-Script-Palm");
                    final String tableName = reference.getKey();

                    makeListener(reference, tableName);
                } else if (tablePosition == 5) {
                    DatabaseReference reference = refAllTables.child("Santa-Cruz-Dance-Guzman-32-9-pulgadas");
                    final String tableName = reference.getKey();

                    makeListener(reference, tableName);
                } else if (tablePosition == 6) {
                    DatabaseReference reference = refAllTables.child("Skate-Completo-Element-Nyjah-King");
                    final String tableName = reference.getKey();

                    makeListener(reference, tableName);
                } else if (tablePosition == 7) {
                    DatabaseReference reference = refAllTables.child("Skate-Completo-Element-Nyjah-Dialed");
                    final String tableName = reference.getKey();

                    makeListener(reference, tableName);
                } else if (tablePosition == 8) {
                    DatabaseReference reference = refAllTables.child("Element-04CP1Y-Complete-Skateboard");
                    final String tableName = reference.getKey();

                    makeListener(reference, tableName);
                } else if (tablePosition == 9) {
                    DatabaseReference reference = refAllTables.child("Element-Skateboards-monopatín-camuflaje-7-5");
                    final String tableName = reference.getKey();

                    makeListener(reference, tableName);
                }
            }
        });

        rvSkates.setAdapter(adapter);
    }

    //Este metodo se encarga de recojer los valores de cada tabla organizada en Firebase, para después
    //poder crear un enlace que redirigirá al producto en Amazon. Este lleva nuestro tag de Amazon.
    public void makeListener(DatabaseReference dbReference, final String tableName) {
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tableCode = dataSnapshot.getValue(String.class);
                String URLToGo = "https://www.amazon.es/" + String.valueOf(tableName) + "/dp/" + tableCode + "?t=sk80b-21";

                Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(URLToGo));
                startActivity(browserIntent);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}