package spora;

import rovar.Rovar;
import interfaces.*;

import static tesztelo.Menu.parancsFeldolgozo;

/**
 * GyorsítóSpóra osztály
 *
 * @class GyorsitoSpora
 *
 * @brief GyorsítóSpórákat reprezentáló osztály
 *
 * @details
 * Osztály gyorsító hatású spóráktárolására
 * Őse az absztrak Spora osztály
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
    public class GyorsitoSpora extends Spora implements hatasKifejtes {
    /**
     * Gyorsító spóra osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public GyorsitoSpora(int sz, int id) {
        super(sz, "gyorsito", id);
    }

    /**
     * Spóra törlése
     */
    public void torles() {}

    /**
     * Spóra hatásának kifejtése
     * @param rovar a rovar akin a hatást kívánja kifejteni
     */
    @Override
    public void hatasKifejtes(Rovar rovar) {
        int regi = rovar.getSebesseg();
        if (rovar.getSebesseg() < 3) {
            rovar.setSebesseg(rovar.getSebesseg() + 1);
        }
    }
}
