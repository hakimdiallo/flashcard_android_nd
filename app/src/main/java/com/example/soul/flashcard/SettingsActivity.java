package com.example.soul.flashcard;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends MenuActivity {
    private EditText tempsReponse;
    private EditText dureeInac;
    private Button save;
    private boolean tdr, di;
    public static final String F_PREFERENCES = "flashPref";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);
        super.onCreateDrawer();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.activity_settings,(ViewGroup)findViewById(R.id.content_frame));

        tdr = di = false;

        save = (Button) findViewById(R.id.save);
        save.setEnabled(false);

        tempsReponse = (EditText) findViewById(R.id.tempsReponse);
        tempsReponse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tdr = false;
                save.setEnabled(tdr);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()){
                    tdr = true;
                    if (tdr && di){
                        save.setEnabled(true);
                    }
                }
            }
        });

        dureeInac = (EditText) findViewById(R.id.dureeInac);
        dureeInac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                di = false;
                save.setEnabled(di);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()){
                    di = true;
                    if (tdr && di){
                        save.setEnabled(true);
                    }
                }
            }
        });

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        tempsReponse.setText(""+preferences.getInt(getString(R.string.tdr),30));
        dureeInac.setText(""+preferences.getInt(getString(R.string.di),7));

    }

    public void save(View view){
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();
        int tr = Integer.parseInt(tempsReponse.getText().toString());
        edit.putInt(getString(R.string.tdr),tr);
        int di = Integer.parseInt(dureeInac.getText().toString());
        edit.putInt(getString(R.string.di),di);
        edit.commit();
        tempsReponse.setEnabled(false);
        dureeInac.setEnabled(false);
        save.setEnabled(false);
        TextView mess = (TextView) findViewById(R.id.message);
        mess.setText(R.string.aftersave);
    }
}
