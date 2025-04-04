package spora;

import rovar.Rovar;

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
public class VagasGatloSpora extends Spora {
    /**
     * VagasGatloSpora osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public VagasGatloSpora(int sz) {
        super(sz);
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
        System.out.println("\t\t>VagasGatloSpora->hatasKifejtes()");
    }
}
