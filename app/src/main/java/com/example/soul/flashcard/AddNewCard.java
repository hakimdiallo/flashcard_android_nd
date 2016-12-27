package com.example.soul.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNewCard extends AppCompatActivity {
    private EditText question, reponse;
    private boolean q, r;
    private Button save;
    private Spinner spinner;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);

        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

        save = (Button) findViewById(R.id.save);
        save.setEnabled(false);

        q = r = false;

        question = (EditText) findViewById(R.id.question);
        question.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                q = false;
                save.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    q = true;
                }
                if(q && r){
                    save.setEnabled(true);
                }
            }
        });
        reponse = (EditText) findViewById(R.id.reponse);
        reponse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                r = false;
                save.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()){
                    r = true;
                }
                if(q && r){
                    save.setEnabled(true);
                }
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);

    }

    public void save(View view){
        InterfaceFlashProvider fp = new InterfaceFlashProvider(this);
        String qstr = question.getText().toString();
        String rstr = reponse.getText().toString();
        int pr = Integer.parseInt(String.valueOf(spinner.getSelectedItem()));
        //int pr = Integer.parseInt(prio.getText().toString());
        Intent intent = getIntent();
        String nom = intent.getStringExtra("nom");
        if (fp.insertIntoCarte(nom,qstr,rstr,pr)){
            Toast.makeText(this,"La carte a bien été enregitrée.",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Erreur lors de l'enregistrement. Veuillez réessayer.",Toast.LENGTH_LONG).show();
        }
    }
}
