package spora;

import rovar.Rovar;
import interfaces.*;

import static tesztelo.Menu.parancsFeldolgozo;

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
 * @see spora.Spora
 * @see gomba.Gomba
 * @see tekton.Tekton
 *
 * @note Prototípus állapotban van.
 *
 * @author todortoth
 * @version 1.0
 * @version 1.1 - comment Update
 * @date 2025-03-22
 * @author Bence338
 * @version 2.0 - Prototípus
 * @date 2025-04-22
 */
public class BenitoSpora extends Spora implements hatasKifejtes {
    /**
     * BenitoSpora osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public BenitoSpora(int sz, int id) {
        super(sz,"benito", id);
    }

    /**
     * Spóra törlése
     */
    public void torles() {}
    /**
     * Spóra hatásának kifejtése
     * @param rovar a rovar akin a hatást kívánja kifejteni
     */

    public void hatasKifejtes(Rovar rovar) {
        int regi= rovar.getSebesseg();
        rovar.setSebesseg(0);
    }
}
