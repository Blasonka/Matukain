package backend.tekton;

import backend.gomba.Gomba;

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
    }

    /**
     * Kifejti a Tekton hatását, azaz maximum egy gomba fonal nőhet rajta
     */
    @Override
    public void hatasKifejtes(Gomba gomba){
        if (gomba.getGombafonalak().size()==0){
            System.out.println("A gomba növeszthet egy fonalat");
        }
        else{
            for (int i=1; i<gomba.getGombafonalak().size(); i++){
                gomba.removeFonal(gomba.getGombafonalak().get(i));
            }
            System.out.println("A gomba nem növeszthet fonalat");
        }
    }

    @Override
    public Tekton klonoz(int ujID, int ujX, int ujY) {
        return new MaxEgyFonalTekton(ujID, ujX, ujY);
    }
}