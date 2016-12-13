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
    }

    public void add(View view){
        Intent intent = new Intent();
    }

    public void create(View view){
        Intent intent = new Intent();
    }

    public void delete(View view){
        Intent intent = new Intent();
    }
}
