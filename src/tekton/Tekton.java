package tekton;

import spora.*;
import gomba.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public int getID() {
        return id;
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
    public boolean szomszedosTekton(Tekton szomszed){
        System.out.println("Tekton->szomszedosTekton(szomszed)");
        System.out.println("A két tekton szomszédos?(Y/N)");
        Scanner scanner = new Scanner(System.in);
        String valasz = scanner.nextLine();
        scanner.close();
        if (valasz.equals("Y")) {
            System.out.println("A két tekton szomszédos");
            return true;
        } else {
            System.out.println("A két tekton nem szomszédos");
            return false;
        }

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