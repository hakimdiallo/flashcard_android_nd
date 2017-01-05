package com.example.soul.flashcard;//import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.soul.flashcard.R;
import com.example.soul.flashcard.ScreenSlidePageFragment;

import java.util.ArrayList;

public class PlayAGame extends MenuActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private int num_pages ;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    private String sujet,question;
    private ArrayList<String[]> listQuestionReponse;

    boolean cardFlipped = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int i =0;
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_play_a_game);
        super.onCreateDrawer();
        overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

        LayoutInflater inflater = getLayoutInflater();
        inflater.inflate(R.layout.activity_play_a_game,(ViewGroup)findViewById(R.id.content_frame));

        Intent intent= getIntent();
        sujet = intent.getStringExtra("nom");
        InterfaceFlashProvider ip = new InterfaceFlashProvider(this);
        listQuestionReponse = ip.getQuestionReponse(sujet,"1");

        num_pages = listQuestionReponse.size();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ScreenSlidePageFragment page = new ScreenSlidePageFragment();
            page.setQuestion(listQuestionReponse.get(position)[0]);
            page.setResponse(listQuestionReponse.get(position)[1]);
            listQuestionReponse.remove(position);
            page.setContext(getApplicationContext());
            return page;
        }

        @Override
        public int getCount() {
            return num_pages;
        }

    }
}

