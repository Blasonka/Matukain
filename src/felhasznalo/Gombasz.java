package felhasznalo;

import gomba.Gomba;
import tekton.Tekton;
import gomba.Gombafonal;

import java.util.ArrayList;
import java.util.List;

/**
 * Gombász osztály
 *
 * @class Gombasz
 *
 * @brief A gombászokat reprezentáló osztály
 *
 * @details
 * Gombász osztály, mely a felhasználó osztályból származik.
 * Az osztály feladata a gombász játékos gombáinak tárolása, azokkal műveletek végzése.
 *
 * @see felhasznalo.Felhasznalo
 * @see gomba.Gomba
 *
 * @note Szkeleton állapotban van, a metódusok nincsenek teljesen implementálva.
 *
 * @author todortoth
 * @version 1.0
 * @version 1.1 - comment Update
 * @date 2025-03-22
 */
public class Gombasz extends Felhasznalo {
    /**
     * Gombász gombáit tároló lista
     * @var List<Gomba> gombak
     * @brief gombák listája
     */
    List<Gomba> gombak;
    /**
     * Gombasz konstruktora
     * @param p a felhasználó pontjainak száma
     * @param a a felhasználó alap akciópontjainak száma
     * @param h a felhasználó körben felhasználható akciópontjainak száma
     */
    public Gombasz(int p, int a, int h) {
        super(p, a, h);
        gombak = new ArrayList<>();
        System.out.println(">Gombasz->Gombasz()");
    }
    public Gomba melyikGomba(Gombafonal fonal) {
        return gombak.get(gombak.indexOf(fonal));
    }

    /**
     * Gombák létrehozása
     * @param t1 a tekton amire a létrheozás megvalósítandó
     */
    public void gombaLetrehozas(Tekton t1) {

    }
}
