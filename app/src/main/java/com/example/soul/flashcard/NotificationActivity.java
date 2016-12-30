package com.example.soul.flashcard;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class NotificationActivity extends MenuActivity {
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification);
        super.onCreateDrawer();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.activity_notification,(ViewGroup)findViewById(R.id.content_frame));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int duree = preferences.getInt(getString(R.string.di),7);
        InterfaceFlashProvider fp = new InterfaceFlashProvider(this);

        list = (ListView) findViewById(R.id.listview);
        String[] noms = fp.getCartes(duree);

        ArrayList<String> al = new ArrayList<String>();
        Collections.addAll(al,noms);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.jeux_view,al);
        list.setAdapter(adapter);

    }
}
