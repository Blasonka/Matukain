package backend.spora;

import backend.gomba.Gomba;
import backend.interfaces.hatasKifejtes;
import backend.tekton.Tekton;
import backend.rovar.Rovar;

/**
 * BénítóSpóra osztály
 *
 * @class BenitoSpora
 *
 * @brief BénítóSpórákat reprezentáló osztály
 *
 * @details
 *  Osztály béníto hatású spórák tárolására
 *  Őse az absztrak Spora osztály
 *
 * @see Spora
 * @see Gomba
 * @see Tekton
 *
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author todortoth
 * @version 1.0
 * @version 1.1 - comment Update
 * @date 2025-03-22
 */
public class BenitoSpora extends Spora implements hatasKifejtes {
    /**
     * BenitoSpora osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public BenitoSpora(int sz) {
        super(sz,"benito");
    }

    /**
     * Spóra törlése
     */
    public void torles() {
    }
    /**
     * Spóra hatásának kifejtése
     * @param rovar a rovar akin a hatást kívánja kifejteni
     */

    public void hatasKifejtes(Rovar rovar) {
        int regi= rovar.getSebesseg();
        rovar.setSebesseg(0);
    }
}
