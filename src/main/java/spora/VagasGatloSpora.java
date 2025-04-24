package spora;

import rovar.Rovar;
import interfészek.hatasKifejtes;

/**
 * VágásGátlóSpóra osztály
 *
 * @class VagasGatloSpora
 *
 * @brief VágásGátlóSpórákat reprezentáló osztály
 *
 * @details
 *  Osztály vágás gátló hatású spórák tárolására
 *  Őse az absztrak Spora osztály
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
public class VagasGatloSpora extends Spora implements hatasKifejtes {
    /**
     * VagasGatloSpora osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public VagasGatloSpora(int sz, String s) {
        super(sz, s);
        System.out.println("\t>VagasGatloSpora->VagasGatloSpora()");
    }
    /**
     * Spóra törlése
     */
    @Override
    public void torles() {
        System.out.println("\t\t>VagasGatloSpora->torles()");
    }

    /**
     * Spóra hatásának kifejtése
     * @param rovar a rovar akin a hatást kívánja kifejteni
     */
    @Override
    public void hatasKifejtes(Rovar rovar) {
        rovar.vaghate=false;
    }
}
