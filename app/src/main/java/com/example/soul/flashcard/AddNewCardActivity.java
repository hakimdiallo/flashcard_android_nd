package com.example.soul.flashcard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class AddNewCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);
        Intent intent = getIntent();
        String nom = intent.getStringExtra("nom");
        Toast.makeText(AddNewCardActivity.this,nom,Toast.LENGTH_LONG).show();
    }
}
