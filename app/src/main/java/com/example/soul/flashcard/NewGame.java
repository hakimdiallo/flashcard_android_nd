package com.example.soul.flashcard;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by anhndmin on 12/13/16.
 */

public class NewGame extends AppCompatActivity {
    private static String authority="myflashcard";
    public EditText name_t;
    public TextView res;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgame);
        Intent intent = getIntent();
        name_t = (EditText) findViewById(R.id.name);
        res = (TextView) findViewById(R.id.result);
    }

    public void create(View view){
        name = name_t.getText().toString();
        InterfaceFlashProvider ip =new InterfaceFlashProvider(this);
        boolean check= ip.insertIntoJeux(name);
        if(check){
            res.setText("Le sujet est bien ajouté!!!");
        }
        else {
            res.setText("Sujet existed déjà!!!");
        }
    }
}
