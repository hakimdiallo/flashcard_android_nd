package com.example.soul.flashcard;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
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
    InterfaceFlashProvider fp;
    private String nom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game_to_add_card);

        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

        Intent intent = getIntent();
        options = intent.getBooleanExtra("option",false);

        ListView listView = (ListView) findViewById(R.id.listview);

        fp = new InterfaceFlashProvider(this);
        String[] noms = fp.getJeux();

        ArrayList<String> al = new ArrayList<String>();
        Collections.addAll(al,noms);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.jeux_view,al);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nom = adapter.getItem(position);
                if (options){
                    Intent in = new Intent(ShowGameToAddCardActivity.this,AddNewCardActivity.class);
                    in.putExtra("nom",nom);
                    startActivity(in);
                }else{
                    new AlertDialog.Builder(ShowGameToAddCardActivity.this)
                            .setTitle(R.string.titleDelete)
                            .setMessage(R.string.messageDelete)
                            .setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    fp.deleteJeux(nom);
                                    adapter.remove(nom);
                                    Toast.makeText(ShowGameToAddCardActivity.this,R.string.deleted,Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ShowGameToAddCardActivity.this,R.string.cancelDelete,Toast.LENGTH_LONG).show();
                                }
                    })
                    .show();

                }
            }
        });
    }
}
