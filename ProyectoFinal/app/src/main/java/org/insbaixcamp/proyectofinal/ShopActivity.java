package org.insbaixcamp.proyectofinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

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
        listaSkates.add(new SkateArticles("Nyjah Rise Up Lion 7.75", "100$", R.drawable.skate1));
        listaSkates.add(new SkateArticles("Cut Out Seal 7.7", "100$", R.drawable.skate2));
        listaSkates.add(new SkateArticles("Shrapnel Tie Dye", "100$", R.drawable.skate3));
        listaSkates.add(new SkateArticles("-Elemento X negro ovejas 10 Año colabo sello", "100$", R.drawable.skate4));
        listaSkates.add(new SkateArticles("Script Nam Palm 7.75", "100$", R.drawable.skate5));
        listaSkates.add(new SkateArticles("Santa Cruz Deck Dance with Death Guzman", "100$", R.drawable.skate6));
        listaSkates.add(new SkateArticles("Nyjah King 7.75", "100$", R.drawable.skate7));
        listaSkates.add(new SkateArticles("Nyjah Dialed 7.7", "100$", R.drawable.skate8));
        listaSkates.add(new SkateArticles("Mr. Element 04CP1Y", "100$", R.drawable.skate9));
        listaSkates.add(new SkateArticles("River Camo 7.5 Assorted U", "100$", R.drawable.skate10));

    }

    public void onClick(View view) {


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
                Toast.makeText(getApplicationContext(),
                        "Selección: " + listaSkates.get
                                (rvSkates.getChildAdapterPosition(view))
                                .getNombre(), Toast.LENGTH_SHORT).show();
            }
        });

        rvSkates.setAdapter(adapter);
    }
}