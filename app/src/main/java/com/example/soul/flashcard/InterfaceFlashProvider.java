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

    public boolean insertIntoJeux(String nom){
        boolean check = true;
        ContentValues values = new ContentValues();
        values.put(COLONNE_NOM,nom);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(autorithy).appendPath(TABLE_JEUX);
        Uri uri = builder.build();

        try {
            contentResolver.insert(uri,values);
            Log.d("Insertion:","Sucess of insertion...");
        }catch (SQLiteConstraintException e){
            check = false;
            Log.d("Erro:",e.getMessage());
        }
        return check;
    }

    public boolean insertIntoCarte(String nom, String question, String reponse, int prio){
        boolean check = true;
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
            check = false;
            Log.d("Erro:",e.getMessage());
        }
        return check;
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

    public void deleteJeux(String nom){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(autorithy).appendPath(TABLE_JEUX);
        Uri uri = builder.build();
        int res = contentResolver.delete(uri,null, new String[]{nom});
    }

    public ArrayList<String> getQuestion(String sujet, String prio){
        ArrayList<String> res = new ArrayList<String>();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(autorithy).appendPath("questions");
        Uri uri = builder.build();
        Cursor cursor = contentResolver.query(uri,null,null,new String[]{sujet,prio},null);
        if(cursor !=null){
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                res.add(cursor.getString(0));
            }
        }
        return res;
    }

    public ArrayList<String> getAnswer(String sujet, String prio){
        ArrayList<String> res = new ArrayList<String>();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(autorithy).appendPath("reponses");
        Uri uri = builder.build();
        Cursor cursor = contentResolver.query(uri,null,null,new String[]{sujet,prio},null);
        if(cursor !=null){
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                res.add(cursor.getString(0));
            }
        }
        return res;
    }

    public void init(){
        insertIntoJeux("MATHS");
        insertIntoJeux("FRANCAIS");
        insertIntoJeux("HISTOIRE");
        insertIntoCarte("MATHS","2x2?","4",1);
        insertIntoCarte("MATHS","2x3?","6",1);
        insertIntoCarte("MATHS","9x9?","81",2);
        insertIntoCarte("MATHS","12x12?","144",3);
        insertIntoCarte("MATHS","200x200?","40000",4);
        insertIntoCarte("HISTOIRE","Napoleaon bonaparte","Roi",1);
        insertIntoCarte("FRANCAIS","Tomber dans les pommes","Evanouir",2);
    }
}
