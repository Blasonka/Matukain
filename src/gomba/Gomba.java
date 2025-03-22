package gomba;

import tekton.Tekton;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A gombát reprezentáló osztály
 */
public class Gomba {

    private List<Gombafonal> gombafonalak;
    private Gombatest gombatest;
    private Tekton tekton;

    /**
     * Gomba konstruktora
     * @param tekton a tekton, amin a gomba található
     */
    public Gomba(Tekton tekton) {
        this.gombafonalak = new ArrayList<Gombafonal>();
        this.gombatest = null;
        this.tekton = tekton;
    }

    /**
     * Visszaadja, hogy két tekton közt van-e fonal
     * @return true, ha van fonal, false egyébként
     */
    public boolean fonalOsszekoti(Tekton tekton1, Tekton tekton2) {
        for (Gombafonal gombafonal : gombafonalak) {
            if (gombafonal.getHatar1() == tekton1 && gombafonal.getHatar2() == tekton2) {
                System.out.println("Van fonal a két tekton között");
                return true;
            }
        }
        System.out.println("Nincs fonal a két tekton között");
        return false;
    }

    /**
     * Fonal hozzáadása
     * @param gombafonal a hozzáadandó fonal
     */
    public void addFonal(Gombafonal gombafonal) {
        gombafonalak.add(gombafonal);
        System.out.println("Fonal hozzáadva");
    }

    /**
     * Fonal eltávolítása
     * @param gombafonal az eltávolítandó fonal
     */
    public void removeFonal(Gombafonal gombafonal) {
        gombafonalak.remove(gombafonal);
        System.out.println("Fonal eltávolítva");
    }
}
