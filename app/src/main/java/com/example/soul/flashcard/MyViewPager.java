package com.example.soul.flashcard;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by soul on 05/01/17.
 */

public class MyViewPager extends ViewPager {

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return false;
    }
}
