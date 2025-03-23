package spora;

import rovar.Rovar;

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
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author todortoth
 * @version 1.0
 * @version 1.1 - comment Update
 * @date 2025-03-22
 */
public class LassitoSpora extends Spora {
    /**
     * Lassító spóra osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public LassitoSpora(int sz) {
        super(sz);
        System.out.println("\t>LassitoSpora->LassitoSpora()");
    }

    /**
     * Spóra törlése
     */
    @Override
    public void torles() {
        System.out.println("\t\t>LassitoSpora->torles()");
    }

    /**
     * Spóra hatásának kifejtése
     * @param rovar a rovar akin a hatást kívánja kifejteni
     */
    @Override
    public boolean hatasKifejtes(Rovar rovar) {
        System.out.println("\t\t>LassitoSpora->hatasKifejtes()");
        return false;
    }
}
