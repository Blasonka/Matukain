package backend.felhasznalo;

import backend.gomba.Gombafonal;
import backend.rovar.Rovar;
import backend.spora.Spora;
import backend.tekton.Tekton;

import java.util.ArrayList;
import java.util.List;

/**
 * Rovarász osztály
 *
 * @class Rovarasz
 *
 * @brief A rovarászokat reprezentáló osztály
 *
 * @details
 *  Rovarasz osztály, mely a felhasználó osztályból származik.
 *  Az osztály feladata a rovarász játékos rovarjainak tárolása, azokkal műveletek végzése.
 *
 * @see Felhasznalo
 * @see Rovar
 *
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author todortoth
 * @version 1.0
 * @version 1.1 - comment Update
 * @date 2025-03-22
 */
public class Rovarasz extends Felhasznalo {
    /**
     * Rovarász rovarait tároló lista
     * @var List<Rovar> rovarak
     * @brief rovarak listája
      */
    List<Rovar> rovarok;

    /**
     * Rovarasz konstruktora
     * @param name - a felhasználó neve
     */
    public Rovarasz(int id, String name) {
        super(id, name, 6);
        rovarok = new ArrayList<>();
    }

    /**
     * Visszaadja a rovarász rovarait
     * @return rovarok - a rovarász rovarait tartalmazó lista
     */
    public List<Rovar> getRovarok() {
        return rovarok;
    }

    /**
     * Hozzáad egy rovar objektumot a rovarok listájához
     * @param r - a hozzáadni kívánt rovar
     */
    public Rovar addRovar(Rovar r){
        rovarok.add(r);
        return r;
    }

    /**
     * Rovar mozgatása egy másik tektonra
     * @param rovar - a rovar, amelyet mozgatni szeretnénk
     * @param celTekton - a cél tekton, ahova a rovart szeretnénk mozgatni
     */
    public void rovarMozgat(Rovar rovar, Tekton celTekton) {
        if (hatralevoAkciopont > rovar.getSebesseg() && rovar.getSebesseg()!=0) {
            rovar.attesz(celTekton);
            hatralevoAkciopont -= (4-rovar.getSebesseg());
        }
    }

    /**
     * Rovar elvág egy gombafonalat
     * @param rovar - a rovar, amelyik elvágja a gombafonalat
     * @param fonal - a gombafonal, amelyet elvágnak
     */
    public void rovarFonalatVag(Rovar rovar, Gombafonal fonal){
        if (hatralevoAkciopont > 0 && rovar.getSebesseg() != 0 && rovar.isVaghate()) {
            rovar.fonalElvagas(fonal);
            hatralevoAkciopont -= (4-rovar.getSebesseg());
        }
    }

    /**
     * Rovar elfogyaszt egy spórát
     * @param rovar - a rovar, amelyik elfogyasztja a spórát
     * @param spora - a spóra, amelyet elfogyasztanak
     */
    public void rovarSporatFogyaszt(Rovar rovar, Spora spora){
        if (hatralevoAkciopont > 0 && rovar.getSebesseg() != 0) {
            rovar.elfogyaszt(spora);
            hatralevoAkciopont -= (4-rovar.getSebesseg());
        }
    }
}
