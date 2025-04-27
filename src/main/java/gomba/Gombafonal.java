package gomba;

import tekton.Tekton;

import java.util.Random;

/**
 * Gombafonal osztály
 *
 * @class Gombafonal
 *
 * @brief A gombafonalakat reprezentáló osztály
 *
 * @details
 * Ez az osztály felelős a gombafonalak reprezentálásáért, azokkal kapcsolatos műveletek végzéséért.
 * Az osztály tartalmazza a gombafonalak végpontjait, a gombatestet amiből kinőttek, hogy el lett-e rágva egy rovar által valamint a gombafonalakhoz kapcsolódó műveleteket.
 *
 * @see felhasznalo.Gombasz
 *
 * @note Prototípus állapotban van.
 *
 * @author Blasek
 * @version 2.0
 * @date 2025-04-24
 */
public class Gombafonal {

    private int id;
    /**
     * A gombafonal elragva állapota
     * @var boolean elragva
     * @brief A gombafonal elragva állapotát tároló változó.
     */
    private boolean elragva;

    /**
     * A gombafonal pusztulásának számlálója
     * @var int pusztulasSzamlalo
     * @brief A gombafonal pusztulásának számlálóját tároló változó (hány körig van még életben).
     */
    private int pusztulasSzamlalo;

    /**
     * A gombafonalhoz tartozó gombatest
     * @var Gombatest test
     * @brief A gombafonalhoz tartozó gombatest.
     */
    private Gombatest test;

    /**
     * A gombafonal egyik végpontja
     * @var Tekton hatar1
     */
    private Tekton hatar1;

    /**
     * A gombafonal másik végpontja
     * @var Tekton hatar2
     */
    private Tekton hatar2;

    /**
     * Létrehoz egy gombafonalat a megadott két tekton között
     * @param hatar1 az egyik végpontja a gombafonalnak
     * @param hatar2 a másik végpontja a gombafonalnak
     * @param test a gombatest, amiből kinőtt a gombafonal
     */
    public Gombafonal(int id, Tekton hatar1, Tekton hatar2, Gombatest test) {
        Random rand = new Random();
        this.id = id;
        this.elragva = false;
        this.pusztulasSzamlalo = rand.nextInt(1,3);
        this.test = test;
        this.hatar1 = hatar1;
        this.hatar2 = hatar2;
    }

    /**
     * Visszaadja a gombafonal azonosítóját
     * @return a gombafonal azonosítója
     */
    public int getID() { return id; }

    /**
     * Visszaadja a gombafonal elragva állapotát
     * @return true, ha elragva, false egyébként
     */
    public boolean getElragva() {
        return elragva;
    }

    /**
     * Visszaadja a gombafonal pusztulásának számlálóját
     * @return a gombafonal pusztulásának számlálója
     */
    public int getPusztulasSzamlalo() {
        return pusztulasSzamlalo;
    }

    /**
     * Visszaadja a gombafonalhoz tartozó gombatestet
     * @return a gombafonalhoz tartozó gombatest
     */
    public Gombatest getTest() {
        return test;
    }

    /**
     * Visszaadja a gombafonal egyik végpontját
     * @return a gombafonal egyik végpontja
     */
    public Tekton getHatar1() {
        return hatar1;
    }

    /**
     * Visszaadja a gombafonal másik végpontját
     * @return a gombafonal másik végpontja
     */
    public Tekton getHatar2() {
        return hatar2;
    }

    public boolean eltartva() {
        return test != null;
    }

    /**
     * Beállítja a gombafonal elragva állapotát elrágvára.
     * @brief A gombafonal elragva állapotának beállítása (igazra).
     */
    public void elragas() {
        elragva = true;
    }


    public void setPusztulasSzamlalo(int ertek) {
        this.pusztulasSzamlalo = ertek;
    }

    public boolean csokkentPusztulasSzamlalo() {
        this.pusztulasSzamlalo--;
        return this.pusztulasSzamlalo <= 0;
    }

    public Tekton getHatar() {
        return hatar1;
    }
    public Tekton getMasikHatar() {
        return hatar2;
    }
}
