package com.example.soul.flashcard;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ShowGameToAddCard extends MenuActivity {
    private int options;
    InterfaceFlashProvider fp;
    private String nom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_show_game_to_add_card);
        super.onCreateDrawer();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.activity_show_game_to_add_card,(ViewGroup)findViewById(R.id.content_frame));

        Intent intent = getIntent();
        options = intent.getIntExtra("option",0);

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
                /* Ajouter une carte*/
                if (options==1){
                    Intent in = new Intent(ShowGameToAddCard.this,AddNewCard.class);
                    in.putExtra("nom",nom);
                    startActivity(in);
                /*Supprimer un jeu*/
                }else if(options==2){
                    new AlertDialog.Builder(ShowGameToAddCard.this)
                            .setTitle(R.string.titleDelete)
                            .setMessage(R.string.messageDelete)
                            .setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    fp.deleteJeux(nom);
                                    adapter.remove(nom);
                                    Toast.makeText(ShowGameToAddCard.this,R.string.deleted,Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ShowGameToAddCard.this,R.string.cancelDelete,Toast.LENGTH_LONG).show();
                                }
                    })
                    .show();

                }
                /* Afficher un sujet pour choisir a jouer */
                else if(options==3){
                    Intent in = new Intent(ShowGameToAddCard.this,PlayAGame.class);
                    in.putExtra("nom",nom);
                    startActivity(in);
                }
            }
        });
    }
}
