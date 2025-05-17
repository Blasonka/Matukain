package backend.gomba;

import backend.felhasznalo.Gombasz;
import backend.spora.*;
import backend.tekton.Tekton;

import java.util.Random;


/**
 * Gombatest osztály
 *
 * @class Gombatest
 *
 * @brief A gombatesteket reprezentáló osztály
 *
 * @details
 * Ez az osztály felelős a gombatestek reprezentálásáért, azokkal kapcsolatos műveletek végzéséért.
 * Az osztály tartalmazza a tektont ahol helyezkedik a test, a gombatestekből kilőhető spórák számát, az eddig kilőtt spórák számát, valamint a gombatestek fejlettségét.
 *
 * @see Gombasz
 *
 * @note Prototípus állapotban van.
 *
 * @author Blasek
 * @version 2.0
 * @date 2025-04-24
 */
public class Gombatest {

    /**
     * A gombatest azonosítója
     * @var int id
     * @brief A gombatest azonosítóját tároló változó.
     */
    private int id;

    /**
     * A gombatesthez tartozó tekton
     * @var Tekton tekton
     * @brief A gombatesthez tartozó tekton.
     */
    private Tekton tekton;

    /**
     * A gombatestből maximálisan kilőhető spórák maximális száma
     * @var int maxSporaKiloves
     * @brief A gombatestből kilőhető spórák maximális számát tároló változó.
     */
    private int maxSporaKiloves;

    /**
     * A gombatestből eddig kilőtt spórák száma
     * @var int eddigiSzorasok
     * @brief A gombatestből eddig kilőtt spórák számát tároló változó.
     */
    private int eddigiSzorasok;
    /**
     * A gombatestből jelen pillanatban kilőhető spórák száma
     * @var int kilohetoSporakSzama
     * @brief A gombatestből jelenleg kilőhető spórák számát tároló változó.
     */
    private int kilohetoSporakSzama;

    /**
     * A gombatest fejlettsége
     * @var boolean fejlett
     * @brief A gombatest fejlettségét tároló változó.
     */
    private boolean fejlett;

    /**
     * Gombatest konstruktora
     */
    public Gombatest(int id, Tekton t) {
        this.id = id;
        this.tekton = t;
        this.maxSporaKiloves = 6;
        this.eddigiSzorasok = 0;
        this.kilohetoSporakSzama = 3;
        this.fejlett = false;
    }

    /**
     * Gombatest konstruktora
     */
    public Gombatest(int id, Tekton t, boolean fejlett) {
        this.id = id;
        this.tekton = t;
        this.maxSporaKiloves = 6;
        this.eddigiSzorasok = 0;
        this.kilohetoSporakSzama = 3;
        this.fejlett = fejlett;
    }

    /**
     * Visszaadja a gombatest azonosítóját
     * @return a gombatest azonosítója
     */
    public int getID() {return id;}

    /**
     * Visszaadja a gombatesthez tartozó tekton
     * @return a gombatesthez tartozó tekton
     */
    public Tekton getTekton() { return tekton; }

    /**
     * Visszaadja a gombatestből maximálisan kilőhető spórák számát
     * @return a gombatestből maximálisan kilőhető spórák száma
     */
    public int getMaxSporaKiloves() { return maxSporaKiloves; }

    /**
     * Visszaadja a gombatestből eddig kilőtt spórák számát
     * @return a gombatestből eddig kilőtt spórák száma
     */
    public int getEddigiSzorasok() { return eddigiSzorasok; }

    /**
     * Visszaadja a gombatestből jelenleg kihelyezhető spórák számát
     * @return a gombatestből jelenleg kihelyezhető spórák száma
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
     * Visszaadja, hogy a gombatest tud-e még spórát szórni
     * @return true, ha még tud spórát szórni, false egyébként
     */
    public boolean tudSporatSzorni() {
        return kilohetoSporakSzama > 0 && eddigiSzorasok < maxSporaKiloves;
    }

    /**
     * Spóra elhelyezése
     * @param tekton a tekton, amin elhelyezzük az új spórát
     */
    public void sporaSzoras(Tekton tekton) {
        Random rand = new Random();
        Spora spora = null;
        int randomSpora = rand.nextInt(4);
        switch (randomSpora) {
            case 0:
                spora = new BenitoSpora(rand.nextInt(1,4));
                break;
            case 1:
                spora = new GyorsitoSpora(rand.nextInt(1,4));
                break;
            case 2:
                spora = new LassitoSpora(rand.nextInt(1,4));
                break;
            case 3:
                spora = new VagasGatloSpora(rand.nextInt(1,4));
                break;
            default:
                break;
        }
        kilohetoSporakSzama--;
        eddigiSzorasok++;
        tekton.addSpora(spora);
    }

    public void sporaSzoras(Tekton tekton, Spora spora) {
        kilohetoSporakSzama--;
        eddigiSzorasok++;
        tekton.addSpora(spora);
    }
}
