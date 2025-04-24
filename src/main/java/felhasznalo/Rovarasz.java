package felhasznalo;

import gomba.Gombafonal;
import jateklogika.gameLogic;
import rovar.Rovar;
import spora.Spora;
import tekton.Tekton;


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
 * @see felhasznalo.Felhasznalo
 * @see rovar.Rovar
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
    public Rovarasz(String name) {
        super(0, name, 6);
        rovarok = new ArrayList<>();
        System.out.println(">Rovarasz->Rovarasz()");
    }

    public List<Rovar> getRovarok() {
        return rovarok;
    }



    public void addRovar(Rovar r){
        rovarok.add(r);
    }
}
