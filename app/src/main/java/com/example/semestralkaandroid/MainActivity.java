package com.example.semestralkaandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Trieda MainActivity je zodpovedna za spustenie aplikacie a vytvorenie uvodnej plochy.
 */
public class MainActivity extends AppCompatActivity {

    private TextView view;
    public static int Najskore = 0;
    public static int skore = 0;
    public static int DosialNajSkore = 0;
    private SharedPreferences prefs;


    /**
     * Hlavna metoda ktora spusta hru.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent getIntent = getIntent();
        view = findViewById(R.id.HightSkore);
        skore = getIntent.getIntExtra("Vysledne skore: ", 0);
        prefs = this.getSharedPreferences("NajSkore", Context.MODE_PRIVATE);
        DosialNajSkore = prefs.getInt("kluc", 0);
        if (skore > DosialNajSkore) {
            Najskore = skore;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("kluc", Najskore);
            editor.commit();
            view.setText(Integer.toString(Najskore));
        } else {
            view.setText(Integer.toString(DosialNajSkore));
        }

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
        if (Najskore > DosialNajSkore) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("kluc", Najskore);
            editor.commit();
        }
        super.onDestroy();
    }
}