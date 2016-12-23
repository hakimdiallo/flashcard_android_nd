package com.example.soul.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button add;
    private Button create;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = (Button) findViewById(R.id.buttonAdd);
        create = (Button) findViewById(R.id.buttonCreate);
        delete = (Button) findViewById(R.id.buttonDelete);

        InterfaceFlashProvider fp = new InterfaceFlashProvider(this);
        fp.init();
    }

    public void add(View view){
        Intent intent = new Intent(this,ShowGameToAddCard.class);
        intent.putExtra("option",1);
        startActivity(intent);
    }

    public void create(View view){
        Intent intent = new Intent(this, AddNewGame.class);
        startActivity(intent);
    }

    public void delete(View view){
        Intent intent = new Intent(this,ShowGameToAddCard.class);
        intent.putExtra("option",2);
        startActivity(intent);
    }

    public void jouer(View view){
        Intent intent = new Intent(this,ShowGameToAddCard.class);
        intent.putExtra("option",3);
        startActivity(intent);
    }
}
