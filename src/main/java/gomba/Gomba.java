package gomba;

import tekton.Tekton;
import tesztelo.ParancsFeldolgozo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static tesztelo.Menu.parancsFeldolgozo;

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
 * @note Prototípus állapotban van.
 *
 * @author Blasek
 * @version 2.0
 * @date 2025-04-24
 */
public class Gomba {

    /**
     * A gomba azonosítója
     * @var int id
     * @brief A gomba azonosítóját tároló változó.
     */
    private int id;

    /**
     * Gombafonalak listája
     * @var List<Gombafonal> gombafonalak
     * @brief A gombafonalakat tároló lista.
     */
    private List<Gombafonal> gombafonalak;

    /**
     * Gombatestek listája
     * @var List<Gombatest> gombatestek
     * @brief A gombatesteket tároló lista.
     */
    private List<Gombatest> gombatestek;

    public Gomba(int id) {
        this.id = id;
        this.gombafonalak = new ArrayList<Gombafonal>();
        this.gombatestek = new ArrayList<Gombatest>();
    }

    /**
     * Visszaadja, hogy két tekton közt van-e fonal
     * @return true, ha van fonal, false egyébként
     */
    public boolean fonalOsszekoti(Tekton tekton1, Tekton tekton2) {
        for (Gombafonal gombafonal : gombafonalak) {
            if (gombafonal.getHatar1() == tekton1 && gombafonal.getHatar2() == tekton2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Fonal növesztése két tekton közés, vagy egy darab tektonra (ekkor a másik tekton ugyan az mint az első)
     * @param tekton1 az egyik tekton, amelyen a fonál növekszik
     * @param tekton2 a másik tekton, amelyen a fonál növekszik
     * @param gombatest a gombatest, amelyhez a fonal tartozik
     */
    public void fonalNovesztes(Tekton tekton1, Tekton tekton2, Gombatest gombatest) {
        if (gombatestek.isEmpty()) {
            System.out.println("Nincs gombatest, nem lehet fonalat növeszteni");
        } else if (fonalOsszekoti(tekton1, tekton2)) {
            System.out.println("Már van fonal a két tekton között");
        } else {
            if (tekton1.szomszedosTekton(tekton2) || tekton2.szomszedosTekton(tekton1)) {
                Random rand = new Random();
                boolean validId = false;
                int nextId = 0;
                while (!validId) {
                    nextId = rand.nextInt(100);
                    validId = true;
                    for (Gombafonal gombafonal : gombafonalak) {
                        if (gombafonal.getID() == nextId) {
                            validId = false;
                            break;
                        }
                    }
                }
                Gombafonal gombafonal = new Gombafonal(nextId, tekton1, tekton2, gombatest);
                addFonal(gombafonal);
            } else {
                System.out.println("A két tekton nem szomszédos, nem lehet fonalat növeszteni");
            }
        }
    }

    /**
     * Fonal növesztése két tekton közés, vagy egy darab tektonra (ekkor a másik tekton ugyan az mint az első)
     * @param tekton1 az egyik tekton, amelyen a fonál növekszik
     * @param tekton2 a másik tekton, amelyen a fonál növekszik
     * @param gombatest a gombatest, amelyhez a fonal tartozik
     * @param gombafonal a gombafonal, amelyet hozzáadunk
     */
    public void fonalNovesztes(Tekton tekton1, Tekton tekton2, Gombatest gombatest, Gombafonal gombafonal) {
        if (gombatestek.isEmpty()) {
            System.out.println("Nincs gombatest, nem lehet fonalat növeszteni");
        } else if (fonalOsszekoti(tekton1, tekton2)) {
            System.out.println("Már van fonal a két tekton között");
        } else {
            if (tekton1.szomszedosTekton(tekton2) || tekton2.szomszedosTekton(tekton1)) {
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
        parancsFeldolgozo.print("Gomba ("+ getID() +") gombatestek értéke megváltozott: -> Gombatest"+ gombafonal.getID() +"\n");
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
     * Gombatest hozzáadása
     * @return a gombatest
     */
    public void addGombatest(Gombatest gombatest) {
        gombatestek.add(gombatest);
        parancsFeldolgozo.print("Gomba ("+ getID() +") gombatestek értéke megváltozott: -> Gombatest"+ gombatest.getID() +"\n");
    }

    /**
     * Gombatest eltávolítása
     * @param gombatest az eltávolítandó gombatest
     */
    public void removeGombatest(Gombatest gombatest) {
        gombatestek.remove(gombatest);
        System.out.println("Gombatest eltávolítva");
    }

    /**
     * Visszaadja a gomba által növesztett gombafonalakat
     * @return a gombafonalak listája
     */
    public List<Gombafonal> getGombafonalak() {
        return gombafonalak;
    }

    /**
     * Visszaadja a gombatesteket
     * @return a gombatestek listája
     */
    public List<Gombatest> getGombatest() {
        return gombatestek;
    }

    public int getID() {
        return id;
    }
}
