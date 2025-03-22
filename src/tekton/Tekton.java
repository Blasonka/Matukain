package tekton;

import spora.Spora;

import java.util.ArrayList;
import java.util.List;

/**
 * Absztrakt osztály Tektonok adatainak tárolására
 */

public abstract class Tekton {
    /// Tekton azonosítóját tárolja
    private int id;

    /// Tekton X koordinátáját tárolja
    private int koordinataX;

    /// Tekton X koordinátáját tárolja
    private int koordinataY;

    /// Tektonon lévő spórákat tárolja egy listában
    private List<Spora> sporak;

    /**
     * Tekton osztály konstruktora
     * @param id beállítja az id értékét
     * @param koordinataX beállítja az X koordináta értékét
     * @param koordinataY beállítja az Y koordináta értékét
     */
    public Tekton(int id, int koordinataX, int koordinataY) {
        this.id = id;
        this.koordinataX = koordinataX;
        this.koordinataY = koordinataY;
        sporak = new ArrayList<>();
        System.out.println("Tekton->Tekton()");
    }

    /// Tekton törése
    public void tores(){
        System.out.println("Tekton->tores()");
    }

    /// Spóra hozzáadása
    public void addSpora(Spora spora){
        System.out.println("Tekton->addSpora(Spora)");
    }

    /**
     * Spóra törlése
     * @param spora törli az adott spórát
     */
    public void removeSpora(Spora spora){
        System.out.println("Tekton->removeSpora(Spora)");
    }

    /// Meghatározza a szomszédos tektonokat
    public void szomszedosTekton(){
        System.out.println("Tekton->szomszedosTekton()");
    }

    /// Meghatározza, hogy a tekton szabad-e
    public boolean szabadTekton(){
        System.out.println("Tekton->szabadTekton()");
        return true;
    }

    /// Gombát növeszt a tektonra
    public void gombaNovesztes(){
        System.out.println("Tekton->gombaNovesztes()");
    }

    /// Tekton hatásának kifejtése, absztrakt metódus
    public abstract void hatasKifejtes();
}