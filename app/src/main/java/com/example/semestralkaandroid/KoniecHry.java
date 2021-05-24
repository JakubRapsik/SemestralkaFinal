package com.example.semestralkaandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Trieda KoniecHry sluzi na vygenerovanie finalnej plochy.
 *
 * @author Jakub Rapsik
 * @version 1.0
 */
public class KoniecHry extends AppCompatActivity {

    private int skore;

    /**
     * Hlavna metoda triedy ktora zadefinuje vsetky udaje.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.konice_hry);
        int vysledok = this.getIntent().getIntExtra("Vysledok", 3);
        skore = this.getIntent().getIntExtra("Vysledne skore: ", 0);
        Button button = this.findViewById(R.id.VysledokBtn);
        TextView textView = this.findViewById(R.id.VysledokTxt);
        TextView Skore = this.findViewById(R.id.FinalSkore);
        if (vysledok == 0) {
            textView.setTextColor(Color.GREEN);
            textView.setText("Vyhral si");
            button.setText("Menu");
            Skore.setText(Integer.toString(skore));
        } else {
            textView.setTextColor(Color.RED);
            textView.setText("Koniec hry");
            button.setText("Hraj znova");
            Skore.setText(Integer.toString(skore));
        }

    }

    /**
     * Metoda, ktoru vyuziva tlacidlo, aby sa mohlo vratit do hlavnej plochy
     *
     * @param view Potrebny parameter na zistenie tlacidla
     */
    public void znova(View view) {
        Intent intent = new Intent(KoniecHry.this, MainActivity.class);
        intent.putExtra("Vysledne skore: ", skore);
        startActivity(intent);
        finish();
    }

}
