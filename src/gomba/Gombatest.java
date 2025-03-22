package gomba;

import spora.Spora;
import tekton.Tekton;

import java.util.Random;

/**
 * A gombatesteket reprezentáló osztály
 */
public class Gombatest {

    private int elettartam;
    private int kilohetoSporakSzama;
    private boolean fejlett;

    /**
     * Gombatest konstruktora
     */
    public Gombatest() {
        this.elettartam = 0;
        this.kilohetoSporakSzama = 6;
        this.fejlett = false;
        System.out.println("Gombatest létrejött");
    }

    /**
     * Visszaadja a gombatest élettartamát
     * @return a gombatest élettartama
     */
    public int getElettartam() {
        return elettartam;
    }

    /**
     * Visszaadja a gombatestből kihelyezhető spórák számát
     * @return a gombatestből kihelyezhető spórák száma
     */
    public int getKilohetoSporakSzama() {
        return kilohetoSporakSzama;
    }

    /**
     * Visszaadja a gombatest fejlettségét
     * @return a gombatest fejlettsége
     */
    public boolean getFejlett() {
        return fejlett;
    }

    /**
     * Spóra elhelyezése
     * @param tekton a tekton, amin elhelyezzük az új spórát
     */
    public void sporaLoves(Tekton tekton) {
        Random rand = new Random();
        Spora spora;
        int randomSpora = rand.nextInt(4);
        switch (randomSpora) {
            case 0:
                spora = Spora.FEKETE;
                break;
            case 1:
                spora = Spora.FEHER;
                break;
            case 2:
                spora = Spora.PIROS;
                break;
            case 3:
                spora = Spora.ZOLD;
                break;
            default:
                spora = Spora.FEKETE;
                break;
        }
        System.out.println("Gombatest->sporaLoves()");
    }

    /**
     * Fonal növesztése
     * @param tekton1 az egyik tekton, amelyen a fonál növekszik
     * @param tekton2 a másik tekton, amelyen a fonál növekszik
     */
    public void fonalNovesztes(Tekton tekton1, Tekton tekton2) {
        System.out.println("Gombatest->fonalNovesztes()");
    }
}
