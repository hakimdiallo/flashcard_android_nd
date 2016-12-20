package com.example.soul.flashcard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ShowGameToAddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_game_to_add_card);

        ListView listView = (ListView) findViewById(R.id.listview);
        //Method m = getContentResolver().getClass().getMethod("getCards",null);
        //ArrayList<String> lists = getContentResolver().getClass().getMethod("getCards",null);
    }
}
