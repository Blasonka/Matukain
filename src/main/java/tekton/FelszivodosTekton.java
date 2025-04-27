package tekton;

import gomba.Gomba;
import gomba.Gombafonal;

import static tesztelo.Menu.parancsFeldolgozo;

/**
 * FelszivodosTekton osztály
 *
 * @class FelszivodosTekton
 *
 * @brief A FelszivodosTekton reprezentáló osztály
 *
 * @details
 * Osztály olyan Tektonok adatainak tárolására, melyeken adott idő után felszívódnak a gombafonalak
 *
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author Monostori
 * @version 1.0
 * @date 2025-03-21
 */
public class FelszivodosTekton extends Tekton{
    /**
     * @var int szamlalo
     * @brief Számláló, mely alapján meghatározható, hogy mikor szívódjon fel a gombafonal
     */
    private int szamlalo;

    /**
     * FelszivodosTekton osztály konstruktora, a számlálót 0-ra állítja
     * @param id beállítja az id értékét
     * @param koordinataX beállítja az X koordináta értékét
     * @param koordinataY beállítja az Y koordináta értékét
     */
    public FelszivodosTekton(int id, int koordinataX, int koordinataY){
        super(id, koordinataX, koordinataY);
        szamlalo = 0;
        System.out.println("Létrejött egy FelszivodosTekton");
    }

    @Override
    public Tekton klonoz(int ujID, int ujX, int ujY) {
        return new FelszivodosTekton(ujID, ujX, ujY);
    }

    /**
     * Visszaadja a számláló értékét
     * @return a számláló értéke
     */
    public int getSzamlalo(){
        return szamlalo;
    }

    /**
     * Kifejti a Tekton hatását, azaz egy gombafonal adott idő után felszívódik
     * @param gomba a gomba, amelyiken a hatást kifejti
     */
    @Override
    public void hatasKifejtes(Gomba gomba){
        szamlalo--;
        parancsFeldolgozo.print("Tekton (" + this.getID() + ") szamlalo értéke megváltozott: " + szamlalo+1 + " -> " + szamlalo);
        if (szamlalo==0){
            for (Gombafonal fonal : gomba.getGombafonalak()) {
                if (fonal.getHatar1() == this || fonal.getHatar2() == this) {
                    gomba.removeFonal(fonal);
                    parancsFeldolgozo.print("Tekton(" + this.getID() +") hatására Fonal (" + fonal.getId() + ") eltűnt");
                }
            }
        }
    }
}