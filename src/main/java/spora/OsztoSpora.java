package spora;

import rovar.Rovar;
import tekton.Tekton;
import interfaces.*;

import java.util.Random;

import static tesztelo.Menu.parancsFeldolgozo;

/**
 * OsztóSpóra osztály
 *
 * @class OsztoSpora
 *
 * @brief OsztóSpórákat reprezentáló osztály
 *
 * @details
 * Osztály osztó hatású spórák tárolására
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

public class OsztoSpora extends Spora implements hatasKifejtes {
    /**
     * Osztó spóra osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public OsztoSpora(int sz, int id) {
        super(sz, "oszto", id);
    }

    /**
     * Spóra hatásának kifejtése
     * @param rovar a rovar akin a hatást kívánja kifejteni
     */
    public void hatasKifejtes(Rovar rovar){
       Tekton jelenlegi = rovar.getTekton();
        Random r = new Random();
        Rovar ujRovar = new Rovar(jelenlegi, r.nextInt(1000));

        parancsFeldolgozo.print("Spóra (" + getID() + ") hatására Rovar (" + rovar.getID() + ") osztódott: Rovar (" + rovar.getID() + ") + Rovar (" + (rovar.getID() + 1) + ")\n");
    }

    /**
     * Spóra törlése
     */
    public void torles(){

    }

}
