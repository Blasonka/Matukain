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
public class LassitoSpora extends Spora implements HatasKifejtes {
    /**
     * Lassító spóra osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public LassitoSpora(int sz, HatasKifejtes h, String s) {
        super(sz,h,s );
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
    public void hatasKifejtes(Rovar rovar){
        rovar.sebesseg =1;
    }
}
