package backend.tekton;

import backend.gomba.Gomba;

/**
 * GombatestNelkuliTekton osztály
 *
 * @class GombatestNelkuliTekton
 *
 * @brief A GombatestNelkuliTekton reprezentáló osztály
 *
 * @details
 * Osztály olyan Tektonok adatainak tárolására, melyeken nem nőhet gombatest
 *
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author Monostori
 * @version 1.0
 * @date 2025-03-21
 */
public class GombatestNelkuliTekton extends Tekton {
    /**
     * GombatestNelkulTekton osztály konstruktora
     * @param id beállítja az id értékét
     * @param koordinataX beállítja az X koordináta értékét
     * @param koordinataY beállítja az Y koordináta értékét
     */
    public GombatestNelkuliTekton(int id, int koordinataX, int koordinataY){
        super(id, koordinataX, koordinataY);
    }

    /**
     * Kifejti a Tekton hatását, azaz nem nőhet rajta gombatest
     * @param gomba a gomba, amelyiken a hatást kifejti
     */
    @Override
    public void hatasKifejtes(Gomba gomba){
        if (gomba.getGombatest().size() != 0){
            gomba.getGombatest().clear();
        }
        //parancsFeldolgozo.print("meghivodott a GombatestNelkuliTekton hatasKifejtes metódusa");
    }
    @Override
    public Tekton klonoz(int ujID, int ujX, int ujY) {
        return new GombatestNelkuliTekton(ujID, ujX, ujY);
    }
}