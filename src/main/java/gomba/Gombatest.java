package gomba;

import spora.*;
import tekton.Tekton;

import java.util.Random;

import static tesztelo.Menu.parancsFeldolgozo;

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
 * @see felhasznalo.Gombasz
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
    public void sporaSzoras(Tekton tekton, char type, int id) {
        if (!tekton.szomszedosTekton(getTekton())) {
            switch (type) {
                case 'O':
                    parancsFeldolgozo.print("Spóra (" + id + ") Osztó NEM elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + getID() + ") által\n");
                    break;
                case 'L':
                    parancsFeldolgozo.print("Spóra (" + id + ") Lassító NEM elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + getID() + ") által\n");
                    break;
                case 'G':
                    parancsFeldolgozo.print("Spóra (" + id + ") Gyorsító NEM elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + getID() + ") által\n");
                    break;
                case 'B':
                    parancsFeldolgozo.print("Spóra (" + id + ") Bénító NEM elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + getID() + ") által\n");
                    break;
                case 'V':
                    parancsFeldolgozo.print("Spóra (" + id + ") Vágásgátló NEM elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + getID() + ") által\n");
                    break;
            } return;
        }
        Spora spora = null;
        switch (type) {
            case 'O':
                spora = new OsztoSpora(1, id);
                parancsFeldolgozo.print("Spóra (" + id + ") Osztó elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + getID() + ") által\n");
                break;
            case 'L':
                spora = new LassitoSpora(1, id);
                parancsFeldolgozo.print("Spóra (" + id + ") Lassító elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + getID() + ") által\n");
                break;
            case 'G':
                spora = new GyorsitoSpora(1, id);
                parancsFeldolgozo.print("Spóra (" + id + ") Gyorsító elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + getID() + ") által\n");
                break;
            case 'B':
                spora = new BenitoSpora(1, id);
                parancsFeldolgozo.print("Spóra (" + id + ") Bénító elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + getID() + ") által\n");
                break;
            case 'V':
                spora = new VagasGatloSpora(1, id);
                parancsFeldolgozo.print("Spóra (" + id + ") Vágásgátló elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + getID() + ") által\n");
                break;
            default : {
                Random rand = new Random();
                int randomSpora = rand.nextInt(4);
                switch (randomSpora) {
                    case 0:
                        spora = new BenitoSpora(rand.nextInt(1, 4), id);
                        parancsFeldolgozo.print("Spóra (" + id + ") Bénító elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + id + ") által\n");
                        break;
                    case 1:
                        spora = new GyorsitoSpora(rand.nextInt(1, 4), id);
                        parancsFeldolgozo.print("Spóra (" + id + ") Gyorsító elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + id + ") által\n");
                        break;
                    case 2:
                        spora = new LassitoSpora(rand.nextInt(1, 4), id);
                        parancsFeldolgozo.print("Spóra (" + id + ") Lassító elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + id + ") által\n");
                        break;
                    case 3:
                        spora = new VagasGatloSpora(rand.nextInt(1, 4), id);
                        parancsFeldolgozo.print("Spóra (" + id + ") Vágásgátló elhelyezve Tekton (" + tekton.getID() + ") Gombatest (" + id + ") által\n");
                        break;
                    default:
                        break;
                }
            }
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
