package spora;

import interfaces.hatasKifejtes;
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
public abstract class Spora implements hatasKifejtes {
    /**
     * Spóra élettartamát nyilvántartó számláló
     * @var int szamlalo
     * @brief Élettartam
     */
    protected int szamlalo;
    protected String nev;

    /**
     * Spora osztály konstruktora
     * @param sz számláló értéke
     */
    public Spora(int sz, String s) {
        szamlalo = sz;
        nev = s;
        System.out.println(">Spora->Spora()");
    }
    /**
     * Spóra törlése
     */
    public abstract void torles();
    public void hatasKifejtes(Object obj) {
        if (obj instanceof Rovar) {
            hatasKifejtes((Rovar) obj);
        } else {
            throw new IllegalArgumentException("Invalid object type. Expected Rovar.");
        }
    }
    public abstract void hatasKifejtes(Rovar target);
    public int getSzamlalo() {
        return szamlalo;
    }

    public int getID() {return 0;}
}
