package tekton;

import gomba.Gomba;
import gomba.Gombafonal;

import java.util.ArrayList;
import java.util.List;

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
 * @note Prototípus állapotban van, a grafikus részek nincsenek implementálva.
 *
 * @author Monostori
 * @version 2.0
 * @date 2025-04-27
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
        szamlalo = 2;
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
        parancsFeldolgozo.print("Tekton (" + this.getID() + ") szamlalo értéke megváltozott: " + (szamlalo+1) + " -> " + szamlalo + "\n");
        if (szamlalo==0){
            List<Gombafonal> fonalak = new ArrayList<Gombafonal>();
            for (Gombafonal fonal : gomba.getGombafonalak()) {
                if (fonal.getHatar1() == this || fonal.getHatar2() == this) {
                    fonalak.add(fonal);
                    parancsFeldolgozo.print("Tekton(" + this.getID() +") hatására Fonal (" + fonal.getID() + ") eltűnt" + "\n");
                }
            } for (Gombafonal fonal : fonalak) {
                gomba.removeFonal(fonal);
            } szamlalo = 2;
        }
    }
}