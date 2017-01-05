package com.example.soul.flashcard;


import android.content.Context;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by anhndmin on 1/4/17.
 */

public class ScreenSlidePageFragment extends Fragment {
    private boolean cardFlipped = false;
    private TextView viewQuest, viewRes;
    private String question, reponse;
    private CountDownTimer timer;
    private TextView textTimer;
    private String strTime;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_container, container, false);

        ((FrameLayout)rootView.findViewById(R.id.container)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
            }
        });

        textTimer = (TextView) rootView.findViewById(R.id.timer);
        long time = PreferenceManager.getDefaultSharedPreferences(context).getInt(getString(R.string.tdr),15);
        textTimer.setText(String.valueOf(time));
        timer = new CountDownTimer(time * 1000,1000) {
            @Override
            public void onTick(long l) {
                long s = l/1000;
                strTime = String.valueOf(s);
                textTimer.setText(strTime);
            }

            @Override
            public void onFinish() {
                textTimer.setText("00");
                ReponseFragment fragment = new ReponseFragment();
                fragment.setResponse(reponse);
                getChildFragmentManager()
                        .beginTransaction()
                /*.setCustomAnimations(
                        R.anim.flip_right_in,
                        R.anim.flip_right_out,
                        R.anim.flip_left_in,
                        R.anim.flip_left_out)*/
                        .replace(R.id.container, fragment)
                        .commit();
            }
        }.start();

        QuestionFragment qf = new QuestionFragment();
        qf.setQuestion(question);

        getChildFragmentManager().beginTransaction().add(R.id.container,qf).commit();

        return rootView;
    }

    public void setQuestion(String s){
        question = s;
    }

    public void setResponse(String s){
        reponse = s;
    }

    public void setContext(Context c){
        context = c;
    }

    /**
     * A fragment representing the front of the card.
     */
    public static class QuestionFragment extends Fragment {
        String question;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.question, container, false);

            ((TextView)rootView.findViewById(R.id.question)).setText(question);
            return rootView;
        }

        public void setQuestion(String s){
            question = s;
        }
    }

    /**
     * A fragment representing the back of the card.
     */
    public static class ReponseFragment extends Fragment {
        Spinner spinner;
        String reponse;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.reponse, container, false);

            ((TextView)rootView.findViewById(R.id.reponse)).setText(reponse);
            spinner =(Spinner)rootView.findViewById(R.id.spinner);
            return rootView;
        }

        public void valider(){
            int pr = Integer.parseInt(String.valueOf(spinner.getSelectedItem()));
        }

        public void setResponse(String s){
            reponse = s;
        }
    }

    public void flipCard() {
        Fragment newFragment;
        if (cardFlipped) {
            newFragment = new QuestionFragment();
            ((QuestionFragment)newFragment).setQuestion(question);
        } else {
            newFragment = new ReponseFragment();
            ((ReponseFragment)newFragment).setResponse(reponse);
        }

        getChildFragmentManager()
                .beginTransaction()
                /*.setCustomAnimations(
                        R.anim.flip_right_in,
                        R.anim.flip_right_out,
                        R.anim.flip_left_in,
                        R.anim.flip_left_out)*/
                .replace(R.id.container, newFragment)
                .commit();

        cardFlipped = !cardFlipped;
    }
}




