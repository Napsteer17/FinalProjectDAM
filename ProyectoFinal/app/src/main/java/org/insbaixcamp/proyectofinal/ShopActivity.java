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

    ArrayList<SkateArticles> listaSkates;
    RecyclerView rvSkates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        construirRecycler();

    }

    private void llenarSkates() {
        listaSkates.add(new SkateArticles("Nyjah Rise Up Lion 7.75", "113,18€", R.drawable.skate1));
        listaSkates.add(new SkateArticles("Cut Out Seal 7.7", "139,99€", R.drawable.skate2));
        listaSkates.add(new SkateArticles("Shrapnel Tie Dye", "92,82€", R.drawable.skate3));
        listaSkates.add(new SkateArticles("Elemento X negro ovejas 10 Año colabo sello", "107,52€", R.drawable.skate4));
        listaSkates.add(new SkateArticles("Script Nam Palm 7.75", "129,99€", R.drawable.skate5));
        listaSkates.add(new SkateArticles("Santa Cruz Deck Dance with Death Guzman", "81,78€", R.drawable.skate6));
        listaSkates.add(new SkateArticles("Nyjah King 7.75", "124,99€", R.drawable.skate7));
        listaSkates.add(new SkateArticles("Nyjah Dialed 7.7", "94,54€", R.drawable.skate8));
        listaSkates.add(new SkateArticles("Mr. Element 04CP1Y", "104,86€", R.drawable.skate9));
        listaSkates.add(new SkateArticles("River Camo 7.5 Assorted U", "99,00€", R.drawable.skate10));

    }

    private void construirRecycler() {
        listaSkates = new ArrayList<>();
        rvSkates = (RecyclerView) findViewById(R.id.RecyclerId);


        rvSkates.setLayoutManager(new LinearLayoutManager(this));


        llenarSkates();

        SkateAdapter adapter = new SkateAdapter(listaSkates);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference zonesRef = FirebaseDatabase.getInstance().getReference("Skates");

                int posicionTabla = rvSkates.getChildAdapterPosition(view);

                if (posicionTabla == 0) {
                    DatabaseReference zone1Ref = zonesRef.child("Skate-Completo-Element-Nyjah-Rise");
                    final String nombreTabla = zone1Ref.getKey();

                    makeListener(zone1Ref, nombreTabla);
                } else if (posicionTabla == 1) {
                    DatabaseReference zone1Ref = zonesRef.child("Skate-Completo-Element-Cut-Sea");
                    final String nombreTabla = zone1Ref.getKey();

                    makeListener(zone1Ref, nombreTabla);
                } else if (posicionTabla == 2) {
                    DatabaseReference zone1Ref = zonesRef.child("Bullet-Skateboard-Complete-Shrapnel-BULDEKSHTD");
                    final String nombreTabla = zone1Ref.getKey();

                    makeListener(zone1Ref, nombreTabla);
                } else if (posicionTabla == 3) {
                    DatabaseReference zone1Ref = zonesRef.child("Elemento-ovejas-10-A%C3%B1o-Complete-Exclusivo");
                    final String nombreTabla = zone1Ref.getKey();

                    makeListener(zone1Ref, nombreTabla);
                } else if (posicionTabla == 4) {
                    DatabaseReference zone1Ref = zonesRef.child("Skate-Completo-Element-Script-Palm");
                    final String nombreTabla = zone1Ref.getKey();

                    makeListener(zone1Ref, nombreTabla);
                } else if (posicionTabla == 5) {
                    DatabaseReference zone1Ref = zonesRef.child("Santa-Cruz-Dance-Guzman-32-9-pulgadas");
                    final String nombreTabla = zone1Ref.getKey();

                    makeListener(zone1Ref, nombreTabla);
                } else if (posicionTabla == 6) {
                    DatabaseReference zone1Ref = zonesRef.child("Skate-Completo-Element-Nyjah-King");
                    final String nombreTabla = zone1Ref.getKey();

                    makeListener(zone1Ref, nombreTabla);
                } else if (posicionTabla == 7) {
                    DatabaseReference zone1Ref = zonesRef.child("Skate-Completo-Element-Nyjah-Dialed");
                    final String nombreTabla = zone1Ref.getKey();

                    makeListener(zone1Ref, nombreTabla);
                } else if (posicionTabla == 8) {
                    DatabaseReference zone1Ref = zonesRef.child("Element-04CP1Y-Complete-Skateboard");
                    final String nombreTabla = zone1Ref.getKey();

                    makeListener(zone1Ref, nombreTabla);
                } else if (posicionTabla == 9) {
                    DatabaseReference zone1Ref = zonesRef.child("ELEMENT-River-Camo-7-5-Assorted");
                    final String nombreTabla = zone1Ref.getKey();

                    makeListener(zone1Ref, nombreTabla);
                }
            }
        });

        rvSkates.setAdapter(adapter);
    }

    public void makeListener(DatabaseReference dbReference, final String nombreTabla) {
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String codigoTabla = dataSnapshot.getValue(String.class);
                String URLToGo = "https://www.amazon.es/" + String.valueOf(nombreTabla) + "/dp/" + codigoTabla;

                Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(URLToGo));
                startActivity(browserIntent);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}