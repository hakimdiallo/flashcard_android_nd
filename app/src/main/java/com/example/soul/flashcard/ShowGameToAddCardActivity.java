package com.example.soul.flashcard;

import android.content.Intent;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;

public class ShowGameToAddCardActivity extends AppCompatActivity {
    private boolean options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game_to_add_card);

        Intent intent = getIntent();
        options = intent.getBooleanExtra("option",false);

        ListView listView = (ListView) findViewById(R.id.listview);

        InterfaceFlashProvider fp = new InterfaceFlashProvider(this);
        String[] noms = fp.getJeux();

        ArrayList<String> al = new ArrayList<String>();
        Collections.addAll(al,noms);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.jeux_view,al);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nom = adapter.getItem(position);
                if (options){
                    Intent in = new Intent(ShowGameToAddCardActivity.this,AddNewCardActivity.class);
                    in.putExtra("nom",nom);
                    startActivity(in);
                }else{
                    //delete here
                }
            }
        });
    }
}
