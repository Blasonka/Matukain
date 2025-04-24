package tekton;

import gomba.Gomba;

/**
 * FonalMegtartoTekton osztály
 *
 * @class FonalMegtartoTekton
 *
 * @brief A FonalMegtartoTekton reprezentáló osztály
 *
 * @details
 * Osztály olyan Tektonok adatainak tárolására, amelyik életben tartják azokat a fonalakat,
 * amelyek nincsenek közvetve vagy közvetlenül gombatesthez kötve
 *
 * @note Prototípus állapotban van, a metódusok már implementálva vannak.
 *
 * @author Monostori
 * @version 1.0
 * @date 2025-04-21
 */
public class FonalMegtartoTekton extends Tekton {
    /**
     * FonalMegtartoTekton osztály konstruktora
     * @param id beállítja az id értékét
     * @param koordinataX beállítja az X koordináta értékét
     * @param koordinataY beállítja az Y koordináta értékét
     */
    public FonalMegtartoTekton(int id, int koordinataX, int koordinataY){
        super(id, koordinataX, koordinataY);
        System.out.println("Létrejött egy FonalMegtartoTekton");
    }

    /**
     * Kifejti a Tekton hatását, azaz a gombafonal akkor is megmarad, ha nincs gombatesthez kötve
     * @param gomba a gomba, amelyiken a hatást kifejti
     */
    @Override
    public void hatasKifejtes(Gomba gomba){
        System.out.println("FonalMegtartoTekton hatás kifejtve, azaz a gombafonal akkor is megmarad, ha nincs gombatesthez kötve");
    }
    @Override
    public Tekton klonoz(int ujID, int ujX, int ujY) {
        return new FonalMegtartoTekton(ujID, ujX, ujY);
    }
}