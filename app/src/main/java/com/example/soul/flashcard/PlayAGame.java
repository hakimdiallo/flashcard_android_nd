package com.example.soul.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by anhndmin on 12/23/16.
 */

public class PlayAGame extends AppCompatActivity {
    private ArrayList<String> questions, reponses;
    private String question, reponse;
    TextView question_t, reponse_t, annonce, annonce_reponse;
    EditText votre_reponse;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_a_game);
        Intent intent = getIntent();
        String nom = intent.getStringExtra("sujet");
        String niveau = intent.getStringExtra("niveau");

        question_t = (TextView) findViewById(R.id.question2);
        votre_reponse = (EditText) findViewById(R.id.reponse2);
        reponse_t = (TextView) findViewById(R.id.reponse3);
        annonce= (TextView) findViewById(R.id.annonce);
        annonce_reponse=(TextView) findViewById(R.id.annonce_reponse3);

        annonce.setText("SUJET: "+nom+"-Niveau: "+niveau);

        InterfaceFlashProvider ip = new InterfaceFlashProvider(this);
        questions = ip.getQuestion(nom,niveau);
        reponses = ip.getAnswer(nom,niveau);

        if(!questions.isEmpty() && !reponses.isEmpty()){
            question = questions.remove(0);
            reponse = reponses.remove(0);

            question_t.setText(question);
        }

    }

    public void verifier(View view){
        String votre_res = votre_reponse.getText().toString();

        if(reponse.equals(votre_res)){
            Toast.makeText(this,"BRAVO!!!",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"MAUVAISE REPONSE!!",Toast.LENGTH_SHORT).show();
        }
    }

    public void reponse(View view){
        annonce_reponse.setText("Réponse: ");
        reponse_t.setText(reponse);
    }

    public void suivant(View view){
        if(!questions.isEmpty() && !reponses.isEmpty()){
            votre_reponse.setText("");
            annonce_reponse.setText("");
            reponse_t.setText("");
            question = questions.remove(0);
            reponse = reponses.remove(0);

            question_t.setText(question);
        }
        else {
            Toast.makeText(this,"C'est la dernière question de ce niveau!!!",Toast.LENGTH_SHORT).show();
        }
    }
}
