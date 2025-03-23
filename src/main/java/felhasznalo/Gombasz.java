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
    /**
     * Megállapítja melyik gombához tartozik a kilőtt gombafonal
     * @param fonal a fonal amihez tulajdonos kerestetik
     * @return a keresett gomba / null, ha nincs találat
     */
    public Gomba melyikGomba(Gombafonal fonal) {
        System.out.println(">Gombasz->melyikGomba()");
        for (Gomba g : gombak) {
            /*
            if (g.vannekiilyenfonala(fonal)) {
                return g;
            }*/
        } return null;
    }
    /**
     * Gombák létrehozása
     * @param t1 a tekton amire a létrheozás megvalósítandó
     */
    public void gombaLetrehozas(Tekton t1) {
        System.out.println(">Gombasz->gombaLetrehozas()");
    }
    /**
     * Gombafonal növesztése két tekton között
     * @param t1 egyik tekton
     * @param t2 másik tekton
     * @note Ha ugyanarra a tektonra akarja elhelyezni, akkor t2 lehet null, vagy t1=t2
     */
    public void fonalNovesztes(Tekton t1, Tekton t2) {
        System.out.println(">Gombasz->FonalNovesztes()");
    }
    /**
     * Spóra lövése egy kiválasztott tektonra
     * @param t1 a tekton amire a spóra kerül
     */
    public void sporaLoves(Tekton t1) {
        System.out.println(">Gombasz->sporaLoves()");
    }

    /**
     * Gombák listájának lekérdezése
     * @return gombák listája
     */
    public List<Gomba> getGombak() {
        return gombak;
    }
}
