package tekton;

import gomba.Gomba;
import gomba.Gombafonal;

import java.util.ArrayList;
import java.util.List;

import static tesztelo.Menu.parancsFeldolgozo;

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
 * @note Prototípus állapotban van, a grafikus részek nincsenek implementálva.
 *
 * @author Monostori
 * @version 2.0
 * @date 2025-04-27
 */
public class MaxEgyFonalTekton extends Tekton{
    Gombafonal fonal;
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
        if (gomba.getGombafonalak().size()!=0){
            List<Gombafonal> fonalak = new ArrayList<>();
            for (int i = 0; i < gomba.getGombafonalak().size(); i++){
                if (fonal == null) {
                    fonal = gomba.getGombafonalak().get(i);
                } else {
                    fonalak.add(gomba.getGombafonalak().get(i));
                    parancsFeldolgozo.print("Fonal (" + gomba.getGombafonalak().get(i).getID() + ") NEM sikerült elhelyezni\n");
                }
            }
            for (Gombafonal fonal1 : fonalak) {
                gomba.removeFonal(fonal1);
            }
        }
    }

    @Override
    public Tekton klonoz(int ujID, int ujX, int ujY) {
        return new MaxEgyFonalTekton(ujID, ujX, ujY);
    }
}