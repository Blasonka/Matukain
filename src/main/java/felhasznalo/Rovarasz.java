package felhasznalo;

import gomba.Gombafonal;
import jateklogika.gameLogic;
import rovar.Rovar;
import spora.Spora;
import tekton.Tekton;

import static tesztelo.Menu.parancsFeldolgozo;

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


    // Bocsi hogy belenyúltam csak akartam tesztelni - Tódor
    public void addRovar(Rovar r){
        parancsFeldolgozo.print("Rovarász (" + ID + ") rovarok paraméterének érétke megváltozott: ");
        for (int i = 0; i < rovarok.size(); i++) parancsFeldolgozo.print("Rovar (" + rovarok.get(i).getID() + ")" + (i == rovarok.size() - 1 ? "" : ", "));
        parancsFeldolgozo.print(" -> ");
        rovarok.add(r);
        for (int i = 0; i < rovarok.size(); i++) parancsFeldolgozo.print("Rovar (" + rovarok.get(i).getID() + ")" + (i == rovarok.size() - 1 ? "" : ", "));
    }
}
