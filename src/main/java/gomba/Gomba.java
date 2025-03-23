package gomba;

import tekton.Tekton;

import java.util.ArrayList;
import java.util.List;

/**
 * Gomba osztály
 *
 * @class Gomba
 *
 * @brief A gombákat reprezentáló osztály
 *
 * @details
 * Ez az osztály felelős a gombák reprezentálásáért, azokkal kapcsolatos műveletek végzéséért.
 * Az osztály tartalmazza a gombatesteket, a gombafonalakat, valamint a gombákkal kapcsolatos műveleteket.
 *
 * @see felhasznalo.Gombasz
 *
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author Blasek
 * @version 1.0
 * @date 2025-03-22
 */
public class Gomba {

    /**
     * Gombafonalak listája
     * @var List<Gombafonal> gombafonalak
     * @brief A gombafonalakat tároló lista.
     */
    private List<Gombafonal> gombafonalak;

    /**
     * Gombatest
     * @var Gombatest gombatest
     * @brief A gombatest, amely a gombához tartozik.
     */
    private Gombatest gombatest;

    /**
     * Tekton
     * @var Tekton tekton
     * @brief A tekton, amin a gomba található.
     */
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
     * Fonal növesztése
     * @param tekton1 az egyik tekton, amelyen a fonál növekszik
     * @param tekton2 a másik tekton, amelyen a fonál növekszik
     */
    public void fonalNovesztes(Tekton tekton1, Tekton tekton2) {
        if (gombatest == null) {
            System.out.println("Nincs gombatest, nem lehet fonalat növeszteni");
        } else if (fonalOsszekoti(tekton1, tekton2)) {
            System.out.println("Már van fonal a két tekton között");
        } else {
            if (tekton1.szomszedosTekton(tekton2) || tekton2.szomszedosTekton(tekton1)) {
                Gombafonal gombafonal = new Gombafonal(tekton1, tekton2);
                addFonal(gombafonal);
            } else {
                System.out.println("A két tekton nem szomszédos, nem lehet fonalat növeszteni");
            }
        }
    }

    /**
     * Fonal hozzáadása
     * @param gombafonal a hozzáadandó fonal
     */
    public void addFonal(Gombafonal gombafonal) {
        gombafonalak.add(gombafonal);
        System.out.println("Fonal növesztve");
    }

    /**
     * Fonal eltávolítása
     * @param gombafonal az eltávolítandó fonal
     */
    public void removeFonal(Gombafonal gombafonal) {
        gombafonalak.remove(gombafonal);
        System.out.println("Fonal eltávolítva");
    }


    /**
     * Visszaadja a gomba által növesztett gombafonalakat
     * @return a gombafonalak listája
     */
    public List<Gombafonal> getGombafonalak() {
        return gombafonalak;
    }

    /**
     * Visszaadja a gomba testjét
     * @return a gombatest
     */
    public Gombatest getGombatest() {
        return gombatest;
    }

    /**
     * Vissszaadja a tektont, amelyiken a gomba található
     * @return a tekton
     */
    public Tekton getTekton() {
        return tekton;
    }

    /**
     * Beállítja a gomba testjét
     * @param gombatest a beállítandó gombatest
     */
    public void setGombatest(Gombatest gombatest) {
        this.gombatest = gombatest;
    }
}
