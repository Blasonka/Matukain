package felhasznalo;

import gomba.Gombafonal;
import rovar.Rovar;
import spora.Spora;
import tekton.Tekton;

import java.util.ArrayList;
import java.util.List;

/**
 * Rovarász osztály
 *
 * @class Rovarasz
 *
 * @brief A rovarászokat reprezentáló osztály
 *
 * @details
 *  Rovarasz osztály, mely a felhasználó osztályból származik.
 *  Az osztály feladata a rovarász játékos rovarjainak tárolása, azokkal műveletek végzése.
 *
 * @see felhasznalo.Felhasznalo
 * @see rovar.Rovar
 *
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author todortoth
 * @version 1.0
 * @version 1.1 - comment Update
 * @date 2025-03-22
 */
public class Rovarasz extends Felhasznalo {
    /**
     * Rovarász rovarait tároló lista
     * @var List<Rovar> rovarak
     * @brief rovarak listája
      */
    List<Rovar> rovarak;

    /**
     * Rovarasz konstruktora
     * @param p a felhasználó pontjainak száma
     * @param a a felhasználó alap akciópontjainak száma
     * @param h a felhasználó körben felhasználható akciópontjainak száma
     */
    public Rovarasz(int p, int a, int h) {
        super(p, a, h);
        rovarak = new ArrayList<>();
        System.out.println(">Rovarasz->Rovarasz()");
    }

    /**
     * Rovar mozgatása
     * @param t2 a tekton amire mozogni fog a rovar
     */
    public void attesz(Tekton t2) {
        System.out.println(">Rovarasz->rovarMozgas(t2)");
    }

    /**
     * A rovar egy tetszőlegesen választott spórát elfogyaszt
     * @param sp1 az elfogyasztandó spóra
     */
    public void elfogyaszt(Spora sp1) {
        System.out.println(">Rovarasz->elfogyaszt(sp1)");
    }

    /**
     * A rovar egy tetszőlegesen választott fonalat megsemmisít
     * @param gf1 az elvágandó gombafonal
     */
    public void fonalElvagas(Gombafonal gf1) {
        System.out.println(">Rovarasz->fonalElvagas(gf1)");
    }
}
