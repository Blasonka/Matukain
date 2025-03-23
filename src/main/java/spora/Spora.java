package spora;

import rovar.Rovar;

/**
 * Spora osztály
 *
 * @class Spora
 *
 * @brief Spórákat reprezentáló osztály
 *
 * @details
 * Absztrakt osztály Sporák adatainak tárolására
 *
 * @see spora.VagasGatloSpora
 * @see spora.LassitoSpora
 * @see spora.GyorsitoSpora
 * @see spora.BenitoSpora
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
public abstract class Spora {
    /**
     * Spóra élettartamát nyilvántartó számláló
     * @var int szamlalo
     * @brief Élettartam
     */
    protected int szamlalo;
    /**
     * Spora osztály konstruktora
     * @param sz számláló értéke
     */
    public Spora(int sz) {
        szamlalo = sz;
        System.out.println(">Spora->Spora()");
    }
    /**
     * Spóra törlése
     */
    public void torles() {
        System.out.println(">Spora->torles()");
    }
    /**
     * Spóra hatásának kifejtése, absztrakt metódus
     * @param rovar a rovar akin a hatást kívánja kifejteni
     */
    public abstract boolean hatasKifejtes(Rovar rovar);
}
