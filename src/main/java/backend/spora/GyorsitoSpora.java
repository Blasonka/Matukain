package backend.spora;

import backend.gomba.Gomba;
import backend.interfaces.hatasKifejtes;
import backend.tekton.Tekton;
import backend.rovar.Rovar;

import static backend.tesztelo.Menu.parancsFeldolgozo;

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
    public class GyorsitoSpora extends Spora implements hatasKifejtes {
    /**
     * Gyorsító spóra osztály konstruktora
     * @param sz beállítja a számláló értékét
     */
    public GyorsitoSpora(int sz) {
        super(sz, "gyorsito");
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
        int regi = rovar.getSebesseg();
        if (rovar.getSebesseg() < 3) {
            rovar.setSebesseg(rovar.getSebesseg() + 1);
            parancsFeldolgozo.print("A sebesseg megvaltozott: "+regi+" -> "+rovar.getSebesseg());
        }
    }
}
