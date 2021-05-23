package com.example.semestralkaandroid;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

/**
 * Trieda Hra sluzi na vygenerovanie hracej plochy.
 *
 * @author Jakub Rapsik
 * @version 1.0
 */
public class Hra extends AppCompatActivity {


    private HashMap<String, Integer> polia = null; //Hash mapa potrebuje kluc a data preto array stringov s cislami.
    private HashMap<String, Integer> novepolia = null;
    private GridView grid = null;
    private int Skore = 0;
    private TextView view = null;

    /**
     * @constructor Konstruktor, ktory vytvori novu Hashmapu
     */
    public Hra() {
        this.polia = new HashMap<>();
    }

    /**
     * Metoda, ktora zabespeci, ze pri stlaceni home buttonu sa aktivita posle na pozadie.
     * Pri opetovnom otvoreni aplikacie sa aktivita zobrazi.
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);  // "Hide" your current Activity
    }

    /**
     * Sluzi na ulozenie udajov o danej aktivite
     * Metoda, ktora ulozi aktualny stav aktivity
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * Hlavna metoda celej triedy, ktora vygeneruje a zadefinuje vsetky hlavne casti a stara sa o samotny chod hracej plochy
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main_game);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String[] pozicia = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"}; // Vnutorny grid Pre GridView
        this.view = this.findViewById(R.id.Score);
        this.grid = this.findViewById(R.id.grid);
        Random randomCislo1 = new Random();
        Random randomCislo2 = new Random();
        int cislo1 = randomCislo1.nextInt(16) + 1; //od 1- 16
        int cislo2 = randomCislo2.nextInt(16) + 1;
        if (cislo1 == cislo2 && cislo2 != 16) {
            cislo2++;
        } else {
            if (cislo1 == cislo2) {
                cislo1--;
            }
        }
        Random random3 = new Random();
        Random random4 = new Random();
        int[] zakladneCisla = {2, 4};
        int gen1 = random3.nextInt(zakladneCisla.length);
        int gen2 = random4.nextInt(zakladneCisla.length);
        for (int i = 0; i < pozicia.length; i++) {
            if (Integer.parseInt(pozicia[i]) == cislo1) {
                this.polia.put(pozicia[i], zakladneCisla[gen1]);
            }
            if (Integer.parseInt(pozicia[i]) == cislo2) {
                this.polia.put(pozicia[i], zakladneCisla[gen2]);
            }
            if (Integer.parseInt(pozicia[i]) != cislo1 && Integer.parseInt(pozicia[i]) != cislo2) {
                this.polia.put(pozicia[i], 0);
            }
        }
        final List<String> pozicie = new ArrayList<>(Arrays.asList(pozicia)); //Vytvorenie listu a jeho naplnenie podla array
        this.ZmenaGridu(pozicie);
        this.grid.setOnTouchListener(new Manazer(Hra.this) {
            /**
             * Metoda zachytava pohyb a po jeho zachyteny kontroluje moznost pohybu cisiel v gride
             */
            @Override
            public void onSwipeTop() {
                Hra.this.novepolia = new HashMap<>(Hra.this.polia);
                while (!(Hra.this.kontrolaPohybu(1, 1) && Hra.this.kontrolaPohybu(1, 2) && Hra.this.kontrolaPohybu(1, 3) && Hra.this.kontrolaPohybu(1, 4))) {
                    for (int i = 16; i > 4; i--) {
                        int j = i;
                        if (j > 4 && polia.get(j + "").compareTo(polia.get((j - 4) + "")) == 0) {
                            Hra.this.polia.put((j - 4) + "", Hra.this.polia.get(j + "") + Hra.this.polia.get((j - 4) + ""));
                            Hra.this.polia.put(j + "", 0);
                            String skore = Hra.this.view.getText().toString();
                            int HodnotaSkore = Integer.parseInt(skore);
                            HodnotaSkore += Hra.this.polia.get((j - 4) + "");
                            Skore = HodnotaSkore;
                            Hra.this.view.setText(HodnotaSkore + "");
                        } else if (j > 12 && polia.get((j - 4) + "").compareTo(polia.get((j - 8) + "")) == 0 && polia.get(j + "").compareTo(polia.get((j - 4) + "")) == 0) {
                            Hra.this.polia.put((j - 8) + "", Hra.this.polia.get((j - 8) + "") + Hra.this.polia.get((j - 4) + ""));
                            Hra.this.polia.put((j - 4) + "", Hra.this.polia.get(j + ""));
                            Hra.this.polia.put(j + "", 0);
                            String skore = Hra.this.view.getText().toString();
                            int HodnotaSkore = Integer.parseInt(skore);
                            HodnotaSkore += Hra.this.polia.get((j - 4) + "");
                            Skore = HodnotaSkore;
                            Hra.this.view.setText(HodnotaSkore + "");
                        } else if (j > 8 && Hra.this.polia.get(j - 4 + "") != 0 && Hra.this.polia.get(j - 8 + "") == 0) {
                            Hra.this.polia.put((j - 8) + "", Hra.this.polia.get(j - 4 + ""));
                            Hra.this.polia.put((j - 4) + "", Hra.this.polia.get(j + ""));
                            Hra.this.polia.put(j + "", 0);
                        } else if (j > 4 && Hra.this.polia.get((j - 4) + "") == 0) {
                            Hra.this.polia.put((j - 4) + "", Hra.this.polia.get(j + ""));
                            Hra.this.polia.put(j + "", 0);
                        }
                    }
                }

                ZmenaGridu(pozicie);
                GeneratorCisiel();
                KontrolaStavu();

            }

            /**
             * Metoda zachytava pohyb a po jeho zachyteny kontroluje moznost pohybu cisiel v gride
             */
            @Override
            public void onSwipeBottom() {
                Hra.this.novepolia = new HashMap<>(Hra.this.polia);
                while (!(Hra.this.kontrolaPohybu(2, 13) && Hra.this.kontrolaPohybu(2, 14) && Hra.this.kontrolaPohybu(2, 15) && Hra.this.kontrolaPohybu(2, 16))) {
                    for (int i = 1; i < 13; i++) {
                        int j = i;
                        if (j < 13 && polia.get(j + "").compareTo(polia.get((j + 4) + "")) == 0) {
                            polia.put((j + 4) + "", polia.get(j + "") + polia.get((j + 4) + ""));
                            polia.put(j + "", 0);
                            String skore = Hra.this.view.getText().toString();
                            int HodnotaSkore = Integer.parseInt(skore);
                            HodnotaSkore += Hra.this.polia.get((j + 4) + "");
                            Skore = HodnotaSkore;
                            Hra.this.view.setText(HodnotaSkore + "");
                        } else if (j < 13 && Hra.this.polia.get(j + 4 + "") == Hra.this.polia.get(j + 8 + "") && Hra.this.polia.get(j + "") == Hra.this.polia.get(j + 4 + "")) {
                            Hra.this.polia.put((j + 8) + "", Hra.this.polia.get((j + 8) + "") + Hra.this.polia.get((j + 4) + ""));
                            Hra.this.polia.put((j + 4) + "", Hra.this.polia.get(j + ""));
                            Hra.this.polia.put(j + "", 0);
                            String skore = Hra.this.view.getText().toString();
                            int HodnotaSkore = Integer.parseInt(skore);
                            HodnotaSkore += Hra.this.polia.get((j + 4) + "");
                            Skore = HodnotaSkore;
                            Hra.this.view.setText(HodnotaSkore + "");
                        } else if (j < 9 && Hra.this.polia.get(j + 4 + "") != 0 && Hra.this.polia.get(j + 8 + "") == 0) {
                            Hra.this.polia.put((j + 8) + "", Hra.this.polia.get(j + 4 + ""));
                            Hra.this.polia.put((j + 4) + "", Hra.this.polia.get(j + ""));
                            Hra.this.polia.put(j + "", 0);
                        } else if (j < 13 && polia.get((j + 4) + "") == 0) {
                            polia.put((j + 4) + "", polia.get(j + ""));
                            polia.put(j + "", 0);
                        }
                    }
                }
                ZmenaGridu(pozicie);
                GeneratorCisiel();
                KontrolaStavu();

            }

            /**
             * Metoda zachytava pohyb a po jeho zachyteny kontroluje moznost pohybu cisiel v gride
             */
            @Override
            public void onSwipeLeft() {
                Hra.this.novepolia = new HashMap<>(Hra.this.polia);
                int kontrola;
                while (!(Hra.this.kontrolaPohybu(3, 1) && Hra.this.kontrolaPohybu(3, 5) && Hra.this.kontrolaPohybu(3, 9) && Hra.this.kontrolaPohybu(3, 13))) {
                    for (int i = 16; i > 1; i--) {
                        int j = i;
                        if (j == 13 || j == 9 || j == 5) {
                            kontrola = 1;
                        } else {
                            kontrola = 0;
                        }
                        if (kontrola == 0 && polia.get(j + "").compareTo(polia.get((j - 1) + "")) == 0) {
                            polia.put((j - 1) + "", polia.get(j + "") + polia.get((j - 1) + ""));
                            polia.put(j + "", 0);
                            String skore = Hra.this.view.getText().toString();
                            int HodnotaSkore = Integer.parseInt(skore);
                            HodnotaSkore += Hra.this.polia.get((j - 1) + "");
                            Skore = HodnotaSkore;
                            Hra.this.view.setText(HodnotaSkore + "");
                        } else if (kontrola == 1 && Hra.this.polia.get((j - 1) + "") == Hra.this.polia.get((j - 2) + "") && Hra.this.polia.get(j + "") == Hra.this.polia.get((j - 1) + "")) {
                            Hra.this.polia.put((j - 2) + "", Hra.this.polia.get((j - 2) + "") + Hra.this.polia.get((j - 1) + ""));
                            Hra.this.polia.put((j - 1) + "", Hra.this.polia.get(j + ""));
                            Hra.this.polia.put(j + "", 0);
                            String skore = Hra.this.view.getText().toString();
                            int HodnotaSkore = Integer.parseInt(skore);
                            HodnotaSkore += Hra.this.polia.get((j - 1) + "");
                            Skore = HodnotaSkore;
                            Hra.this.view.setText(HodnotaSkore + "");
                        } else if (kontrola == 0 && polia.get((j - 1) + "") == 0) {
                            polia.put((j - 1) + "", polia.get(j + ""));
                            polia.put(j + "", 0);
                        }

                    }
                }
                ZmenaGridu(pozicie);
                GeneratorCisiel();
                KontrolaStavu();
            }

            /**
             * Metoda zachytava pohyb a po jeho zachyteny kontroluje moznost pohybu cisiel v gride
             */
            @Override
            public void onSwipeRight() {
                Hra.this.novepolia = new HashMap<>(Hra.this.polia);
                int kontrola = 0;
                while (!(Hra.this.kontrolaPohybu(4, 4) && Hra.this.kontrolaPohybu(4, 8) && Hra.this.kontrolaPohybu(4, 12) && Hra.this.kontrolaPohybu(4, 16))) {
                    for (int i = 1; i < 17; i++) {
                        int j = i;
                        if (j == 4 || j == 8 || j == 12 || j == 16) {
                            kontrola = 1;
                        } else {
                            kontrola = 0;
                        }
                        if (kontrola == 0 && polia.get(j + "").compareTo(polia.get((j + 1) + "")) == 0) {
                            polia.put((j + 1) + "", polia.get(j + "") + polia.get((j + 1) + ""));
                            polia.put(j + "", 0);
                            String skore = Hra.this.view.getText().toString();
                            int HodnotaSkore = Integer.parseInt(skore);
                            HodnotaSkore += Hra.this.polia.get((j + 1) + "");
                            Skore = HodnotaSkore;
                            Hra.this.view.setText(HodnotaSkore + "");
                        } else if (kontrola == 1 && Hra.this.polia.get((j + 1) + "") == Hra.this.polia.get((j + 2) + "") && Hra.this.polia.get(j + "") == Hra.this.polia.get((j + 1) + "")) {
                            Hra.this.polia.put((j + 2) + "", Hra.this.polia.get((j + 2) + "") + Hra.this.polia.get((j + 1) + ""));
                            Hra.this.polia.put((j + 1) + "", Hra.this.polia.get(j + ""));
                            Hra.this.polia.put(j + "", 0);
                            String skore = Hra.this.view.getText().toString();
                            int HodnotaSkore = Integer.parseInt(skore);
                            HodnotaSkore += Hra.this.polia.get((j + 1) + "");
                            Skore = HodnotaSkore;
                            Hra.this.view.setText(HodnotaSkore + "");
                        } else if (kontrola == 0 && polia.get((j + 1) + "") == 0) {
                            polia.put((j + 1) + "", polia.get(j + ""));
                            polia.put(j + "", 0);
                        }
                    }

                }

                ZmenaGridu(pozicie);
                GeneratorCisiel();
                KontrolaStavu();
            }
        });
    }

    /**
     * Metoda, ktora sa stara o samotnu aktualizaciu gridu.
     *
     * @param list Parameter, ktory sluzi na zistenie novych pozicii cisiel v gride.
     */
    public void ZmenaGridu(List list) {

        List<String> pom = list;
        this.grid.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pom) {

            /**
             * @param pozicia
             * @param konvertovanyView
             * @param predok
             * @return Vracia TextView na dane policko
             */
            public View getView(int pozicia, View konvertovanyView, ViewGroup predok) { //Sluzi na konvertovanie objektov do TextObjektov a vrati ich ako parameter pre ArrayAdapter
                View pohlad = super.getView(pozicia, konvertovanyView, predok); //Vracia policko na danej pozicii
                TextView textView = (TextView) pohlad;
                //Nastavenie parametrov pre layout
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT
                );
                textView.setLayoutParams(lp);

                textView.setWidth(100);
                textView.setHeight(210);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(25);
                if (Hra.this.polia.get(pom.get(pozicia)) != 0) {
                    textView.setText(Hra.this.polia.get(pom.get(pozicia)) + "");
                } else {
                    textView.setText(""); //Na prazdne pole nastavi prazdny string
                }
                textView.setId(pozicia);
                textView.setBackgroundColor(Color.parseColor("#93ede5"));
                return textView; //Vracia TextView na dane policko
            }
        });
    }

    /**
     * Metoda, ktora sa stara o nahodnu generaciu cisiel, ktore nasledne posle do gridu
     */
    public void GeneratorCisiel() {
        Random ran = new Random();
        int hodnota = ran.nextInt(16) + 1;
        int pocetnenul = 0;
        for (String key : this.polia.keySet()) {
            if (this.polia.get(key) != 0) {
                pocetnenul++;
            }
        }
        if (pocetnenul != 16) {
            while (this.polia.get(hodnota + "") != 0) {
                hodnota = ran.nextInt(16) + 1;
            }
            int[] zakladneCisla = {2, 4};
            int ind1 = ran.nextInt(zakladneCisla.length);
            this.polia.put(hodnota + "", zakladneCisla[ind1]);
            String[] pozicia = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"}; // Vnutorny grid Pre GridView
            List<String> pozicie = Arrays.asList(pozicia);
            this.ZmenaGridu(pozicie);

        }

    }

    /**
     * Metoda, ktora sa stara o zistovanie kontroly stavu a samotne zavolanie konca hry.
     */
    public void KontrolaStavu() {
        for (String kluc : this.polia.keySet()) {
            if (this.polia.get(kluc) == 2048) {
                int hodnota = 0;
                Intent intent = new Intent(Hra.this, KoniecHry.class); //Do intetn nastavime path ku class KoniecHry aby sme to vedeli neskor spustit
                intent.putExtra("Vysledok", hodnota);
                intent.putExtra("Vysledne skore: ", Skore);
                startActivity(intent);   //Spusti triedu KoniecHry a tam sa ohodnotia vsetky veci
                finish();
                return;
            }
        }
        for (String kluc : this.polia.keySet()) {
            if (this.polia.get(kluc) != this.novepolia.get(kluc)) { //Kontrola ci sa stal pohyb
                return;
            }
        }
        int hodnota = 1;
        Intent intent = new Intent(Hra.this, KoniecHry.class);
        intent.putExtra("Vysledok", hodnota);
        intent.putExtra("Vysledne skore: ", Skore);
        startActivity(intent);
        finish();
    }

    /**
     * Metoda, ktora kontroluje ci sa pohyb moze vykonat.
     *
     * @param orientacia Definuje orientaciu podla toho, ktory pohyb bol zaznamenany.
     * @param policko    Definuje, ktore policko sa ma kontrolovat.
     * @return boolean Vracia true alebo false podla toho ci sa pohyb moze vykonat.
     */
    public boolean kontrolaPohybu(int orientacia, int policko) { //1 je hore 2 je dole 3 je vlavo 4 je vpravo
        switch (orientacia) {
            case 1: {
                if (this.polia.get(policko + "") == 0 && this.polia.get((policko + 4) + "") == 0 && this.polia.get((policko + 8) + "") == 0 && this.polia.get((policko + 12) + "") == 0) {
                    return true;
                } else if (this.polia.get((policko + 4) + "") == 0 && this.polia.get((policko + 8) + "") == 0 && this.polia.get((policko + 12) + "") == 0 && this.polia.get(policko + "") != 0) {
                    return true;
                } else if (this.polia.get((policko + 8) + "") == 0 && this.polia.get((policko + 12) + "") == 0 && this.polia.get((policko + 4) + "") != 0 && this.polia.get(policko + "") != 0) {
                    return true;
                } else if (this.polia.get((policko + 12) + "") == 0 && this.polia.get((policko + 4) + "") != 0 && this.polia.get(policko + "") != 0 && this.polia.get((policko + 8) + "") != 0) {
                    return true;
                } else if (polia.get(policko + "") != 0 && polia.get((policko + 4) + "") != 0 && polia.get((policko + 8) + "") != 0 && polia.get((policko + 12) + "") != 0) {
                    return true;
                }
                return false;
            }
            case 2: {
                if (polia.get(policko + "") == 0 && polia.get((policko - 4) + "") == 0 && polia.get((policko - 8) + "") == 0 && polia.get((policko - 12) + "") == 0) {
                    return true;
                } else if (polia.get((policko - 4) + "") == 0 && polia.get((policko - 8) + "") == 0 && polia.get((policko - 12) + "") == 0 && polia.get(policko + "") != 0) {
                    return true;
                } else if (polia.get((policko - 8) + "") == 0 && polia.get((policko - 12) + "") == 0 && polia.get((policko - 4) + "") != 0 && polia.get(policko + "") != 0) {
                    return true;
                } else if (polia.get((policko - 12) + "") == 0 && polia.get((policko - 4) + "") != 0 && polia.get(policko + "") != 0 && polia.get((policko - 8) + "") != 0) {
                    return true;
                } else if (polia.get(policko + "") != 0 && polia.get((policko - 4) + "") != 0 && polia.get((policko - 8) + "") != 0 && polia.get((policko - 12) + "") != 0) {
                    return true;
                }
                return false;
            }
            case 3: {
                if (polia.get(policko + "") == 0 && polia.get((policko + 1) + "") == 0 && polia.get((policko + 2) + "") == 0 && polia.get((policko + 3) + "") == 0) {
                    return true;
                } else if (polia.get((policko + 1) + "") == 0 && polia.get((policko + 2) + "") == 0 && polia.get((policko + 3) + "") == 0 && polia.get(policko + "") != 0) {
                    return true;
                } else if (polia.get((policko + 2) + "") == 0 && polia.get((policko + 3) + "") == 0 && polia.get((policko + 1) + "") != 0 && polia.get(policko + "") != 0) {
                    return true;
                } else if (polia.get((policko + 3) + "") == 0 && polia.get((policko + 1) + "") != 0 && polia.get(policko + "") != 0 && polia.get((policko + 2) + "") != 0) {
                    return true;
                } else if (polia.get(policko + "") != 0 && polia.get((policko + 1) + "") != 0 && polia.get((policko + 2) + "") != 0 && polia.get((policko + 3) + "") != 0) {
                    return true;
                }
                return false;

            }
            case 4: {
                if (polia.get(policko + "") == 0 && polia.get((policko - 1) + "") == 0 && polia.get((policko - 2) + "") == 0 && polia.get((policko - 3) + "") == 0) {
                    return true;
                } else if (polia.get((policko - 1) + "") == 0 && polia.get((policko - 2) + "") == 0 && polia.get((policko - 3) + "") == 0 && polia.get(policko + "") != 0) {
                    return true;
                } else if (polia.get((policko - 2) + "") == 0 && polia.get((policko - 3) + "") == 0 && polia.get((policko - 1) + "") != 0 && polia.get(policko + "") != 0) {
                    return true;
                } else if (polia.get((policko - 3) + "") == 0 && polia.get((policko - 1) + "") != 0 && polia.get(policko + "") != 0 && polia.get((policko - 2) + "") != 0) {
                    return true;
                } else if (polia.get(policko + "") != 0 && polia.get((policko - 1) + "") != 0 && polia.get((policko - 2) + "") != 0 && polia.get((policko - 3) + "") != 0) {
                    return true;
                }
                return false;

            }
        }
        return false;
    }

    /**
     * Metoda, ktora sluzi na pozastavenie aktivity
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Metoda, ktoru vyuziva tlacidlo, aby sa mohlo vratit do hlavnej plochy
     *
     * @param view Potrebny parameter na zistenie tlacidla
     */
    public void back(View view) {
        Intent i = new Intent(Hra.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}






















