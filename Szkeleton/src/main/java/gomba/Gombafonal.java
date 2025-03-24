package gomba;

import tekton.Tekton;

/**
 * Gombafonal osztály
 *
 * @class Gombafonal
 *
 * @brief A gombafonalakat reprezentáló osztály
 *
 * @details
 * Ez az osztály felelős a gombafonalak reprezentálásáért, azokkal kapcsolatos műveletek végzéséért.
 * Az osztály tartalmazza a gombafonalak végpontjait, valamint a gombafonalakhoz kapcsolódó műveleteket.
 *
 * @see felhasznalo.Gombasz
 *
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author Blasek
 * @version 1.0
 * @date 2025-03-22
 */
public class Gombafonal {
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
     */
    public Gombafonal(Tekton hatar1, Tekton hatar2) {
        this.hatar1 = hatar1;
        this.hatar2 = hatar2;
        System.out.println("Gombafonal létrejött");
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

}
