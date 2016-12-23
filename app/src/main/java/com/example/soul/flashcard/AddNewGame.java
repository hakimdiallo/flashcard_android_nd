package com.example.soul.flashcard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by anhndmin on 12/13/16.
 */

public class AddNewGame extends AppCompatActivity {
    private static String authority="myflashcard";
    public EditText name_t;
    public TextView res;
    private String name;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_game);

        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

        create = (Button) findViewById(R.id.create);
        create.setEnabled(false);

        name_t = (EditText) findViewById(R.id.name);
        name_t.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                create.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty())
                    create.setEnabled(true);
            }
        });
        res = (TextView) findViewById(R.id.result);
    }

    public void create(View view){
        name = name_t.getText().toString();
        InterfaceFlashProvider ip =new InterfaceFlashProvider(this);
        boolean check= ip.insertIntoJeux(name);
        if(check){
            res.setText("Le jeu a bien été ajouté!!!");
        }
        else {
            res.setText("le jeu existe déjà!!!");
        }
    }
}
