package spora;

import rovar.Rovar;
import interfészek.*;
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
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author todortoth
 * @version 1.0
 * @version 1.1 - comment Update
 * @date 2025-03-22
 */
    public class GyorsitoSpora extends Spora implements hatasKifejtes {
    /**
     * Gyorsító spóra osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public GyorsitoSpora(int sz, String s) {
        super(sz, s);
        System.out.println("\t>GyorsitoSpora->GyorsitoSpora()");
    }

    /**
     * Spóra törlése
     */
    public void torles() {
        System.out.println("\t\t>GyorsitoSpora->torles()");
    }

    /**
     * Spóra hatásának kifejtése
     * @param rovar a rovar akin a hatást kívánja kifejteni
     */
    @Override
    public void hatasKifejtes(Rovar rovar) {
        if(rovar.sebesseg)rovar.sebesseg=+1;
    }
}
