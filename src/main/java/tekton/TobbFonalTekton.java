package tekton;

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
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author Monostori
 * @version 1.0
 * @date 2025-03-21
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
        System.out.println("Létrejött egy TobbFonalTekton");
    }

    /**
     * Kifejti a Tekton hatását, azaz több gomba fonalai is nőhetnek rajta
     * @param gomba a gomba, amelyiken a hatást kifejti
     */
    @Override
    public void hatasKifejtes(Gomba gomba){
        System.out.println("TobbFonalTekton hatás kifejtve, azaz több gomba fonalai is nőhetnek rajta");
    }
}