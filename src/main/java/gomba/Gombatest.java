package gomba;

import spora.*;
import tekton.Tekton;

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
 * Az osztály tartalmazza a gombatestek élettartamát, a gombatestekből kilőhető spórák számát, valamint a gombatestek fejlettségét.
 *
 * @see felhasznalo.Gombasz
 *
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author Blasek
 * @version 1.0
 * @date 2025-03-22
 */
public class Gombatest {

    private Tekton tekton;
    private int maxSporaKiloves;
    private int eddigiSzorasok;
    /**
     * A gombatestből kilőhető spórák száma
     * @var int kilohetoSporakSzama
     * @brief A gombatestből kilőhető spórák számát tároló változó.
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
    public Gombatest(Tekton t) {
        this.tekton = t;
        this.maxSporaKiloves = 6;
        this.eddigiSzorasok = 0;
        this.kilohetoSporakSzama = 3;
        this.fejlett = false;
    }


    /**
     * Visszaadja a gombatestből kihelyezhető spórák számát
     * @return a gombatestből kihelyezhető spórák száma
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
        tekton.addSpora(spora);
    }
}
