package org.insbaixcamp.proyectofinal;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity{

    EditText caixa;
    LinearLayout lineartext;
    FloatingActionButton fab;
    DatabaseReference myRef;

    private ToDoAdapter toDoAdapter;
    private List<ToDo> listToDo;
    private RecyclerView rv;

    String user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        caixa = (EditText) findViewById(R.id.editTextItem);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        rv = (RecyclerView) findViewById(R.id.recycler_view_items);
        lineartext = (LinearLayout) findViewById(R.id.linealtext);

        listToDo = new ArrayList<ToDo>();
        toDoAdapter = new ToDoAdapter(listToDo);

        rv.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(toDoAdapter);
        rv.scrollToPosition(listToDo.size()-1);

        // Inicialitzar database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("messages");

        user = getIntent().getStringExtra("username");

        ToDo provatasca = new ToDo(user,"hola");
        listToDo.add(provatasca);
        toDoAdapter.notifyDataSetChanged();
        //Si es el llistat de missatges es regenera el recycleview
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {

                    Log.e(getLocalClassName(), snapshot.getValue().toString());
                    listToDo.removeAll(listToDo);
                    for (DataSnapshot row:
                            snapshot.getChildren()) {
                        ToDo tasca = row.getValue(ToDo.class);

                        listToDo.add(tasca);
                    }
                    //Toast.makeText(MainActivity.this, listToDo.toString(), Toast.LENGTH_SHORT).show();
                    toDoAdapter.notifyDataSetChanged();
                    rv.scrollToPosition(listToDo.size()-1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }


    //onclick fab button
    public void enviar(View view) {
        if (lineartext.getVisibility() == View.INVISIBLE) {
            lineartext.setVisibility(View.VISIBLE);
            caixa.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(caixa, InputMethodManager.SHOW_IMPLICIT);
        } else {
            if (!caixa.getText().toString().equals("")) {
                ToDo tasca = new ToDo(user, caixa.getText().toString());
                this.myRef.push().setValue(tasca);
            }
            caixa.setText("");
            lineartext.setVisibility(View.INVISIBLE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(caixa.getWindowToken(), 0);
        }
    }
}
