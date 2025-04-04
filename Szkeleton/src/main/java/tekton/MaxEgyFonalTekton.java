package tekton;

import gomba.Gomba;

/**
 * MaxEgyFonalTekton osztály
 *
 * @class MaxEgyFonalTekton
 *
 * @brief A MaxEgyFonalTekton reprezentáló osztály
 *
 * @details
 * Osztály olyan Tektonok adatainak tárolására, melyeken maximum egy fonal nőhet
 *
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author Monostori
 * @version 1.0
 * @date 2025-03-21
 */
public class MaxEgyFonalTekton extends Tekton{
    /**
     * MaxEgyFonalTekton osztály konstruktora
     * @param id beállítja az id értékét
     * @param koordinataX beállítja az X koordináta értékét
     * @param koordinataY beállítja az Y koordináta értékét
     */
    public MaxEgyFonalTekton(int id, int koordinataX, int koordinataY){
        super(id, koordinataX, koordinataY);
        System.out.println("Létrejött egy MaxEgyFonalTekton");
    }

    /**
     * Kifejti a Tekton hatását, azaz maximum egy gomba fonal nőhet rajta
     */
    @Override
    public void hatasKifejtes(Gomba gomba){
        if (gomba.getGombafonalak().size()==0){
            System.out.println("A gomba növeszthet egy fonalat");
        }
        System.out.println("A gomba nem növeszthet fonalat");
    }
}