package spora;

import rovar.Rovar;
import tekton.Tekton;

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
public abstract class Spora implements HatasKifejtes {
    /**
     * Spóra élettartamát nyilvántartó számláló
     * @var int szamlalo
     * @brief Élettartam
     */
    protected int szamlalo;
    protected String nev;
    private HatasKifejtes effekt;
    /**
     * Spora osztály konstruktora
     * @param sz számláló értéke
     */
    public Spora(int sz, HatasKifejtes h, String s) {
        szamlalo = sz;
        effekt=h;
        nev =s;
        System.out.println(">Spora->Spora()");
    }
    /**
     * Spóra törlése
     */
    public void torles() {
       
    }
    
    
}
