package com.example.semestralkaandroid;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Trieda Manazer je zodpovedna za zistenie pohybu a jeho ovladanie
 *
 * @author Jakub Rapsik
 * @version 1.0
 */
public class Manazer implements View.OnTouchListener {
    private final GestureDetector gestureDetector;

    /**
     * Konstruktor Manazera, ktory inicializuje novy GestureDetektor
     *
     * @param context the context
     * @constructor
     */
    public Manazer(Context context) {
        this.gestureDetector = new GestureDetector(context, new KontrolaPohybu());
    }

    /**
     * @param v     Pohlad
     * @param event Pohyb
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }

    /**
     * Vnorena Trieda, ktora ovlada pohyb
     */
    private final class KontrolaPohybu extends GestureDetector.SimpleOnGestureListener {

        private static final int swipeTreshold = 100; //Kolko musi posunut plst aby to bol swipe
        private static final int velocityTreshold = 100; //Ako rychlo musi swipenut

        /**
         * @param e
         * @return
         */
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        /**
         * @param e1
         * @param e2
         * @param velocityX
         * @param velocityY
         * @return
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean pohyb = false;
            float rozdielY = e2.getY() - e1.getY();
            float rozdielX = e2.getX() - e1.getX();
            if (Math.abs(rozdielX) > Math.abs(rozdielY)) {
                if (Math.abs(rozdielX) > swipeTreshold && Math.abs(velocityX) > velocityTreshold) { //If ktory zabezpeci ze sa posunul prst dost daleko a aj dost rychlo
                    if (rozdielX > 0) {
                        Manazer.this.onSwipeRight();
                    } else {
                        Manazer.this.onSwipeLeft();
                    }
                    pohyb = true;
                }
            } else if (Math.abs(rozdielY) > swipeTreshold && Math.abs(velocityY) > velocityTreshold) {
                if (rozdielY < 0) {
                    Manazer.this.onSwipeTop();
                } else {
                    Manazer.this.onSwipeBottom();
                }
                pohyb = true;
            }
            return pohyb;
        }
    }

    /**
     * Metoda zachytava pohyb.
     */
    public void onSwipeRight() {
    }

    /**
     * Metoda zachytava pohyb.
     */
    public void onSwipeLeft() {
    }

    /**
     * Metoda zachytava pohyb.
     */
    public void onSwipeTop() {
    }

    /**
     * Metoda zachytava pohyb.
     */
    public void onSwipeBottom() {
    }


}


