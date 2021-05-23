package com.example.semestralkaandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Trieda MainActivity je zodpovedna za spustenie aplikacie a vytvorenie uvodnej plochy.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Hlavna metoda ktora spusta hru.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Metoda, ktoru vyuziva tlacidlo, aby zacalo hru
     *
     * @param view Potrebny parameter na zistenie tlacidla
     */
    public void zacniHru(View view) {
        Intent i = new Intent(this, Hra.class);
        startActivity(i);
        finish();
    }

    /**
     * Metoda, ktoru vyuziva tlacidlo, aby ukoncilo celu hru.
     *
     * @param view Potrebny parameter na zistenie tlacidla
     */
    public void koniec(View view) {
        finish();
        System.exit(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}