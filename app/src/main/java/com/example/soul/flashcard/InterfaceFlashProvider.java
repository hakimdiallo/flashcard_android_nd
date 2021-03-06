package com.example.soul.flashcard;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    public final static String COLONNE_INUT = "lastUse";

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

    public boolean jeuxExist(String nom){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(autorithy).appendPath("jeux_nom");
        Uri uri = builder.build();
        Cursor c = contentResolver.query(uri,null,null,new String[]{nom},null);
        if (c.getCount() != 0){
            return true;
        }
        return false;
    }

    public void insertCarte(Carte c){
        if (!jeuxExist(c.getNom_jeux())){
            insertIntoJeux(c.getNom_jeux());
        }
        insertIntoCarte(c.getNom_jeux(),c.getQuestion(),c.getReponse(),c.getPrio());
    }

    public boolean insertIntoCarte(String nom, String question, String reponse, int prio){
        boolean check = true;
        ContentValues values = new ContentValues();
        values.put(COLONNE_NOM,nom);
        values.put(COLONNE_QUESTION,question);
        values.put(COLONNE_REPONSE,reponse);
        values.put(COLONNE_PRIORITY,prio);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date = format.format(new Date());
        values.put(COLONNE_INUT,date);

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
        if(c.getCount() != 0) {
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

    public String[] getCartes(int duree){
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE,-duree);
        Date thatDay = cal.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String startDate = format.format(thatDay);
        //String endDate = format.format(today);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(autorithy).appendPath("cartes_inut");
        Uri uri = builder.build();

        Cursor c = contentResolver.query(uri,null,null,new String[]{startDate},null);
        String[] cartes;
        if(c.getCount() != 0) {
            cartes = new String[c.getCount()];
            int i = 0;
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
                cartes[i++] = c.getString(0);
            }
        }else{
            cartes = new String[0];
        }
        return cartes;
    }

    public void deleteJeux(String nom){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(autorithy).appendPath(TABLE_JEUX);
        Uri uri = builder.build();
        int res = contentResolver.delete(uri,null, new String[]{nom});
    }

    public ArrayList<String[]> getQuestionReponse(String sujet, String prio){
        ArrayList<String[]> res = new ArrayList<String[]>();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("content").authority(autorithy).appendPath("questions_reponses");
        Uri uri = builder.build();
        Cursor cursor = contentResolver.query(uri,null,null,new String[]{sujet,prio},null);
        if(cursor !=null){
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                res.add(new String[]{cursor.getString(0),cursor.getString(1)});
            }
        }
        return res;
    }

    public void init(){
        insertIntoJeux("MATHS");
        insertIntoJeux("FRANCAIS");
        //insertIntoJeux("HISTOIRE");
        insertIntoCarte("MATHS","2x2?","4",1);
        insertIntoCarte("MATHS","2x3?","6",1);
        insertIntoCarte("MATHS","9x9?","81",1);
        insertIntoCarte("MATHS","12x12?","144",1);
        insertIntoCarte("MATHS","200x200?","40000",1);
        //insertIntoCarte("HISTOIRE","Napoleaon bonaparte","Roi",1);
        insertIntoCarte("FRANCAIS","Tomber dans les pommes","Evanouir",1);
    }
}
