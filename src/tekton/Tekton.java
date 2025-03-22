package tekton;

import spora.*;
import gomba.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Absztrakt osztály Tektonok adatainak tárolására
 */

public abstract class Tekton {
    /**
     * Tekton azonosítóját tárolja
     */
    private int id;

    /**
     * Tekton X koordinátáját tárolja
     */
    private int koordinataX;

    /**
     * Tekton Y koordinátáját tárolja
     */
    private int koordinataY;

    /**
     * Tektonon lévő spórákat tárolja egy listában
     */
    private List<Spora> sporak;

    /**
     * Tektonon lévő gombát tárolja
     */
    private Gomba gomba;

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
        gomba = null;
        System.out.println("Tekton->Tekton()");
    }

    /**
     * Visszaadja a tekton ID-ját
     */
    public int getID() {
        return id;
    }

    /**
     * Visszaadja a tekton X koordinátáját
     */
    public int getKoordinataX() {
        return koordinataX;
    }

    /**
     * Visszaadja a tekton Y koordinátáját
     */
    public int getKoordinataY() {
        return koordinataY;
    }

    /**
     * Visszaadja a tektonon lévő spórákat
     */
    public List<Spora> getSporak() {
        return sporak;
    }

    /**
     * Visszaadja a tektonon lévő gombát, vagy null-t, ha nincs rajta gomba
     */
    public Gomba getGomba() {
        return gomba;
    }

    /**
     * Tekton törése
     */
    public void tores(){
        System.out.println("A tekton törölve lett");
    }

    /**
     * Spóra hozzáadása
     * @param spora spórát hozzáadja a tektonhoz
     */
    public void addSpora(Spora spora){
        sporak.add(spora);
        System.out.println("Az adott spóra hozzá lett adva a tektonhoz");
    }

    /**
     * Spóra törlése
     * @param spora törli az adott spórát
     */
    public void removeSpora(Spora spora){
        spora.torles();
        sporak.remove(spora);
        System.out.println("Az adott spóra törölve lett a tektonról");
    }

    /**
     * Meghatározza a szomszédos tektonokat
     */
    public void szomszedosTekton(){
        System.out.println("Meg lettek határozva a szomszédos tektonok");
    }

    /**
     * Visszaadja, hogy az adott tekton szabad-e
     */
    public boolean szabadTekton(){
        if (gomba != null){
            System.out.println("A tekton nem szabad");
            return false;
        }
        else {
            System.out.println("A tekton szabad");
            return true;
        }
    }

    /**
     * Gombát növeszt a tektonra
     */
    public void gombaNovesztes(){
        gomba = new Gomba(this);
        System.out.println("Új gomba nőtt erre a tektonra");
    }

    /// Tekton hatásának kifejtése, absztrakt metódus
    public abstract void hatasKifejtes();
}