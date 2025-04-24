package gomba;

import felhasznalo.Felhasznalo;
import felhasznalo.Gombasz;
import tekton.Tekton;

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
     * Létrehoz egy gombafonalat a megadott két tekton között
     *
     * @param hatar1 az egyik végpontja a gombafonalnak
     * @param hatar2 a másik végpontja a gombafonalnak
     * @param test   a gombatest, amiből kinőtt a gombafonal
     */
    public HusevoGombafonal(Tekton hatar1, Tekton hatar2, Gombatest test) {
        super(hatar1, hatar2, test);
    }
}
