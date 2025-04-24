package spora;

import rovar.Rovar;
import interfészek.*;

import java.util.Objects;

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
public class LassitoSpora extends Spora implements hatasKifejtes {
    /**
     * Lassító spóra osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public LassitoSpora(int sz, String s) {
        super(sz, s );
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
     * @param r a rovar akin a hatást kívánja kifejteni
     */
    public void hatasKifejtes(Rovar r){
        if (!(r.sebesseg<=0)) r.sebesseg-=1;
    }
}
