package gomba;

import tekton.Tekton;

/**
 * A gombatesteket reprezentáló osztály
 */
public class Gombatest {

    private int elettartam;
    private int kilohetoSporakSzama;
    private boolean fejlett;

    /**
     * Gombatest konstruktora
     * @param elettartam a gombatest élettartama
     * @param kilohetoSporakSzama a gombatestből kihelyezhető spórák száma
     * @param fejlett a gombatest fejlettsége
     */
    public Gombatest(int elettartam, int kilohetoSporakSzama, boolean fejlett) {
        this.elettartam = elettartam;
        this.kilohetoSporakSzama = kilohetoSporakSzama;
        this.fejlett = fejlett;
        System.out.println("Gombatest létrejött");
    }

    /**
     * Visszaadja a gombatest élettartamát
     * @return a gombatest élettartama
     */
    public int Getelettartam() {
        return elettartam;
    }

    /**
     * Visszaadja a gombatestből kihelyezhető spórák számát
     * @return a gombatestből kihelyezhető spórák száma
     */
    public int GetkilohetoSporakSzama() {
        return kilohetoSporakSzama;
    }

    /**
     * Visszaadja a gombatest fejlettségét
     * @return a gombatest fejlettsége
     */
    public boolean Getfejlett() {
        return fejlett;
    }

    /**
     * Spóra elhelyezése
     * @param tekton a tekton, amin elhelyezzük az új spórát
     */
    public void sporaLoves(Tekton tekton) {
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
