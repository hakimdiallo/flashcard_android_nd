package com.example.soul.flashcard;

import android.util.Log;

/**
 * Created by soul on 27/12/16.
 */

public class Carte {
    private String nom_jeux;
    private String question;
    private String reponse;
    private int prio;

    public Carte(){
    }

    public String getNom_jeux() {
        return nom_jeux;
    }

    public String getQuestion() {
        return question;
    }

    public String getReponse() {
        return reponse;
    }

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setNom_jeux(String nom_jeux) {
        this.nom_jeux = nom_jeux;
    }

    public void set(String nom, String value){
        if (nom.equals("nomJeux")){
            //Log.d("0: ",value);
            setNom_jeux(value);
        }
        else if (nom.equals("question")){
            //Log.d("1: ",value);
            setQuestion(value);
        }
        else if (nom.equals("reponse")){
            //Log.d("2: ",value);
            setReponse(value);
        }
        else if (nom.equals("prio")){
            //Log.d("3: ",value);
            setPrio(Integer.parseInt(value));
        }
    }
}
