package tekton;

import gomba.Gomba;

/**
 * TobbFonalTekton osztály
 *
 * @class TobbFonalTekton
 *
 * @brief A TobbFonalTektonokat reprezentáló osztály
 *
 * @details
 * Osztály olyan Tektonok adatainak tárolására, melyeken több gomba fonalai is nőhetnek
 *
 * @note Prototípus állapotban van, a grafikus részek nincsenek implementálva.
 *
 * @author Monostori
 * @version 2.0
 * @date 2025-04-27
 */
public class TobbFonalTekton extends Tekton{
    /**
     * TobbFonalTekton osztály konstruktora
     * @param id beállítja az id értékét
     * @param koordinataX beállítja az X koordináta értékét
     * @param koordinataY beállítja az Y koordináta értékét
     */
    public TobbFonalTekton(int id, int koordinataX, int koordinataY){
        super(id, koordinataX, koordinataY);
    }

    /**
     * Kifejti a Tekton hatását, azaz több gomba fonalai is nőhetnek rajta
     * @param gomba a gomba, amelyiken a hatást kifejti
     */
    @Override
    public void hatasKifejtes(Gomba gomba){
    }

    @Override
    public Tekton klonoz(int ujID, int ujX, int ujY) {
        return new TobbFonalTekton(ujID, ujX, ujY);
    }
}