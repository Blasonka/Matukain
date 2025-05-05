package backend.spora;

import backend.gomba.Gomba;
import backend.interfaces.hatasKifejtes;
import backend.tekton.Tekton;
import backend.rovar.Rovar;

import static backend.tesztelo.Menu.parancsFeldolgozo;

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
 * @see Spora
 * @see Gomba
 * @see Tekton
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
    public LassitoSpora(int sz) {
        super(sz, "lassito" );
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
    public void hatasKifejtes(Rovar r) {
        int regi = r.getSebesseg();
        if (!(r.getSebesseg() <= 0)) {
            r.setSebesseg(r.getSebesseg() - 1);
            parancsFeldolgozo.print("A sebesseg megvaltozott: "+regi+" -> "+r.getSebesseg());
        }
    }
}
