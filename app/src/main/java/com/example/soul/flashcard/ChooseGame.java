package com.example.soul.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by anhndmin on 12/23/16.
 */

public class ChooseGame extends AppCompatActivity {
    private String nom;
    Spinner difficulty;
    TextView sujet;
    Button commence;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);
        Intent intent = getIntent();
        nom = intent.getStringExtra("nom");

        sujet =(TextView)findViewById(R.id.sujet);
        sujet.setText("SUJET: "+nom);
        difficulty=(Spinner) findViewById(R.id.difficulty);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        difficulty.setAdapter(adapter);
    }

    public void commencer(View view){
        String niveau= difficulty.getSelectedItem().toString();
        InterfaceFlashProvider ip = new InterfaceFlashProvider(this);
        ArrayList<String> questions = ip.getQuestion(nom, niveau);
        if(questions.isEmpty()){
            Toast.makeText(this,"Il n'y a pas de question de ce niveau!!!!",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, PlayAGame.class);
            intent.putExtra("sujet", nom);
            intent.putExtra("niveau", niveau);
            startActivity(intent);
        }

    }
}
