package gomba;

import felhasznalo.Felhasznalo;
import felhasznalo.Gombasz;
import rovar.Rovar;
import tekton.Tekton;

import static tesztelo.Menu.parancsFeldolgozo;

/**
 * HusevoGombafonal osztály
 *
 * @class HusevoGombafonal
 *
 * @brief A húsevő gombafonalakat reprezentáló osztály
 *
 * @details
 * Ez az osztály felelős a húsevő gombafonalak reprezentálásáért, azokkal kapcsolatos műveletek végzéséért.
 * Az osztály tartalmazza, hogy a gombafonalak evett-e rovart már valamint a rovara megevéséért.
 *
 * @see felhasznalo.Gombasz
 *
 * @note Prototípus állapotban van.
 *
 * @author Blasek
 * @version 2.0
 * @date 2025-04-24
 */
public class HusevoGombafonal extends Gombafonal {

    /**
     * A gombafonal evett-e már rovart
     * @var boolean evettRovart
     * @brief A gombafonal evett-e már rovart állapotát tároló változó.
     */
    private boolean evettRovart;

    /**
     * Létrehoz egy húsevő gombafonalat a megadott két tekton között
     *
     * @param id
     * @param hatar1 az egyik végpontja a gombafonalnak
     * @param hatar2 a másik végpontja a gombafonalnak
     * @param test   a gombatest, amiből kinőtt a gombafonal
     */
    public HusevoGombafonal(int id, Tekton hatar1, Tekton hatar2, Gombatest test) {
        super(id, hatar1, hatar2, test);
        this.evettRovart = false;
    }

    /**
     * Megadja, hogy a gombafonal evett-e már rovart
     *
     * @return true, ha evett már rovart, false, ha nem
     */
    public boolean isEvettRovart() { return evettRovart; }

    /**
     * Metódus egy rovar megevéséhez
     *
     * @param rovar a megenni kívánt rovar
     * @return true, ha a gombafonal megeszi a rovart, false, ha sikertelen
     */
    public boolean megesziRovart(Rovar rovar) {
        evettRovart = true;
        parancsFeldolgozo.print("Fonal (" + getID() + ") elfogyasztotta Rovar (" + rovar.getID()+ ")\n");
        parancsFeldolgozo.print("Fonal (" + getID() + ") evettRovart értéke megváltozott: " + (!evettRovart ? "true" : "false") + " -> " + (evettRovart ? "true" : "false") + "\n");
        return false;
    }
}
