package com.example.soul.flashcard;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by soul on 13/12/16.
 */

public class InterfaceFlashProvider {
    private Context context;
    private ContentResolver contentResolver;

    private String autorithy = "myflashcard";

    public final static int VERSION = 1;
    public final static String DB_NAME = "flashcard";

    public final static String TABLE_JEUX = "jeux";
    public final static String COLONNE_NOM = "nom";


    public final static String TABLE_CARTE = "cartes";
    public final static String COLONNE_QUESTION = "question";
    public final static String COLONNE_REPONSE = "reponse";
    public final static String COLONNE_PRIORITY = "prio";

    public InterfaceFlashProvider(Context c){
        context = c;
        contentResolver = c.getContentResolver();
    }

    public void insertIntoJeux(String nom){
        ContentValues values = new ContentValues();
        values.put(COLONNE_NOM,nom);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(autorithy).appendPath(TABLE_JEUX);
        Uri uri = builder.build();

        try {
            contentResolver.insert(uri,values);
            Log.d("Insertion:","Sucess of insertion...");
        }catch (SQLiteConstraintException e){
            Log.d("Erro:",e.getMessage());
        }
    }

    public void insertIntoCarte(String nom, String question, String reponse, int prio){
        ContentValues values = new ContentValues();
        values.put(COLONNE_NOM,nom);
        values.put(COLONNE_QUESTION,question);
        values.put(COLONNE_REPONSE,reponse);
        values.put(COLONNE_PRIORITY,prio);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(autorithy).appendPath(TABLE_CARTE);
        Uri uri = builder.build();

        try {
            contentResolver.insert(uri,values);
            Log.d("Insertion:","Sucess of insertion...");
        }catch (SQLiteConstraintException e){
            Log.d("Erro:",e.getMessage());
        }
    }

    public String[] getJeux(){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(autorithy).appendPath(TABLE_JEUX);
        Uri uri = builder.build();
        Cursor c = contentResolver.query(uri,null,null,null,null);
        String[] noms;
        if(c != null) {
            noms = new String[c.getCount()];
            int i = 0;
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                noms[i++] = c.getString(0);
            }
        }else{
            noms = new String[0];
        }
        return noms;
    }

    public void init(){
        insertIntoJeux("maths");
        insertIntoJeux("histoire");
        insertIntoJeux("français");
        insertIntoCarte("maths","2x2?","4",1);
        insertIntoCarte("histoire","napoleaon bonaparte","kexkexkex",5);
    }
}
