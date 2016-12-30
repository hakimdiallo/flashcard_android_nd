package com.example.soul.flashcard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends MenuActivity implements ChooseCreateFragment.OnFragmentInteractionListener{
    private Button add;
    private Button create;
    private Button delete;
    private ChooseCreateFragment fragment;
    public static final String F_PREFERENCES = "flashPref";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        super.onCreateDrawer();
        add = (Button) findViewById(R.id.buttonAdd);
        create = (Button) findViewById(R.id.buttonCreate);
        delete = (Button) findViewById(R.id.buttonDelete);

        InterfaceFlashProvider fp = new InterfaceFlashProvider(this);
        fp.init();

        //preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //SharedPreferences.Editor edit = preferences.edit();
        //edit.putInt("tempsDeReponse",30);
        //edit.putInt("dureeInac",5);
        //edit.commit();

        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.activity_main,(ViewGroup)findViewById(R.id.content_frame));
        if (savedInstanceState == null) {
            NotificationService ns = new NotificationService(getApplicationContext());
            ns.sendNotification();
        }
    }

    public void add(View view){
        Intent intent = new Intent(this,ShowGameToAddCard.class);
        intent.putExtra("option",1);
        startActivity(intent);
    }

    public void choose(View view){
        FragmentManager fm = getSupportFragmentManager();
        fragment = new ChooseCreateFragment();
        fragment.show(fm,"Dialog fragment...");
        //Intent intent = new Intent(this, AddNewGame.class);
        //startActivity(intent);
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

    public void create(View view){
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        Intent intent = new Intent(this, AddNewGame.class);
        startActivity(intent);
    }

    public void link(View view){
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        Intent intent = new Intent(this, GameDownloaderActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
