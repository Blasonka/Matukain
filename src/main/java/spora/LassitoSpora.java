package spora;

import rovar.Rovar;
import interfaces.*;

import static tesztelo.Menu.parancsFeldolgozo;

/**
 * LassítóSpóra osztály
 *
 * @class LassitoSpora
 *
 * @brief LassítóSpórákat reprezentáló osztály
 *
 * @details
 * Osztály lassító hatású spórák tárolására
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
public class LassitoSpora extends Spora implements hatasKifejtes {
    /**
     * Lassító spóra osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public LassitoSpora(int sz, int id) {
        super(sz, "lassito", id);
    }

    /**
     * Spóra törlése
     */
    @Override
    public void torles() {}

    /**
     * Spóra hatásának kifejtése
     * @param r a rovar akin a hatást kívánja kifejteni
     */
    public void hatasKifejtes(Rovar r) {
        int regi = r.getSebesseg();
        if (!(r.getSebesseg() <= 0)) {
            r.setSebesseg(r.getSebesseg() - 1);
        }
    }
}
